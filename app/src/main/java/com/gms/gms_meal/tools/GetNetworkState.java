package com.gms.gms_meal.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kam6376 on 2015-06-07.
 */
public class GetNetworkState {
  ConnectivityManager networkManager;
  NetworkInfo mobile;
  NetworkInfo wifi;

  public GetNetworkState(Context context) {
    networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    mobile = networkManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    wifi = networkManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

  }

  public int getState() {
    if (mobile.isConnected()) {
      return 0;
    } else if (wifi.isConnected()) {
      return 1;
    } else {
      return 2;
    }
  }
}
