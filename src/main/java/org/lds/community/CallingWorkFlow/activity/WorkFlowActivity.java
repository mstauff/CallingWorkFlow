package org.lds.community.CallingWorkFlow.activity;

import android.os.Bundle;
import org.lds.community.CallingWorkFlow.CallingWorkFlow;
import org.lds.community.CallingWorkFlow.R;

public class WorkFlowActivity extends org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockFragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.workflow);
        CallingWorkFlow.startRootActivity(WorkFlowActivity.class, this); // required by all "root" activities
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
    }
}
