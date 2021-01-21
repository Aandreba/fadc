package Extras;

import java.util.*;

public class ExtraDate {
    public static Date init(int year, int month, int day, TimeZone tz){
        Calendar cal = new GregorianCalendar(tz);
        cal.set(year,month,day);
        return cal.getTime();
    }
    public static Date init(int year, int month, int day){
        Calendar cal = new GregorianCalendar();
        cal.set(year,month,day);
        return cal.getTime();
    }
    public static Date init(int year, int month, int day, int hour, int minute, TimeZone tz){
        Calendar cal = new GregorianCalendar(tz);
        cal.set(year,month,day,hour,minute);
        return cal.getTime();
    }
    public static Date init(int year, int month, int day, int hour, int minute){
        Calendar cal = new GregorianCalendar();
        cal.set(year,month,day,hour,minute);
        return cal.getTime();
    }
    public static Date init(int year, int month, int day, int hour, int minute, int second, TimeZone tz){
        Calendar cal = new GregorianCalendar(tz);
        cal.set(year,month,day,hour,minute,second);
        return cal.getTime();
    }
    public static Date init(int year, int month, int day, int hour, int minute, int second){
        Calendar cal = new GregorianCalendar();
        cal.set(year,month,day,hour,minute,second);
        return cal.getTime();
    }
}
