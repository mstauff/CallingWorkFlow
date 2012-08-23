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
    public static final String STATUS_ID = "status_id";
    private long statusId = 0;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
	    CallingBaseRecord.POSITION_ID + " INTEGER PRIMARY KEY, " +
	    CallingBaseRecord.INDIVIDUAL_ID + " INTEGER, " +
		CallingBaseRecord.STATUS_ID + " INTEGER " +

	    "FOREIGN KEY(" + CallingBaseRecord.POSITION_ID + ") REFERENCES " +
		                 PositionBaseRecord.TABLE_NAME + "(" + PositionBaseRecord._ID + ")" +
	    ");";

    static final String[] ALL_KEYS = new String[] {
		POSITION_ID,
	    INDIVIDUAL_ID,
		STATUS_ID
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(POSITION_ID, positionId);
	    values.put(INDIVIDUAL_ID, individualId);
	    values.put(STATUS_ID, statusId);
        return values;
    }

    public void setContent(ContentValues values) {
        positionId = values.getAsLong(POSITION_ID);
	    individualId = values.getAsLong(INDIVIDUAL_ID);
	    statusId = values.getAsLong(STATUS_ID);
    }

    public void setContent(Cursor cursor) {
	    positionId = cursor.getLong(cursor.getColumnIndex(POSITION_ID));
	    individualId = cursor.getLong(cursor.getColumnIndex(INDIVIDUAL_ID));
	    statusId = cursor.getLong(cursor.getColumnIndex(STATUS_ID));
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
}