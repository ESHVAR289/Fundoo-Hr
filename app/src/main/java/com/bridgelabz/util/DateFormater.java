package com.bridgelabz.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormater {
    private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss +05:30";
    private static String TAG = DateFormater.class.getSimpleName();
    public static Date getDate(String s) {

        Date date = null;
        try {
            SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_TIME_FORMAT);
            date=dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getCurrentDateString(Date date) {
        DateFormat dateFormat=SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);
        Log.i(TAG, "getCurrentDateString: "+dateFormat.format(date));
        return dateFormat.format(date);
    }
    public static String getCurrentTimeString(Date date) {
        DateFormat dateFormat=SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        return dateFormat.format(date);
    }

    public static String serverSendDate(String date,String time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd,yyyy hh:mm aaa");
        Date date1=null;
        try {
            date1=dateFormat.parse(date+" "+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat=new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(date1);
    }
}
