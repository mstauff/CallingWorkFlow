package org.lds.community.CallingWorkFlow.api;

import com.google.inject.Inject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 9/19/12
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(InjectedTestRunner.class)
public class WorkFlowDBTest {

    @Inject WorkFlowDB db;

    List<Member> memberList= new ArrayList<Member>();
    List<Position> positionList= new ArrayList<Position>();
    List<WorkFlowStatus> statusList= new ArrayList<WorkFlowStatus>();

    @Before
    public void setup(){

        Boolean hasDataMember=db.hasData(Member.TABLE_NAME);
        if (hasDataMember==false){
            memberList= TestUtils.createMembersDB();
        }

        Boolean hasDataPositions=db.hasData(Position.TABLE_NAME);
        if (hasDataPositions==false){
            positionList=TestUtils.createPositionDB();
        }

        Boolean hasDataStatus=db.hasData(WorkFlowStatus.TABLE_NAME);
        if (hasDataStatus==false){
            statusList= TestUtils.createStatusDB();
        }
        if((hasDataMember==false) && (hasDataPositions==false) && (hasDataStatus==false)){
           TestUtils.initializeDatabase(db,memberList,positionList,statusList);
        }

    }

    @Test
    public void  WardListTest(){
        List<Member> wardList=db.getWardList();
        Assert.assertEquals("",wardList.size(),memberList.size());

    }

    @Test
    public void positionListTest(){
        List<Position> positionList= db.getPositions();
        Assert.assertEquals("",positionList.size(),positionList.size());

    }

    @Test
    public void statusListTest(){
        List<WorkFlowStatus> statusList= db.getWorkFlowStatuses();
        Assert.assertEquals("",statusList.size(),statusList.size());

    }

    @Test
    public void updateCallingTest(){
        List<Calling> callingList=new ArrayList<Calling>();

        db.updateCalling( TestUtils.createCallingObj( 40L, "SET_APART", 1111L ) );
        db.updateCalling(TestUtils.createCallingObj(41L, "SET_APART", 2222L));
        db.updateCalling(TestUtils.createCallingObj(42L, "SET_APART", 3333L));
        db.updateCalling(TestUtils.createCallingObj(43L, "PENDING", 4444L));
        db.updateCalling(TestUtils.createCallingObj(44L, "PENDING", 5555L));
        List<CallingViewItem> callingList2=db.getPendingCallings();


    }

    @Test
    public void pendingCallingTest(){
        List<Calling> callingList=new ArrayList<Calling>();

        callingList.add( TestUtils.createCallingObj( 40L, "SUBMITTED", 1111L ) );
        callingList.add(TestUtils.createCallingObj(41L, "SUBMITTED", 2222L));
        callingList.add(TestUtils.createCallingObj(42L, "SUBMITTED", 3333L));
        callingList.add(TestUtils.createCallingObj(43L, "PENDING", 4444L));
        callingList.add(TestUtils.createCallingObj(44L, "PENDING", 5555L));
        db.updateCallings(callingList);
        List<CallingViewItem> callingList2=db.getPendingCallings();

    }
}
