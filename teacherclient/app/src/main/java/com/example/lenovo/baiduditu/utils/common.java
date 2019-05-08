package com.example.lenovo.baiduditu.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by lenovo on 2017/12/11.
 */

public class common {
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(Exception e){
            return null;
        }
    } //md5

    //检验string有无特殊字符
    public static boolean isConSpeCharacters(String string) {
        // TODO Auto-generated method stub
        if(string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
            //如果不包含特殊字符
            return true;
        }
        return false;
    }  //isConSpeCharacters

    //检验手机号是否正确
    public static boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return phone.matches(telRegex);
        }
    }  //isMobileNO

    public static void myToast(Context context, String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }//myToast

    //自定义dailog
    public static void myDailog(String info, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示信息").setMessage(info).show();
    }//myDailog
    public static void setHeadBackground(Window oneWindow){
        if (Build.VERSION.SDK_INT >= 21){
        //    View decorView = getWindow().getDecorView();
            View decorView = oneWindow.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            oneWindow.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }//getProcessName


}