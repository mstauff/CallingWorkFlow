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

public class WardListUpdateTask extends RoboAsyncTask{
    @Inject
    private SharedPreferences preferences;

    @Inject
    private CwfNetworkUtil networkUtil;

    @Inject
    private WorkFlowDB db;

    boolean forceUpdate;

    private ServiceException.ServiceError serviceError = ServiceException.ServiceError.NONE;
    private Handler handler;

    public  WardListUpdateTask(Context context, boolean forceUpdate) {
        super(context);
        this.forceUpdate = forceUpdate;
    }

    protected void onPostExecute() {
    }

    @Override
    public Void call() throws Exception {
        if( forceUpdate || !db.hasData(Member.TABLE_NAME) ) {
            List<Member> members = networkUtil.getWardList();
            if( !members.isEmpty() ) {
                db.updateWardList( members );
            }
        }

        if ( forceUpdate || !db.hasData(Position.TABLE_NAME) ) {
            List<Position> positions = networkUtil.getPositionIds();
            if( !positions.isEmpty() ) {
                db.updatePositions(positions);
            }
        }

        if ( forceUpdate || !db.hasData(WorkFlowStatus.TABLE_NAME) ) {
            List<WorkFlowStatus> statuses = networkUtil.getStatuses();
            if( !statuses.isEmpty() ) {
                db.updateWorkFlowStatus(statuses);
            }
        }

        if ( !db.hasData(Calling.TABLE_NAME) ) {
            List<Calling> callings = networkUtil.getPendingCallings();
            if( !callings.isEmpty() ) {
                db.updateCallings(callings);
            }
        }

        return null;
    }
}


