package com.gms.gms_meal.tools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.gms.gms_meal.Rate_Package.RateViewFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by kam6376 on 2015-06-15.
 */
public class GetRatePHP extends AsyncTask<Void, Void, String> {


    private String[] rate = new String[3];

    Date today;
    String todayNum;
    boolean isToday = false;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {


        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://ppcj2.iptime.org/~kang/getRate.php");

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
//                Log.w("reponse", EntityUtils.toString(httpEntity));
            }
            String result = EntityUtils.toString(httpEntity);
            return result;
        } catch (Exception e) {

        } finally {
            httpClient = null;
            httpGet.abort();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String resource) {
//        super.onPostExecute(resource);
        String rate;
        String num;
        String date;



        try {
            if (resource == null) {
                Log.e("postErr", "resource is null");
                resource = "";
            }

            JSONObject root = new JSONObject(resource);
            JSONArray jsonArray = root.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rate = jsonObject.getString("rate");
                num = jsonObject.getString("num");
                date = jsonObject.getString("date");

                this.rate[0] = rate;
                this.rate[1] = num+" 명";
                this.rate[2] = date;


                Message message = RateViewFragment.getRateHandler.obtainMessage();

                if(!(todayNum==this.rate[2])&&isToday==false){
                    isToday=true;
                    Bundle todayData = new Bundle();
                    String[] addToday = new String[3];
                    addToday[0] = "0.0";
                    addToday[1] = "첫 평점을 남기세요";
                    addToday[2] = todayNum;
                    Message todayMessage = RateViewFragment.getRateHandler.obtainMessage();
                    todayData.putStringArray("rate", addToday);
                    todayMessage.setData(todayData);
                    RateViewFragment.getRateHandler.sendMessage(todayMessage);

                }

                Bundle data = new Bundle();

                data.putStringArray("rate", this.rate);
                message.setData(data);
                RateViewFragment.getRateHandler.sendMessage(message);

            }
//            Log.e("postJSON", rate+"/"+num+"/"+date);
        } catch (Exception e) {
            Log.e("postErr", "JSON is fucked");
            Log.e("postErr", e.getMessage());

        }


    }
}
