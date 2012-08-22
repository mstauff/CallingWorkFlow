package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Date;

import static org.lds.mobile.domain.BaseRecord.dateToDBString;

public final class CallingBaseRecord implements BaseColumns {

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
     * Column definitions for the current record ID.
     */
    public static final String _ID = "id";
    private long id = 0;

	/**
     * Column definitions for the member's individual ID.
     */
    public static final String INDIVIDUAL_ID = "individualId";
    private long individualId = 0;

	/**
     * Column definitions for the calling ID.
     */
    public static final String CALLING_ID = "callingId";
    private long callingId = 0;

    /**
     * Column name for the calling name
     * <P>Type: TEXT</P>
     */
    public static final String CALLING_NAME = "calling_name";
    private String callingName = "";

    /**
     * reference the status id of the workflowstatus table.
     * <P>Type: Integer</P>
     */
    public static final String STATUS_ID = "status_id";
    private long statusId = 0;

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
	private java.util.Date due_date = null;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
	    CallingBaseRecord._ID + " INTEGER PRIMARY KEY," +
	    CallingBaseRecord.CALLING_NAME + " TEXT," +
	    CallingBaseRecord.INDIVIDUAL_ID + " INTEGER," +
	    CallingBaseRecord.ASSIGNED_TO + " INTEGER," +
	    CallingBaseRecord.COMPLETED + " INTEGER," +
	    CallingBaseRecord.STATUS_ID + " INTEGER," +
	    CallingBaseRecord.DUE_DATE + " TEXT " +

	    "FOREIGN KEY(status_id) REFERENCES " + WorkFlowStatusBaseRecord.TABLE_NAME + "(_id)" +
	    ");";

    static final String[] ALL_KEYS = new String[] {
        _ID,
        CALLING_NAME,
	    INDIVIDUAL_ID,
	    ASSIGNED_TO,
	    COMPLETED,
	    STATUS_ID,
	    DUE_DATE
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(CALLING_NAME, callingName);
	    values.put(DUE_DATE, dateToDBString(due_date));
        return values;
    }

    public void setContent(ContentValues values) {
        callingName = values.getAsString(CALLING_NAME);
    }

    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(_ID));
        callingName = cursor.getString(cursor.getColumnIndex(CALLING_NAME));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCallingName() {
        return callingName;
    }

    public void setCallingName(String callingName) {
        this.callingName = callingName;
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

	public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

	public Date getDueDate() {
		return (due_date != null)
			? (Date)due_date.clone()
	        : null;
    }

    public void setDueDate(Date due_date) {
	    this.due_date = (due_date != null)
	        ? (Date) due_date.clone()
			: null;
    }
}