package com.kgv.cookbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 陈可轩 on 2016/12/19 10:31
 * Email : 18627241899@163.com
 * Description :
 */
public class PhoneCodeDbHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "PhoneCode.db";

    public static int DB_VERSION = 1;

    public static String TABLE_NAME = "phone_code";

    public static String COLUMN_PHONE = "phone";

    public static String COLUMN_HOWMUCH = "how_much";

    public static String COLUMN_TIME = "time";

    public PhoneCodeDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(_id integer primary key autoincrement,phone varchar(15),how_much integer,time integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
