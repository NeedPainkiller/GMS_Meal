/*
 * Copyright (c) 2015 Kam6512. All Rights Reserved.
 * https://github.com/kam6512/GMS_Meal
 */

package com.gms.gms_meal.tools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.gms.gms_meal.Rate_Package.RateViewFragment;
import com.gms.gms_meal.SERVER_SECURITY;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by kam6376 on 2015-06-15.
 */
public class GetRatePHP extends AsyncTask<Void, Void, String> {

  @Override
  protected void onPreExecute() {
    super.onPreExecute();

  }

  @Override
  protected String doInBackground(Void... params) {

    HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(SERVER_SECURITY.GET_RATE);

    try {
      HttpResponse httpResponse = httpClient.execute(httpGet);
      HttpEntity httpEntity = httpResponse.getEntity();

      if (httpEntity != null) {
//        Log.w("reponse", "is null");
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
    super.onPostExecute(resource);

    Bundle data = new Bundle();
//    Log.e("resource", resource);

    data.putString("rate", resource);

    Message message = RateViewFragment.getRateHandler.obtainMessage();
    message.setData(data);
    RateViewFragment.getRateHandler.sendMessage(message);
  }
}
