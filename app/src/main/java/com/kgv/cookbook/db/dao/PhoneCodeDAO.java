package com.kgv.cookbook.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kgv.cookbook.db.PhoneCodeDbHelper;
import com.kgv.cookbook.config.AppUtils;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2016/12/19 10:45
 * Email : 18627241899@163.com
 * Description : 控制手机验证码请求次数
 */
public class PhoneCodeDAO {

    private PhoneCodeDbHelper dbHelper;

    public PhoneCodeDAO(Context context) {
        dbHelper = new PhoneCodeDbHelper(context);
    }

    public boolean isExist(String phone) {
        boolean exist = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(PhoneCodeDbHelper.TABLE_NAME, new String[]{PhoneCodeDbHelper.COLUMN_HOWMUCH},
                    "phone=?", new String[]{phone}, null, null, null);
            exist = cursor.moveToNext();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AppUtils.closeIO(cursor,db);
        }
        return exist;
    }

    /**
     * 增加新手机号
     */
    public boolean addNewPhone(String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneCodeDbHelper.COLUMN_PHONE, phone);
        values.put(PhoneCodeDbHelper.COLUMN_HOWMUCH, 3);
        values.put(PhoneCodeDbHelper.COLUMN_TIME, System.currentTimeMillis());
        long newId = db.insert(PhoneCodeDbHelper.TABLE_NAME, null, values);
        AppUtils.closeIO(db);
        return newId != -1;
    }

    /**
     * 获取指定手机号剩余次数
     */
    public int getCodeNumber(String phone) {
        int number = -1;
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            cursor = db.query(PhoneCodeDbHelper.TABLE_NAME, new String[]{PhoneCodeDbHelper.COLUMN_HOWMUCH},
                    "phone=?", new String[]{phone}, null, null, null);
            if (cursor.moveToNext()) {
                number = cursor.getInt(cursor.getColumnIndex(PhoneCodeDbHelper.COLUMN_HOWMUCH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AppUtils.closeIO(cursor,db);
        }
        return number;
    }

    /**
     * 修改指定手机号次数
     */
    public boolean updateCodeNumber(String phone, int number) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneCodeDbHelper.COLUMN_HOWMUCH, number);
        int update = db.update(PhoneCodeDbHelper.TABLE_NAME, values, "phone=?", new String[]{phone});
        AppUtils.closeIO(db);
        return update != 0;
    }

    /**
     * 刷新指定手机号次数
     */
    public boolean refreshCodeNumber(long time) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneCodeDbHelper.COLUMN_HOWMUCH, 3);
        values.put(PhoneCodeDbHelper.COLUMN_TIME, System.currentTimeMillis());
        int update = db.update(PhoneCodeDbHelper.TABLE_NAME, values, "time=?", new String[]{time + ""});
        AppUtils.closeIO(db);
        return update != 0;
    }

    /**
     * 获取所有存在时间
     */
    public ArrayList<Long> getAllTime() {
        ArrayList<Long> times = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(PhoneCodeDbHelper.TABLE_NAME, new String[]{PhoneCodeDbHelper.COLUMN_TIME}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                long time = cursor.getLong(cursor.getColumnIndex(PhoneCodeDbHelper.COLUMN_TIME));
                times.add(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AppUtils.closeIO(cursor,db);
        }
        return times;
    }


}
