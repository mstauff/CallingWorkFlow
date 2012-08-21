package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
* Created with IntelliJ IDEA.
* User: matts
* Date: 8/13/12
* Time: 10:51 AM
* To change this template use File | Settings | File Templates.
*/
public final class CallingBaseRecord implements BaseColumns {

    // This class cannot be instantiated
    CallingBaseRecord() {}

    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "classes";


    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "class_name DESC";

    /*
    * Column definitions
    */
    public static final String _ID = "id";
    private long id = 0;

    /**
     * Column name for the title of the note
     * <P>Type: TEXT</P>
     */
    public static final String KEY_CLASS_NAME = "class_name";
    private String className = "";

    /**
     * class external id (most likely won't have one)
     * <P>Type: TEXT</P>
     */
    public static final String KEY_CLASS_EXTERNAL_ID = "class_external_id";
    private long classExternalId;

    /**
     * parent auxiliary name (i.e. Young Men)
     * <P>Type: TEXT</P>
     */
    public static final String KEY_AUX_NAME = "aux_name";
    private String auxiliaryName = "";

    /**
     * parent auxiliary external id
     * <P>Type: TEXT</P>
     */
    public static final String KEY_AUX_EXTERNAL_ID = "aux_external_id";
    private long auxiliaryExternalId = 0;

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + CallingBaseRecord.TABLE_NAME + " ("
            + CallingBaseRecord._ID + " INTEGER PRIMARY KEY,"
            + CallingBaseRecord.KEY_AUX_NAME + " TEXT,"
            + CallingBaseRecord.KEY_AUX_EXTERNAL_ID + " INTEGER,"
            + CallingBaseRecord.KEY_CLASS_NAME + " TEXT,"
            + CallingBaseRecord.KEY_CLASS_EXTERNAL_ID + " INTEGER"
            + ");";

    static final String[] ALL_KEYS = new String[] {
            _ID,
            KEY_CLASS_NAME,
            KEY_CLASS_EXTERNAL_ID,
            KEY_AUX_NAME,
            KEY_AUX_EXTERNAL_ID
    };

    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_CLASS_NAME, className);
        values.put(KEY_CLASS_EXTERNAL_ID, classExternalId);
        values.put(KEY_AUX_NAME, auxiliaryName);
        values.put(KEY_AUX_EXTERNAL_ID, auxiliaryExternalId);
        return values;
    }

    public void setContent(ContentValues values) {
        className = values.getAsString(KEY_CLASS_NAME);
        classExternalId = values.getAsLong(KEY_CLASS_EXTERNAL_ID);
        auxiliaryName = values.getAsString(KEY_AUX_NAME);
        auxiliaryExternalId = values.getAsLong(KEY_AUX_EXTERNAL_ID);
    }

    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(_ID));
        className = cursor.getString(cursor.getColumnIndex(KEY_CLASS_NAME));
        classExternalId = cursor.getLong(cursor.getColumnIndex(KEY_CLASS_EXTERNAL_ID));
        auxiliaryName = cursor.getString(cursor.getColumnIndex(KEY_AUX_NAME));
        auxiliaryExternalId = cursor.getLong(cursor.getColumnIndex(KEY_AUX_EXTERNAL_ID));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassExternalId() {
        return classExternalId;
    }

    public void setClassExternalId(long classExternalId) {
        this.classExternalId = classExternalId;
    }

    public String getAuxiliaryName() {
        return auxiliaryName;
    }

    public void setAuxiliaryName(String auxiliaryName) {
        this.auxiliaryName = auxiliaryName;
    }

    public long getAuxiliaryExternalId() {
        return auxiliaryExternalId;
    }

    public void setAuxiliaryExternalId(long auxiliaryExternalId) {
        this.auxiliaryExternalId = auxiliaryExternalId;
    }
}