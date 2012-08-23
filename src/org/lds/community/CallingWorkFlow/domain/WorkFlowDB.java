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
			String whereClause = "WHERE individualId=" + calling.getIndividualId() +
					             "  AND positionId=" + calling.getPositionId();
			db.delete(CallingBaseRecord.TABLE_NAME, whereClause, null);
            db.insert( CallingBaseRecord.TABLE_NAME, null, calling.getContentValues() );
        }
		db.close();
	}

	public void savePositions(List<PositionBaseRecord> positions) {
		SQLiteDatabase db = dbHelper.getDb();
		for( PositionBaseRecord position : positions) {
			String whereClause = "WHERE positionId=" + position.getPositionId();
			db.delete(PositionBaseRecord.TABLE_NAME, whereClause, null);
            db.insert( PositionBaseRecord.TABLE_NAME, null, position.getContentValues() );
        }
		db.close();
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
	public List<CallingViewItem> getPendingCallings() {
		String SQL = "SELECT " + PositionBaseRecord.TABLE_NAME + ".*, " + CallingBaseRecord.TABLE_NAME + ".*" +
				     "  FROM " + PositionBaseRecord.TABLE_NAME + ", " + CallingBaseRecord.TABLE_NAME +
				     " WHERE " + PositionBaseRecord.TABLE_NAME + "." + CallingBaseRecord.COMPLETED + "= 0" +
				     "   AND " + PositionBaseRecord._ID + "=" + CallingBaseRecord.POSITION_ID;

		Cursor results = dbHelper.getDb().rawQuery(SQL, null);

        List<CallingViewItem> callings = new ArrayList<CallingViewItem>( results.getCount() );
        while( !results.isAfterLast() ) {
	        CallingViewItem calling = new CallingViewItem();
	        calling.setContent(results);
	        callings.add(calling);
            results.moveToNext();
        }
        return callings;
	}

	/*
	 * /callings/completed/<sinceDate>
	 */
	public List<CallingViewItem> getCompletedCallings() {
		String SQL = "SELECT " + PositionBaseRecord.TABLE_NAME + ".*, " + CallingBaseRecord.TABLE_NAME + ".*" +
				     "  FROM " + PositionBaseRecord.TABLE_NAME + ", " + CallingBaseRecord.TABLE_NAME +
				     " WHERE " + PositionBaseRecord.TABLE_NAME + "." + CallingBaseRecord.COMPLETED + "= 1" +
				     "   AND " + PositionBaseRecord._ID + "=" + CallingBaseRecord.POSITION_ID;

		Cursor results = dbHelper.getDb().rawQuery(SQL, null);

        List<CallingViewItem> callings = new ArrayList<CallingViewItem>( results.getCount() );
        while( !results.isAfterLast() ) {
	        CallingViewItem calling = new CallingViewItem();
	        calling.setContent(results);
	        callings.add(calling);
            results.moveToNext();
        }
        return callings;
	}

	/*
	 * /wardlist
	 */
	public List<MemberBaseRecord> getWardList() {
		String SQL = "SELECT " + MemberBaseRecord.TABLE_NAME + ".* " +
				     "  FROM " + MemberBaseRecord.TABLE_NAME;
		Cursor results = dbHelper.getDb().rawQuery(SQL, null);
		List<MemberBaseRecord> members = new ArrayList<MemberBaseRecord>( results.getCount() );
        while( !results.isAfterLast() ) {
	        MemberBaseRecord member = new MemberBaseRecord();
	        member.setContent(results);
	        members.add(member);
            results.moveToNext();
        }
        return members;
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
	        db.execSQL( PositionBaseRecord.CREATE_SQL );
            db.execSQL( CallingBaseRecord.CREATE_SQL );
            db.execSQL( MemberBaseRecord.CREATE_SQL );
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
}