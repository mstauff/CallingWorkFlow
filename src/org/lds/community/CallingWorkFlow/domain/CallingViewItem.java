package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;

public class CallingViewItem {

	private long positionId = 0;
	private String positionName = "";
	private long individualId = 0;
    private long statusId = 0;
	private Integer completed = 0;
	public long assigned_to = 0;
	private long due_date = 0;
	private Integer isSynced = 0;

	CallingViewItem() {}

	CallingViewItem(CallingBaseRecord calling, PositionBaseRecord position) {
		positionId = calling.getPositionId();
		positionName = position.getPositionName();
		individualId = calling.getIndividualId();
		statusId = calling.getStatusId();
		completed = calling.getCompleted();
		assigned_to = calling.assigned_to;
		due_date = calling.getDueDate();
		isSynced = calling.getIsSynced();
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

	public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
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

	static final String[] ALL_KEYS = new String[] {
        PositionBaseRecord._ID,
		PositionBaseRecord.POSITION_ID,
		PositionBaseRecord.POSITION_NAME,
		CallingBaseRecord.INDIVIDUAL_ID,
		CallingBaseRecord.STATUS_ID,
		CallingBaseRecord.ASSIGNED_TO,
		CallingBaseRecord.DUE_DATE,
		CallingBaseRecord.IS_SYNCED,
		CallingBaseRecord.COMPLETED
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(PositionBaseRecord.POSITION_ID, positionId);
	    values.put(PositionBaseRecord.POSITION_NAME, positionName);
	    values.put(CallingBaseRecord.INDIVIDUAL_ID, individualId);
        values.put(CallingBaseRecord.STATUS_ID, statusId);
        values.put(CallingBaseRecord.ASSIGNED_TO, assigned_to);
        values.put(CallingBaseRecord.DUE_DATE, due_date);
        values.put(CallingBaseRecord.IS_SYNCED, isSynced);
        values.put(CallingBaseRecord.COMPLETED, completed);
        return values;
    }

    public void setContent(ContentValues values) {
        positionName = values.getAsString(PositionBaseRecord.POSITION_NAME);
	    positionId = values.getAsLong(PositionBaseRecord.POSITION_ID);
	    individualId = values.getAsLong(CallingBaseRecord.INDIVIDUAL_ID);
        statusId = values.getAsLong(CallingBaseRecord.STATUS_ID);
        assigned_to = values.getAsLong(CallingBaseRecord.ASSIGNED_TO);
        due_date = values.getAsLong(CallingBaseRecord.DUE_DATE);
        isSynced = values.getAsInteger(CallingBaseRecord.IS_SYNCED);
        completed = values.getAsInteger(CallingBaseRecord.COMPLETED);
    }

    public void setContent(Cursor cursor) {
        positionName = cursor.getString(cursor.getColumnIndex(PositionBaseRecord.POSITION_NAME));
	    positionId = cursor.getLong(cursor.getColumnIndex(PositionBaseRecord.POSITION_ID));
	    individualId = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.INDIVIDUAL_ID));
        statusId = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.STATUS_ID));
        assigned_to = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.ASSIGNED_TO));
        due_date = cursor.getLong(cursor.getColumnIndex(CallingBaseRecord.DUE_DATE));
        isSynced = cursor.getInt(cursor.getColumnIndex(CallingBaseRecord.IS_SYNCED));
        completed = cursor.getInt(cursor.getColumnIndex(CallingBaseRecord.COMPLETED));
    }
}