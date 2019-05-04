package com.better517na.userdataservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time;
    }
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
}
