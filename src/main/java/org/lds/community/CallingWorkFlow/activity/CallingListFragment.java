package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.fragment.RoboListFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.List;

public class CallingListFragment extends RoboListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @Inject
    WorkFlowDB db;

    @Inject
    CwfNetworkUtil networkUtil;

    @InjectView(value = R.id.addNewCallingBtn)
    Button newCallingBtn;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }
    private int currentPositionInList = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<CallingViewItem> CallingViewItems = db.getCallings(false);
		String[] values = new String[CallingViewItems.size()];
		for(int i=0; i < CallingViewItems.size(); i++) {
			values[i] = CallingViewItems.get(i).getPositionName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

        newCallingBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCalling( view );
            }
        });

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data
	}

    public void addNewCalling(View v){
         Intent intent = new Intent(getActivity(),DetailActivity.class);
         startActivity(intent);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
	    return getLayoutInflater( savedInstanceState ).inflate( R.layout.callingworkflow_list, container );
    }
	/*
	@Override
	public void onPause() {
		super.onPause();
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
		//outState.putInt("LIST_POS", getListView().getFirstVisiblePosition());
    }

    public void selectPosition(int position, long id) {
        currentPositionInList = position;
    }
    */

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