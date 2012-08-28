package org.lds.community.CallingWorkFlow.domain;

import android.test.AndroidTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkFlowDBTest extends AndroidTestCase {

    WorkFlowDB db;

    @Before
    public void setUp() throws Exception {
        db = new WorkFlowDB(getContext());
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
    public void testUpdateWardList() throws Exception {

        List<Member> memberList = new ArrayList<Member>();
        memberList.add(new Member("lastName1", "firstName1", 11111));
        memberList.add(new Member("lastName2", "firstName2", 22222));

        db.updateWardList(memberList);

        List<Member> fromDbMemberList = db.getWardList();

        assertEquals("Records not stored in db", memberList.size(), fromDbMemberList.size());


    }
}
