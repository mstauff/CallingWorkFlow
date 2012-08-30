package org.lds.community.CallingWorkFlow.activity;

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
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockListFragment;

import javax.inject.Inject;

public class CallingListFragment  extends RoboSherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    WorkFlowDB db;

    @Inject
    CwfNetworkUtil networkUtil;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    public void updateCallingList(View view) {
    }
    public void addNewCalling(View view) {
    }
    public void removeCalling(View view) {
    }
    private int currentPositionInList = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.id.workflow_listview_layout, container, false);
    }

	@Override
	public void onPause() {

	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
		outState.putInt("LIST_POS", getListView().getFirstVisiblePosition());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectPosition(position, id);
    }

    public void selectPosition(int position, long id) {
        currentPositionInList = position;
    }

    private CursorAdapter listAdapter;

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return null;
	}

	@Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor data) {
        setSelection(currentPositionInList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        listAdapter.changeCursor(null);
    }
}