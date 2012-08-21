package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public final class WorkFlowBaseRecord implements BaseColumns {

    // This class cannot be instantiated
    WorkFlowBaseRecord() {}

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "attendance";


    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "attendance_date DESC";

    /*
    * Column definitions
    */
    public static final String _ID = "id";
    private long id = 0;

    public static final String KEY_STUDENT_ID = "student_id";
    private long studentId = 0;

    public static final String KEY_CLASS_ID = "class_id";
    private long classId = 0;

    public static final String KEY_DATE = "attendance_date";
    private long attendanceDate = 0;

    public static final String KEY_STATUS = "status";
    private WorkFlowStatus status;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + WorkFlowBaseRecord.TABLE_NAME + " ("
                          + WorkFlowBaseRecord._ID + " INTEGER PRIMARY KEY,"
                          + WorkFlowBaseRecord.KEY_STUDENT_ID + " INTEGER,"
                          + WorkFlowBaseRecord.KEY_CLASS_ID + " INTEGER,"
                          + WorkFlowBaseRecord.KEY_DATE + " INTEGER,"
                          + WorkFlowBaseRecord.KEY_STATUS + " TEXT"
                          + ");";

    static final String[] ALL_KEYS = new String[] {
            _ID,
            KEY_STUDENT_ID,
            KEY_CLASS_ID,
            KEY_DATE,
            KEY_STATUS
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, studentId);
        values.put(KEY_CLASS_ID, classId);
        values.put(KEY_DATE, attendanceDate);
        values.put(KEY_STATUS, status.toString());
        return values;
    }

    public void setContent(ContentValues values) {
        studentId = values.getAsLong(KEY_STUDENT_ID);
        classId = values.getAsLong(KEY_CLASS_ID);
        attendanceDate = values.getAsLong(KEY_DATE);
        status = WorkFlowStatus.valueOf(values.getAsString(KEY_STATUS));
    }

    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(_ID));
        studentId = cursor.getLong(cursor.getColumnIndex(KEY_STUDENT_ID));
        classId = cursor.getLong(cursor.getColumnIndex(KEY_CLASS_ID));
        attendanceDate = cursor.getLong(cursor.getColumnIndex(KEY_DATE));
        status = WorkFlowStatus.valueOf(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(long attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatus status) {
        this.status = status;
    }
}
