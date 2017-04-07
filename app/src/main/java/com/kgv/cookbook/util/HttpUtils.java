package com.kgv.cookbook.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  @author KEXUAN CHEN
 *  @time 2016/8/14  12:45
 *  @desc OkHttp http请求
 */
public class HttpUtils {

    private static HttpUtils util;
    private OkHttpClient client;

    public HttpUtils() {
        //为Http请求设置超时时间
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)  //10秒后连接超时
                .writeTimeout(10, TimeUnit.SECONDS) //写数据超时时间
                .readTimeout(30, TimeUnit.SECONDS) //读数据超时时间
                .build();
    }

    //异步Get
    public void doGet(String url, final HttpResponse httpResponse) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (httpResponse != null){
                    httpResponse.onError("连接服务器失败");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (httpResponse!= null){
                        httpResponse.onError("连接服务器失败");
                    }

                }

                String body = response.body().string();

                if(httpResponse != null){
                    //请求成功时执行抽象类的回调方法
                    httpResponse.parse(body);
                }
            }
        });
    }

    //异步post
    public void doPost(String url, HashMap<String, String> params, final HttpResponse httpResponse) {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (httpResponse != null) {
                    httpResponse.onError("连接服务器失败");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (httpResponse != null) {
                        httpResponse.onError("连接服务器失败");
                    }
                }

                String body = response.body().string();

                if (httpResponse != null) {
                    httpResponse.parse(body);
                }
            }
        });
    }

//    public String doGet(String urlPath){
//
//        String resultJsonStr = "";
//        HttpURLConnection conn = null;
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(urlPath);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == 200){
//                InputStream in = conn.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(in));
//                String line;
//                while ((line = reader.readLine()) != null){
//                    resultJsonStr += line;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null){
//                conn.disconnect();
//            }
//            if (reader != null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return resultJsonStr;
//    }

    //多线程单例
    public static HttpUtils getInstance() {
        if (util == null) {
            synchronized (HttpUtils.class) {
                if (util == null) {
                    util = new HttpUtils();
                }
            }
        }
        return util;
    }
}
