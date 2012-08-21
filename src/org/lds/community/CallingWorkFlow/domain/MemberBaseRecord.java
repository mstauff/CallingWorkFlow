package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * MemberBaseRecord table contract
 */
public class MemberBaseRecord implements BaseColumns {

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "member";


    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "last_name DESC";

    /*
    * Column definitions
    */
    public static final String _ID = "_id";
    private long id = 0;

    public static final String KEY_CLASS_ID = "class_id";
    private long classId = 0;

    /**
     * Column name for the lds.org individual id
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String KEY_INDIVIDUAL_ID = "individual_id";
    private long individualId = 0;

    /**
     * <P>Type: TEXT</P>
     */
    public static final String KEY_LAST = "last_name";
    private String lastName = "";

    /**
     * Column name of the note content
     * <P>Type: TEXT</P>
     */
    public static final String KEY_FIRST = "first_name";
    private String firstName = "";

    /**
     * Column name for the creation timestamp
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
//    public static final String COLUMN_NAME_ACTIVE_DATE = "start_on";

    /**
     * Column name for the modification timestamp
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
//    public static final String COLUMN_NAME_INACTIVE_DATE = "ends_on";
    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + MemberBaseRecord.TABLE_NAME + " ("
                          + MemberBaseRecord._ID + " INTEGER PRIMARY KEY,"
                          + MemberBaseRecord.KEY_CLASS_ID + " INTEGER,"
                          + MemberBaseRecord.KEY_INDIVIDUAL_ID + " INTEGER,"
                          + MemberBaseRecord.KEY_LAST + " TEXT,"
                          + MemberBaseRecord.KEY_FIRST + " TEXT"
                          + ");";

    static final String[] ALL_KEYS = new String[] {
            _ID,
            KEY_CLASS_ID,
            KEY_INDIVIDUAL_ID,
            KEY_FIRST,
            KEY_LAST
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_CLASS_ID, classId);
        values.put(KEY_INDIVIDUAL_ID, individualId);
        values.put(KEY_LAST, lastName);
        values.put(KEY_FIRST, firstName);
        return values;
    }

    public void setContent(ContentValues values) {
        individualId = values.getAsLong(KEY_INDIVIDUAL_ID);
        classId = values.getAsLong(KEY_CLASS_ID);
        lastName = values.getAsString(KEY_LAST);
        firstName = values.getAsString(KEY_FIRST);
    }

    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(_ID));
        individualId = cursor.getLong(cursor.getColumnIndex(KEY_INDIVIDUAL_ID));
        classId = cursor.getLong(cursor.getColumnIndex(KEY_CLASS_ID));
        lastName = cursor.getString(cursor.getColumnIndex(KEY_LAST));
        firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRST));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(long individualId) {
        this.individualId = individualId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
