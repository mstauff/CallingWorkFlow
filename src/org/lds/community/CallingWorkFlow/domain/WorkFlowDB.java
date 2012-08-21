package org.lds.community.CallingWorkFlow.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/9/12
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkFlowDB {
    // Used for debugging and logging
    private static final String TAG = "WorkFlowDB";

    /**
     * The database that the provider uses as its underlying data store
     */
    private static final String DATABASE_NAME = "attendance.db";

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 2;

    private DatabaseHelper dbHelper;

    public WorkFlowDB(Context context) {
        dbHelper = new DatabaseHelper( context );
    }

    public List<Member> getStudentsForClass( long classId ) {
        Cursor results = dbHelper.getDb().query( Member.TABLE_NAME, Member.ALL_KEYS, Member.KEY_CLASS_ID + "=?",
                new String[]{String.valueOf(classId)}, null, null, Member.KEY_LAST );
        List<Member> members = new ArrayList<Member>( results.getCount() );
        while( !results.isAfterLast() ) {
            Member member = new Member();
            member.setContent(results);
            members.add(member);
            results.moveToNext();
        }
        return members;
    }

    public void saveStudents( List<Member> members) {
        SQLiteDatabase db = dbHelper.getDb();
        for( Member member : members) {
            if( member.getId() > 0 ) {
                String where = Member._ID;
                String[] id = new String[]{ String.valueOf( member.getId() )};

                db.update( Member.TABLE_NAME, member.getContentValues(), where, id );
            } else {
                db.insert( Member.TABLE_NAME, null, member.getContentValues() );
            }
        }
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private SQLiteDatabase db;
        DatabaseHelper(Context context) {

            // calls the super constructor, requesting the default cursor factory.
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         *
         * Creates the underlying database with table name and column names taken from the
         * NotePad class.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // todo - setup FK & INDEXES
            this.db = db;
            db.execSQL( CallingBaseRecord.CREATE_SQL );
            db.execSQL( MemberBaseRecord.CREATE_SQL );
            db.execSQL( WorkFlowBaseRecord.CREATE_SQL );
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

/*
      */
/**
 *
 * Initializes the provider by creating a new DatabaseHelper. onCreate() is called
 * automatically when Android creates the provider in response to a resolver request from a
 * client.
 *//*

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
