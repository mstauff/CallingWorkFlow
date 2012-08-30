package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import org.lds.community.CallingWorkFlow.R;
import roboguice.activity.RoboActivity;

public class StartupActivity extends RoboActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* We do not have a startup/splash screen yet */
        setContentView(R.layout.workflow);
	    Intent intent = new Intent(this, WorkFlowActivity.class);
	    startActivity(intent);
    }
}