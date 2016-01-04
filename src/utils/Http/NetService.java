/**
 * Summary: 网络请求层封装
 * Version 1.0
 * Author: zhaomi@jugame.com.cn
 * Company: muji.com
 * Date: 13-11-5
 * Time: 下午12:38
 * Copyright: Copyright (c) 2013
 */
package cn.pedant.SafeWebViewBridge.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class NetService {
    public static String fetchHtml (String url) throws Exception{
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = HttpClientWrapper.getHttpClient().execute(httpGet);
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream is = response.getEntity().getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[4096];
                int lenRead;
                while ((lenRead = is.read(bytes)) != -1) {
                    if (lenRead > 0) {
                        baos.write(bytes, 0, lenRead);
                    }
                }
                if (baos.size() > 0) {
                    return new String(baos.toByteArray(), HTTP.UTF_8);
                }
            } else {
                android.util.Log.w("NetService", "response code not correct-------------->" + response.getStatusLine().getStatusCode());
            }
        } else {
            android.util.Log.w("NetService", "response null");
        }
        return null;
    }

}
