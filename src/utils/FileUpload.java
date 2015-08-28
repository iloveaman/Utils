package com.kocla.preparationtools.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2015/7/6.
 */
public class FileUpload {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static final String TAG = "uploadFile";


    private static final int TIME_OUT = 10 * 1000; // 超时时间


    private static final String CHARSET = "utf-8"; // 设置编码


    /**
     * Android上传文件到服务端
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型


        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */


                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                // if(res==200)
                // {
                Log.e(TAG, "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.e(TAG, "result : " + result);
                // }
                // else{
                // Log.e(TAG, "request error");
                // }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param url    Service net address
     * @param params text content
     * @param files  pictures
     * @return String result of Service response
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params, Map<String, File> files)
            throws IOException {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null) {
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\"" + file.getValue().getName() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());
                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(LINEND.getBytes());
            }
        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        //----------------------------------------
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder sb1 = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb1.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
        //----------------------------------------
    }


    /**
     * 以HttpClient的DoGet方式向服务器发送请数据 （带上cookiestore）
     *
     * @param map  传递进来的数据，以map的形式进行了封装
     * @param path 要求服务器servlet的地址
     * @return 返回的boolean类型的参数
     * @throws Exception
     */
    public static String submitDataByHttpClientDoGet(Map<String, String> map, String path, Context context) throws Exception, IOException {
        DefaultHttpClient hc = new DefaultHttpClient();
        // 请求路径
        StringBuilder sb = new StringBuilder(path);
        if (map != null) {
            sb.append("?");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        String str = sb.toString();
        System.out.println(str);
        str = str.replaceAll(" ", "%20");

        System.out.println("str = " + str);
        HttpGet httpGet = new HttpGet(str);

        CookieStore cookieStore = new BasicCookieStore();
        hc.setCookieStore(cookieStore);
        HttpResponse response = hc.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }

    /**
     * 可获得cookies
     * @param map
     * @param path
     * @param callBack
     */
    public static void doGetWithCookie(final Map<String, String> map, final String path, final FileUploadCallBack callBack) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient hc = new DefaultHttpClient();
                // 请求路径
                StringBuilder sb = new StringBuilder(path);
                if (map != null) {
                    sb.append("?");
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        sb.append(entry.getKey()).append("=").append(entry.getValue());
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                String str = sb.toString();
                System.out.println(str);
                str = str.replaceAll(" ", "%20");
                System.out.println("str = " + str);
                HttpGet httpGet = new HttpGet(str);
                CookieStore cookieStore = new BasicCookieStore();
                hc.setCookieStore(cookieStore);
                HttpResponse response = null;
                try {
                    response = hc.execute(httpGet);
                    //StringBuilder stringBuilder = getStringBuilder(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    callBack.onError(response.getStatusLine().getStatusCode(), e);
                    Looper.loop();
                }
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // return EntityUtils.toString(response.getEntity());
                    try {
                        String response_ = EntityUtils.toString(response.getEntity());
                        Looper.prepare();
                        callBack.onSucceed(response_);
                        callBack.onSucceed(cookieStore, response_);
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onError(response.getStatusLine().getStatusCode(), e);
                    }
                } else {
                    Looper.prepare();
                    callBack.onFailed(response.getStatusLine().getStatusCode());
                    Looper.loop();
                }
                //return null;
            }
        });
    }

    /**
     * 可放入cookie
     * @param map
     * @param path
     * @param callBack
     */
    public static void doGetWithPutCookie(final CookieStore cookieStore,final Map<String, String> map, final String path, final FileUploadCallBack callBack) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient hc = new DefaultHttpClient();
                // 请求路径
                StringBuilder sb = new StringBuilder(path);
                if (map != null) {
                    sb.append("?");
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        sb.append(entry.getKey()).append("=").append(entry.getValue());
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                String str = sb.toString();
                System.out.println(str);
                str = str.replaceAll(" ", "%20");

                System.out.println("str = " + str);
                HttpGet httpGet = new HttpGet(str);
                hc.setCookieStore(cookieStore);
                HttpResponse response = null;
                try {
                    response = hc.execute(httpGet);
                } catch (IOException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    callBack.onError(response.getStatusLine().getStatusCode(), e);
                    Looper.loop();
                }
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        String response_ = EntityUtils.toString(response.getEntity());
                        Looper.prepare();
                        callBack.onSucceed(response_);
                        callBack.onSucceed(cookieStore, response_);
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Looper.prepare();
                        callBack.onError(response.getStatusLine().getStatusCode(), e);
                        Looper.loop();
                    }
                } else {
                    Looper.prepare();
                    callBack.onFailed(response.getStatusLine().getStatusCode());
                    Looper.loop();
                }
            }
        });
    }

    /**
     * 获取返回值内容
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static StringBuilder getStringBuilder(HttpResponse response) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HTTP.UTF_8));

        String s = null;
        while ((s = bufferedReader.readLine()) != null) {
            builder.append(s);
        }
        return builder;
    }
}

