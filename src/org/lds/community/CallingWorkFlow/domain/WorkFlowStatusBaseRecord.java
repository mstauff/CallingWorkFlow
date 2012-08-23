package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class WorkFlowStatusBaseRecord implements BaseColumns {
	/* This class cannot be instantiated */
	WorkFlowStatusBaseRecord() {}

	/************************************* Fields *********************************************/
    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "WorkFlowStatusType";

	/**
	 * Column status enum
	 */
	public static final String STATUS_NAME = "status_name";
	private String status_name = "";

	/**
	 * Column status enum
	 */
	public static final String SEQUENCE = "sequence";
	private Integer sequence = 0;

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "sequence";

	/********************************* Properties *********************************************/
	public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
				    WorkFlowStatusBaseRecord._ID + " INTEGER PRIMARY KEY," +
			        WorkFlowStatusBaseRecord.STATUS_NAME + " TEXT NOT NULL," +
				    WorkFlowStatusBaseRecord.SEQUENCE + " INTEGER);";

	static final String[] ALL_KEYS = new String[] {
			_ID,
			STATUS_NAME,
			SEQUENCE
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(STATUS_NAME, status_name);
	    values.put(SEQUENCE, sequence);
        return values;
    }

    public void setContent(ContentValues values) {
        status_name = values.getAsString(STATUS_NAME);
	    sequence = values.getAsInteger(SEQUENCE);
    }

    public void setContent(Cursor cursor) {
	    status_name = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
	    sequence = cursor.getInt(cursor.getColumnIndex(SEQUENCE));
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
}
