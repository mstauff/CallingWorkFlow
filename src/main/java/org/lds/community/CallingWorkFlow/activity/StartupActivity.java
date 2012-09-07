package org.lds.community.CallingWorkFlow.activity;

import android.content.Intent;
import android.os.Bundle;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.task.WardListUpdateTask;
import roboguice.activity.RoboActivity;

import javax.inject.Inject;

public class StartupActivity extends RoboActivity {

    @Inject
    WorkFlowDB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        new WardListUpdateTask(getApplicationContext(), false).execute();
	    /* We do not have a startup/splash screen yet */
	    Intent intent = new Intent(this, WorkFlowActivity.class);
	    startActivity(intent);
    }
}