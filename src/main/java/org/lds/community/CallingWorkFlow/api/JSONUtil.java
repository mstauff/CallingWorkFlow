package org.lds.community.CallingWorkFlow.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/22/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONUtil {
    public static final String MEMBER_FIRST = "firstName";
    public static final String MEMBER_LAST = "lastName";
    public static final String MEMBER_IND_ID = "individualId";

    public static final String POSITION_ID = "positionId";
    public static final String POSITION_NAME = "positionName";

    public static final String STATUS_NAME = "statusName";
    public static final String STATUS_ORDER = "sortOrder";
    public static final String STATUS_COMPLETE= "isComplete";

    public static final String CALLING_IND_ID = "individualId";
    public static final String CALLING_POS_ID = "positionId";
    public static final String CALLING_STATUS_NAME = "statusName";

    public static final String CALLING_OBJ = "calling";

    public static Member parseMember( JSONObject json ) throws JSONException {
        Member member = new Member();
        member.setFirstName( json.getString( MEMBER_FIRST ) );
        member.setLastName(json.getString(MEMBER_LAST));
        member.setIndividualId(json.getLong(MEMBER_IND_ID));

        return member;

    }

    public static List<Member> parseMemberList( JSONArray jsonArray ) throws JSONException {
        List<Member> memberList = new ArrayList<Member>( jsonArray.length() );
        final int numJsonObjects = jsonArray.length();
        for (int i = 0; i < numJsonObjects; i++) {
            memberList.add( parseMember( jsonArray.getJSONObject(i) ) );
        }

        return memberList;
    }

   public static List<Calling> parseCallings( JSONArray jsonArray ) throws JSONException {
        List<Calling> callingList = new ArrayList<Calling>( jsonArray.length() );

       final int numJsonObjects = jsonArray.length();
       for (int i = 0; i < numJsonObjects; i++) {
            callingList.add(parseCalling(jsonArray.getJSONObject(i)));
        }

        return callingList;
    }

    public static Calling parseCalling( JSONObject json ) throws JSONException {
        Calling calling = new Calling();
        calling.setIndividualId(json.getLong(CALLING_IND_ID));
        calling.setPositionId(json.getLong(CALLING_POS_ID));
        calling.setStatusName( json.getString( CALLING_STATUS_NAME ) );
        return calling;
    }

    public static Position parsePosition( JSONObject json ) throws JSONException {
        Position position = new Position();
        position.setPositionId(json.getLong(POSITION_ID));
        position.setPositionName(json.getString(POSITION_NAME));

        return position;

    }

    public static List<Position> parsePositionIds(JSONArray jsonArray) throws JSONException {
        List<Position> positionList = new ArrayList<Position>( jsonArray.length() );

        final int numJsonObjects = jsonArray.length();
        for (int i = 0; i < numJsonObjects; i++) {
            positionList.add(parsePosition(jsonArray.getJSONObject(i)));
        }

        return positionList;

    }

    public static WorkFlowStatus parseStatus( JSONObject json ) throws JSONException {
        WorkFlowStatus status = new WorkFlowStatus();
        status.setStatusName( json.getString( STATUS_NAME ) );
        status.setSequence(json.getInt(STATUS_ORDER));
        status.setComplete(json.getBoolean(STATUS_COMPLETE));

        return status;

    }

    public static List<WorkFlowStatus> parseStatuses(JSONArray jsonArray) throws JSONException {
        List<WorkFlowStatus> statusList = new ArrayList<WorkFlowStatus>( jsonArray.length() );

        final int numJsonObjects = jsonArray.length();
        for (int i = 0; i < numJsonObjects; i++) {
            statusList.add(parseStatus(jsonArray.getJSONObject(i)));
        }

        return statusList;

    }

}
