package org.lds.community.CallingWorkFlow.activity;

import android.os.Bundle;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.activity.RoboSherlockFragmentActivity;

public class WorkFlowActivity extends RoboSherlockFragmentActivity {
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
    }
}