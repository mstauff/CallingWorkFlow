package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;

public class CallingViewItem {

	private long positionId = 0;
	private String positionName = "";
	private long individualId = 0;
    private String statusName = "";
	private Integer completed = 0;
	private long assigned_to = 0;
	private long due_date = 0;
	private Integer isSynced = 0;
	private String status_name = "";
	private Integer sequence = 0;

	CallingViewItem() {}

	CallingViewItem(CallingBaseRecord calling, PositionBaseRecord position, WorkFlowStatusBaseRecord status) {
		positionId = calling.getPositionId();
		positionName = position.getPositionName();
		individualId = calling.getIndividualId();
		statusName = calling.getStatusName();
		completed = calling.getCompleted();
		assigned_to = calling.getAssignedToId();
		due_date = calling.getDueDate();
		isSynced = calling.getIsSynced();
		status_name = status.getStatusName();
		sequence = status.getSequence();
	}

	public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

	public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

	public long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(long individualId) {
        this.individualId = individualId;
    }

	public long getAssignedToId() {
        return assigned_to;
    }

    public void setAssignedTo(long assigned_to) {
        this.assigned_to = assigned_to;
    }

	public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

	public long getDueDate() {
		return this.due_date;
    }

    public void setDueDate(long due_date) {
	    this.due_date = due_date;
    }

	public Integer getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Integer isSynced) {
        this.isSynced = isSynced;
    }

	public String getStatusName() {
		return this.status_name;
	}

	public void setStatusName(String status_name) {
		this.status_name = status_name;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

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
        values.put(CallingBaseRecord.STATUS_NAME, statusName);
        values.put(CallingBaseRecord.ASSIGNED_TO, assigned_to);
        values.put(CallingBaseRecord.DUE_DATE, due_date);
        values.put(CallingBaseRecord.IS_SYNCED, isSynced);
        values.put(CallingBaseRecord.COMPLETED, completed);
	    values.put(WorkFlowStatusBaseRecord.STATUS_NAME, status_name);
	    values.put(WorkFlowStatusBaseRecord.SEQUENCE, sequence);
        return values;
    }

    public void setContent(ContentValues values) {
        positionName = values.getAsString(PositionBaseRecord.POSITION_NAME);
	    positionId = values.getAsLong(PositionBaseRecord.POSITION_ID);
	    individualId = values.getAsLong(CallingBaseRecord.INDIVIDUAL_ID);
        statusName = values.getAsString(CallingBaseRecord.STATUS_NAME);
        assigned_to = values.getAsLong(CallingBaseRecord.ASSIGNED_TO);
        due_date = values.getAsLong(CallingBaseRecord.DUE_DATE);
        isSynced = values.getAsInteger(CallingBaseRecord.IS_SYNCED);
        completed = values.getAsInteger(CallingBaseRecord.COMPLETED);
	    status_name = values.getAsString(WorkFlowStatusBaseRecord.STATUS_NAME);
	    sequence = values.getAsInteger(WorkFlowStatusBaseRecord.SEQUENCE);
    }

    public void setContent(Cursor cursor) {
        positionName = cursor.getString(cursor.getColumnIndex(PositionBaseRecord.POSITION_NAME));
	    positionId = cursor.getLong(cursor.getColumnIndex(PositionBaseRecord.POSITION_ID));
	    individualId = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.INDIVIDUAL_ID));
        statusName = cursor.getString(cursor.getColumnIndex(CallingBaseRecord.STATUS_NAME));
        assigned_to = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.ASSIGNED_TO));
        due_date = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.DUE_DATE));
        isSynced = cursor.getInt(cursor.getColumnIndex(CallingBaseRecord.IS_SYNCED));
        completed = cursor.getInt(cursor.getColumnIndex(CallingBaseRecord.COMPLETED));
	    status_name = cursor.getString(cursor.getColumnIndex(WorkFlowStatusBaseRecord.STATUS_NAME));
	    sequence = cursor.getInt(cursor.getColumnIndex(WorkFlowStatusBaseRecord.SEQUENCE));
    }
}