package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import roboguice.activity.RoboActivity;

public class StartupActivity extends RoboActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* We do not have a startup/splash screen yet */
	    Intent intent = new Intent(this, WorkFlowActivity.class);
	    startActivity(intent);
    }
}