package org.lds.community.CallingWorkFlow.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockListFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;

public class CallingListFragment  extends RoboSherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    WorkFlowDB db;
    /** Called when the activity is first created. */
    @InjectView(R.id.callingsList)
    ListView callingsListView;

    @Inject
    CwfNetworkUtil networkUtil;


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if( parent.getId() == R.id.callingGroupsSpinner ) {
            // lookup new class
            //ListView classMembersList = (ListView) findViewById( R.id.classMembersList );
        }
    }
    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public void updateCallingList(View view) {

    }
    public void addNewCalling(View view) {

    }
    public void removeCalling(View view) {

    }

    @Inject
    private SharedPreferences preferences;

    private boolean dualFragments = false;
    private int currentPositionInList = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.id.callingsList, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
	    /*
        dualFragments = getActivity().findViewById(R.id.callings_position_list) != null;

        if (dualFragments) {
            // set selection mode
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); // Highlight the selected item

            // setup the right edge border
            getActivity().findViewById(R.id.callings_listview_layout).setBackgroundResource(R.drawable.listview_dual_background);
        } else {
            // set selection mode
            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);

            getActivity().findViewById(R.id.callings_listview_layout).setBackgroundDrawable(null);
        }
        */
        if (savedState != null) {
            currentPositionInList = savedState.getInt("LIST_POS");
        }

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (dualFragments) {
            outState.putInt("LIST_POS", currentPositionInList);
        } else {
            outState.putInt("LIST_POS", getListView().getFirstVisiblePosition());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectPosition(position, id);
    }

    public void selectPosition(int position, long id) {
        // Only if we're showing both fragments should the item be "highlighted"
        if (dualFragments) {
            ListView lv = getListView();
            lv.setItemChecked(position, true);
            id = getCheckedItemID(id);
            //CallingsPositionListFragment positionsFragment = (CallingsPositionListFragment) getFragmentManager().findFragmentById(R.id.callings_position_list);
            //positionsFragment.loadPositionList(id);
        } else {
            //Intent callingsListIntent = new Intent(getActivity(), CallingsPositionsActivity.class);
            //callingsListIntent.putExtra(CallingsPositionListFragment.EXTRA_LEADER_GROUP, id);
           // startActivity(callingsListIntent);
        }
        currentPositionInList = position;
    }

    private long getCheckedItemID(long defaultID) {
        long[] ids = getListView().getCheckItemIds();
        if (ids.length > 0) {
            return ids[0];
        }

        return defaultID;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.callings_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (unitMenu.onOptionsItemSelected(item)) {
            updateUnit();
            getLoaderManager().restartLoader(0, null, this);
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_item_unit:
                unitMenu.addSubMenuUnits(item.getSubMenu());
                return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    private CursorAdapter listAdapter;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null; //new CallingLoader(getActivity(), unitLeadershipGroupManager, preferences);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor data) {
        /*
	    if (listAdapter == null) {
            listAdapter = new LeaderGroupListAdapter(getActivity(), data);
            setListAdapter(listAdapter);
        } else {
            listAdapter.changeCursor(data);
        }

        if (dualFragments) {
            selectPosition(currentPositionInList, -1);
        }
        */
        setSelection(currentPositionInList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        listAdapter.changeCursor(null);
    }

    /*
	public static class CallingLoader extends CursorLoader {

        private SharedPreferences preferences;
        private UnitLeadershipGroupManager manager;

        public CallingLoader(Context context, UnitLeadershipGroupManager manager, SharedPreferences preferences) {
            super(context);
            this.preferences = preferences;
            this.manager = manager;
        }

        @Override
        public Cursor loadInBackground() {
            long unitID = preferences.getLong(Prefs.PREF_CURRENT_UNIT_ID, 0);
            return manager.findAllByUnit(unitID);
        }
    }
	*/
}
