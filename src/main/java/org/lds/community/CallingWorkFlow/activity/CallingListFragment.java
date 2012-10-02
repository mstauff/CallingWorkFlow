package org.lds.community.CallingWorkFlow.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.lds.community.CallingWorkFlow.Adapter.CallingViewItemAdapter;
import org.lds.community.CallingWorkFlow.InternalIntents;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockListFragment;
import roboguice.inject.InjectView;
import roboguice.receiver.RoboBroadcastReceiver;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CallingListFragment extends RoboSherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @Inject
    WorkFlowDB db;


    @Inject
    CwfNetworkUtil networkUtil;

    @Inject
    CallingManager callingManager;

    @InjectView(value = R.id.addNewCallingBtn)
    Button newCallingBtn;

    RoboBroadcastReceiver syncCompleteReceiver;

    private CallingViewItemAdapter callingViewItemAdapter;
    private List<CallingViewItem> callingViewItems;
    protected boolean spinnerInitialized = false;
    private Spinner actionBarFilterSpinner;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }

    private int currentPositionInList = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newCallingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCalling(view);
            }
        });
        loadListData(null, false);
        this.setHasOptionsMenu(true);

        syncCompleteReceiver = new RoboBroadcastReceiver() {
            @Override
            protected void handleReceive(Context context, Intent intent) {
                refreshListData(false);
            }
        };
        LocalBroadcastManager.getInstance( this.getActivity() ).registerReceiver( syncCompleteReceiver, new IntentFilter(InternalIntents.SYNC_COMPLETE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance( this.getActivity() ).unregisterReceiver(syncCompleteReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.calling_list_menu,menu);

        MenuItem filterSpinner = menu.findItem(R.id.menu_item_filter);
        String[] filterOptions = getResources().getStringArray(R.array.filters_list);
        SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,filterOptions);
        actionBarFilterSpinner = new Spinner(getActivity());
        actionBarFilterSpinner.setAdapter(adapter);
        actionBarFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boolean showCompleted = false;
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        showCompleted = true;
                        break;
                }
                refreshListData( showCompleted );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        filterSpinner.setActionView(actionBarFilterSpinner);

        super.onCreateOptionsMenu(menu,inflater);
    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem menuItem){
    //    return true;
    //}

    /**
     * Right now the only filter on the list data is whether a calling is pending or complete. As we expand the
     * filters we'll need to refactor this method to take some type of filter obj. or enum rather than just a  boolean
     *
     * @param showCompleted
     */
    public void refreshListData(boolean showCompleted) {
        if( callingViewItems == null ) {
            loadListData(null, showCompleted);
        } else {
            callingViewItems.clear();
            callingViewItems.addAll(showCompleted ? db.getCompletedCallings() : db.getPendingCallings());
            this.callingViewItemAdapter.notifyDataSetChanged();
        }

    }

    private void loadListData(List<CallingViewItem> listItems, boolean showCompleted) {
        if (listItems == null || listItems.size() == 0) {
            callingViewItems = db.getCallings(false);
        } else {
            callingViewItems = listItems;
        }
        this.callingViewItemAdapter = new CallingViewItemAdapter(getActivity(), android.R.layout.simple_list_item_2, callingViewItems);
        setListAdapter(callingViewItemAdapter);
        ListView listView = getListView();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                displayStatusPopup(position);
                return true;
            }
        });
    }

    private void displayStatusPopup(final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.calling_status_popup, null);

        final Spinner statusSpinner = (Spinner) popupView.findViewById(R.id.calling_status_spinner);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if( spinnerInitialized ) {
                    String selectedItem = (String) statusSpinner.getSelectedItem();
                    CallingViewItem callingViewItem = callingViewItems.get(position);
                    callingViewItem.setStatusName(selectedItem);
                    callingManager.saveCalling(callingViewItem, getActivity());
                    popupWindow.dismiss();
                    spinnerInitialized = false;
                    callingViewItems.set(position, callingViewItem);
                    callingViewItemAdapter.notifyDataSetChanged();
                } else {
                    spinnerInitialized = true;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        List<WorkFlowStatus> statusList = db.getWorkFlowStatuses();
        List<CharSequence> statusOptions = new ArrayList<CharSequence>();
        for (WorkFlowStatus s : statusList) {
            statusOptions.add(s.getStatusName());
        }
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, statusOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerAdapter);
        statusSpinner.setSelection( spinnerAdapter.getPosition( callingViewItems.get( position ).getStatusName() ));

        popupWindow.showAtLocation(getListView(), 1, 0, 0);
        statusSpinner.performClick();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CallingViewItem callingViewItem = callingViewItems.get(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(CallingDetailFragment.CALLING, callingViewItem);

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return getLayoutInflater(savedInstanceState).inflate(R.layout.callingworkflow_list, container);
    }

    public void addNewCalling(View v){
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean complete = false;
        if( actionBarFilterSpinner != null ) {
            complete = actionBarFilterSpinner.getSelectedItemPosition() == 1;
        }
        refreshListData(complete);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }
}