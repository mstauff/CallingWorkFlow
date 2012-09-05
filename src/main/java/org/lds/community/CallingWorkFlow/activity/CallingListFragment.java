package org.lds.community.CallingWorkFlow.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.fragment.RoboListFragment;

import javax.inject.Inject;

public class CallingListFragment extends RoboListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    WorkFlowDB db;

    @Inject
    CwfNetworkUtil networkUtil;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }
    private int currentPositionInList = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	// Do something with the data

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
    private CursorAdapter listAdapter;

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
	}

	@Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        listAdapter.changeCursor(null);
    }
}