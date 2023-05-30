package com.example.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper( Context context) {
        super(context, "QLTV", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu = "CREATE TABLE THUTHU(matt text primary key,hoten text, matkhau text)";
        db.execSQL(dbThuThu);

        String dbThanhVien = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text,namsinh text)";
        db.execSQL(dbThanhVien);

        String dbLoai = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement,tenloai text)";
        db.execSQL(dbLoai);
        String dbSach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text,giathue integer,maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);
        String dbPhieuMuon ="CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement,matv integer references THANHVIEN(matv),matt text references THUTHU(matt),masach integer references SACH(masach),ngay text, trasach integer , tienthue integer)";
        db.execSQL(dbPhieuMuon);

        //data mau
        db.execSQL("INSERT INTO LOAISACH VALUES(1,'Thieunhi'),(2,'tinhcam'),(3,'giaokhoa')");
        db.execSQL("INSERT INTO SACH VALUES(1,'DORAEMON',2500,1),(2,'conan',2000,1)");
        db.execSQL("INSERT INTO THUTHU VALUES('THUTHU1','DUONG','123'),('THUTHU2','DUNG','456')");
        db.execSQL("INSERT INTO THANHVIEN VALUES(1,'DUC','24/06/2003'),(2,'HUNG','11/04/2002')");
        db.execSQL("INSERT INTO PHIEUMUON VALUES(1,1,'THUTHU1',1,'22/06/2023',1,2550),(2,1,'THUTHU2',2,'27/06/2023',0,3550),(3,2,'THUTHU2',2,'11/06/2023',1,7550)");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!= newVersion){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
