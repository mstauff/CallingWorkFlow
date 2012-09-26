package org.lds.community.CallingWorkFlow.Utilities;

import android.content.ContentValues;
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
    public static int SUBMITTED=0;
    public static int PENDING=1;
    public static int SET_APART=2;
    public static int DECLINED=3;

    public static List<Member> createMembersDB(WorkFlowDB db){
        List<Member> memberList= new ArrayList<Member>();
        memberList.add(createMemberObj("Joe", "Jones", 1111L));
        memberList.add(createMemberObj("James", "Peterson", 2222L));
        memberList.add(createMemberObj("Eric", "Bastidas", 3333L) );
        memberList.add(createMemberObj("Bill", "Wiley", 4444L));
        memberList.add(createMemberObj("Steve", "Jonas", 5555L));
        memberList.add(createMemberObj("Erika", "Jasmin", 6666L));
        memberList.add(createMemberObj("Adam", "Peres", 7777L));
        if(db!=null){
            initializeDatabase(db,memberList,null,null);
        }
        return memberList;
    }

    public static List<Position> createPositionDB(WorkFlowDB db){
        List<Position> positionList= new ArrayList<Position>();
        positionList.add(createPositionObj(40L, "RS Secretary") );
        positionList.add(createPositionObj(41L, "Elder Quorum First Counselor"));
        positionList.add(createPositionObj(42L, "Elder Quorum Second Counselor"));
        positionList.add(createPositionObj(43L, "Primary sunBean teacher"));
        positionList.add(createPositionObj(44L, "Sunday School 14-15 teacher"));
        if(db!=null){
            initializeDatabase(db,null,positionList,null);
        }
        return positionList;
    }

    public static List<WorkFlowStatus> createStatusDB(WorkFlowDB db){
        List<WorkFlowStatus> statusList= new ArrayList<WorkFlowStatus>();
        statusList.add( createStatus(false,"SUBMITTED",null,null,1));
        statusList.add( createStatus(false,"PENDING",null,null,2));
        statusList.add( createStatus(true,"SET_APART",null,null,3));
        statusList.add( createStatus(true,"DECLINED",null,null,4));
        if(db!=null){
            initializeDatabase(db,null,null,statusList);
        }
        return statusList;
    }

    public static void initializeDatabase(WorkFlowDB db, List<Member> memberList,List<Position> positionList,List<WorkFlowStatus> statusList){

        if(memberList==null){
           memberList = createMembersDB(null);
        }
        db.updateWardList( memberList);

        if(positionList==null){
            positionList = createPositionDB(null);
        }
        db.updatePositions(positionList);

        if(statusList==null){
            statusList = createStatusDB(null);
        }
        db.updateWorkFlowStatus(statusList);

    }

    public static Member createMemberObj(String firstName, String lastName, Long individualId){
        Member memberObj=new Member();
        memberObj.setFirstName(firstName);
        memberObj.setLastName(lastName);
        memberObj.setIndividualId(individualId);
        return memberObj;
     }

    public static Calling createCallingObj(Long positionId, String statusName, Long individualId, Boolean isSync){
        Calling callingObj=new Calling();
        callingObj.setIndividualId(individualId);
        callingObj.setPositionId(positionId);
        callingObj.setStatusName(statusName);
        callingObj.setIsSynced(isSync);
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

    public static Calling getCallingObjectFromList(List<? extends Calling> callingList,Long individualId, long positionID) {
        Calling calling = null;

        for( Calling c : callingList ) {
            if( c.getIndividualId() == individualId && c.getPositionId() == positionID ) {
                calling = c;
                break;
            }
        }
        return calling;
    }

    public static Position getPositionObjectFromList(List<? extends Position> positionList,Long PositionId, String name) {
        Position position = null;

        for( Position p : positionList ) {
            if( p.getPositionId() == PositionId && p.getPositionName() == name ) {
                position = p;
                break;
            }
        }
        return position;
    }


    public static void assertEntityEquals(Calling sourceCalling, Calling resultCalling, String failMsg){

        Assert.assertEquals(failMsg + " getIndividualId did not match", sourceCalling.getIndividualId(), resultCalling.getIndividualId());
        Assert.assertEquals(failMsg + " getStatusName did not match",sourceCalling.getStatusName(), resultCalling.getStatusName());
        Assert.assertEquals(failMsg + " getAssignedToId did not match",sourceCalling.getAssignedToId(), resultCalling.getAssignedToId());
        Assert.assertEquals(failMsg + " getDueDate did not match",sourceCalling.getDueDate(), resultCalling.getDueDate());
        Assert.assertEquals(failMsg + " getIsSynced did not match",sourceCalling.getIsSynced(), resultCalling.getIsSynced());
        Assert.assertEquals(failMsg + " getPositionId did not match",sourceCalling.getPositionId(), resultCalling.getPositionId());

    }

    public static void assertEntityEquals(Position sourcePosition,Position resultPosition, String failMsg){

        Assert.assertEquals(failMsg + " getPositionName did not match", sourcePosition.getPositionName(), resultPosition.getPositionName());
        Assert.assertEquals(failMsg + " getPositionId did not match",sourcePosition.getPositionId(), resultPosition.getPositionId());
//        Assert.assertEquals(failMsg + " getContentValues did not match",sourcePosition.getContentValues(), resultPosition.getContentValues());

    }

    public static void assertEntityEquals(WorkFlowStatus sourceWfs,WorkFlowStatus resultWfs, String failMsg){

        Assert.assertEquals(failMsg + " getStatusName did not match", sourceWfs.getStatusName(), resultWfs.getStatusName());
        Assert.assertEquals(failMsg + " getComplete did not match",sourceWfs.getComplete(), resultWfs.getComplete());
//        Assert.assertEquals(failMsg + " getContentValues did not match",sourceWfs.getContentValues(), resultWfs.getContentValues());
        Assert.assertEquals(failMsg + " getSequence did not match",sourceWfs.getSequence(), resultWfs.getSequence());

    }

    public static void assertEntityEquals(Member sourceMember,Member resultMember, String failMsg){

        Assert.assertEquals(failMsg + " getStatusName did not match", sourceMember.getFirstName(), resultMember.getFirstName());
        Assert.assertEquals(failMsg + " getComplete did not match",sourceMember.getLastName(), resultMember.getLastName());
//        Assert.assertEquals(failMsg + " getContentValues did not match",sourceMember.getContentValues(), resultMember.getContentValues());
        Assert.assertEquals(failMsg + " getSequence did not match",sourceMember.getIndividualId(), resultMember.getIndividualId());

    }

    public static Boolean assertEntityEqualsBoolean(Member sourceMember,Member resultMember, String failMsg){

        Assert.assertEquals(failMsg + " getStatusName did not match", sourceMember.getFirstName(), resultMember.getFirstName());
        Assert.assertEquals(failMsg + " getComplete did not match",sourceMember.getLastName(), resultMember.getLastName());
        Assert.assertEquals(failMsg + " getContentValues did not match",sourceMember.getContentValues(), resultMember.getContentValues());
        Assert.assertEquals(failMsg + " getSequence did not match",sourceMember.getIndividualId(), resultMember.getIndividualId());

        return true;
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

    public static int getCallingStatusCompletedFromList(List<Calling> callingList, WorkFlowDB db) {
        int count=0;
        List<WorkFlowStatus>  statusList= createStatusDB(null);
        for( Calling c : callingList ) {
            for (WorkFlowStatus w:statusList){
                if( c.getStatusName().equals(w.getStatusName())){
                    if( w.getComplete()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static Calling callingView_ToCalling(CallingViewItem callingViewItem) {
        //converts from one object to another
        Calling calling=new Calling();
        calling.setIndividualId(callingViewItem.getIndividualId());
        calling.setDueDate(callingViewItem.getDueDate());
        calling.setIsSynced(callingViewItem.getIsSynced());
        calling.setPositionId(callingViewItem.getPositionId());
        calling.setStatusName(callingViewItem.getStatusName());
        calling.setAssignedTo(callingViewItem.getAssignedToId());

        return   calling;
    }

    public static List<Calling> convertCViewToCallingList(List<CallingViewItem> callingViewList){
        // converts from CallingViewList object  to Calling object List
        List<Calling> callingList =new ArrayList<Calling>();

        for(CallingViewItem c:callingViewList) {
            Calling calling= callingView_ToCalling(c);
            callingList.add(calling);
        }
        return callingList;
    }

}

