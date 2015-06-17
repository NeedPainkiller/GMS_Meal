package com.gms.gms_meal.tools;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by kam6376 on 2015-06-15.
 */
public class GetRatePHP extends AsyncTask<String, Void, String> {

    String params;
    private String[] rate = new String[3];

    Calendar calendar;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        this.params = params[0];
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://ppcj2.iptime.org/~kang/getRate.php");

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                Log.e("getRate", "Err");
            }
            String result = EntityUtils.toString(httpEntity);
            return result;
        } catch (Exception e) {

        }finally {
            httpClient = null;
            httpGet.abort();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String resource) {
//        super.onPostExecute(resource);
        String ratew;
        String num;
        String date;

        String jsonLine;

        try {
            if(resource == null){
                Log.e("postErr","resource is null");
                resource = "";
            }

            JSONObject root = new JSONObject(resource);
            JSONArray jsonArray = root.getJSONArray("");

        }catch (Exception e){

        }
    }
}
