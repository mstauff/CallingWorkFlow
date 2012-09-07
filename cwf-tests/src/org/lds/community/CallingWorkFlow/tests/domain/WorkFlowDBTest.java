package org.lds.community.CallingWorkFlow.tests.domain;

import android.content.ContentValues;
import android.test.AndroidTestCase;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;

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
        ContentValues values2 = new ContentValues();

        Position position=new Position();
        values.put("POSITION_NAME", "Bishop");
        values.put("POSITION_ID", "4L");

        position.setContent(values);


//        Long positionId=new Long(4);
//
//        Calling calling=new Calling();
//        ContentValues values = new ContentValues();
//        values.put("POSITION_ID", positionId);
//        values.put("INDIVIDUAL_ID", 222222L);
//        values.put("STATUS_NAME", "ACTIVE");
//        values.put("ASSIGNED_TO", 111111L);
//        values.put("DUE_DATE", 123456874455L);
//        values.put("IS_SYNCED", 1);
//        values.put("COMPLETED", true);
//        try{
//            calling.setContent(values);
//        } catch ( Exception e) {
//            e.printStackTrace();
//        }


//        Member member=new Member();
//
//        ContentValues values2 = new ContentValues();
//
//        values.put("INDIVIDUAL_ID", "111111");
//        values.put("LAST_NAME", "Jones");
//        values.put("FIRST_NAME", "Joe");
//
//        member.setContent(values);
//
//        values2=member.getContentValues();

    }
}
