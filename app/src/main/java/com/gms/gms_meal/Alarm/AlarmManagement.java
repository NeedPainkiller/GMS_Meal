package com.gms.gms_meal.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kam6376 on 2015-06-12.
 */
public class AlarmManagement {

  AlarmManager alarmManager;

  GregorianCalendar gregorianCalendar;

  Context context;

  public AlarmManagement(Context context) {

    this.context = context;
    alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    gregorianCalendar = new GregorianCalendar();

  }

  public void setrAlarm(boolean time) {
    if (time == true) {
      gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), 11, 00);
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(true));

    } else {
      gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), 17, 00);
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(false));
    }


  }

  public void cancleAlarm(boolean time) {
    if (time == true) {
      alarmManager.cancel(getPendingIntent(true));
    } else {
      alarmManager.cancel(getPendingIntent(false));
    }

  }

  Intent i;

  PendingIntent getPendingIntent(boolean time) {
    if (time == true) {
      i = new Intent(context, AlarmLunchReciever.class);
    } else {
      i = new Intent(context, AlarmDinnerReciever.class);
    }

    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
    return pendingIntent;
  }
}
