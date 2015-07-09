package com.gms.gms_meal.DB;

import android.provider.BaseColumns;

/**
 * Created by kam6376 on 2015-06-09.
 */
public class CreateDB {
  public static final class CreateDataBase implements BaseColumns {

    public static final String DATE = "date";
    public static final String DAY = "day";
    public static final String LUNCH = "lunch";
    public static final String DINNER = "dinner";
    public static final String _TABLE_NAME = "meal";
    public static final String CREATE_DB = "create table " + _TABLE_NAME + "("
        + _ID + " integer primary key autoincrement, "
        + DATE + " text not null , "
        + DAY + " text not null , "
        + LUNCH + " text not null , "
        + DINNER + " text not null );";

    public static boolean reset = false;
  }
}
