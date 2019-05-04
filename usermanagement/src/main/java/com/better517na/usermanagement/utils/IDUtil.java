package com.better517na.usermanagement.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtil {
    public static String getStudentID(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return "Stu"+format;
    }
    public static String getSignID(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return "Sig"+format;
    }
    public static String getSignID(String stuNumber){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String format = simpleDateFormat.format(date);
        return "Sig"+stuNumber+format;
    }
    public static String getClaID(String teaNumber){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String format = simpleDateFormat.format(date);
        return "Cla"+teaNumber+format;
    }
    public static String getSetID(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return "Set"+format;
    }
}
