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

    List<Member> memberMaster= new ArrayList<Member>();
    List<Position> positionMaster= new ArrayList<Position>();
    List<WorkFlowStatus> statusMaster= new ArrayList<WorkFlowStatus>();

    WorkFlowStatus secondChanceStatus=TestUtils.createStatus(false,"SECOND_CHANCE",null,null,6) ;
    Position newPosition=TestUtils.createPositionObj(1234456789L,"This Position Is New");
    Member newMember1=TestUtils.createMemberObj("Jose","Riveros", 123456789L);
    Member newMember2=TestUtils.createMemberObj("Alma","Gonzales",123456999L);

    @Before
    public void setup(){
        memberMaster= TestUtils.createMembersDB(db);
        positionMaster=TestUtils.createPositionDB(db);
        statusMaster= TestUtils.createStatusDB(db);
    }

    @Test
    public void  WardListTest(){
        List<Member> memberList=db.getWardList();
        Assert.assertEquals("",memberList.size(),memberMaster.size());
        int index=0;
        for(Member m:memberList){
            TestUtils.assertEntityEquals(m,memberMaster.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void positionListTest(){
        List<Position> positionList= db.getPositions();
        Assert.assertEquals("",positionList.size(),positionMaster.size());
        int index=0;
        for(Position p:positionList){
            TestUtils.assertEntityEquals(p,positionList.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void statusListTest(){
        List<WorkFlowStatus> statusList= db.getWorkFlowStatuses();
        Assert.assertEquals("",statusList.size(),statusMaster.size());
        int index=0;
        for(WorkFlowStatus p:statusList){
            TestUtils.assertEntityEquals(p,statusList.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void getCompleted_Pending_Sync_CallingsTest(){
        db.updateCallings(createCallingList());
        List<CallingViewItem> callingCompleted=db.getCompletedCallings();
        List<CallingViewItem> callingPending=db.getPendingCallings();
        List<CallingViewItem> callingSync=db.getCallingsToSync();

        Assert.assertTrue("Did not return completed callings",callingCompleted.size()>=1);
        Assert.assertTrue("Did not return pending callings",callingPending.size()>=1);
        Assert.assertTrue("Did not return sync callings",callingSync.size()>=1);
    }

    @Test
    public void updateCallingsTest(){
        db.updateCallings(createCallingList());
        Calling callingObj=new Calling();
        List<CallingViewItem> callingPending=db.getPendingCallings();
        List<Calling> callingsToUpdate=TestUtils.convertCViewToCallingList(callingPending);
        for (Calling c:callingsToUpdate){
            c.setStatusName(statusMaster.get(TestUtils.DECLINED).getStatusName());
            callingObj=c;
            break;
        }

        db.updateCallings(callingsToUpdate)  ;

        List<CallingViewItem> callingUpdates=db.getCompletedCallings();
        Calling callingResult= TestUtils.getCallingObjectFromList(callingUpdates,callingObj.getIndividualId(),callingObj.getPositionId());
        TestUtils.assertEntityEquals(callingObj,callingResult,"");
    }

    @Test
    public void DuplicateCallingsTest(){
        List<Calling> callingList=createCallingList();
        db.updateCallings(callingList);
        List<CallingViewItem> callingListResult=db.getCompletedCallings();
        int completedCallings=TestUtils.getCallingStatusCompletedFromList(callingList, db);

        Assert.assertEquals("Did not return the correct Completed callings count",callingListResult.size(),completedCallings);

        // add duplicate calling to list
        List<Calling> callingListDup=createCallingList();
        callingListDup.addAll(createCallingList());
        db.updateCallings(callingListDup);

        // check if duplicates were saved to db
        List<CallingViewItem> callingListResult2=db.getCompletedCallings();

        int completedCallings2=TestUtils.getCallingStatusCompletedFromList(TestUtils.convertCViewToCallingList(callingListResult2), db);
        Assert.assertEquals("Did return duplicate callings",callingListResult2.size(),completedCallings2);
    }

    // do not delete.  kept for Second round
//    @Test
//    public void getCallingsNonExistingStatusTest(){
//
//        List<Calling> callingList=new ArrayList<Calling>();
//        List<CallingViewItem> callingList2=new ArrayList<CallingViewItem>();
//
//        callingList.add(TestUtils.createCallingObj(40L, "NO_STATUS", 1111L,false));
//        callingList.add(TestUtils.createCallingObj(41L, "NO_STATUS", 2222L,false));
//        callingList.add(TestUtils.createCallingObj(42L, "NO_STATUS", 3333L,false));
//        db.updateCallings(callingList);
//
//        callingList2=db.getCallings(false);
          // todo insert raw query to get result
//        Assert.assertEquals("Did  return callingS, Should Not",callingList2.size(),0);
//    }


    @Test
    public void addStatusToDBTest(){

        List<WorkFlowStatus> statusList=db.getWorkFlowStatuses() ;
        statusList.add(secondChanceStatus);
        db.updateWorkFlowStatus(statusList);

        List<WorkFlowStatus> statusListNew=db.getWorkFlowStatuses() ;
        Assert.assertEquals("Did not added Status to DB",statusList.size(),statusListNew.size());
    }

    @Test
    public void addPositionToDBTest(){
         List<Position> positionList=db.getPositions();
        positionList.add(newPosition);
        db.updatePositions(positionList);

        List<Position> positionListNew=db.getPositions();
        Assert.assertEquals("Did not add position to DB",positionList.size(), positionListNew.size());
        Position positionResult=TestUtils.getPositionObjectFromList(positionList, newPosition.getPositionId(), newPosition.getPositionName()) ;
        TestUtils.assertEntityEquals(positionResult,newPosition,"");
    }

    @Test
    public void addDuplicatePositionToDBTest(){
        // should not add duplicate positions to db

        List<Position> positionList=db.getPositions();

        List<Position> positionListDup =new ArrayList<Position>();
        positionListDup.addAll(createPositionList());

        db.updatePositions(positionListDup);

        List<Position> positionListResult=db.getPositions();
        Assert.assertEquals("Duplicate positions added to DB",positionList.size(), positionListResult.size());
    }

    @Test
    public void addMemberToDBTest(){
        List<Member> memberList=db.getWardList() ;
        memberList.add(newMember1);
        memberList.add(newMember2);
        db.updateWardList(memberList);

        List<Member> memberListResult=db.getWardList() ;
        Assert.assertEquals("Did not add member to DB",memberList.size(), memberListResult.size());

        for (int i = 0; i < memberList.size(); i++) {
            TestUtils.assertEntityEquals(memberList.get(i),memberListResult.get(i),"");
        }
    }

    @Test
    public void addDuplicateMemberToDBTest(){
        // should not add duplicate members to db
        List<Member> memberList=db.getWardList() ;
        List<Member> memberListDup=new ArrayList<Member>() ;
        memberListDup.addAll(memberList);
        memberListDup.addAll(createMemberList());
        db.updateWardList(memberListDup);

        List<Member> memberListResult=db.getWardList() ;
        Assert.assertEquals("duplicate members added to db",memberListResult.size(), memberList.size());

        for (int i = 0; i < memberList.size(); i++) {
            TestUtils.assertEntityEquals(memberList.get(i),memberListResult.get(i),"");
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // UTIL METHODS
    private List<Calling> createCallingList(){

        List<Calling> cList=new ArrayList<Calling>();

        cList.add(TestUtils.createCallingObj(positionMaster.get(0).getPositionId(),statusMaster.get(TestUtils.SUBMITTED).getStatusName() , memberMaster.get(0).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(1).getPositionId(),statusMaster.get(TestUtils.SUBMITTED).getStatusName() , memberMaster.get(1).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(2).getPositionId(),statusMaster.get(TestUtils.PENDING).getStatusName() , memberMaster.get(2).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(3).getPositionId(),statusMaster.get(TestUtils.PENDING).getStatusName() , memberMaster.get(3).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(4).getPositionId(),statusMaster.get(TestUtils.PENDING).getStatusName() , memberMaster.get(4).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(4).getPositionId(),statusMaster.get(TestUtils.SET_APART).getStatusName() , memberMaster.get(5).getIndividualId(),true));
        return cList;
    }

    private List<Calling> createCallingNullList(){

        List<Calling> cList=new ArrayList<Calling>();

        cList.add(TestUtils.createCallingObj(null,statusMaster.get(TestUtils.SUBMITTED).getStatusName() ,0L,false));
        cList.add(TestUtils.createCallingObj(positionMaster.get(1).getPositionId(),statusMaster.get(TestUtils.SUBMITTED).getStatusName() ,0L,false));
        return cList;
    }

    private List<Position> createPositionList(){
        List<Position> positionList =new ArrayList<Position>();

        positionList.add(TestUtils.createPositionObj(positionMaster.get(0).getPositionId(), positionMaster.get(0).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMaster.get(1).getPositionId(), positionMaster.get(1).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMaster.get(2).getPositionId(), positionMaster.get(2).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMaster.get(3).getPositionId(), positionMaster.get(3).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMaster.get(4).getPositionId(), positionMaster.get(4).getPositionName()) );
        return positionList;
    }

    private List<Member> createMemberList(){
        List<Member> memberList =new ArrayList<Member>();
        memberList.add(TestUtils.createMemberObj(memberMaster.get(0).getFirstName(), memberMaster.get(0).getLastName(),memberMaster.get(0).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(1).getFirstName(), memberMaster.get(1).getLastName(),memberMaster.get(1).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(2).getFirstName(), memberMaster.get(2).getLastName(),memberMaster.get(2).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(3).getFirstName(), memberMaster.get(3).getLastName(),memberMaster.get(3).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(4).getFirstName(), memberMaster.get(4).getLastName(),memberMaster.get(4).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(5).getFirstName(), memberMaster.get(5).getLastName(),memberMaster.get(5).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMaster.get(6).getFirstName(), memberMaster.get(6).getLastName(),memberMaster.get(6).getIndividualId()));

        return memberList;

    }

}
