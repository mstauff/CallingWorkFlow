package org.lds.community.CallingWorkFlow.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import roboguice.RoboGuice;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class SyncCallingsTask extends AsyncTask<String, String, Void> {

    @Inject
    private CallingManager callingMgr;

    @Inject
    private WorkFlowDB db;

    private ProgressDialog progressDialog;
    private Context context;

    private List<String> successes = new ArrayList<String>();
    private List<String> failures = new ArrayList<String>();
    private boolean noChanges = true;

    private static final String TAG = SyncCallingsTask.class.getSimpleName();

    public SyncCallingsTask(Context context) {
        RoboGuice.getInjector( context ).injectMembers(this);
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String successString = strings[0];
        String failureString = strings[1];
        String downloadString = strings[2];

        List<CallingViewItem> callingsToSync = db.getCallingsToSync();
        noChanges = callingsToSync.isEmpty();
        String updateMsg = "";
        String baseMsgString;
        List<String> successOrFailList;
        if( noChanges ) {
            // todo - need a new string to display here
        } else {
            for( CallingViewItem calling : callingsToSync ) {
                boolean success = callingMgr.updateCallingOnServer( calling );
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
        }

        callingsToSync = db.getCallingsToDelete();
        noChanges = callingsToSync.isEmpty();
        if( noChanges ) {
            // todo - display
        } else {
            for( CallingViewItem calling : callingsToSync ) {
                boolean success = callingMgr.deleteCallingOnServer( calling );
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
        }

        if( failures.isEmpty() ) {
            publishProgress( downloadString );
            callingMgr.refreshCallingsFromServer();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... updateString) {
        // print out the string
        Log.d( TAG, "Progress update: " + updateString );
        progressDialog.setMessage( updateString[0] );
    }

    @Override
    protected void onPreExecute() {
        // create the status update area
        if( progressDialog == null ) {
            progressDialog = ProgressDialog.show( context, context.getString(R.string.pref_sync_callings_now_title),"");
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        Log.d( TAG, "Post Execute" );

        if( progressDialog != null && progressDialog.isShowing() ) {
            StringBuilder result = new StringBuilder();
//            if( (context.getString( R.string.calling_update_success_summary, successes.size() ));
            if( !failures.isEmpty() ) {
                result.append(context.getString( R.string.calling_update_failure_summary, failures.size() ));
            }

            progressDialog.setMessage( result.toString() );
            // delay dismissal 2 seconds to allow user time to read error or success
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }}, 2000);
        }
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

