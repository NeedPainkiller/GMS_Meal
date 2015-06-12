package com.gms.gms_meal;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.gms.gms_meal.DB.CreateDB;
import com.gms.gms_meal.DB.DataBaseAdmin;
import com.gms.gms_meal.tools.GetMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DinnerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    ArrayList<MealItemData> mealItemDataArrayList = new ArrayList<MealItemData>();
    int pos;
    Context context;
    public static Handler getMealHandler;

    GetMeal getMeal;

    DataBaseAdmin dataBaseAdmin;

    Cursor cursor;

    //    RecyclerViewFragment newInstance(int position) {
//        pos = position;
//
//        return new RecyclerViewFragment();
//    }
    DinnerViewFragment(int position, Context context) {
        pos = position;
        this.context = context;
    }

    DinnerViewFragment getFrag() {
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
//        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mealItemDataArrayList));
//        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(mAdapter));
        mRecyclerView.setAdapter(mAdapter);

        dataBaseAdmin = new DataBaseAdmin(context);
        dataBaseAdmin.open();
        cursor = dataBaseAdmin.select();
        init();
//        context = getActivity();


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        getMealHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                String[] info = msg.getData().getStringArray("info");

                mealItemDataArrayList.add(new MealItemData(info[0], info[1], info[3]));

                mAdapter.notifyDataSetChanged();
                dataBaseAdmin.insertData(info[0], info[1], info[2], info[3]);

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


//        try {
        if (cursor.getCount() == 0 || CreateDB.CreateDataBase.reset == true) {
            dataBaseAdmin.deleteAll();
            Toast.makeText(context, "DinnerDB" + cursor.getCount(), Toast.LENGTH_SHORT).show();
//                throw new Exception();
        }

        Toast.makeText(context, "DinnerDB" + cursor.getCount(), Toast.LENGTH_SHORT).show();
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
                mealItemDataArrayList.add(new MealItemData(date, day, dinner));

                mAdapter.notifyDataSetChanged();
//                        Toast.makeText(context, date + day + lunch + dinner, Toast.LENGTH_LONG).show();
            } else {
                dataBaseAdmin.deleteRaw(cursor.getString(cursor.getColumnIndex("date")));
//                    Toast.makeText(context, "index fucked", Toast.LENGTH_SHORT).show();
            }


        }


//        } catch (Exception e) {
//
//            Log.e("dinner", "dinner : " + e.getMessage());
//
//            dataBaseAdmin.deleteAll();
//            Toast.makeText(context, "DinnerNet"+cursor.getCount(), Toast.LENGTH_SHORT).show();
//
//            Calendar calendar = Calendar.getInstance();
//            int today = calendar.get(Calendar.DAY_OF_MONTH);
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, today);
//
//            int lastDay = calendar.getActualMaximum(Calendar.DATE);
//            GetNetworkState getNetworkState = new GetNetworkState(getActivity());
//            if (getNetworkState.getState() == 1) {
//                for (int i = 0; i < lastDay - today; ++i) {
////            mealItemDataArrayList.add(new MealItemData("1025","zs"));
//                    getMeal = new GetMeal("dinner", pos);
//                    getMeal.execute(i);
//
//                }
//            } else {
//                getMeal = new GetMeal("dinner", pos);
//                getMeal.execute(0);
//            }
//        }finally {

        CreateDB.CreateDataBase.reset = false;
//        }
//        cursor.moveToFirst();

    }


}