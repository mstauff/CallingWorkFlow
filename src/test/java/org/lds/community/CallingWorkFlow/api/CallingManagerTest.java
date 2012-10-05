package org.lds.community.CallingWorkFlow.api;

import android.content.Context;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.apache.http.client.methods.HttpDelete;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.*;

import java.util.List;

import static org.junit.Assert.*;

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
    public void testDeleteCallingWithActiveNetwork() throws Exception {

        List<Calling> callings = TestUtils.createCallingList( db, 5, false, true );
        Calling callingToDelete = callings.get(0);
        String deleteUrl = String.format(CwfNetworkUtil.CALLING_DELETE_URL, callingToDelete.getIndividualId(), callingToDelete.getPositionId());
        TestUtils.httpMockJSONResponse("", deleteUrl, HttpDelete.METHOD_NAME);
        manager.deleteCalling( callingToDelete, context );
        Thread.sleep( 2000 );
        TestUtils.validateURLRequest( deleteUrl );
        List<CallingViewItem> callingsFromDB = db.getCallingsToDelete();
        assertTrue("Some callings were marked for deletion, but not removed", callingsFromDB.isEmpty() );

        callingsFromDB = db.getPendingCallings();
        callingToDelete = TestUtils.getCallingObjectFromList( callingsFromDB, callingToDelete.getIndividualId(), callingToDelete.getPositionId() );

        assertNull( "Calling was not removed from the db", callingToDelete );
    }

    @Test
    public void testDeleteWithNoNetwork() throws Exception {

        // test the scenario where a network cxn is not available when the user performs the delete
        List<Calling> callings = TestUtils.createCallingList( db, 5, false, true );
        Calling callingToDelete = callings.get(0);
        Robolectric.setDefaultHttpResponse( 404, "NOT FOUND" );
        manager.deleteCalling( callingToDelete, context );
        Thread.sleep( 2000 );

        // at this point the calling should be marked for deletion, but the attempt to record it on the network should
        // have failed. Validate that it's still in the db, but marked for delete
        List<CallingViewItem> callingsFromDB = db.getCallingsToDelete();
        assertFalse("No callings were marked for deletion", callingsFromDB.isEmpty());
        callingToDelete = callingsFromDB.get(0);
        assertTrue("Calling was not marked for deletion", callingToDelete.getMarkedForDelete());

        // now provide a mock for the network delete and perform the delete on the server
        String deleteUrl = String.format(CwfNetworkUtil.CALLING_DELETE_URL, callingToDelete.getIndividualId(), callingToDelete.getPositionId());
        TestUtils.httpMockJSONResponse("", deleteUrl, HttpDelete.METHOD_NAME);
        boolean deleted = manager.deleteCallingOnServer(callingToDelete);
        assertTrue("Attempt to delete from server returned false", deleted);

        TestUtils.validateURLRequest( deleteUrl );

        // should not be in the db
        callingsFromDB = db.getCallingsToDelete();
        assertTrue("There are outstanding callings to remove from db", callingsFromDB.isEmpty());
        callingsFromDB = db.getPendingCallings();
        callingToDelete = TestUtils.getCallingObjectFromList( callingsFromDB, callingToDelete.getIndividualId(), callingToDelete.getPositionId() );
        assertNull("Calling was not removed from the db", callingToDelete);

    }


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
