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

    /**
     * Column name for the lds.org individual id
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String INDIVIDUAL_ID = "individual_id";
    private long individualId = 0;

    /**
     * <P>Type: TEXT</P>
     */
    public static final String LAST_NAME = "last_name";
    private String lastName = "";

    /**
     * Column name of the note content
     * <P>Type: TEXT</P>
     */
    public static final String FIRST_NAME = "first_name";
    private String firstName = "";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + MemberBaseRecord.TABLE_NAME + " ("
                          + MemberBaseRecord._ID + " INTEGER PRIMARY KEY,"
                          + MemberBaseRecord.INDIVIDUAL_ID + " INTEGER,"
                          + MemberBaseRecord.LAST_NAME + " TEXT,"
                          + MemberBaseRecord.FIRST_NAME + " TEXT"
                          + ");";

    static final String[] ALL_KEYS = new String[] {
        _ID,
        INDIVIDUAL_ID,
        FIRST_NAME,
        LAST_NAME
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(INDIVIDUAL_ID, individualId);
        values.put(LAST_NAME, lastName);
        values.put(FIRST_NAME, firstName);
        return values;
    }

    public void setContent(ContentValues values) {
        individualId = values.getAsLong(INDIVIDUAL_ID);
        lastName = values.getAsString(LAST_NAME);
        firstName = values.getAsString(FIRST_NAME);
    }

    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(_ID));
        individualId = cursor.getLong(cursor.getColumnIndex(INDIVIDUAL_ID));
        lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
        firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
