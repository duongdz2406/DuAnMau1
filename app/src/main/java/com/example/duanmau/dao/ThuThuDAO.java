package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    public ThuThuDAO(Context context){
        dbHelper = new DbHelper(context);

    }

    // dang nhap
    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM THUTHU WHERE matt=? AND matkhau=?",new String[]{matt,matkhau});
        if(cursor.getCount()!=0){
            return true;
        }else{
            return false;
        }
    }
    public int capNhatMK(String username,String passcu,String passmoi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM THUTHU WHERE matt =? AND matkhau=?",new String[]{username,passcu});
        if (cursor.getCount()>0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau",passmoi);
           long check= sqLiteDatabase.update("THUTHU",contentValues,"matt=?",new String[]{username});
            if (check==-1){
                return -1;
            }
            return 1;
        }
        return 0;
    }
}
