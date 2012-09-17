package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.domain.Calling;

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
@RunWith(InjectedTestRunner.class)
public class CwfNetworkUtilTest{

    @Inject
    CwfNetworkUtil networkUtil;


    @Test
    public void testUpdateCalling() throws Exception {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        Calling calling = new Calling( 5555, 22, "APPROVED", 0, 0, false );
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
