package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class CallingBaseRecord implements BaseColumns {

    /* This class cannot be instantiated */
    CallingBaseRecord() {}

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "callings";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "calling_name DESC";

	/**
     * Column definitions for the member's individual ID.
     */
    public static final String INDIVIDUAL_ID = "individualId";
    private long individualId = 0;

	/**
     * Column definitions for the position ID.
     */
    public static final String POSITION_ID = "positionId";
    private long positionId = 0;

	/**
     * reference the status id of the workflowstatus table.
     * <P>Type: Integer</P>
     */
    public static final String STATUS_NAME = "status_name";
    private String statusName = "";

	/**
     * Column name for the completed field
     * <P>Type: INTEGER</P>
     */
	public static final String COMPLETED = "completed";
	private Integer completed = 0;

	/**
     * Column name for the assigned_to field
     * <P>Type: INTEGER</P>
     */
	public static final String ASSIGNED_TO = "assigned_to";
	public long assigned_to = 0;

	/**
     * Column name for the due_date field
     * <P>Type: TEXT</P>
     */
	public static final String DUE_DATE = "due_date";
	private long due_date = 0;

	/**
     * Column name for the is_synced field
     * <P>Type: INTEGER for boolean representation</P>
     */
	public static final String IS_SYNCED = "is_synced";
	private Integer isSynced = 0;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
	    CallingBaseRecord.POSITION_ID + " INTEGER, " +
	    CallingBaseRecord.INDIVIDUAL_ID + " INTEGER, " +
		CallingBaseRecord.STATUS_NAME + " TEXT, " +
	    CallingBaseRecord.ASSIGNED_TO + " INTEGER," +
	    CallingBaseRecord.DUE_DATE + " INTEGER, " +
	    CallingBaseRecord.IS_SYNCED + " INTEGER, " +
		CallingBaseRecord.COMPLETED + " INTEGER " +
		"PRIMARY KEY (" + CallingBaseRecord.POSITION_ID + "," + CallingBaseRecord.INDIVIDUAL_ID + ")" +
	    "FOREIGN KEY(" + CallingBaseRecord.POSITION_ID + ") REFERENCES " +
		                 PositionBaseRecord.TABLE_NAME + "(" + PositionBaseRecord._ID + ")" +
	    ");";

    static final String[] ALL_KEYS = new String[] {
		POSITION_ID,
	    INDIVIDUAL_ID,
            STATUS_NAME,
	    ASSIGNED_TO,
        DUE_DATE,
        IS_SYNCED,
        COMPLETED
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(POSITION_ID, positionId);
	    values.put(INDIVIDUAL_ID, individualId);
	    values.put(STATUS_NAME, statusName);
	    values.put(ASSIGNED_TO, assigned_to);
        values.put(DUE_DATE, due_date);
        values.put(IS_SYNCED, isSynced);
	    values.put(COMPLETED, completed);
        return values;
    }

    public void setContent(ContentValues values) {
        positionId = values.getAsLong(POSITION_ID);
	    individualId = values.getAsLong(INDIVIDUAL_ID);
	    statusName = values.getAsString(STATUS_NAME);
	    assigned_to = values.getAsLong(ASSIGNED_TO);
        due_date = values.getAsLong(DUE_DATE);
        isSynced = values.getAsInteger(IS_SYNCED);
	    completed = values.getAsInteger(COMPLETED);
    }

    public void setContent(Cursor cursor) {
	    positionId = cursor.getLong(cursor.getColumnIndex(POSITION_ID));
	    individualId = cursor.getLong(cursor.getColumnIndex(INDIVIDUAL_ID));
        statusName = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
	    assigned_to = cursor.getLong(cursor.getColumnIndex(ASSIGNED_TO));
        due_date = cursor.getLong(cursor.getColumnIndex(DUE_DATE));
        isSynced = cursor.getInt(cursor.getColumnIndex(IS_SYNCED));
	    completed = cursor.getInt(cursor.getColumnIndex(COMPLETED));
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

	public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
}