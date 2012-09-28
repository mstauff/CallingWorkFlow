package org.lds.community.CallingWorkFlow.api;

import android.content.Context;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(InjectedTestRunner.class)
public class CallingManagerTest {

    @Inject
    CallingManager manager;

    @Inject
    Context context;

    @Inject
    WorkFlowDB db;

    @Inject
    private CwfNetworkUtil networkUtil;

    @Before
    public void setUp() throws Exception {

        assertNotNull( "Injection of context failed", context );
        assertNotNull( "Injection of calling manager failed", manager );
    }
/*
    @After
    public void tearDown() throws Exception {
        for(Calling calling : existingCallings ) {
            networkUtil.updateCalling(calling);
        }
    }
*/


    @Test
    public void testUpdateCalling() throws Exception {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        List<Member> members = TestUtils.createMembersDB(db);
        List<Position> positions = TestUtils.createPositionDB(db);
        List<WorkFlowStatus> statuses = TestUtils.createStatusDB(db);

        WorkFlowStatus status = null;
        for( WorkFlowStatus curStatus : statuses ) {
            if( !curStatus.getComplete() ) {
                status = curStatus;
                break;
            }
        }
        assertNotNull("No pending statuses in the list, cannot continue", status);

        Calling calling = new Calling( members.get(0).getIndividualId(), positions.get(0).getPositionId(),
                status.getStatusName(), 0, 0, false );
        manager.saveCalling( calling, context );
        List<CallingViewItem> callings = db.getPendingCallings();
        Calling callingFromList = TestUtils.getCallingObjectFromList( callings, calling.getIndividualId(), calling.getPositionId() );
        assertNotNull( "Calling was not retrieved from the db", callingFromList );
        TestUtils.assertEntityEquals(calling, callingFromList, "Calling was not saved in db");
        Thread.sleep( 2000 );
        callings = db.getPendingCallings();
        callingFromList = TestUtils.getCallingObjectFromList( callings, calling.getIndividualId(), calling.getPositionId() );
        TestUtils.assertEntityEquals(calling, callingFromList, "Calling was not persisted to cloud");

        networkUtil.deleteCalling( calling );
    }
}
