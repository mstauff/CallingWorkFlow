package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class CallingBaseRecord implements BaseRecord, Serializable{

    public CallingBaseRecord() {}

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "calling";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "calling_name DESC";

    /**
     * Column definitions for the member's individual ID.
     */
    public static final String INDIVIDUAL_ID = "mem_individual_id";
    private long individualId = 0;

    /**
     * Column definitions for the position ID.
     */
    public static final String POSITION_ID = "pos_position_id";
    private long positionId = 0;

    /**
     * reference the status id of the workflowstatus table.
     * <P>Type: Integer</P>
     */
    public static final String STATUS_NAME = "workflow_status_name";
    private String statusName = "";

    /**
     * Column name for the assignedTo field
     * <P>Type: INTEGER</P>
     */
    public static final String ASSIGNED_TO = "assigned_to";
    public long assignedTo = 0;

    /**
     * Column name for the dueDate field
     * <P>Type: TEXT</P>
     */
    public static final String DUE_DATE = "due_date";
    private long dueDate = 0;

    /**
     * Column name for the is_synced field
     * <P>Type: INTEGER for boolean representation</P>
     */
    public static final String IS_SYNCED = "is_synced";
    private Integer isSynced = 0;

    public static final String MARKED_FOR_DELETE = "to_be_deleted";
    private Integer markedForDelete = 0;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " (" +
            CallingBaseRecord.POSITION_ID + " INTEGER NULL, " +
            CallingBaseRecord.INDIVIDUAL_ID + " INTEGER NULL, " +
            CallingBaseRecord.STATUS_NAME + " TEXT, " +
            CallingBaseRecord.ASSIGNED_TO + " INTEGER," +
            CallingBaseRecord.DUE_DATE + " INTEGER, " +
            CallingBaseRecord.IS_SYNCED + " INTEGER, " +
            CallingBaseRecord.MARKED_FOR_DELETE + " INTEGER, " +
            "PRIMARY KEY(" + CallingBaseRecord.POSITION_ID + "," + CallingBaseRecord.INDIVIDUAL_ID + "), " +
            "FOREIGN KEY(" + CallingBaseRecord.POSITION_ID + ") REFERENCES " +
            PositionBaseRecord.TABLE_NAME + "(" + PositionBaseRecord.POSITION_ID + "), " +
            "FOREIGN KEY(" + CallingBaseRecord.INDIVIDUAL_ID + ") REFERENCES " +
            MemberBaseRecord.TABLE_NAME + "(" + MemberBaseRecord.INDIVIDUAL_ID + ") " +
            ");";

    static final String[] ALL_KEYS = new String[] {
            POSITION_ID,
            INDIVIDUAL_ID,
            STATUS_NAME,
            ASSIGNED_TO,
            DUE_DATE,
            IS_SYNCED,
            MARKED_FOR_DELETE
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(POSITION_ID, positionId);
        values.put(INDIVIDUAL_ID, individualId);
        values.put(STATUS_NAME, statusName);
        values.put(ASSIGNED_TO, assignedTo);
        values.put(DUE_DATE, dueDate);
        values.put(IS_SYNCED, isSynced);
        values.put(MARKED_FOR_DELETE, markedForDelete);
        return values;
    }

    public void setContent(ContentValues values) {
        positionId = values.getAsLong(POSITION_ID);
        individualId = values.getAsLong(INDIVIDUAL_ID);
        statusName = values.getAsString(STATUS_NAME);
        assignedTo = values.getAsLong(ASSIGNED_TO);
        dueDate = values.getAsLong(DUE_DATE);
        isSynced = values.getAsInteger(IS_SYNCED);
        markedForDelete = values.getAsInteger(MARKED_FOR_DELETE);
    }

    public void setContent(Cursor cursor) {
        positionId = cursor.getLong(cursor.getColumnIndex(POSITION_ID));
        individualId = cursor.getLong(cursor.getColumnIndex(INDIVIDUAL_ID));
        statusName = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
        assignedTo = cursor.getLong(cursor.getColumnIndex(ASSIGNED_TO));
        dueDate = cursor.getLong(cursor.getColumnIndex(DUE_DATE));
        isSynced = cursor.getInt(cursor.getColumnIndex(IS_SYNCED));
        markedForDelete = cursor.getInt(cursor.getColumnIndex(MARKED_FOR_DELETE));
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
        return assignedTo;
    }

    public void setAssignedTo(long assigned_to) {
        this.assignedTo = assigned_to;
    }

    public long getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(long due_date) {
        this.dueDate = due_date;
    }

    public boolean getIsSynced() {
        return isSynced == 1;
    }

    public void setIsSynced( boolean isSynced ) {
        this.isSynced = isSynced ? 1 : 0;
    }

    public boolean getMarkedForDelete() {
        return markedForDelete == 1;
    }

    public void setMarkedForDelete( boolean toDelete ) {
        this.markedForDelete = toDelete ? 1 : 0;
    }

    @Override
    public String toString() {
        return "CallingBaseRecord{" +
                "individualId=" + individualId +
                ", positionId=" + positionId +
                ", statusName='" + statusName + '\'' +
                ", assignedTo=" + assignedTo +
                ", dueDate=" + dueDate +
                ", isSynced=" + isSynced +
                ", markedForDelete=" + markedForDelete +
                '}';
    }
}