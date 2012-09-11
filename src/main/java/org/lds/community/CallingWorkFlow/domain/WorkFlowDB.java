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
    public static final String CALLING_VIEW_ITEM_JOIN = "SELECT " + "p.*, "
            + "c.*,"
            + "w.*" +
            "  FROM " + PositionBaseRecord.TABLE_NAME + " p, "
            + CallingBaseRecord.TABLE_NAME + " c, "
            + WorkFlowStatusBaseRecord.TABLE_NAME + " w" +
            " WHERE p." + PositionBaseRecord.POSITION_ID + "= c." + CallingBaseRecord.POSITION_ID +
            "   AND w." + WorkFlowStatusBaseRecord.STATUS_NAME + "= c." + CallingBaseRecord.STATUS_NAME;

    private DatabaseHelper dbHelper;

    @Inject
    public WorkFlowDB(Context context) {
        dbHelper = new DatabaseHelper( context );

    }

    public List<WorkFlowStatus> getWorkFlowStatuses() {
        return getData( WorkFlowStatus.TABLE_NAME, WorkFlowStatus.class );
    }
    public void updateWorkFlowStatus(List<WorkFlowStatus> statuses) {
        updateData( WorkFlowStatus.TABLE_NAME, statuses );
    }

    public void updatePositions(List<Position> positions) {
		updateData( Position.TABLE_NAME, positions);
	}

    public List<Position> getPositions() {
        return getData( Position.TABLE_NAME, Position.class );
    }

	/*
	 * /callings/pending/<sinceDate>
	 */
	public List<CallingViewItem> getPendingCallings() {
        return getCallings( false );
    }

	/*
	 * /callings/completed/<sinceDate>
	 */
	public List<CallingViewItem> getCompletedCallings() {
        return getCallings( true );
	}

	private List<CallingViewItem> getCallings(boolean completed) {
        String completedDbValue = completed ? "1" : "0";
		String SQL = CALLING_VIEW_ITEM_JOIN + " AND c." + CallingBaseRecord.COMPLETED + "=" + completedDbValue;

		Cursor results = null;
        List<CallingViewItem> callings = new ArrayList<CallingViewItem>();
        try {
            results = dbHelper.getDb().rawQuery(SQL, null);
            results.moveToFirst();

            while( !results.isAfterLast() ) {
                CallingViewItem calling = new CallingViewItem();
                calling.setContent(results);
                callings.add(calling);
                results.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeCursor( results );
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
	/**
	 * Updates the given callings in the db.
	 */
	public void saveCallings(List<CallingBaseRecord> callings) {
		SQLiteDatabase db = dbHelper.getDb();
		for( CallingBaseRecord calling : callings) {
			String whereClause = getWhereForColumns( Calling.INDIVIDUAL_ID, Calling.POSITION_ID );
            String[] whereArgs = {String.valueOf(calling.getIndividualId()), String.valueOf(calling.getPositionId())};
            db.update(CallingBaseRecord.TABLE_NAME, calling.getContentValues(), whereClause, whereArgs);
        }
		db.close();
	}

    public List<CallingViewItem> getCallingsToSync() {
        List<CallingViewItem> callings = new ArrayList<CallingViewItem>();
        Cursor results = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String SQL = CALLING_VIEW_ITEM_JOIN + " AND c." + Calling.IS_SYNCED + "=0";
            results = db.rawQuery( SQL, null );
            results.moveToFirst();
            while(!results.isAfterLast()) {
                CallingViewItem calling = new CallingViewItem();
                calling.setContent( results );
                callings.add( calling );
                results.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeCursor(results);
        }
        return callings;
    }

    /**
     * This method nukes all existing callings in the db and replaces them with the list that is passed in. Should probably
     * not be used.
     *
     * @param callings
     */
    public void updateCallings(List<Calling> callings) {
        updateData( Calling.TABLE_NAME, callings );
    }


    /*
      * /wardlist
      */
	public List<Member> getWardList() {
        return getData( Member.TABLE_NAME, Member.class );
	}

    public void updateWardList( List<Member> memberList ) {
        updateData( Member.TABLE_NAME, memberList );
    }

    // generic/helper methods
    private <T extends BaseRecord>List<T> getData(String tableName, Class<T> clazz ) {
               List<T> resultList = new ArrayList<T>( );
               Cursor results = null;
               try {
                   results = dbHelper.getDb().query(tableName, null, null, null, null, null, null);
                   results.moveToFirst();
                   while( !results.isAfterLast() ) {
                       T record = clazz.newInstance();
                       record.setContent(results);
                       resultList.add(record);
                       results.moveToNext();
                   }
               } catch (Exception e) {
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               } finally {
                   closeCursor( results );
               }
               return resultList;


    }
    private void updateData( String tableName, List<? extends BaseRecord> data ) {
        SQLiteDatabase db = dbHelper.getDb();
        db.beginTransaction();
        try {
            db.delete(tableName, null, null);
            DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper( db, tableName);

            for( BaseRecord dataRow : data ) {
                insertHelper.insert( dataRow.getContentValues() );
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            db.endTransaction();
        }


    }

    private void closeCursor(Cursor results) {
        if( results != null ) {
            results.close();
        }
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


    public boolean hasData( String tableName ) {
        return DatabaseUtils.queryNumEntries( dbHelper.getReadableDatabase(), tableName ) > 0;
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