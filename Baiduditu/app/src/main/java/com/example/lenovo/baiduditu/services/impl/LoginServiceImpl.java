package com.example.lenovo.baiduditu.services.impl;

import android.content.SharedPreferences;

import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.services.ILoginService;

import org.json.JSONObject;

/**
 * Created by lenovo on 2018/12/22.
 */

public class LoginServiceImpl implements ILoginService {
    private final static String LOGIN_URL = "http://39.106.151.156:8080/user/student/login";
    private SharedPreferences.Editor editor;
    @Override
    public boolean rememberPassword(SharedPreferences pref,String account, String password) {
        editor = pref.edit();
        try {
            editor.putString("account",account);
            editor.putString("password",password);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void loginRequest(Student stud,String account, String password) {

    }
    private void parseJSONWithJSONObject(Student student,String jsonData) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status==200){
                JSONObject js = jsonObject.getJSONObject("data");
                student.setStuName(js.getString("stuName"));
                student.setStuId(js.getString("stuId"));
                student.setStuSex(js.getInt("stuSex"));
                student.setStuNumber(js.getString("stuNumber"));
                student.setStuPassword(js.getString("stuPassword"));
                student.setStuPhone(js.getString("stuPhone"));
                student.setStuMail(js.getString("stuMail"));
                student.setClassName(js.getString("className"));
                student.setRegisterTime(js.getString("registerTime"));
                student.setPermissions(js.getString("permissions"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
