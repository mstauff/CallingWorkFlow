package org.lds.community.CallingWorkFlow.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.RoboGuice;

import javax.inject.Inject;
import java.util.List;


public class UpdateCallingTask extends AsyncTask<Calling, Void, Void> {

    @Inject
    private CallingManager callingMgr;

    private static final String TAG = UpdateCallingTask.class.getSimpleName();

    public UpdateCallingTask(Context context) {
        RoboGuice.getInjector( context ).injectMembers(this);
    }

    @Override
    protected Void doInBackground(Calling... callings) {

        for(Calling calling : callings ) {
            callingMgr.updateCallingOnServer( calling );
        }

        return null;
    }

/*    protected void onProgressUpdate(String updateString) {
        // print out the string
        Log.d( TAG, "Progress update: " + updateString );

    }

    protected void onPreExecute() {
        // create the status update area

    }

    protected void onPostExecute() {
        // todo - show success/complete messages
        Log.d( TAG, "Post Execute" );

    }*/
}

/*
public class LoginTask extends AsyncTask<String, Void, Boolean> {

    @Inject
    private SharedPreferences preferences;

    @Inject
    private NetworkUtilities networkUtilities;

    private ServiceError serviceError = ServiceError.NONE;
    private Handler handler;

    public LoginTask(Context context) {
        RoboGuice.getInjector(context).injectMembers(this);
    }

    public void attach(Handler handler) {
        this.handler = handler;
    }

    public void detach() {
        handler = null;
    }

    @Override
    public Boolean doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        try {
            networkUtilities.authenticate(username, password);
        } catch (ServiceException e) {
            serviceError = e.getErrorCode();
        }

        if (serviceError == ServiceError.NONE) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Prefs.PREF_USERNAME, username);
            editor.putString(Prefs.PREF_PASSWORD, EncryptUtil.encrypt(password));
            editor.commit();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPostExecute(Boolean result) {
        if (handler == null) {
            return;
        }

        if (result) {
            handler.sendMessage(Message.obtain(handler, SetupLoginActivity.MSG_DONE));
        } else {
            handler.sendMessage(Message.obtain(handler, SetupLoginActivity.MSG_ERROR, serviceError));
        }
    }
}
*/

