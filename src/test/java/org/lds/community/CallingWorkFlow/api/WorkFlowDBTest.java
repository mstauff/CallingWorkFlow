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
    //todo make more complehensible variables for future programer
    @Inject WorkFlowDB db;

    List<Member> memberMasterDB= new ArrayList<Member>();
    List<Position> positionMasterDB= new ArrayList<Position>();
    List<WorkFlowStatus> statusMasterDB= new ArrayList<WorkFlowStatus>();

    WorkFlowStatus secondChanceStatus=TestUtils.createStatus(false,"SECOND_CHANCE",null,null,6) ;
    Position newPosition=TestUtils.createPositionObj(1234456789L,"This Position Is New");
    Member newMember1=TestUtils.createMemberObj("Jose","Riveros", 123456789L);
    Member newMember2=TestUtils.createMemberObj("Alma","Gonzales",123456999L);

    @Before
    public void setup(){
        memberMasterDB= TestUtils.createMembersDB(db);
        positionMasterDB=TestUtils.createPositionDB(db);
        statusMasterDB= TestUtils.createStatusDB(db);
    }

    @Test
    public void  WardListTest(){
        List<Member> memberList=db.getWardList();
        Assert.assertEquals("",memberList.size(),memberMasterDB.size());
        int index=0;
        for(Member m:memberList){
            TestUtils.assertEntityEquals(m,memberMasterDB.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void positionListTest(){
        List<Position> positionList= db.getPositions();
        Assert.assertEquals("",positionList.size(),positionMasterDB.size());
        int index=0;
        for(Position p:positionList){
            TestUtils.assertEntityEquals(p,positionList.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void statusListTest(){
        List<WorkFlowStatus> statusList= db.getWorkFlowStatuses();
        Assert.assertEquals("",statusList.size(),statusMasterDB.size());
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
        List<CallingViewItem> callingPending=db.getPendingCallings();

        List<Calling> callingsToUpdate=new ArrayList<Calling>();
        callingsToUpdate.addAll(callingPending);

        callingsToUpdate.get(0).setStatusName(TestUtils.getStatus(db,true));

        db.updateCallings(callingsToUpdate)  ;

        List<CallingViewItem> callingUpdatedFromDB=db.getCompletedCallings();
        Calling callingResultObj= TestUtils.getCallingObjectFromList(callingUpdatedFromDB,callingsToUpdate.get(0).getIndividualId(),callingsToUpdate.get(0).getPositionId());
        TestUtils.assertEntityEquals(callingsToUpdate.get(0),callingResultObj,"");
    }

//    @Test
//    public void DuplicateCallingsTest(){
//        List<Calling> callingList=createCallingList();
//        db.updateCallings(callingList);
//        List<CallingViewItem> callingListFromDB=db.getCompletedCallings();
//        int completedCallings=TestUtils.getCallingStatusCompletedFromList(callingListFromDB, db);
//
//        Assert.assertEquals("Did not return the correct Completed callings count",callingListFromDB.size(),completedCallings);
//
//        // add duplicate calling to list
//        List<Calling> callingListDup=new ArrayList<Calling>();
//        callingListDup.addAll(callingList);
//        db.updateCallings(callingListDup);
//
//        // check if duplicates were saved to db
//        List<CallingViewItem> callingListFromDB2=db.getCompletedCallings();
//
//        int completedCallings2=TestUtils.getCallingStatusCompletedFromList(TestUtils.convertCViewToCallingList(callingListFromDB2), db);
//        Assert.assertEquals("Did return duplicate callings",callingListFromDB2.size(),completedCallings2);
//    }

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

        List<Position> positionListFromDB=db.getPositions();

        List<Position> positionListDup =new ArrayList<Position>();
        positionListDup.addAll(positionListFromDB);

        db.updatePositions(positionListDup);

        List<Position> positionListResult=db.getPositions();
        Assert.assertEquals("Duplicate positions added to DB",positionListFromDB.size(), positionListResult.size());
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
        db.updateWardList(memberListDup);

        List<Member> memberListResult=db.getWardList() ;
        Assert.assertEquals("duplicate members added to db",memberListResult.size(), memberList.size());

        for (int i = 0; i < memberList.size(); i++) {
            TestUtils.assertEntityEquals(memberList.get(i),memberListResult.get(i),"");
        }
    }


//    //------------------------------------------------------------------------------------------------------------------
    // UTIL METHODS
    private List<Calling> createCallingList(){

        List<Calling> cList=new ArrayList<Calling>();

        cList.add(TestUtils.createCallingObj(positionMasterDB.get(0).getPositionId(),TestUtils.getStatus(db,false) , memberMasterDB.get(0).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(1).getPositionId(),TestUtils.getStatus(db,false) , memberMasterDB.get(1).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(2).getPositionId(),TestUtils.getStatus(db,false) , memberMasterDB.get(2).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(3).getPositionId(),TestUtils.getStatus(db,false) , memberMasterDB.get(3).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(4).getPositionId(),TestUtils.getStatus(db,false) , memberMasterDB.get(4).getIndividualId(),false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(4).getPositionId(),TestUtils.getStatus(db,true) , memberMasterDB.get(5).getIndividualId(),true));
        return cList;
    }

    private List<Calling> createCallingNullList(){

        List<Calling> cList=new ArrayList<Calling>();

        cList.add(TestUtils.createCallingObj(null,statusMasterDB.get(TestUtils.SUBMITTED).getStatusName() ,0L,false));
        cList.add(TestUtils.createCallingObj(positionMasterDB.get(1).getPositionId(),statusMasterDB.get(TestUtils.SUBMITTED).getStatusName() ,0L,false));
        return cList;
    }

    private List<Position> createPositionList(){
        List<Position> positionList =new ArrayList<Position>();

        positionList.add(TestUtils.createPositionObj(positionMasterDB.get(0).getPositionId(), positionMasterDB.get(0).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMasterDB.get(1).getPositionId(), positionMasterDB.get(1).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMasterDB.get(2).getPositionId(), positionMasterDB.get(2).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMasterDB.get(3).getPositionId(), positionMasterDB.get(3).getPositionName()) );
        positionList.add(TestUtils.createPositionObj(positionMasterDB.get(4).getPositionId(), positionMasterDB.get(4).getPositionName()) );
        return positionList;
    }

    private List<Member> createMemberList(){
        List<Member> memberList =new ArrayList<Member>();
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(0).getFirstName(), memberMasterDB.get(0).getLastName(),memberMasterDB.get(0).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(1).getFirstName(), memberMasterDB.get(1).getLastName(),memberMasterDB.get(1).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(2).getFirstName(), memberMasterDB.get(2).getLastName(),memberMasterDB.get(2).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(3).getFirstName(), memberMasterDB.get(3).getLastName(),memberMasterDB.get(3).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(4).getFirstName(), memberMasterDB.get(4).getLastName(),memberMasterDB.get(4).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(5).getFirstName(), memberMasterDB.get(5).getLastName(),memberMasterDB.get(5).getIndividualId()));
        memberList.add(TestUtils.createMemberObj(memberMasterDB.get(6).getFirstName(), memberMasterDB.get(6).getLastName(),memberMasterDB.get(6).getIndividualId()));

        return memberList;

    }

}
