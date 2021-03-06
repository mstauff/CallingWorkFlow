package org.lds.community.CallingWorkFlow.api;

import android.content.Context;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.task.UpdateCallingTask;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static org.lds.community.CallingWorkFlow.task.UpdateCallingTask.CallingUpdateType;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/30/12
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class CallingManager {

    @Inject
    WorkFlowDB db;

    @Inject
    CwfNetworkUtil networkUtil;

    List<CallingViewItem> currentViewCallingList;

    public List<CallingViewItem> getCurrentViewCallingList() {
        return currentViewCallingList;
    }

    public void setCurrentViewCallingList(List<CallingViewItem> currentViewCallingList) {
        this.currentViewCallingList = currentViewCallingList;
    }

    public void saveCalling(Calling calling, Context context) {
        calling.setIsSynced(false);
        db.updateCalling(calling);
        updateInBackground(context, CallingUpdateType.UPDATE, calling);
    }

    public void saveCallings(List<Calling> callings, Context context) {
        for( Calling calling : callings ) {
            calling.setIsSynced( false );
            db.updateCalling( calling );
        }
        updateInBackground(context, CallingUpdateType.UPDATE, callings.toArray(new Calling[ callings.size() ]));
    }

    private void updateInBackground(Context context, CallingUpdateType updateType, Calling... callings) {
        UpdateCallingTask updateCallingTask = new UpdateCallingTask( context, updateType);
        updateCallingTask.execute( callings );
    }

    public void deleteCalling(Calling calling, Context context) {
        calling.setMarkedForDelete( true );
        db.updateCalling( calling );
        updateInBackground(context, CallingUpdateType.DELETE, calling);
    }

    public void deleteCallings(List<Calling> callings, Context context) {
        for (Calling calling : callings) {
            calling.setMarkedForDelete( true );
            db.updateCalling( calling );
        }
        updateInBackground(context, CallingUpdateType.DELETE, callings.toArray(new Calling[ callings.size() ]));
    }

    public boolean deleteCallingOnServer( Calling calling ) {
        boolean success = false;
        // 1 - make the rest call to update calling
        // 2 - if successful then update isSynce & save to db
        if( networkUtil.deleteCalling( calling ) ) {
            db.deleteCalling(calling);
            success = true;
        }

        // return true if was successful, false otherwise
        return success;
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

    public void refreshCallingsFromServer() {
        List<Calling> allCallings = networkUtil.getCallings();
        for( Calling calling : allCallings ) {
            calling.setIsSynced( true );
        }
        db.updateCallings( allCallings );
    }

    public static boolean areCallingsEqual(Calling calling, Calling updatedCalling) {
        // for now we're just going with same indId & posId - may beef it up later
        return calling != null && updatedCalling != null
                && calling.getPositionId() == updatedCalling.getPositionId()
                && calling.getIndividualId() == updatedCalling.getIndividualId();
    }
}
