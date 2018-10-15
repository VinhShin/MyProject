package com.yongquan.listenyourvocabularies.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yongquan.listenyourvocabularies.MainActivity.VobModel;

import java.util.ArrayList;
import java.util.List;

public class WorkWithDB {
    public static void addVob(Activity context, VobModel vobModel){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.VOB_NAME_COL, vobModel.getName());
        values.put(DatabaseHelper.VOB_TYPE_COL, vobModel.getType());
        values.put(DatabaseHelper.VOB_PRON_COL, vobModel.getPron());
        values.put(DatabaseHelper.VOB_MEAN_COL, vobModel.getMean());
        //Neu de null thi khi value bang null thi loi
        db.insert(DatabaseHelper.VOB_NAME_TABLE,null,values);
        db.close();
    }
    public static List<VobModel> getAllVob(Context context){
        List<VobModel> vobModelList = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+DatabaseHelper.VOB_NAME_TABLE+"",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VOB_NAME_COL));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VOB_TYPE_COL));
                String mean = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VOB_MEAN_COL));
                String pron = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VOB_PRON_COL));
                vobModelList.add(new VobModel(name,type,pron,mean));
                cursor.moveToNext();
            }
        }
        db.close();
        return vobModelList;


    }
}
