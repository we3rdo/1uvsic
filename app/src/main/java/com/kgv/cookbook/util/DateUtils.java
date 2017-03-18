package com.kgv.cookbook.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 陈可轩 on 2017/2/9 15:00
 * Email : 18627241899@163.com
 * Description : 日期
 */
public class DateUtils {

    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String getToday() {
        Date curDate = new Date(System.currentTimeMillis());
        return dateFormatter.format(curDate);
    }

    public static String getYesterday() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return dateFormatter.format(date);
    }

    public static ArrayList<String> getLastWeek() {
        Date date = new Date();
        ArrayList<String> lastWeek = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            lastWeek.add(dateFormatter.format(date));
        }
        Collections.reverse(lastWeek);
        return lastWeek;
    }

    public static ArrayList<String> getNextWeek() {
        Date date = new Date();
        ArrayList<String> nextWeek = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
            nextWeek.add(dateFormatter.format(date));
        }
        return nextWeek;
    }

    public static ArrayList<String> getPastWeek() {
        ArrayList<String> week = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                week.add("星期日");
                week.add("星期一");
                week.add("星期二");
                week.add("星期三");
                week.add("星期四");
                week.add("星期五");
                week.add("星期六");
                break;
            case 2:
                week.add("星期一");
                week.add("星期二");
                week.add("星期三");
                week.add("星期四");
                week.add("星期五");
                week.add("星期六");
                week.add("星期日");
                break;
            case 3:
                week.add("星期二");
                week.add("星期三");
                week.add("星期四");
                week.add("星期五");
                week.add("星期六");
                week.add("星期日");
                week.add("星期一");
                break;
            case 4:
                week.add("星期三");
                week.add("星期四");
                week.add("星期五");
                week.add("星期六");
                week.add("星期日");
                week.add("星期一");
                week.add("星期二");
                break;
            case 5:
                week.add("星期四");
                week.add("星期五");
                week.add("星期六");
                week.add("星期日");
                week.add("星期一");
                week.add("星期二");
                week.add("星期三");
                break;
            case 6:
                week.add("星期五");
                week.add("星期六");
                week.add("星期日");
                week.add("星期一");
                week.add("星期二");
                week.add("星期三");
                week.add("星期四");
                break;
            case 7:
                week.add("星期六");
                week.add("星期日");
                week.add("星期一");
                week.add("星期二");
                week.add("星期三");
                week.add("星期四");
                week.add("星期五");
                break;
        }
        return week;
    }
}
