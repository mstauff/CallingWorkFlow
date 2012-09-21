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
    public void getCompletedCallingsTest(){

        db.updateCalling( TestUtils.createCallingObj( 40L, "SET_APART", 1111L,false));
        db.updateCalling(TestUtils.createCallingObj(41L, "SET_APART", 2222L,false));
        db.updateCalling(TestUtils.createCallingObj(42L, "SET_APART", 3333L,false));
        db.updateCalling(TestUtils.createCallingObj(43L, "PENDING", 4444L,false));
        db.updateCalling(TestUtils.createCallingObj(44L, "PENDING", 5555L,false));
        List<CallingViewItem> callingList2=db.getCompletedCallings();
        Assert.assertEquals("Did not return 3 callings",callingList2.size(),3);
    }

    @Test
    public void getCompletedDuplicateCallingsTest(){
        db.updateCalling(TestUtils.createCallingObj( 40L, "SET_APART", 1111L,false));
        db.updateCalling(TestUtils.createCallingObj(41L, "SET_APART", 2222L,false));
        db.updateCalling(TestUtils.createCallingObj(42L, "SET_APART", 3333L,false));
        db.updateCalling(TestUtils.createCallingObj(43L, "PENDING", 4444L,false));
        db.updateCalling(TestUtils.createCallingObj(44L, "PENDING", 5555L,false));

        List<CallingViewItem> callingList=db.getCompletedCallings();
        Assert.assertEquals("Did not return 3 callings",callingList.size(),3);

        db.updateCalling(TestUtils.createCallingObj(40L, "SET_APART", 1111L,false));
        db.updateCalling(TestUtils.createCallingObj(41L, "SET_APART", 2222L,false));
        db.updateCalling(TestUtils.createCallingObj(42L, "SET_APART", 3333L,false));
        db.updateCalling(TestUtils.createCallingObj(43L, "PENDING", 4444L,false));
        db.updateCalling(TestUtils.createCallingObj(44L, "PENDING", 5555L,false));

        List<CallingViewItem> callingList2=db.getCompletedCallings();

        Assert.assertEquals("Did return duplicate callings",callingList2.size(),3);
    }

    @Test
    public void getPendingCallingsTest(){
        List<Calling> callingList=new ArrayList<Calling>();

        callingList.add(TestUtils.createCallingObj(40L, "SUBMITTED", 1111L,false));
        callingList.add(TestUtils.createCallingObj(41L, "SUBMITTED", 2222L,false));
        callingList.add(TestUtils.createCallingObj(42L, "SUBMITTED", 3333L,false));
        callingList.add(TestUtils.createCallingObj(43L, "PENDING", 4444L,false));
        callingList.add(TestUtils.createCallingObj(44L, "PENDING", 5555L,false));
        callingList.add(TestUtils.createCallingObj(44L, "SET_APART", 6666L,false));

        db.updateCallings(callingList);
        List<CallingViewItem> callingList2=db.getPendingCallings();
        Assert.assertEquals("Did not return 5 callings",callingList2.size(),5);
    }


    @Test
    public void getCallingsTestTrueFalse(){

        List<Calling> callingList=new ArrayList<Calling>();

        callingList.add(TestUtils.createCallingObj(40L, "SET_APART", 1111L,false));
        callingList.add(TestUtils.createCallingObj(41L, "SUBMITTED", 2222L,false));
        callingList.add(TestUtils.createCallingObj(42L, "SUBMITTED", 3333L,false));
        callingList.add(TestUtils.createCallingObj(43L, "PENDING", 4444L,false));
        callingList.add(TestUtils.createCallingObj(44L, "PENDING", 5555L,false));
        callingList.add(TestUtils.createCallingObj(44L, "SET_APART", 6666L,false));
        db.updateCallings(callingList);

        List<CallingViewItem> callingList2=db.getCallings(true);
        Assert.assertEquals("Did not return 2 calling",callingList2.size(),2);

        callingList2=db.getCallings(false);
        Assert.assertEquals("Did not return 4 callings",callingList2.size(),4);

    }


    @Test
    public void getCallingsNonExistingStatusTest(){
        List<Calling> callingList=new ArrayList<Calling>();
        List<CallingViewItem> callingList2=new ArrayList<CallingViewItem>();

        callingList.add(TestUtils.createCallingObj(40L, "NO_STATUS", 1111L,false));
        callingList.add(TestUtils.createCallingObj(41L, "NO_STATUS", 2222L,false));
        callingList.add(TestUtils.createCallingObj(42L, "NO_STATUS", 3333L,false));
        db.updateCallings(callingList);

        callingList2=db.getCallings(false);
        Assert.assertEquals("Did  return callingS, Should Not",callingList2.size(),0);
    }

    @Test
    public void getCallingsToSyncTest(){
        List<Calling> callingList=new ArrayList<Calling>();
        callingList.add(TestUtils.createCallingObj(40L, "SUBMITTED", 1111L,true));
        callingList.add(TestUtils.createCallingObj(41L, "APPROVED", 2222L,false));
        callingList.add(TestUtils.createCallingObj(42L, "SET_APART", 3333L,false));
        db.updateCallings(callingList);

        List<CallingViewItem> callingList2=db.getCallingsToSync();
        Assert.assertEquals("Did not return callings ",callingList2.size(),2);
    }

    @Test
    public void addStatusToDBTest(){

        List<WorkFlowStatus> statusList=db.getWorkFlowStatuses() ;
        Assert.assertEquals("",statusList.size(),3);
        statusList.add( TestUtils.createStatus(true,"DECLINED",null,null,4));
        db.updateWorkFlowStatus(statusList);

        List<WorkFlowStatus> statusListNew=db.getWorkFlowStatuses() ;
        Assert.assertEquals("Did not added Status to DB",statusList.size(),statusListNew.size());
    }

    @Test
    public void addPositionToDBTest(){
        List<Position> positionList=db.getPositions();
        positionList.add(TestUtils.createPositionObj(50L,"Primary Secretary"));
        db.updatePositions(positionList);

        List<Position> positionListNew=db.getPositions();
        Assert.assertEquals("Did not add position to DB",positionList.size(), positionListNew.size());
    }

    @Test
    public void addDuplicatePositionToDBTest(){
        // should not add duplicate positions to db

        List<Position> positionList=db.getPositions();
        positionList.add(TestUtils.createPositionObj(40L, "RS Secretary") );
        positionList.add(TestUtils.createPositionObj(41L, "Elder Quorum First Counselor"));
        positionList.add(TestUtils.createPositionObj(42L, "Elder Quorum Second Counselor"));
        positionList.add(TestUtils.createPositionObj(43L, "Primary sunBean teacher"));
        positionList.add(TestUtils.createPositionObj(44L, "Sunday School 14-15 teacher"));

        db.updatePositions(positionList);

        List<Position> positionListNew=db.getPositions();
        Assert.assertEquals("Duplicate positions added to DB",positionList.size()-5, positionListNew.size());
    }

    @Test
    public void addMemberToDBTest(){
        List<Member> memberList=db.getWardList() ;
        memberList.add(TestUtils.createMemberObj("Jose", "Riveros", 14000L));
        memberList.add(TestUtils.createMemberObj("Alma", "Gonzales", 15000L));

        db.updateWardList(memberList);

        List<Member> memberList2=db.getWardList() ;
        Assert.assertEquals("Did not add member to DB",memberList.size(), memberList2.size());

    }

    @Test
    public void addDuplicateMemberToDBTest(){
        // should not add duplicate members to db
        List<Member> memberList=db.getWardList() ;
        memberList.add(TestUtils.createMemberObj("Joe", "Jones", 1111L));
        memberList.add(TestUtils.createMemberObj("James", "Peterson", 2222L));
        memberList.add(TestUtils.createMemberObj("Eric", "Bastidas", 3333L) );
        memberList.add(TestUtils.createMemberObj("Bill", "Wiley", 4444L));
        memberList.add(TestUtils.createMemberObj("Steve", "Jonas", 5555L));
        memberList.add(TestUtils.createMemberObj("Erika", "Jasmin", 6666L));
        memberList.add(TestUtils.createMemberObj("Adam", "Peres", 7777L));
        db.updateWardList(memberList);

        List<Member> memberList2=db.getWardList() ;
        Assert.assertEquals("duplicate members added to db",memberList.size()-7, memberList2.size());

    }
}
