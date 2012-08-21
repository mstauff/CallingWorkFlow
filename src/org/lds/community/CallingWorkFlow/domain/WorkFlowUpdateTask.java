package org.lds.community.CallingWorkFlow.domain;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;

import javax.inject.Inject;
import java.util.List;

//import org.lds.ldstools.Prefs;
//import org.lds.ldstools.activity.SetupLoginActivity;
//import org.lds.ldstools.api.NetworkUtilities;
//import org.lds.ldstools.api.ServiceError;
//import org.lds.ldstools.api.ServiceException;

public class WorkFlowUpdateTask extends AsyncTask<String, Void, List<Member>> {
    @Inject
    private SharedPreferences preferences;

/*
    @Inject
    private NetworkUtilities networkUtilities;

    private ServiceError serviceError = ServiceError.NONE;
*/
    private Handler handler;
    @Override
    protected List<Member> doInBackground(String... strings) {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void onPostExecute(List<Member> members) {

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

