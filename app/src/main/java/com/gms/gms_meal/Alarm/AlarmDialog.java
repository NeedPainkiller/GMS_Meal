package com.gms.gms_meal.Alarm;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.gms.gms_meal.DB.DataBaseAdmin;
import com.gms.gms_meal.R;

import java.text.SimpleDateFormat;
import java.util.Date;

//import is.arontibo.library.ElasticDownloadView;

/**
 * Created by kam6376 on 2015-06-10.
 */
public class AlarmDialog extends Dialog {


  TextView dialogInfo;
  TextView dialogDate;
  TextView dialogDay;


  DataBaseAdmin dataBaseAdmin;
  Cursor cursor;

  Context context;
  String index;
  boolean find = false;
  boolean time = false;

  public AlarmDialog(Context context, boolean time) {
    super(context);
    this.context = context;
    this.time = time;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.d_alarm);

    dialogInfo = (TextView) findViewById(R.id.dialog_info);
    dialogDate = (TextView) findViewById(R.id.dialog_date);
    dialogDay = (TextView) findViewById(R.id.dialog_day);

    Date now = new Date();

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    now.setDate(now.getDate());

    index = format.format(now);
    dataBaseAdmin = new DataBaseAdmin(context);
    dataBaseAdmin.open();
    cursor = dataBaseAdmin.select();

    cursor.moveToFirst();

    getData();
  }

  @Override
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    dataBaseAdmin.close();
    cursor.close();

  }

  void getData() {
    if (index.equals(cursor.getString(cursor.getColumnIndex("date"))) || find == true) {
      String date = cursor.getString(cursor.getColumnIndex("date"));
      String day = cursor.getString(cursor.getColumnIndex("day"));
      String lunch = cursor.getString(cursor.getColumnIndex("lunch"));
      String dinner = cursor.getString(cursor.getColumnIndex("dinner"));
      find = true;

      if (time == true) {
        dialogInfo.setText(lunch);
      } else {
        dialogInfo.setText(dinner);
      }

      dialogDate.setText(date);
      dialogDay.setText(day);

      return;
//                        Toast.makeText(context, date + day + lunch + dinner, Toast.LENGTH_LONG).show();
    } else {
      dataBaseAdmin.deleteRaw(cursor.getString(cursor.getColumnIndex("date")));
//                    Toast.makeText(context, "index fucked", Toast.LENGTH_SHORT).show();
      cursor.moveToNext();
      getData();

    }
  }

}
