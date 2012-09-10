package org.lds.community.CallingWorkFlow.tests.domain;

import android.content.ContentValues;
import android.test.AndroidTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/29/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class WorkFlowDBTest extends AndroidTestCase {

    WorkFlowDB db;

    @Before
    public void setUp() throws Exception {
        db = new WorkFlowDB( getContext() );
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveCallings() throws Exception {
        List<CallingBaseRecord> callingList=new ArrayList<CallingBaseRecord>();
        List<CallingViewItem> callingList2=new ArrayList<CallingViewItem>();

        Calling cal1=createCalling(52L,true,23132132132L,222222L,111111L,false,"ACTIVE");
        Calling cal2=createCalling(53L,true,122121321l,222222L,555555l,false,"ACTIVE");
        Calling cal3=createCalling(54L,true,2313211132132L,222222L,3333333l,false,"ACTIVE");
        callingList.add(cal1);
        callingList.add(cal2);
        callingList.add(cal3);
//        db.saveCallings(callingList);
//        callingList2=db.getCompletedCallings();
//        assertEquals("Records not stored in db", callingList.size(), callingList2.size());
        //todo getCompletedCallings does not work
    }

    @Test
    public void testSavePositions() throws Exception {

    }

    @Test
    public void testGetCallingIds() throws Exception {

    }

    @Test
    public void testGetPendingCallings() throws Exception {

    }

    @Test
    public void testGetCompletedCallings() throws Exception {

    }

    @Test
    public void testGetWardList() throws Exception {
         List<Member> fromDbMemberList = db.getWardList();
        assertTrue("Records not found in db", fromDbMemberList.size()>=1);

    }

    @Test
    public void test_Member_UpdateWardList() throws Exception {

          List<Member> memberList = new ArrayList<Member>();
          memberList.add(new Member("lastName1", "firstName1", 11111));
          memberList.add(new Member("lastName2", "firstName2", 22222));

          db.updateWardList(memberList);
          List<Member> fromDbMemberList = db.getWardList();
          assertEquals("Records not stored in db", memberList.size(), fromDbMemberList.size());
    }

    @Test
    public void test_member_setContent(){

        ContentValues values = new ContentValues();
        Member member=new Member();
        values.put(Member.INDIVIDUAL_ID, 111111L);
        values.put(Member.LAST_NAME, "Jones");
        values.put(Member.FIRST_NAME, "Joe");

        member.setContent(values);
        ContentValues values2=member.getContentValues();

        assertEquals("ContentValues are not equal", values.size(), values2.size());
        assertEquals("INDIVIDUAL_ID Id was not equal",values.getAsString(Member.INDIVIDUAL_ID), values2.getAsString(Member.INDIVIDUAL_ID));
        assertEquals("FIRST_NAME Id was not equal",values.getAsString(Member.FIRST_NAME), values2.getAsString(Member.FIRST_NAME));
        assertEquals("LAST_NAME Id was not equal",values.getAsString(Member.LAST_NAME), values2.getAsString(Member.LAST_NAME));
    }

    @Test
    public void test_position_setContent(){

        ContentValues values = new ContentValues();
        Position position=new Position();
        values.put(Position.POSITION_NAME, "Bishop");
        values.put(Position.POSITION_ID, new Long(4));

        position.setContent(values);
        ContentValues values2=position.getContentValues();

        assertEquals("ContentValues are not equal", values.size(), values2.size());
        assertEquals("POSITION_NAME Id was not equal",values.getAsString(Position.POSITION_NAME), values2.getAsString(Position.POSITION_NAME));
        assertEquals("POSITION_ID Name Id was not equal",values.getAsString(Position.POSITION_ID), values2.getAsString(Position.POSITION_ID));

    }

    @Test
    public void test_Calling_setContent(){


        Calling calling=new Calling();
        ContentValues values = new ContentValues();
        values.put(Calling.POSITION_ID, 4L);
        values.put(Calling.INDIVIDUAL_ID, 222222L);
        values.put(Calling.STATUS_NAME, "ACTIVE");
        values.put(Calling.ASSIGNED_TO, 111111L);
        values.put(Calling.DUE_DATE, 123456874455L);
        values.put(Calling.IS_SYNCED, 1);
        values.put(Calling.COMPLETED, 1);

        calling.setContent(values);
        ContentValues values2=calling.getContentValues();

        assertEquals("ContentValues are not equal", values.size(), values2.size());
        assertEquals("INDIVIDUAL_ID Id was not equal",values.getAsString(calling.INDIVIDUAL_ID), values2.getAsString(calling.INDIVIDUAL_ID));
        assertEquals("POSITION_ID Id was not equal",values.getAsString(calling.POSITION_ID), values2.getAsString(calling.POSITION_ID));
        assertEquals("STATUS_NAME Id was not equal",values.getAsString(calling.STATUS_NAME), values2.getAsString(calling.STATUS_NAME));
        assertEquals("ASSIGNED_TO Id was not equal",values.getAsString(calling.ASSIGNED_TO), values2.getAsString(calling.ASSIGNED_TO));
        assertEquals("DUE_DATE Id was not equal",values.getAsString(calling.DUE_DATE), values2.getAsString(calling.DUE_DATE));
        assertEquals("IS_SYNCED Id was not equal",values.getAsString(calling.IS_SYNCED), values2.getAsString(calling.IS_SYNCED));
        assertEquals("COMPLETED Id was not equal",values.getAsString(calling.COMPLETED), values2.getAsString(calling.COMPLETED));
    }

    // UTIL METHOD
    private Calling createCalling(Long positionId,boolean completed,Long dueDate, Long assignedTo,Long individualId, boolean isSync, String status){
        Calling calling=new Calling();
        calling.setPositionId(positionId);
        calling.setCompleted(completed);
        calling.setDueDate(dueDate);
        calling.setAssignedTo(assignedTo);
        calling.setIndividualId(individualId);
        calling.setIsSynced(isSync);
        calling.setStatusName(status);

        return calling;

    }

}
