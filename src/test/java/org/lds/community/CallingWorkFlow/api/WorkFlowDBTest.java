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
import java.util.HashSet;
import java.util.List;
import java.util.Random;

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
    Random generator = new Random( );

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
            TestUtils.assertEntityEquals(p,positionMasterDB.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void statusListTest(){
        List<WorkFlowStatus> statusList= db.getWorkFlowStatuses();
        Assert.assertEquals("",statusList.size(),statusMasterDB.size());
        int index=0;
        for(WorkFlowStatus wf:statusList){
            TestUtils.assertEntityEquals(wf,statusMasterDB.get(index),"Entity not equal");
            index++;
        }
    }

    @Test
    public void getCompleted_Pending_Sync_CallingsTest(){

        List<Calling> callingNotCompletedSource=createCallingList(30,false,false);
        List<Calling> callingCompletedSource=createCallingList(40,true,true);
        List<Calling> callingLisSource=new ArrayList<Calling>();
        callingLisSource.addAll(callingNotCompletedSource);
        callingLisSource.addAll(callingCompletedSource);
        db.updateCallings(callingLisSource);

        List<CallingViewItem> callingCompleted=db.getCompletedCallings();
        List<CallingViewItem> callingPending=db.getPendingCallings();
        List<CallingViewItem> callingSync=db.getCallingsToSync();

        Assert.assertTrue("Did not return completed callings",callingCompleted.size()>=1);
        Assert.assertTrue("Did not return pending callings",callingPending.size()>=1);
        Assert.assertTrue("Did not return sync callings",callingSync.size()>=1);
    }

    @Test
    public void updateCallingsTest(){
        db.updateCallings(createCallingList(5,false,false));
        List<CallingViewItem> callingPending=db.getPendingCallings();

        List<Calling> callingsToUpdate=new ArrayList<Calling>();
        callingsToUpdate.addAll(callingPending);

        callingsToUpdate.get(0).setStatusName(TestUtils.getStatusName(db,true));

        db.updateCallings(callingsToUpdate)  ;

        List<CallingViewItem> callingUpdatedFromDB=db.getCompletedCallings();
        Calling callingResultObj= TestUtils.getCallingObjectFromList(callingUpdatedFromDB,callingsToUpdate.get(0).getIndividualId(),callingsToUpdate.get(0).getPositionId());
        TestUtils.assertEntityEquals(callingsToUpdate.get(0),callingResultObj,"");
    }

    @Test
    public void DuplicateCallingsTest(){

        List<Calling> callingsNotCompleted=createCallingList(5,false,false);
        List<Calling> callingsCompleted=createCallingList(5,true,true);
        List<Calling> callingsSource=new ArrayList<Calling>();
        callingsSource.addAll(callingsNotCompleted);
        callingsSource.addAll(callingsCompleted);

        db.updateCallings(callingsSource);

        List<CallingViewItem> callingsSourceList=db.getCompletedCallings();

        // add duplicate calling to list
        List<Calling> callingsDuplicateList=new ArrayList<Calling>();
        callingsDuplicateList.addAll(callingsSource);
        callingsDuplicateList.addAll(callingsSource);
        db.updateCallings(callingsDuplicateList);

        // check if duplicates were saved to db
        List<CallingViewItem> callingsListFromDBResults=db.getCompletedCallings();

        Assert.assertEquals("Did return duplicate callings",callingsListFromDBResults.size(),callingsSourceList.size());
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

        List<Position> positionsFromDBSource=db.getPositions();

        List<Position> positionDuplicateList =new ArrayList<Position>();
        positionDuplicateList.addAll(positionsFromDBSource);

        db.updatePositions(positionDuplicateList);

        List<Position> positionsFromDBResult=db.getPositions();
        Assert.assertEquals("Duplicate positions added to DB",positionsFromDBResult.size(), positionsFromDBSource.size());
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
        List<Member> membersSource=db.getWardList() ;
        List<Member> memberDuplicateList=new ArrayList<Member>() ;
        memberDuplicateList.addAll(membersSource);
        db.updateWardList(memberDuplicateList);

        List<Member> memberListResult=db.getWardList() ;
        Assert.assertEquals("duplicate members added to db",memberListResult.size(), membersSource.size());

        for (int i = 0; i < membersSource.size(); i++) {
            TestUtils.assertEntityEquals(membersSource.get(i),memberListResult.get(i),"");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // UTIL METHODS
    private List<Calling> createCallingList(int numberOfCallings, Boolean isComplete, Boolean isSync){
         // create as many callings as the member and position permits
        List<Calling> cList=new ArrayList<Calling>();

        HashSet hs = new HashSet();
        Long positionID=0L;
        Long individualId=0L;
        Boolean getAnother=true;

        for(int i=0; i < numberOfCallings;i++){
            while (getAnother==true){

                positionID= TestUtils.getRandomPositionID(generator,positionMasterDB);
                individualId= TestUtils.getRandomIndividualId(generator,memberMasterDB);

                if (!hs.contains(String.valueOf(positionID) + " " + String.valueOf(individualId))){
                    hs.add(String.valueOf(positionID) + " " + String.valueOf(individualId)) ;

                    Calling calling=TestUtils.createCallingObj( positionID,TestUtils.getStatusName(db,isComplete),individualId,isSync);
                    cList.add(calling);

                    getAnother=false;
                }else{
                    if((positionMasterDB.size() * memberMasterDB.size())==hs.size()) {
                       getAnother=false;

                    }else{
                        getAnother=true;
                    }
                 }
            }

            getAnother=true;
        }
        return cList;
    }

}
