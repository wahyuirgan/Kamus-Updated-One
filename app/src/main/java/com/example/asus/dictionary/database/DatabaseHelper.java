package com.example.asus.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.asus.dictionary.database.DatabaseContract.KamusColumns.FIELD_MEANING;
import static com.example.asus.dictionary.database.DatabaseContract.KamusColumns.FIELD_WORD;
import static com.example.asus.dictionary.database.DatabaseContract.TABLE_ENG_TO_INA;
import static com.example.asus.dictionary.database.DatabaseContract.TABLE_INA_KE_ENG;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME         = "dbdictionary";
    private static final int DATABASE_VERSION   = 1;

    private static String CREATE_TABLE_INA_KE_ENG = "create table "+TABLE_INA_KE_ENG+
            " ("+_ID+" integer primary key autoincrement, " +
            FIELD_WORD+" text not null, " +
            FIELD_MEANING+" text not null);";

    private static String CREATE_TABLE_ENG_TO_INA = "create table "+TABLE_ENG_TO_INA+
            " ("+_ID+" integer primary key autoincrement, " +
            FIELD_WORD+" text not null, " +
            FIELD_MEANING+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INA_KE_ENG);
        db.execSQL(CREATE_TABLE_ENG_TO_INA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INA_KE_ENG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENG_TO_INA);
        onCreate(db);
    }
}
