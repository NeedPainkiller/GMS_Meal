package com.gms.gms_meal.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by kam6376 on 2015-06-09.
 */
public class DataBaseAdmin {
  private static final String DATABASE_NAME = "meal.db";
  private static final int DATABASE_VERSION = 1;
  public static SQLiteDatabase sqLiteDatabase;
  public DataBaseHelper databaseHelper;
  private Context context;

  public DataBaseAdmin(Context context) {
    this.context = context;
  }

  public DataBaseAdmin open() {
    try {
      databaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
      sqLiteDatabase = databaseHelper.getWritableDatabase();
    } catch (Exception e) {

    }
    return this;
  }

  public void close() {
    sqLiteDatabase.close();
  }

  public void insertData(String date, String day, String lunch, String dinner) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(CreateDB.CreateDataBase.DATE, date);
    contentValues.put(CreateDB.CreateDataBase.DAY, day);
    contentValues.put(CreateDB.CreateDataBase.LUNCH, lunch);
    contentValues.put(CreateDB.CreateDataBase.DINNER, dinner);
    sqLiteDatabase.insert(CreateDB.CreateDataBase._TABLE_NAME, null, contentValues);

  }

  public void deleteAll() {
    String sql = "DELETE FROM " + CreateDB.CreateDataBase._TABLE_NAME;
    sqLiteDatabase.execSQL(sql);
  }

  public boolean deleteRaw(String date) {
    Toast.makeText(context, "deleteRaw = " + date, Toast.LENGTH_SHORT).show();
    return sqLiteDatabase.delete(CreateDB.CreateDataBase._TABLE_NAME, CreateDB.CreateDataBase.DATE + "=?", new String[]{date}) > 0;

  }

  public Cursor select() {

    Cursor cursor = sqLiteDatabase.query(CreateDB.CreateDataBase._TABLE_NAME, null, null, null, null, null, null);
    return cursor;
  }

  private class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
      super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      // 최초 DB를 만들때 한번만 호출된다.
      db.execSQL(CreateDB.CreateDataBase.CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
      db.execSQL("DROP TABLE IF EXISTS " + CreateDB.CreateDataBase._TABLE_NAME);
      onCreate(db);
    }
  }


}
