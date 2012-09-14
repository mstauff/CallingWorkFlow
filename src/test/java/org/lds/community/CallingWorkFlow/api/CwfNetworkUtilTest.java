package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.domain.Calling;
import roboguice.test.RobolectricRoboTestRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricRoboTestRunner.class)
public class CwfNetworkUtilTest{

    CwfNetworkUtil networkUtil;


    @Test
    public void testUpdateCalling() throws Exception {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        networkUtil = new CwfNetworkUtil();
        Calling calling = new Calling( 5555, 22, "APPROVED", false, 0, 0, false );
        networkUtil.updateCalling( calling );
        List<Calling> callings = networkUtil.getPendingCallings();
        boolean found = false;
        for( Calling c : callings ) {

            if( c.getIndividualId() == calling.getIndividualId() && c.getPositionId() == calling.getPositionId() ) {
                found = true;
                break;
            }
        }
        assertTrue( "Calling wasn't saved", found );
        networkUtil.deleteCalling( calling );
    }
}
