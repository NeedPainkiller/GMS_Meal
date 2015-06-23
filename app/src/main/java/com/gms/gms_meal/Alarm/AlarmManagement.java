package com.gms.gms_meal.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gms.gms_meal.Alarm.AlarmDinnerReciever;
import com.gms.gms_meal.Alarm.AlarmLunchReciever;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kam6376 on 2015-06-12.
 */
public class AlarmManatPendingIY_OFgement {

  AlarmManager alarmManager;

  GregorianCalendar gregorianCalendar;

  Context context;

  public AlarmManagement(Context context) {
    this.context = context;

    alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    gregorianCalendar = new GregorianCalendar();

//        Toast.makeText(context, gregorianCalendar.get(Calendar.YEAR) + "/" + gregorianCalendar.get(Calendar.MONTH) + "/" + gregorianCalendar.get(Calendar.DAY_OF_MONTH) + "/" + gregorianCalendar.get(Calendar.HOUR_OF_DAY) + "/" + gregorianCalendar.get(Calendar.MINUTE) + 1, Toast.LENGTH_SHORT).show();

//        gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR),gregorianCalendar.get(Calendar.MONTH),gregorianCalendar.get(Calendar.DAY_OF_MONTH),11,00);
//        gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DA gre
_MONTH), gregorianCalendar.get(Calendar.HOUR_OF_DAY), gregorianCalendar.get(Calendar.MINUTE));
  }

  public void setrAlarm(boolean time) {
    if (time == true) {
      gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), 11, 00);
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(true));
    } else {

      gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), 17, 00);
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,l(ge
gorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(false));
    }


  }

  public void cancleAlarm(boolean time) {
    if (time == true) {
      alarmManager.cancenten
t(true));
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
