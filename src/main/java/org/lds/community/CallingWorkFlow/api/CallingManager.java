package org.lds.community.CallingWorkFlow.api;

import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;

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

    public boolean updateCallingOnServer( Calling calling ) {
        // todo - perform business logic:
        // 1 - make the rest call to update calling
        // 2 - if successful then update isSynce & save to db
        // return true if was successful, false otherwise

        return true;

    }
}
