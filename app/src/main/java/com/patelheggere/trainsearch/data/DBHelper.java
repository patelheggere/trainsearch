package com.patelheggere.trainsearch.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "trainconnections.db";
    private int rating = 0;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public static String[] projectionForTrainConnections(){
        return new String[]{
                SQLTableCreater.TrainConnections._ID,
                SQLTableCreater.TrainConnections.COLUMN_NAME_CONNECTIONS
        };
    }

    //Query to create Table for School Notice board message
    private static final String SQL_CREATE_TRAINCONNECTIONS = "CREATE TABLE " + SQLTableCreater.TrainConnections.TABLE_NAME + " (" +
            SQLTableCreater.TrainConnections._ID + " INTEGER," +
            SQLTableCreater.TrainConnections.COLUMN_NAME_CONNECTIONS + " TEXT)";
    //end of query
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRAINCONNECTIONS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.execSQL(SQL_CREATE_TRAINCONNECTIONS);
    }

    public static SQLiteDatabase writeToSQLDB(SQLiteDatabase dbWrite, String connections) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLTableCreater.TrainConnections.COLUMN_NAME_CONNECTIONS, connections);
            long ret = dbWrite.insert(SQLTableCreater.TrainConnections.TABLE_NAME, null, contentValues);
            Log.d(TAG, "writeToSQLDB: "+ret);

        return dbWrite;
    }






    public static List<String> readFromSQLDB(SQLiteDatabase dbRead)
    {
        List<String> connectionlist= new ArrayList<>();
        String selection = "SELECT  * FROM " + SQLTableCreater.TrainConnections.TABLE_NAME ;
        String[] selectionArgs = {  };
        Cursor cursor = dbRead.rawQuery(selection, null);

        /*Cursor cursor = dbRead.query(
                SQLTableCreater.TrainConnections.TABLE_NAME,
                projectionForTrainConnections(),
                selection,
                null,
                null,
                null,
                null
        );*/
        while (cursor.moveToNext()) {
            connectionlist.add(cursor.getString(cursor.getColumnIndexOrThrow(SQLTableCreater.TrainConnections.COLUMN_NAME_CONNECTIONS)));
            System.out.println("Column values:\n"+cursor.getString(cursor.getColumnIndexOrThrow(SQLTableCreater.TrainConnections.COLUMN_NAME_CONNECTIONS)));
            /*schoolMessage.setId(cursor.getString(cursor.getColumnIndexOrThrow(SQLTableCreater.NoticeBoardSchoolMessages.COLUMN_NAME_MESSAGE_ID)));
            schoolMessage.setSubject(cursor.getString(cursor.getColumnIndexOrThrow(SQLTableCreater.NoticeBoardSchoolMessages.COLUMN_NAME_SUBJECT)));
            schoolMessage.setDateTimeSent(cursor.getString(cursor.getColumnIndexOrThrow(SQLTableCreater.NoticeBoardSchoolMessages.COLUMN_NAME_DATETIME)));
            schoolMessage.setUnRead(cursor.getInt(cursor.getColumnIndexOrThrow(SQLTableCreater.NoticeBoardSchoolMessages.COLUMN_NAME_UNREAD)));*/
            //Log.d(TAG, "readFromSQLDB: "+schoolMessage.getSubject()+"Date:"+schoolMessage.getDateTimeSent()+"unread:"+schoolMessage.getUnRead());
        }
        dbRead.close();
        return connectionlist;
    }

    public static void deleteFromDB(SQLiteDatabase dbRead, SQLiteDatabase dbWrite, DBHelper mDbHelper, String id)
    {
        dbWrite = mDbHelper.getWritableDatabase();
        String selection = SQLTableCreater.TrainConnections._ID + " = ?";
        String[] selectionArgs = { id };
        int ret = dbWrite.delete(SQLTableCreater.TrainConnections.TABLE_NAME, selection, selectionArgs);
        dbWrite.close();
    }
}
