package com.example.lenovo.baiduditu.utils;

import okhttp3.MediaType;

public class Constants {
//    private static final String URl = "10.18.42.63:8801";
private static final String URl = "zhuojiu.free.idcfengye.com";
    public static final String REGIST_URL ="http://"+URl+"/student/regist";
    public static final String VERIFY_URL = "http://"+URl+"/sms/verifySMSCode";
    public static final String mobAppKey="22d8290d63094";
    public static final String mobAppSecret="5d190cd04e968ca288e776fe376d84d6";
    public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    public static final String CHANGEPASSWORD_URL ="http://"+URl+"/student/changePassword";
    public static final String CHANGE_URL = "http://"+URl+"/student/change";
    public final static String SIGN_URL = "http://"+URl+"/sign/sign";
    public final static String QUERYVSET_URL = "http://"+URl+"/sign/queryVSet";
    public final static String RECORD_URL = "http://"+URl+"/sign/record";
    public final static String STUGETRECORD_URL = "http://"+URl+"/sign/stuGetRecords";
    public final static String LOGIN_URL = "http://"+URl+"/student/login";
}
