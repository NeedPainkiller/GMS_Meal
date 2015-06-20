package com.gms.gms_meal.Rate_Package;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

/**
 * Created by kam6376 on 2015-06-15.
 */
public class RateViewFragment extends Fragment {

  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;

  ArrayList<RateItemData> rateItemDataArrayList = new ArrayList<RateItemData>();

  Context context;

  public static Handler getRateHandler;


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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);

    mAdapter = new RecyclerViewMaterialAdapter(new RateRecyclerViewAdapter(rateItemDataArrayList, context));
    mRecyclerView.setAdapter(mAdapter);

    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    getRateHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
//                if(msg.what == 0){
//                    rateItemDataArrayList.add(new RateItemData("0","0Έν","today"));
//
//                    mAdapter.notifyDataSetChanged();
//                }else{
        String[] rate = msg.getData().getStringArray("rate");
        Log.e("raetHandler", rate[0] + "/" + rate[1] + "/" + rate[2]);

        rateItemDataArrayList.add(new RateItemData(rate[0], rate[1], rate[2]));

        mAdapter.notifyDataSetChanged();
//                }


      }
    };

    new GetRatePHP().execute();
  }
}
