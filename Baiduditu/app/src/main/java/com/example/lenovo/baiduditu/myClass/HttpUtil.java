package com.example.lenovo.baiduditu.myClass;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/12/17.
 */

public class HttpUtil {
    //post异步请求
    public static void postEnqueueRequest(RequestBody requestBody, String mUrl,Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(mUrl).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
    //post同步请求
    public static Response postExecuteRequest(RequestBody requestBody, String mUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(mUrl).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response;
        }
        return null;
    }



        public static void getOkHttpRequest(String mUrl, Callback callback){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(mUrl).build();
            client.newCall(request).enqueue(callback);
        }


}

