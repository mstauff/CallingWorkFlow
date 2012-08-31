package org.lds.community.CallingWorkFlow.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;
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

    @Inject
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

    public List<CallingViewItem> getCallingsToSync() {
        List<CallingViewItem> callings = new ArrayList<CallingViewItem>();
        Cursor results = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // todo - need to change this query to have all the data for a CallingViewItem, not just Calling
            results = db.query( CallingBaseRecord.TABLE_NAME, null, CallingBaseRecord.IS_SYNCED + "=false", null, null, null, null );
            results.moveToFirst();
            while(!results.isAfterLast()) {
                CallingViewItem calling = new CallingViewItem();
                calling.setContent( results );
                callings.add( calling );
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeCursor(results);
        }
        return callings;
    }

    private void closeCursor(Cursor results) {
        if( results != null ) {
            results.close();
        }
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
		String SQL = "SELECT " + PositionBaseRecord.TABLE_NAME + ".*, " + CallingBaseRecord.TABLE_NAME + ".*"
				               + WorkFlowStatusBaseRecord.TABLE_NAME + ".*" +
				     "  FROM " + PositionBaseRecord.TABLE_NAME + ", " + CallingBaseRecord.TABLE_NAME +
				     " WHERE " + PositionBaseRecord.TABLE_NAME + "" + CallingBaseRecord.COMPLETED + "= 0" +
				     "   AND " + PositionBaseRecord._ID + "=" + CallingBaseRecord.POSITION_ID +
				     "   AND " + WorkFlowStatusBaseRecord._ID + "=" + CallingBaseRecord.STATUS_NAME;

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
		String SQL = "SELECT " + PositionBaseRecord.TABLE_NAME + ".*, " + CallingBaseRecord.TABLE_NAME + ".*"
				               + WorkFlowStatusBaseRecord.TABLE_NAME + ".*" +
				     "  FROM " + PositionBaseRecord.TABLE_NAME + ", " + CallingBaseRecord.TABLE_NAME +
				     " WHERE " + PositionBaseRecord.TABLE_NAME + "" + CallingBaseRecord.COMPLETED + "= 1" +
				     "   AND " + PositionBaseRecord._ID + "=" + CallingBaseRecord.POSITION_ID +
				     "   AND " + WorkFlowStatusBaseRecord._ID + "=" + CallingBaseRecord.STATUS_NAME;

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

    public boolean updateCalling( Calling calling ) {
        int result = 0;
        try {
            String whereClause = getWhereForColumns( Calling.INDIVIDUAL_ID, Calling.POSITION_ID );
            result = dbHelper.getWritableDatabase().update( Calling.TABLE_NAME, calling.getContentValues(), whereClause,
                    new String[]{String.valueOf(calling.getIndividualId()), String.valueOf(calling.getPositionId())} );
        } catch (Exception e) {
            Log.w(TAG, "Exception updating calling: " + e.toString() );
        }
        return result > 0;
    }

    public static String getWhereForColumns(String... columnNames) {
        StringBuilder whereClause = new StringBuilder();
        for( String columnName : columnNames ) {
            whereClause.append( columnName + "=?, " );
        }
        // remove extra ", " from the last element
        if( whereClause.length() > 2 ) {
            whereClause.delete( whereClause.length() -2, whereClause.length() );
        }
        return whereClause.toString();
    }

    /*
      * /wardlist
      */
	public List<Member> getWardList() {
        List<Member> members = new ArrayList<Member>( );
        Cursor results = null;
        try {
            results = dbHelper.getDb().query(MemberBaseRecord.TABLE_NAME, null, null, null, null, null, null);
            results.moveToFirst();
            while( !results.isAfterLast() ) {
                Member member = new Member();
                member.setContent(results);
                members.add(member);
                results.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeCursor( results );
        }
        return members;
	}

    public void updateWardList( List<Member> memberList ) {
        SQLiteDatabase db = dbHelper.getDb();
        db.beginTransaction();
        try {
            db.delete(MemberBaseRecord.TABLE_NAME, null, null);
            DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper( db, Member.TABLE_NAME);

            for( Member member : memberList ) {
                insertHelper.insert( member.getContentValues() );
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            db.endTransaction();
        }


    }

    public boolean hasMemberData() {
        return DatabaseUtils.queryNumEntries( dbHelper.getReadableDatabase(), Member.TABLE_NAME ) > 0;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private SQLiteDatabase db;
        DatabaseHelper(Context context) {

            /* Calls the super constructor, requesting the default cursor factory. */
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // todo - does this need to change to have a writeable instance only where necessary?
            // use readable everywhere else?
            db = super.getWritableDatabase();
        }

        /**
         * Creates the underlying database with table name and column names taken from the
         * NotePad class.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // todo - setup INDEXES
            this.db = db;
            db.execSQL( WorkFlowStatusBaseRecord.CREATE_SQL );
            db.execSQL( MemberBaseRecord.CREATE_SQL );
            db.execSQL( PositionBaseRecord.CREATE_SQL );
            db.execSQL( CallingBaseRecord.CREATE_SQL );
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