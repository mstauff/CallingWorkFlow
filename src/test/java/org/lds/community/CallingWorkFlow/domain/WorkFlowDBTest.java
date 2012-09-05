package org.lds.community.CallingWorkFlow.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/31/12
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkFlowDBTest {
    @Test
    public void testGetWhereForColumns() throws Exception {

        assertEquals( "getWhereForColumns broken with single column", "individualId=?", WorkFlowDB.getWhereForColumns("individualId" ) );
        assertEquals( "getWhereForColumns broken for multiple columns", "individualId=?, positionId=?", WorkFlowDB.getWhereForColumns("individualId", "positionId" ) );

    }
}
