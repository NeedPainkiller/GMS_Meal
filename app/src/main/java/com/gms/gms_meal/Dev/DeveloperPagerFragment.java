package com.gms.gms_meal.Dev;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gms.gms_meal.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by kam6376 on 2015-05-26.
 */
public class DeveloperPagerFragment extends Fragment {
  DeveloperPagerAdapter developerPagerAdapter;
  Shimmer shimmer;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    View view = inflater.inflate(R.layout.f_developer, container, false);

    final ImageView devIamge = (ImageView) view.findViewById(R.id.devImage);
    devIamge.setImageResource(getArguments().getInt("image"));
    devIamge.post(new Runnable() {
      @Override
      public void run() {
        Matrix matrix = new Matrix();
        matrix.reset();

        float wv = devIamge.getWidth();
        float hv = devIamge.getHeight();

        float wi = devIamge.getDrawable().getIntrinsicWidth();
        float hi = devIamge.getDrawable().getIntrinsicHeight();

        float width = wv;
        float height = hv;

        if (wi / wv > hi / hv) {
          matrix.setScale(hv / hi, hv / hi);
          width = wi * hv / hi;
        } else {
          matrix.setScale(wv / wi, wv / wi);
          height = hi * wv / wi;
        }

        matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
        devIamge.setScaleType(ImageView.ScaleType.MATRIX);
        devIamge.setImageMatrix(matrix);
      }
    });
    ShimmerTextView text = (ShimmerTextView) view.findViewById(R.id.devName);
    ShimmerTextView detail = (ShimmerTextView) view.findViewById(R.id.devDetail);
    shimmer = new Shimmer();
    shimmer.start(text);
    shimmer.start(detail);

    text.setText(getArguments().getString("name"));
    detail.setText(getArguments().getString("detail"));

//        TextView more = (TextView) view.findViewById(R.id.more);
//
//        more.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (developerPagerAdapter != null) {
//                    developerPagerAdapter.remove(DeveloperPagerFragment.this);
//                    developerPagerAdapter.notifyDataSetChanged();
//                }
//                return true;
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (developerPagerAdapter != null) {
//                    int select = (int) (Math.random() * 4);
//
//                    int[] resD = {R.mipmap.bg_nina, R.mipmap.bg_niju, R.mipmap.bg_yuki, R.mipmap.bg_kero};
//                    String[] resS = {"Nina", "Niju", "Yuki", "Kero"};
//
//                    DeveloperPagerFragment newP = new DeveloperPagerFragment();
//                    Bundle b = new Bundle();
//                    b.putInt("image", resD[select]);
//                    b.putString("name", resS[select]);
//                    newP.setArguments(b);
//                    developerPagerAdapter.add(newP);
//                }
//            }
//        });
    return view;
  }

  public void setAdapter(DeveloperPagerAdapter catsAdapter) {
    developerPagerAdapter = catsAdapter;
  }
}
