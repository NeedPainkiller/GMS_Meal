package com.gms.gms_meal.Meal_Package;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DinnerViewFragment extends Fragment {

  public static Handler getMealHandler;
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private ArrayList<MealItemData> mealItemDataArrayList = new ArrayList<MealItemData>();
  private int pos;
  private Context context;
  private View v;
  private DataBaseAdmin dataBaseAdmin;

  private Cursor cursor;


  public DinnerViewFragment() {
    pos =1 ;
    this.context = MainActivity.context;
    this.v = MainActivity.v;
  }

  public DinnerViewFragment getFrag() {
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
    mAdapter = new RecyclerViewMaterialAdapter(new MealRecyclerViewAdapter(mealItemDataArrayList,getActivity()));
    mRecyclerView.setAdapter(mAdapter);

    dataBaseAdmin = new DataBaseAdmin(context);
    dataBaseAdmin.open();
    cursor = dataBaseAdmin.select();

    init();

    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    getMealHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);


        String[] info = msg.getData().getStringArray("info");

        mealItemDataArrayList.add(new MealItemData(info[0], info[1], info[3]));

        mAdapter.notifyDataSetChanged();
//        dataBaseAdmin.insertData(info[0], info[1], info[2], info[3]);

        mRecyclerView.scrollToPosition(RecyclerView.NO_POSITION);
      }

    };


  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    dataBaseAdmin.close();
    cursor.close();
  }

  void init() {

    if (cursor.getCount() == 0 || CreateDB.CreateDataBase.reset == true) {
      dataBaseAdmin.deleteAll();
      Snackbar.make(v, "DB에 저장된 리스트를 불러옵니다 : " + cursor.getCount() + "개", Snackbar.LENGTH_SHORT).show();
    }

    Date now = new Date();

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    now.setDate(now.getDate());

    String index = format.format(now);

    boolean find = false;

//    Log.e("Dinner", "find = " + find);
    while (cursor.moveToNext()) {

      if (index.equals(cursor.getString(cursor.getColumnIndex("date"))) || find == true) {
        String date = cursor.getString(cursor.getColumnIndex("date"));
        String day = cursor.getString(cursor.getColumnIndex("day"));
        String lunch = cursor.getString(cursor.getColumnIndex("lunch"));
        String dinner = cursor.getString(cursor.getColumnIndex("dinner"));
        find = true;
        mealItemDataArrayList.add(new MealItemData(date, day, dinner));

        mAdapter.notifyDataSetChanged();

      } else {
        dataBaseAdmin.deleteRaw(cursor.getString(cursor.getColumnIndex("date")));
      }
    }
  }
}
