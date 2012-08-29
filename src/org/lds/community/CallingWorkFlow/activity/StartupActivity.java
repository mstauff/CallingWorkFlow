package org.lds.community.CallingWorkFlow.activity;

import android.os.Bundle;
import org.lds.community.CallingWorkFlow.CallingWorkFlow;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.task.StartupTask;
import roboguice.activity.RoboActivity;

public class StartupActivity extends RoboActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* We do not have a startup/splash screen yet */
        setContentView(R.layout.main);

        new StartupTask(this, CallingWorkFlow.class).execute();
    }
}