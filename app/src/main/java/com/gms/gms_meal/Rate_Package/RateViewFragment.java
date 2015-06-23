package com.gms.gms_meal.Rate_Package;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.gms.gms_meal.R;
import com.gms.gms_meal.tools.GetRatePHP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kam6376 on 2015-06-15.
 */
public class RateViewFragment extends Fragment {

  public static Handler getRateHandler;
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private ArrayList<RateItemData> rateItemDataArrayList = new ArrayList<RateItemData>();
  private Context context;

  public RateViewFragment(Context context) {
    this.context = context;
  }

  public RateViewFragment getFrag() {
    return this;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recyclerview, container, false);
  }

  @Override
  public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);
    mAdapter = new RecyclerViewMaterialAdapter(new RateRecyclerViewAdapter(rateItemDataArrayList, context, view));
    mRecyclerView.setAdapter(mAdapter);

    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    getRateHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);

        String resource = msg.getData().getString("rate");

        String rate;
        String date;

        try {
          if (resource == null) {
            Log.e("postErr", "resource is null");
          }

          JSONObject root = new JSONObject(resource);
          JSONArray jsonArray = root.getJSONArray("results");
          String numRes = root.getString("num_results");
          if (Integer.parseInt(numRes) == 0) {
            Snackbar.make(view, "DB서버에 Data가 없습니다", Snackbar.LENGTH_SHORT).show();
          } else {
            Snackbar.make(view, numRes + " 개의 평점이 있습니다.", Snackbar.LENGTH_SHORT).show();
          }
          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            rate = jsonObject.getString("rate");
            date = jsonObject.getString("date");

            rateItemDataArrayList.add(new RateItemData(rate, "익명", date));

            mAdapter.notifyDataSetChanged();
          }
          Collections.reverse(rateItemDataArrayList);
        } catch (Exception e) {
          Log.e("postErr", "JSON is fucked");
          Log.e("postErr", e.getMessage());

        }
      }
    };
    new GetRatePHP().execute();
  }
}
