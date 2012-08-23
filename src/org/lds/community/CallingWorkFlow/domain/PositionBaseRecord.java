package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class PositionBaseRecord implements BaseColumns {

	/* This class cannot be instantiated */
	PositionBaseRecord() {}

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "positions";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "position_name DESC";

	/**
     * Column definitions for the position ID.
     */
    public static final String POSITION_ID = "positionId";
    private long positionId = 0;

    /**
     * Column name for the position name
     * <P>Type: TEXT</P>
     */
    public static final String POSITION_NAME = "position_name";
    private String positionName = "";

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

	public static final String IS_DIRTY = "is_dirty";
	private Integer isDirty = 0;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + PositionBaseRecord.TABLE_NAME + " (" +
	    PositionBaseRecord._ID + " INTEGER PRIMARY KEY," +
		PositionBaseRecord.POSITION_ID + " INTEGER, " +
	    PositionBaseRecord.POSITION_NAME + " TEXT," +
	    PositionBaseRecord.ASSIGNED_TO + " INTEGER," +
	    PositionBaseRecord.COMPLETED + " INTEGER," +
	    PositionBaseRecord.DUE_DATE + " INTEGER, " +
		PositionBaseRecord.IS_DIRTY + " INTEGER " +

	    "FOREIGN KEY(status_id) REFERENCES " + WorkFlowStatusBaseRecord.TABLE_NAME + "(_id)" +
	    ");";

    static final String[] ALL_KEYS = new String[] {
        _ID,
		POSITION_ID,
        POSITION_NAME,
	    ASSIGNED_TO,
	    COMPLETED,
	    DUE_DATE,
		IS_DIRTY
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(POSITION_ID, positionId);
	    values.put(POSITION_NAME, positionName);
	    values.put(ASSIGNED_TO, assigned_to);
	    values.put(COMPLETED, completed);
	    values.put(DUE_DATE, due_date);
	    values.put(IS_DIRTY, isDirty);
        return values;
    }

    public void setContent(ContentValues values) {
        positionName = values.getAsString(POSITION_NAME);
	    positionId = values.getAsLong(POSITION_ID);
	    assigned_to = values.getAsLong(ASSIGNED_TO);
	    completed = values.getAsInteger(COMPLETED);
	    due_date = values.getAsLong(DUE_DATE);
	    isDirty = values.getAsInteger(IS_DIRTY);
    }

    public void setContent(Cursor cursor) {
        positionName = cursor.getString(cursor.getColumnIndex(POSITION_NAME));
	    positionId = cursor.getLong(cursor.getColumnIndex(POSITION_ID));
	    assigned_to = cursor.getLong(cursor.getColumnIndex(ASSIGNED_TO));
	    completed = cursor.getInt(cursor.getColumnIndex(COMPLETED));
	    due_date = cursor.getLong(cursor.getColumnIndex(DUE_DATE));
	    isDirty = cursor.getInt(cursor.getColumnIndex(IS_DIRTY));
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

	public Integer getIsDirty() {
        return isDirty;
    }

    public void setIsDirty(Integer isDirty) {
        this.isDirty = isDirty;
    }
}