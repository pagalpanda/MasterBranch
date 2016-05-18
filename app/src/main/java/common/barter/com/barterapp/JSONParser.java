package common.barter.com.barterapp;

import android.support.annotation.NonNull;

import org.apache.http.HttpException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by amitpa on 8/23/2015.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    @NonNull
    public HttpURLConnection getHttpConnectionFromUrl(String urlR, String method) throws IOException {
        URL url = new URL(urlR);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {
        try {
            return makeHttpRequest(getHttpConnectionFromUrl(url,method),params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // function get json from url
    // by making HTTP POST or GET method
    public JSONObject makeHttpRequest(HttpURLConnection conn,
                                      HashMap<String, String> params) {

        // Making HTTP request
        try {

            String response = "";
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
                json = null;
                throw new HttpException(responseCode+"");
            }
            try {
                jObj = new JSONObject(new JSONTokener(response.toString()));
            }catch (Exception j)
            {
                jObj = null;
                j.printStackTrace();
            }

        }catch (Exception E)
        {
            E.printStackTrace();
        }


        return jObj;
// read the response

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}