package com.hexin.znkflib.support.network.impl;

import com.hexin.znkflib.support.log.ZnkfLog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 网络相关工具类
 * @author  sunxianglei@myhexin.com
 * @date 2018/7/25.
 */
final class HttpTool {

    private static final String TAG = "HttpTool";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String HTTPS = "https";

    public static String stringResponse(String url){
        return stringResponse( url, null, GET);
    }

    public static String stringResponse(String url, Map<String, String> params){
        return stringResponse(url, params, GET);
    }

    public static String stringResponse(String method, String url){
        return stringResponse(url, null, method);
    }

    public static String stringResponse( String url, Map<String, String> params, String method){
        if(GET.equals(method)){
            return stringResponseGet(url, params);
        }else if(POST.equals(method)){
            return stringResponsePost(url, params);
        }
        return "";
    }

    private static String stringResponseGet(String url, Map<String, String> params){
        BufferedReader reader = null;
        InputStream in = null;
        StringBuffer request = new StringBuffer(url);
        StringBuffer response = new StringBuffer();
        try {
            if(params != null && !params.isEmpty()){
                request.append("?");
                Iterator iter = params.entrySet().iterator();
                while(iter.hasNext()){
                    Map.Entry<String, String> entry = (Map.Entry)iter.next();
                    request.append(entry.getKey()).append("=")
                            .append(entry.getValue()!=null?URLEncoder.encode(entry.getValue(), "UTF-8"):"")
                            .append("&");
                }
                request.deleteCharAt(request.length() - 1);
            }
            URL mUrl = new URL(request.toString());
            ZnkfLog.d(TAG, "request url = " + mUrl.toString());
            HttpURLConnection urlConnection;
            // 信任所有证书，这样完全没有安全性可言，但暂时这么做
            if(url.contains(HTTPS)){
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[]{new TrustAllManager()}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new TrustHostnameVerifier());
                urlConnection = (HttpsURLConnection) mUrl.openConnection();
            }else{
                urlConnection = (HttpURLConnection) mUrl.openConnection();
            }
            urlConnection.setRequestMethod(GET);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            InputStream inputStream = urlConnection.getInputStream();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedInputStream(inputStream);
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
            }
            ZnkfLog.d(TAG, "response data = " + response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (KeyManagementException e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private static String stringResponsePost(String url, Map<String, String> params){
        BufferedReader reader = null;
        InputStream in = null;
        StringBuffer response = new StringBuffer();
        try {
            URL mUrl = new URL(url);
            HttpURLConnection urlConnection;
            // 信任所有证书，这样完全没有安全性可言，但暂时这么做
            if(url.contains(HTTPS)){
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[]{new TrustAllManager()}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new TrustHostnameVerifier());
                urlConnection = (HttpsURLConnection) mUrl.openConnection();
            }else{
                urlConnection = (HttpURLConnection) mUrl.openConnection();
            }
            urlConnection.setRequestMethod(POST);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);

            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            urlConnection.connect();
            DataOutputStream output = new DataOutputStream(urlConnection.getOutputStream());
            StringBuffer request = new StringBuffer();
            if(params != null && !params.isEmpty()){
                Iterator iter = params.entrySet().iterator();
                while(iter.hasNext()){
                    Map.Entry<String, String> entry = (Map.Entry)iter.next();
                    request.append(entry.getKey()).append("=")
                            .append(entry.getValue()!=null?URLEncoder.encode(entry.getValue(), "UTF-8"):"")
                            .append("&");
                }
                request.deleteCharAt(request.length() - 1);
            }
            output.writeBytes(request.toString());
            output.flush();
            output.close();

            InputStream inputStream = urlConnection.getInputStream();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedInputStream(inputStream);
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (KeyManagementException e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private static class TrustHostnameVerifier implements HostnameVerifier {

        /**
         * 信任所有主机-对任何证书都不做检查
         * @param hostname
         * @param session
         * @return
         */
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
