package org.lds.community.CallingWorkFlow.tests.api;

import android.test.AndroidTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.api.CwfNetworkUtil;
import org.lds.community.CallingWorkFlow.domain.Calling;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CwfNetworkUtilTest extends AndroidTestCase {

    CwfNetworkUtil networkUtil;
    List<Calling> existingCallings;

    @Before
    public void setUp() throws Exception {
        networkUtil = new CwfNetworkUtil();
        existingCallings = networkUtil.getPendingCallings();
        for(Calling calling : existingCallings ) {
            networkUtil.deleteCalling( calling );
        }
    }

    @After
    public void tearDown() throws Exception {
        for(Calling calling : existingCallings ) {
            networkUtil.updateCalling(calling);
        }
    }


    @Test
    public void testUpdateCalling() throws Exception {
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
