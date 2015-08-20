package com.gms.gms_meal.Meal_Package;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.gms.gms_meal.DB.CreateDB;
import com.gms.gms_meal.DB.DataBaseAdmin;
import com.gms.gms_meal.MainActivity;
import com.gms.gms_meal.R;
import com.gms.gms_meal.tools.GetMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class LunchViewFragment extends Fragment {

  public static Handler getMealHandler;
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private ArrayList<MealItemData> mealItemDataArrayList = new ArrayList<MealItemData>();
  private int pos;
  private Context context;
  private View v;
  private GetMeal getMeal;

  private DataBaseAdmin dataBaseAdmin;
  private Cursor cursor;

  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;

  public LunchViewFragment() {
    pos = 0;
    this.context = MainActivity.context;
    this.v = MainActivity.v;
  }

  public LunchViewFragment getFrag() {
    return this;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recyclerview, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);

    mAdapter = new RecyclerViewMaterialAdapter(new MealRecyclerViewAdapter(mealItemDataArrayList, getActivity()));
    mRecyclerView.setAdapter(mAdapter);
    dataBaseAdmin = new DataBaseAdmin(context);
    dataBaseAdmin.open();
    cursor = dataBaseAdmin.select();

    sharedPreferences = context.getSharedPreferences("Alarm", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();

    init();

    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    getMealHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);

        String[] info = msg.getData().getStringArray("info");

        if (info[2].equals("데이터 연결을 확인해 주십시오.") || info[3].equals("데이터 연결을 확인해 주십시오.")) {
          editor.putBoolean("canAlarm", false);
          editor.commit();
        } else {
          dataBaseAdmin.insertData(info[0], info[1], info[2], info[3]);
          editor.putBoolean("canAlarm", true);
          editor.commit();

        }

        mealItemDataArrayList.add(new MealItemData(info[0], info[1], info[2]));
        mAdapter.notifyDataSetChanged();

        Bundle data = new Bundle();
        data.putStringArray("info", info);

        Message message = DinnerViewFragment.getMealHandler.obtainMessage();
        message.setData(data);
        DinnerViewFragment.getMealHandler.sendMessage(message);
      }
    };

  }

  @Override
  public void onDestroy() {
    super.onDestroyView();
//    MaterialViewPagerHelper.unregister(context);
    dataBaseAdmin.close();
    cursor.close();
  }

  boolean getCount() {
    if (cursor.getCount() == 0 || CreateDB.CreateDataBase.reset == true) {
      dataBaseAdmin.deleteAll();


      return false;

    } else {
      return true;
    }
  }

  void init() {

    mealItemDataArrayList.clear();
    try {

      if (getCount()) {

      } else {
        throw new Exception();
      }
      Snackbar.make(v, "DB에 저장된 리스트를 불러옵니다 : " + cursor.getCount() + "개", Snackbar.LENGTH_SHORT).show();

      Date now = new Date();

      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      now.setDate(now.getDate());

      String index = format.format(now);

      boolean find = false;

      while (cursor.moveToNext()) {

        if (index.equals(cursor.getString(cursor.getColumnIndex("date"))) || find == true) {
          String date = cursor.getString(cursor.getColumnIndex("date"));
          String day = cursor.getString(cursor.getColumnIndex("day"));
          String lunch = cursor.getString(cursor.getColumnIndex("lunch"));
          String dinner = cursor.getString(cursor.getColumnIndex("dinner"));
          find = true;
          mealItemDataArrayList.add(new MealItemData(date, day, lunch));

          mAdapter.notifyDataSetChanged();

        } else {
          dataBaseAdmin.deleteRaw(cursor.getString(cursor.getColumnIndex("date")));

          if (getCount()) {

          } else {
            throw new Exception();
          }
        }
      }

    } catch (Exception e) {
//      Log.e("lunch", "lunch : " + e.getMessage());


      editor.putBoolean("canAlarm", false);
      editor.commit();
      dataBaseAdmin.deleteAll();

      Snackbar.make(v, "급식정보를 네트워크로 받아오는중....", Snackbar.LENGTH_SHORT).show();

      Calendar calendar = Calendar.getInstance();
      int today = calendar.get(Calendar.DAY_OF_MONTH);
      calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, today);

      int lastDay = calendar.getActualMaximum(Calendar.DATE);

      for (int i = 0; i < lastDay - today; ++i) {
        getMeal = new GetMeal("lunch", pos);
        getMeal.execute(i);
      }


    } finally {
      CreateDB.CreateDataBase.reset = false;
    }
  }


}
