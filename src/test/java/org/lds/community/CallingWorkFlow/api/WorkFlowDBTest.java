package org.lds.community.CallingWorkFlow.api;

import android.content.Context;
import com.google.inject.Inject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;

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

    @Inject
    WorkFlowDB db;

    @Before
    public void setup(){
        Context context=null;
        db=new WorkFlowDB(context);

        Boolean hasDataMember=db.hasData(Member.TABLE_NAME);
        if (hasDataMember==false){
            TestUtils.initMembersDB();
        }

        Boolean hasDataPositions=db.hasData(Position.TABLE_NAME);
        if (hasDataPositions==false){
            TestUtils.initPositionDB();
        }

        Boolean hasDataStatus=db.hasData(WorkFlowStatus.TABLE_NAME);
        if (hasDataStatus==false){
            TestUtils.initStatusDB();
        }
    }

    @Test
    public void  WardListTest(){
        List<Member> wardList=db.getWardList();
        Assert.assertEquals("",wardList.size(),7);

    }

    @Test
    public void positionListTest(){
        List<Position> positionList= db.getPositions();
        Assert.assertEquals("",positionList.size(),5);

    }

    @Test
    public void statusListTest(){
        List<WorkFlowStatus> statusList= db.getWorkFlowStatuses();
        Assert.assertEquals("",statusList.size(),2);

    }



}
