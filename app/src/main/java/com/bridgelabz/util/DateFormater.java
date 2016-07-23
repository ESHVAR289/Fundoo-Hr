package com.bridgelabz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {

    public static Date getDate(String s) {
        Date date = null;
        String DATE_TIME_FORMAT = "yyyy-MM-DD HH:mm:ss +05:30";
        try {
            SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_TIME_FORMAT);
            date=dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getCurrentDateString(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public static String getCurrentTimeString(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }
}
