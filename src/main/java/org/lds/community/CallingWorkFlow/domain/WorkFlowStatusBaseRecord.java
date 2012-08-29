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
	private String statusName = "";

	/**
	 * Column status enum
	 */
	public static final String SEQUENCE = "sequence";
	private Integer sequence = 0;

	public static final String IS_COMPLETE = "is_complete";
	private Integer isComplete = 0;

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "sequence";

	/********************************* Properties *********************************************/
	public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
			        WorkFlowStatusBaseRecord.STATUS_NAME + " TEXT PRIMARY KEY," +
			        WorkFlowStatusBaseRecord.IS_COMPLETE + " INTEGER," +
				    WorkFlowStatusBaseRecord.SEQUENCE + " INTEGER);";

	static final String[] ALL_KEYS = new String[] {
			STATUS_NAME,
			SEQUENCE,
            IS_COMPLETE
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
	    values.put(STATUS_NAME, statusName);
	    values.put(IS_COMPLETE, isComplete);
	    values.put(SEQUENCE, sequence);
        return values;
    }

    public void setContent(ContentValues values) {
        statusName = values.getAsString(STATUS_NAME);
	    sequence = values.getAsInteger(SEQUENCE);
    }

    public void setContent(Cursor cursor) {
	    statusName = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
	    sequence = cursor.getInt(cursor.getColumnIndex(SEQUENCE));
    }

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String status_name) {
		this.statusName = status_name;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

    public Integer getComplete() {
        return isComplete;
    }

    public void setComplete(Integer complete) {
        isComplete = complete;
    }
}
