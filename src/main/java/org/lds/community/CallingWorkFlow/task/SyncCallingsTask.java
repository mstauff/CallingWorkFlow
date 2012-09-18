package org.lds.community.CallingWorkFlow.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.RoboGuice;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class SyncCallingsTask extends AsyncTask<String, String, Void> {
    @Inject
    private SharedPreferences preferences;

    @Inject
    private CallingManager callingMgr;

    @Inject
    private WorkFlowDB db;

    private List<String> successes = new ArrayList<String>();
    private List<String> failures = new ArrayList<String>();
    private String successString;
    private String failureString;

    private static final String TAG = SyncCallingsTask.class.getSimpleName();

    public SyncCallingsTask(Context context) {
        RoboGuice.getInjector( context ).injectMembers(this);
    }

    @Override
    protected Void doInBackground(String... strings) {
        successString = strings[0];
        failureString = strings[1];

        List<CallingViewItem> callingsToSync = db.getCallingsToSync();
        String updateMsg = "";
        String baseMsgString;
        List<String> successOrFailList;
        for( CallingViewItem calling : callingsToSync ) {
            boolean success = callingMgr.updateCallingOnServer( calling.getCalling() );
            if( success ) {
                baseMsgString = successString;
                successOrFailList = successes;
            } else {
                baseMsgString = failureString;
                successOrFailList = failures;
            }
            updateMsg = String.format( baseMsgString, calling.getFirstName() + " " + calling.getLastName(), calling.getPositionName() );
            successOrFailList.add( updateMsg );
            publishProgress( updateMsg );

        }

        if( failures.isEmpty() ) {
            callingMgr.refreshCallingsFromServer();
            // todo
            // publishProgress()
        }
        return null;
    }

    protected void onProgressUpdate(String updateString) {
        // print out the string
        Log.d( TAG, "Progress update: " + updateString );

    }

    protected void onPreExecute() {
        // create the status update area

    }

    protected void onPostExecute() {
        // todo - show success/complete messages
        Log.d( TAG, "Post Execute" );

    }
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

