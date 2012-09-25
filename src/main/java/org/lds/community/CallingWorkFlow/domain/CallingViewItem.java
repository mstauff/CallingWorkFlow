package org.lds.community.CallingWorkFlow.domain;

import android.database.Cursor;
import org.lds.community.CallingWorkFlow.api.DBUtil;

import java.io.Serializable;

public class CallingViewItem extends Calling {

    private String positionName = "";
    private String firstName = "";
    private String lastName = "";
    private Integer completed = 0;

    public CallingViewItem() {}

/*	CallingViewItem(CallingBaseRecord calling, PositionBaseRecord position, WorkFlowStatusBaseRecord status) {
		positionId = calling.getPositionId();
		positionName = position.getPositionName();
		individualId = calling.getIndividualId();
		firstName = calling.getStatusName();
		completed = calling.getCompleted();
		assignedTo = calling.getAssignedToId();
		dueDate = calling.getDueDate();
		isSynced = calling.getIsSynced();
		statusName = status.getStatusName();
		sequence = status.getSequence();
	}*/

/*
    public Calling getCalling() {
        return new Calling( individualId, positionId, statusName, assignedTo, dueDate, getIsSynced() );
    }
*/
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public boolean getCompleted() {
        return completed == 1;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed ? 1 : 0;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setContent(Cursor cursor) {
        super.setContent(cursor);
        positionName = DBUtil.getDbStringIfPresent(PositionBaseRecord.POSITION_NAME, cursor, "");
        firstName = DBUtil.getDbStringIfPresent(MemberBaseRecord.FIRST_NAME, cursor, "");
        lastName = DBUtil.getDbStringIfPresent(MemberBaseRecord.LAST_NAME, cursor, "");
        assignedTo = DBUtil.getDbLongIfPresent(CallingBaseRecord.ASSIGNED_TO, cursor, -1);
        completed = DBUtil.getDbIntIfPresent(WorkFlowStatusBaseRecord.IS_COMPLETE, cursor, 0);
    }

    // these functions shouldn't be needed since this doesn't map directly to a single db row
/*
	static final String[] ALL_KEYS = new String[] {
        PositionBaseRecord._ID,
		PositionBaseRecord.POSITION_ID,
		PositionBaseRecord.POSITION_NAME,
		CallingBaseRecord.INDIVIDUAL_ID,
		CallingBaseRecord.STATUS_NAME,
		CallingBaseRecord.ASSIGNED_TO,
		CallingBaseRecord.DUE_DATE,
		CallingBaseRecord.IS_SYNCED,
		CallingBaseRecord.COMPLETED,
		WorkFlowStatusBaseRecord.STATUS_NAME,
	    WorkFlowStatusBaseRecord.SEQUENCE
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(PositionBaseRecord.POSITION_ID, positionId);
	    values.put(PositionBaseRecord.POSITION_NAME, positionName);
	    values.put(CallingBaseRecord.INDIVIDUAL_ID, individualId);
        values.put(CallingBaseRecord.STATUS_NAME, firstName);
        values.put(CallingBaseRecord.ASSIGNED_TO, assignedTo);
        values.put(CallingBaseRecord.DUE_DATE, dueDate);
        values.put(CallingBaseRecord.IS_SYNCED, isSynced);
        values.put(CallingBaseRecord.COMPLETED, completed);
	    values.put(WorkFlowStatusBaseRecord.STATUS_NAME, statusName);
	    values.put(WorkFlowStatusBaseRecord.SEQUENCE, sequence);
        return values;
    }

    public void setContent(ContentValues values) {
        positionName = values.getAsString(PositionBaseRecord.POSITION_NAME);
	    positionId = values.getAsLong(PositionBaseRecord.POSITION_ID);
	    individualId = values.getAsLong(CallingBaseRecord.INDIVIDUAL_ID);
        firstName = values.getAsString(MemberBaseRecord.FIRST_NAME);
        lastName = values.getAsString(MemberBaseRecord.LAST_NAME);
        assignedTo = values.getAsLong(CallingBaseRecord.ASSIGNED_TO);
        dueDate = values.getAsLong(CallingBaseRecord.DUE_DATE);
        isSynced = values.getAsInteger(CallingBaseRecord.IS_SYNCED);
        completed = values.getAsInteger(CallingBaseRecord.COMPLETED);
	    statusName = values.getAsString(WorkFlowStatusBaseRecord.STATUS_NAME);
	    sequence = values.getAsInteger(WorkFlowStatusBaseRecord.SEQUENCE);
    }
*/

}