package com.example.lenovo.baiduditu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String handTimeForm(String time){
        if(time != null){
            if(time.length()>20){
                return time.substring(0,19);
            }else {
                return time;
            }
        }
        return time;
    }
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time;
    }
    public static Date getTimeByString(String sTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse(sTime);
        return parse;
    }
}
