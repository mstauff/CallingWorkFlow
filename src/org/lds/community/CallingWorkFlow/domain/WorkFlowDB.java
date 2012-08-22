package org.lds.community.CallingWorkFlow.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowDB {
    /* Used for debugging and logging */
    private static final String TAG = "WorkFlowDB";

    /**
     * The database that the provider uses as its underlying data store
     */
    private static final String DATABASE_NAME = "callingWorkFlow.db";

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 2;

    private DatabaseHelper dbHelper;

    public WorkFlowDB(Context context) {
        dbHelper = new DatabaseHelper( context );
    }

	/*
	 * /calling/<ind id>/update?callingId=<#>&status=<text>&date=<long>&byWho=<indId> optional POST body with history data
	 */
	public void saveCallings(List<CallingBaseRecord> callings) {
		SQLiteDatabase db = dbHelper.getDb();
		for( CallingBaseRecord calling : callings) {
            if( calling.getId() > 0 ) {
                String where = CallingBaseRecord._ID;
                String[] id = new String[]{ String.valueOf(calling.getId()) };

                db.update( CallingBaseRecord.TABLE_NAME, calling.getContentValues(), where, id );
            } else {
                db.insert( CallingBaseRecord.TABLE_NAME, null, calling.getContentValues() );
            }
        }
	}

	/*
	 * /callingId
	 */
	public Long getCallingIds(List<CallingBaseRecord> calling) {
		return null;
	}

	/*
	 * /callings/pending/<sinceDate>
	 */
	public List<CallingBaseRecord> getPendingCallings() {
		String SQL = "SELECT " + CallingBaseRecord.TABLE_NAME + ".* " +
				     "  FROM " + CallingBaseRecord.TABLE_NAME + ", " + WorkFlowStatusBaseRecord.TABLE_NAME +
				     " WHERE " + CallingBaseRecord.TABLE_NAME + "." + CallingBaseRecord.STATUS_ID + "=" +
				                 WorkFlowStatusBaseRecord.TABLE_NAME + "." + WorkFlowStatusBaseRecord._ID +
				     "   AND " + WorkFlowStatusBaseRecord.STATUS_NAME + "!=" + WorkFlowStatus.SET_APART;
		Cursor results = dbHelper.getDb().rawQuery(SQL, null);

        List<CallingBaseRecord> callings = new ArrayList<CallingBaseRecord>( results.getCount() );
        while( !results.isAfterLast() ) {
	        CallingBaseRecord calling = new CallingBaseRecord();
            calling.setContent(results);
            callings.add(calling);
            results.moveToNext();
        }
        return callings;
	}

	/*
	 * /callings/completed/<sinceDate>
	 */
	public List<CallingBaseRecord> getCompletedCallings() {
		String SQL = "SELECT " + CallingBaseRecord.TABLE_NAME + ".* " +
				     "  FROM " + CallingBaseRecord.TABLE_NAME + ", " + WorkFlowStatusBaseRecord.TABLE_NAME +
				     " WHERE " + CallingBaseRecord.TABLE_NAME + "." + CallingBaseRecord.STATUS_ID + "=" +
				                 WorkFlowStatusBaseRecord.TABLE_NAME + "." + WorkFlowStatusBaseRecord._ID +
				     "   AND " + WorkFlowStatusBaseRecord.STATUS_NAME + "==" + WorkFlowStatus.SET_APART;
		Cursor results = dbHelper.getDb().rawQuery(SQL, null);

        List<CallingBaseRecord> callings = new ArrayList<CallingBaseRecord>( results.getCount() );
        while( !results.isAfterLast() ) {
	        CallingBaseRecord calling = new CallingBaseRecord();
            calling.setContent(results);
            callings.add(calling);
            results.moveToNext();
        }
        return callings;
	}

	/*
	 * /wardlist
	 */
	public List<Member> getWardList() {
		return null;
	}

    static class DatabaseHelper extends SQLiteOpenHelper {

        private SQLiteDatabase db;
        DatabaseHelper(Context context) {

            /* Calls the super constructor, requesting the default cursor factory. */
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Creates the underlying database with table name and column names taken from the
         * NotePad class.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // todo - setup INDEXES
            this.db = db;
            db.execSQL( CallingBaseRecord.CREATE_SQL );
            db.execSQL( MemberBaseRecord.CREATE_SQL );
            db.execSQL( WorkFlowBaseRecord.CREATE_SQL );
	        db.execSQL( WorkFlowStatusBaseRecord.CREATE_SQL );
        }

        /**
         *
         * Demonstrates that the provider must consider what happens when the
         * underlying datastore is changed. In this sample, the database is upgraded the database
         * by destroying the existing data.
         * A real application should upgrade the database in place.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // for now - do nothing
        }

        public SQLiteDatabase getDb() {
            return db;
        }
    }

/**
 *
 * Initializes the provider by creating a new DatabaseHelper. onCreate() is called
 * automatically when Android creates the provider in response to a resolver request from a
 * client.
 */
/*

      @Override
      public boolean onCreate() {

          // Creates a new helper object. Note that the database itself isn't opened until
          // something tries to access it, and it's only created if it doesn't already exist.
          dbHelper = new DatabaseHelper(getContext());

          // Assumes that any failures will be reported by a thrown exception.
          return true;
      }
*/
}