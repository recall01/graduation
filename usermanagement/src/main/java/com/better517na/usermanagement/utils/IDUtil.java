package com.better517na.usermanagement.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtil {
    public static String getID(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return "Stu"+format;
    }
}
