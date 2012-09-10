package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentProvider;
import android.content.ContentValues;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 9/6/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DomainClassTests {

   public Member memberMock;


    @Test
    public void test_WorkFlowDB_GetWhereForColumns() throws Exception {
        assertEquals( "getWhereForColumns broken with single column", "individualId=?", WorkFlowDB.getWhereForColumns("individualId" ) );
        assertEquals( "getWhereForColumns broken for multiple columns", "individualId=?, positionId=?", WorkFlowDB.getWhereForColumns("individualId", "positionId" ) );
    }

    @Test
    public void test_Member_getAllKeys(){
        Member member=new Member();
        String[] keyList=member.getAllKeys();
        Assert.assertTrue("", keyList.length >= 1);
    }
    
    @Test
    public void test_Calling_GelAllKeys(){
        Calling calling=new Calling();
        String[] keyList=calling.getAllKeys();
        Assert.assertTrue("", keyList.length >= 1);
    }

    @Test
    public void test_Position_GelAllKeys(){
        Position position=new Position();
        String[] keyList=position.getAllKeys();
        Assert.assertTrue("", keyList.length >= 1);
    }

    @Test
    public void test_WorkFlowStatus_GelAllKeys(){
        WorkFlowStatus wfStatus=new WorkFlowStatus();
        String[] keyList=wfStatus.getAllKeys();
        Assert.assertTrue("", keyList.length >= 1);
    }


}
