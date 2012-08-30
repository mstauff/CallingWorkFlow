package org.lds.community.CallingWorkFlow.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import roboguice.RoboGuice;

public class StartupTask extends AsyncTask<Void, Void, Boolean> {

    private Activity contextActivity;
    private Class startupActivityClass;


    public StartupTask(Activity contextActivity, Class startupActivityClass) {
        RoboGuice.getInjector(contextActivity).injectMembers(this);
        this.contextActivity = contextActivity;
        this.startupActivityClass = startupActivityClass;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //check preference and start CallerId on App-startup.
        //Most applicable on reinstall (debug only?)
        //new BroadcastReceiver().onReceive(contextActivity, null);
        return true;
    }
	@Override
    public void onPostExecute(Boolean result) {
        Intent startIntent = new Intent(contextActivity, startupActivityClass);

        // start initial UI
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        contextActivity.startActivity(startIntent);
    }
}