/*
 * Copyright (c) 2015 Kam6512. All Rights Reserved.
 * https://github.com/kam6512/GMS_Meal
 */

package com.gms.gms_meal.Rate_Package;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;

import com.andexert.library.RippleView;
import com.gms.gms_meal.R;
import com.gms.gms_meal.SERVER_SECURITY;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kam6376 on 2015-06-19.
 */
public class RateDialog extends Dialog implements RippleView.OnRippleCompleteListener {
  private RippleView rippleFuck, rippleShit, rippleSoso, rippleGood, rippleWell;


  private Context context;
  private View v;

  public RateDialog(Context context, View v) {
    super(context);
    this.context = context;
    this.v = v;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.d_vote_rate);
    Snackbar.make(v, "평점을 선택하세요", Snackbar.LENGTH_SHORT).show();
    rippleFuck = (RippleView) findViewById(R.id.rateRippleFuck);
    rippleFuck.setOnRippleCompleteListener(this);
    rippleShit = (RippleView) findViewById(R.id.rateRippleShit);
    rippleShit.setOnRippleCompleteListener(this);
    rippleSoso = (RippleView) findViewById(R.id.rateRippleSoso);
    rippleSoso.setOnRippleCompleteListener(this);
    rippleGood = (RippleView) findViewById(R.id.rateRippleGood);
    rippleGood.setOnRippleCompleteListener(this);
    rippleWell = (RippleView) findViewById(R.id.rateRippleWell);
    rippleWell.setOnRippleCompleteListener(this);


  }

  @Override
  public void onComplete(RippleView rippleView) {
    String index = "0";
    switch (rippleView.getId()) {
      case R.id.rateRippleFuck:
        index = "1";
        break;
      case R.id.rateRippleShit:
        index = "2";
        break;
      case R.id.rateRippleSoso:
        index = "3";
        break;
      case R.id.rateRippleGood:
        index = "4";
        break;
      case R.id.rateRippleWell:
        index = "5";
        break;

    }
    Snackbar.make(v, index + "점 업로딩..", Snackbar.LENGTH_SHORT).show();
    new PostRate().execute(index);

  }

  class PostRate extends AsyncTask<String, Void, Boolean> {
    String rate;
    String date;

    @Override
    protected Boolean doInBackground(String... params) {
      Date now = new Date();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
      date = simpleDateFormat.format(now);

      rate = params[0];

      ArrayList<NameValuePair> postRateValues = new ArrayList<NameValuePair>();
      postRateValues.add(new BasicNameValuePair("rate", rate));
      postRateValues.add(new BasicNameValuePair("date", date));
      HttpClient httpClient = new DefaultHttpClient();
      HttpPost httpPost = new HttpPost(SERVER_SECURITY.POST_RATE);

      HttpParams httpParams = httpClient.getParams();
      httpPost.setParams(httpParams);

      HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
      HttpConnectionParams.setSoTimeout(httpParams, 5000);

      try {
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postRateValues, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        httpClient.execute(httpPost);
//        return EntityUtils.getContentCharSet(urlEncodedFormEntity);
        return true;

      } catch (Exception e) {
//        Log.e("Rate", "is fucked : " + e.getMessage());
        return false;
      } finally {
        dismiss();
        httpPost.abort();
      }


    }

    @Override
    protected void onPostExecute(Boolean s) {
      super.onPostExecute(s);
      if (s == true) {
        Snackbar.make(v, "등록되었습니다.", Snackbar.LENGTH_SHORT).show();
      } else {
        Snackbar.make(v, "등록 실패.", Snackbar.LENGTH_SHORT).show();
      }

    }

  }
}
