package com.gms.gms_meal.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gms.gms_meal.MainActivity;

/**
 * Created by kam6376 on 2015-06-12.
 */
public class AlarmLunchReciever extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    AlarmDialog alarmDialog = new AlarmDialog(MainActivity.context, true);
    try {
      alarmDialog.show();
    }catch (Exception e){

    }

  }
}
