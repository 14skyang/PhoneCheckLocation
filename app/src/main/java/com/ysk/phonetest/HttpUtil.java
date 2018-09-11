package com.ysk.phonetest;
import android.text.LoginFilter;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用于进行网络请求
 */
public class HttpUtil {
   /* public static final String APP_KEY= "2799678a59dbc";
    public static final String URL= "http://apicloud.mob.com/v1/mobile/address/query";*/
    //发送消息到服务器
    public static PhoneBean sendPhone(String edittext){

        PhoneBean phoneBean = null;
        String gsonResult = doGet(edittext);
        Log.e("gsonResult",gsonResult);
        Gson gson = new Gson();

        if (gsonResult != null){

            try {

                phoneBean = gson.fromJson(gsonResult, PhoneBean.class);
                Log.e("phoneBean",phoneBean.getResult().getMobileNumber());
               Log.e("111", phoneBean.resultBean.getCity());
            }catch (Exception e){

            }
        }
        return phoneBean;//把值返回给phoneBean
    }
    public static String doGet(String edittext){

        String result = null;
        String url = setParmat(edittext);

        Log.e("url", url);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response=client.newCall(request).execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("result", result);
        return result;
    }

    //设置参数
    public static String setParmat(String edittext){
        String url = "";
        url = "http://apicloud.mob.com/v1/mobile/address/query?key=2799678a59dbc&phone=" + edittext;
        return url;
    }
}
