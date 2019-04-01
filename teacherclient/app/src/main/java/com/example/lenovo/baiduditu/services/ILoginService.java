package com.example.lenovo.baiduditu.services;

import android.content.SharedPreferences;

import com.example.lenovo.baiduditu.model.Student;

/**
 * Created by lenovo on 2018/12/22.
 */

public interface ILoginService {
    //记住密码
    boolean rememberPassword(SharedPreferences pref,String account, String password);
    void loginRequest(Student student, String account, String password);
}
