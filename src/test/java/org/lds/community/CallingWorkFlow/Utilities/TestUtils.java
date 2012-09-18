package org.lds.community.CallingWorkFlow.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import org.junit.Assert;
import org.lds.community.CallingWorkFlow.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 9/17/12
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */



public class TestUtils {
     Context context;

     WorkFlowDB db=new WorkFlowDB(context);


    public  void initializeDb(){
       //create positions, status and member list
       Member member1=createMemberObj("Joe", "Jones", 1L) ;
       Member member2=createMemberObj("James", "Peterson", 2L) ;
       Member member3=createMemberObj("Eric", "Carrasco", 3L) ;
       Member member4=createMemberObj("Bill", "Wiley", 4L) ;
       Member member5=createMemberObj("Steve", "Jonas", 5L) ;
       Member member6=createMemberObj("Erika", "Jasmin", 6L) ;
       Member member7=createMemberObj("Adam", "Peres", 7L) ;

        List<Member> memberList= new ArrayList<Member>();
        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);
        memberList.add(member4);
        memberList.add(member5);
        memberList.add(member6);
        memberList.add(member7);
        db.updateWardList(memberList);

        // add  positions
        List<Position> positionList= new ArrayList<Position>();
        Position position1=createPositionObj(40L, "RS Secretary") ;
        Position position2=createPositionObj(41L, "Elder Quorum First Counselor") ;
        Position position3=createPositionObj(42L, "Elder Quorum Second Counselor") ;
        Position position4=createPositionObj(43L, "Primary sunBean teacher") ;
        Position position5=createPositionObj(44L, "Sunday School 14-15 teacher") ;

        positionList.add(position1);
        positionList.add(position2);
        positionList.add(position3);
        positionList.add(position4);
        positionList.add(position5);
        db.updatePositions(positionList);

        // ADDING STATUS
        List<WorkFlowStatus> statusList= new ArrayList<WorkFlowStatus>();

        WorkFlowStatus status1=createStatus(true,"SUBMITTED",null,null,1);
        WorkFlowStatus status2=createStatus(true,"PENDING",null,null,2);
        statusList.add( status1);
        statusList.add( status2);
        db.updateWorkFlowStatus(statusList);
    }

    public static Member createMemberObj(String firstName, String lastName, Long individualId){
        Member memberObj=new Member();
        memberObj.setFirstName(firstName);
        memberObj.setLastName(lastName);
        memberObj.setIndividualId(individualId);
        return memberObj;
     }

    public static Calling createCallingObj(Long positionId, String statusName, Long individualId){
        Calling callingObj=new Calling();
        callingObj.setIndividualId(individualId);
        callingObj.setPositionId(positionId);
        callingObj.setStatusName(statusName);
        callingObj.setIsSynced(false);
        callingObj.setAssignedTo(0);
        callingObj.setDueDate(0);
        return callingObj;
    }

    public static Position createPositionObj(Long positionId, String positionName){
        Position positionObj=new Position();
        positionObj.setPositionId(positionId);
        positionObj.setPositionName(positionName);

        return positionObj;
    }

    public static WorkFlowStatus createStatus(boolean complete, String statusName, ContentValues cValues, Cursor cursor, int sequence){
        WorkFlowStatus statusObj=new WorkFlowStatus();
        statusObj.setStatusName(statusName);
        statusObj.setComplete(complete);
        statusObj.setSequence(sequence);

        return statusObj;
    }

    public static boolean foundCallingFromList(List<Calling> callingList, Long individualId, long positionID) {
        boolean found = false;
        for( Calling c : callingList ) {
            if( c.getIndividualId() == individualId && c.getPositionId() == positionID ) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static Calling getCallingObjectFromList(List<Calling> callingList,Long individualId, long positionID) {
        Calling calling=new Calling();

        for( Calling c : callingList ) {
            if( c.getIndividualId() == individualId && c.getPositionId() == positionID ) {
                calling = c;
                break;
            }
        }
        return calling;
    }

    public static void assertEntityEquals(Calling sourceCalling,Calling resultCalling){

        Assert.assertEquals("getIndividualId did not match", sourceCalling.getIndividualId(), resultCalling.getIndividualId());
        Assert.assertEquals("getStatusName did not match",sourceCalling.getStatusName(), resultCalling.getStatusName());
        Assert.assertEquals("getAssignedToId did not match",sourceCalling.getAssignedToId(), resultCalling.getAssignedToId());
        Assert.assertEquals("getDueDate did not match",sourceCalling.getDueDate(), resultCalling.getDueDate());
        Assert.assertEquals("getIsSynced did not match",sourceCalling.getIsSynced(), resultCalling.getIsSynced());
        Assert.assertEquals("getPositionId did not match",sourceCalling.getPositionId(), resultCalling.getPositionId());

    }

    public static void assertEntityEquals(Position sourcePosition,Position resultPosition){

        Assert.assertEquals("getPositionName did not match", sourcePosition.getPositionName(), resultPosition.getPositionName());
        Assert.assertEquals("getPositionId did not match",sourcePosition.getPositionId(), resultPosition.getPositionId());
        Assert.assertEquals("getContentValues did not match",sourcePosition.getContentValues(), resultPosition.getContentValues());

    }

    public static void assertEntityEquals(WorkFlowStatus sourceWfs,WorkFlowStatus resultWfs){

        Assert.assertEquals("getStatusName did not match", sourceWfs.getStatusName(), resultWfs.getStatusName());
        Assert.assertEquals("getComplete did not match",sourceWfs.getComplete(), resultWfs.getComplete());
        Assert.assertEquals("getContentValues did not match",sourceWfs.getContentValues(), resultWfs.getContentValues());
        Assert.assertEquals("getSequence did not match",sourceWfs.getSequence(), resultWfs.getSequence());

    }

    public static void assertEntityEquals(Member sourceMember,Member resultMember){

        Assert.assertEquals("getStatusName did not match", sourceMember.getFirstName(), resultMember.getFirstName());
        Assert.assertEquals("getComplete did not match",sourceMember.getLastName(), resultMember.getLastName());
        Assert.assertEquals("getContentValues did not match",sourceMember.getContentValues(), resultMember.getContentValues());
        Assert.assertEquals("getSequence did not match",sourceMember.getIndividualId(), resultMember.getIndividualId());

    }

    public static int getCallingStatusCountFromList(List<Calling> callingList, String status) {
        int count=0;

        for( Calling c : callingList ) {
            if( c.getStatusName().equals(status)) {
                count++;
            }
        }
        return count;
    }
}
