package org.lds.community.CallingWorkFlow.domain;

import android.provider.BaseColumns;

public final class WorkFlowStatusBaseRecord implements BaseColumns {
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

	/*********************************** Methods **********************************************/

}
