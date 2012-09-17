package org.lds.community.CallingWorkFlow.api;

import android.content.Context;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.task.UpdateCallingTask;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/30/12
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallingManager {

    @Inject
    WorkFlowDB db;

    @Inject
    CwfNetworkUtil networkUtil;

    public void saveCalling(Calling calling, Context context) {
        calling.setIsSynced( false );
        db.updateCalling( calling );
        UpdateCallingTask updateCallingTask = new UpdateCallingTask( context );
        updateCallingTask.execute( calling );
    }

    public boolean updateCallingOnServer( Calling calling ) {
        boolean success = false;
        // 1 - make the rest call to update calling
        Calling updatedCalling = networkUtil.updateCalling( calling );

        // 2 - if successful then update isSynce & save to db
        if( areCallingsEqual( calling, updatedCalling ) ) {
            calling.setIsSynced( true );
            db.updateCalling(calling);
            success = true;
        }

        // return true if was successful, false otherwise
        return success;
    }

    private boolean areCallingsEqual(Calling calling, Calling updatedCalling) {
        // for now we're just going with same indId & posId - may beef it up later
        return calling != null && updatedCalling != null
                && calling.getPositionId() == updatedCalling.getPositionId()
                && calling.getIndividualId() == updatedCalling.getIndividualId();
    }
}
