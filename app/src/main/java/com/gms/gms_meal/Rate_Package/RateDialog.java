package com.gms.gms_meal.Rate_Package;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.andexert.library.RippleView;
import com.gms.gms_meal.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kam6376 on 2015-06-19.
 */
public class RateDialog extends Dialog implements RippleView.OnRippleCompleteListener {
  RippleView rippleFuck, rippleShit, rippleSoso, rippleGood, rippleWell;


  Context context;

  public RateDialog(Context context) {
    super(context);
    this.context = context;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.d_vote_rate);

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
    new PostRate().execute(index);

  }

  class PostRate extends AsyncTask<String, Void, String> {
    String rate;
    String date;

    @Override
    protected String doInBackground(String... params) {
      Date now = new Date();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      date = simpleDateFormat.format(now);

      rate = params[0];

      ArrayList<NameValuePair> postRateValues = new ArrayList<NameValuePair>();
      postRateValues.add(new BasicNameValuePair("rate", rate));
      postRateValues.add(new BasicNameValuePair("date", date));
      HttpClient httpClient = new DefaultHttpClient();
      HttpPost httpPost = new HttpPost("http://ppcj2.iptime.org/~kang/postRate.php");

      HttpParams httpParams = httpClient.getParams();
      httpPost.setParams(httpParams);

      HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
      HttpConnectionParams.setSoTimeout(httpParams, 5000);

      try {
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postRateValues, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        HttpResponse httpResponse =httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
//        EntityUtils.getContentCharSet(urlEncodedFormEntity);

        return EntityUtils.toString(httpEntity);

      } catch (Exception e) {
        Log.e("Rate", "is fucked : " + e.getMessage());
      }finally {
        httpPost.abort();
        return null;
      }


    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      Log.e("Rate", "is succesed : " +s);
    }

  }
}
