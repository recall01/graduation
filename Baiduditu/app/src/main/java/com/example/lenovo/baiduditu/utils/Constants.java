package com.example.lenovo.baiduditu.utils;

import okhttp3.MediaType;

public class Constants {
    public static final String REGIST_URL ="http://10.18.42.63:8801/student/regist";
    public static final String VERIFY_URL = "http://10.18.42.63:8801/sms/verifySMSCode";
    public static final String mobAppKey="22d8290d63094";
    public static final String mobAppSecret="5d190cd04e968ca288e776fe376d84d6";
    public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    public static final String CHANGEPASSWORD_URL ="http://10.18.42.63:8801/student/changePassword";
}
