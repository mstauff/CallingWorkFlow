package org.lds.community.CallingWorkFlow.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lds.community.CallingWorkFlow.domain.CallingBaseRecord;
import org.lds.community.CallingWorkFlow.domain.Member;

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


    public static Member parseMember( JSONObject json ) throws JSONException {
        Member member = new Member();
        member.setFirstName( json.getString( MEMBER_FIRST ) );
        member.setLastName(json.getString(MEMBER_LAST));
        member.setIndividualId(json.getLong(MEMBER_IND_ID));
        return member;

    }

    public static List<Member> parseMemberList( JSONArray jsonArray ) throws JSONException {
        List<Member> memberList = new ArrayList<Member>( jsonArray.length() );

        for (int i = 0; i < jsonArray.length(); i++) {
            memberList.add( parseMember( jsonArray.getJSONObject(i) ) );
        }

        return memberList;
    }

/*
    public static Calling parseCalling( JSONObject json ) throws JSONException {
        Calling calling = new Calling();
        calling.setIndividualId( json.getLong( CALLING_IND_ID ) );
        calling.setFirstName( json.getLong( CALLING_POS_ID ) );
        calling.setLastName( json.getString( CALLING_STATUS_NAME ) );
        return calling;
    }
*/

/*
    public static Member parsePosition( JSONObject json ) throws JSONException {
        Position position = new Position();
        position.setPositionId( json.getLong( POSITION_ID ) );

        return position;

    }
*/

}
