package com.yongquan.listenyourvocabularies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int newVersion = 1;
    public final static String DATABASE_NAME = "yonquan";
    public final static String VOB_NAME_TABLE = "vob_name_table";
    public final static String VOB_ID_COL = "vob_id_table";
    public final static String VOB_NAME_COL = "vob_name_table";
    public final static String VOB_TYPE_COL = "vob_type_col";
    public final static String VOB_PRON_COL = "vob_pron_col";
    public final static String VOB_MEAN_COL = "vob_mean_col";
    public final static SQLiteDatabase sqliteDatabase = null;

    public static DatabaseHelper databaseHelper = null;


    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null);
        }
        return databaseHelper;
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + VOB_NAME_TABLE + "("
                + VOB_ID_COL + " INTEGER PRIMARY KEY autoincrement,"
                + VOB_NAME_COL + " TEXT,"
                + VOB_TYPE_COL + " TEXT,"
                + VOB_PRON_COL + " TEXT,"
                + VOB_MEAN_COL + " TEXT" + ")";
        // Execute script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
