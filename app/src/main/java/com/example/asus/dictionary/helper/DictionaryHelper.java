package com.example.asus.dictionary.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.asus.dictionary.model.DictionaryModel;
import com.example.asus.dictionary.R;
import com.example.asus.dictionary.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static com.example.asus.dictionary.database.DatabaseContract.KamusColumns.FIELD_MEANING;
import static com.example.asus.dictionary.database.DatabaseContract.KamusColumns.FIELD_WORD;
import static com.example.asus.dictionary.database.DatabaseContract.TABLE_ENG_TO_INA;
import static com.example.asus.dictionary.database.DatabaseContract.TABLE_INA_KE_ENG;

public class DictionaryHelper {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DictionaryHelper(Context context){
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<DictionaryModel> getDataByName(String cari, String selection){

        String category;
        Cursor cursor;
        if(Objects.equals(selection, "Eng")){
            cursor = database.query(TABLE_ENG_TO_INA,null,FIELD_WORD+" LIKE ?",new String[]{cari.trim()+"%"},null,null,_ID + " ASC",null);
            category = context.getResources().getString(R.string.engtoina);
        }else{
            cursor = database.query(TABLE_INA_KE_ENG,null,FIELD_WORD+" LIKE ?",new String[]{cari.trim()+"%"},null,null,_ID + " ASC",null);
            category = context.getResources().getString(R.string.inakeeng);
        }
        cursor.moveToFirst();

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)));
                dictionaryModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MEANING)));
                dictionaryModel.setCategory(category);

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<DictionaryModel> getAllData(String selection){
        Cursor cursor;
        String category;
        if(Objects.equals(selection, "Eng")){
            cursor = database.query(TABLE_ENG_TO_INA,null,null,null,null,null,_ID+ " ASC",null);
            category = context.getResources().getString(R.string.engtoina);
        }else{
            cursor = database.query(TABLE_INA_KE_ENG,null,null,null,null,null,_ID+ " ASC",null);
            category = context.getResources().getString(R.string.inakeeng);
        }
        cursor.moveToFirst();

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)));
                dictionaryModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MEANING)));
                dictionaryModel.setCategory(category);

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public long insert(DictionaryModel kamusModel, String selection){
        String table;
        if(Objects.equals(selection, "Eng")){
            table = TABLE_ENG_TO_INA;
        }else{
            table = TABLE_INA_KE_ENG;
        }

        ContentValues initialValues =  new ContentValues();
        initialValues.put(FIELD_WORD, kamusModel.getWord());
        initialValues.put(FIELD_MEANING, kamusModel.getDescription());
        return database.insert(table, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void insertTransaction(DictionaryModel kamusModel, String selection){
        String table;
        if(Objects.equals(selection, "Eng")){
            table = TABLE_ENG_TO_INA;
        }else{
            table = TABLE_INA_KE_ENG;
        }

        String sql = "INSERT INTO "+table+" ("+FIELD_WORD+", "+FIELD_MEANING
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getWord());
        stmt.bindString(2, kamusModel.getDescription());
        stmt.execute();
        stmt.clearBindings();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int update(DictionaryModel dictionaryModel, String selection){
        String table;
        if(Objects.equals(selection, "Eng")){
            table = TABLE_ENG_TO_INA;
        }else{
            table = TABLE_INA_KE_ENG;
        }

        ContentValues args = new ContentValues();
        args.put(FIELD_WORD, dictionaryModel.getWord());
        args.put(FIELD_MEANING, dictionaryModel.getDescription());
        return database.update(table, args, _ID + "= '" + dictionaryModel.getId() + "'", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int delete(int id, String selection){
        String table;
        if(Objects.equals(selection, "Eng")){
            table = TABLE_ENG_TO_INA;
        }else{
            table = TABLE_INA_KE_ENG;
        }

        return database.delete(table, _ID + " = "+id+" ", null);
    }

}
