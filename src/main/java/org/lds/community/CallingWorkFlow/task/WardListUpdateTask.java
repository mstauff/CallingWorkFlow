package org.lds.community.CallingWorkFlow.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.api.ServiceException;
import org.lds.community.CallingWorkFlow.domain.*;
import roboguice.util.RoboAsyncTask;

import javax.inject.Inject;
import java.util.List;

//import org.lds.ldstools.Prefs;
//import org.lds.ldstools.activity.SetupLoginActivity;
//import org.lds.ldstools.api.NetworkUtilities;
//import org.lds.ldstools.api.ServiceError;
//import org.lds.ldstools.api.ServiceException;

public class WardListUpdateTask extends RoboAsyncTask{
    @Inject
    private SharedPreferences preferences;

    @Inject
    private CwfNetworkUtil networkUtil;

    @Inject
    private WorkFlowDB db;

    private ServiceException.ServiceError serviceError = ServiceException.ServiceError.NONE;
    private Handler handler;

    public  WardListUpdateTask(Context context) {
        super(context);
    }

/*
    @Override
    protected Void doInBackground(Void... unused) {
        List<Member> members = networkUtil.getWardList();
        if( !members.isEmpty() ) {
            db.updateWardList( members );
        }
        return null;
    }
*/

    protected void onPostExecute() {
    }

    @Override
    public Void call() throws Exception {
        if( !db.hasData(Member.TABLE_NAME) ) {
            List<Member> members = networkUtil.getWardList();
            if( !members.isEmpty() ) {
                db.updateWardList( members );
            }
        }

        if ( !db.hasData(Position.TABLE_NAME) ) {
            List<Position> positions = networkUtil.getPositionIds();
            if( !positions.isEmpty() ) {
                db.updatePositions(positions);
            }
        }

        if ( !db.hasData(WorkFlowStatus.TABLE_NAME) ) {
            List<WorkFlowStatus> statuses = networkUtil.getStatuses();
            if( !statuses.isEmpty() ) {
                db.updateWorkFlowStatus(statuses);
            }
        }

        if ( !db.hasData(Calling.TABLE_NAME) ) {
            List<Calling> callings = networkUtil.getPendingCallings();
            if( !callings.isEmpty() ) {
                db.updateCallings( callings );
            }
        }

        return null;
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

