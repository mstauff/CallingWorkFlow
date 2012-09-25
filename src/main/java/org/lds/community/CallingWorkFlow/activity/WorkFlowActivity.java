package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockFragmentActivity;

public class WorkFlowActivity extends RoboSherlockFragmentActivity {
	@Inject
	CallingListFragment callingListFragment;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.workflow);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//	    callingListFragment.spinnerInitialized = false;
    }
    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        menu.add(Menu.NONE, 0, 0, getString( R.string.pref_title));
	        return super.onCreateOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case 0:
	                startActivity(new Intent(this, SettingsActivity.class));
	                return true;
	        }
	        return false;
	    }

}