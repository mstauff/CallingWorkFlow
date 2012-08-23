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

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + PositionBaseRecord.TABLE_NAME + " (" +
	    PositionBaseRecord._ID + " INTEGER PRIMARY KEY, " +
		PositionBaseRecord.POSITION_ID + " INTEGER, " +
	    PositionBaseRecord.POSITION_NAME + " TEXT, " +

	    "FOREIGN KEY(status_id) REFERENCES " + WorkFlowStatusBaseRecord.TABLE_NAME + "(_id)" +
	    ");";

    static final String[] ALL_KEYS = new String[] {
        _ID,
		POSITION_ID,
        POSITION_NAME
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(POSITION_ID, positionId);
	    values.put(POSITION_NAME, positionName);
        return values;
    }

    public void setContent(ContentValues values) {
        positionName = values.getAsString(POSITION_NAME);
	    positionId = values.getAsLong(POSITION_ID);
    }

    public void setContent(Cursor cursor) {
        positionName = cursor.getString(cursor.getColumnIndex(POSITION_NAME));
	    positionId = cursor.getLong(cursor.getColumnIndex(POSITION_ID));
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
}