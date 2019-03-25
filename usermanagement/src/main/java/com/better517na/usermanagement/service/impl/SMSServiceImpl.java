package com.better517na.usermanagement.service.impl;

import com.better517na.usermanagement.business.ISignBusiness;
import com.better517na.usermanagement.model.*;
import com.better517na.usermanagement.service.ISMSService;
import com.better517na.usermanagement.service.ISignService;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_SUCCESS;

@Service
public class SMSServiceImpl implements ISMSService {

    @Override
    public Response verifySMSCode(String address, String params) {
        Response response = new Response();
        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            //get result
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = inputStreamToString(conn.getInputStream());
                //解析返回结果
                SMSResponse smsResponse = new Gson().fromJson(result, SMSResponse.class);
                if (smsResponse.status == 200){
                    response.setStatus(RESPONSE_SUCCESS);
                    response.setMsg("验证成功！");
                }else {
                    response.setStatus(smsResponse.status);
                    response.setMsg("验证失败 错误代码:"+smsResponse.status);
                }
                return response;
            } else {
                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }
    //将InputStream类型转为String
    public  static  String  inputStreamToString(InputStream is)  throws IOException {
        ByteArrayOutputStream baos   =   new   ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }
}
