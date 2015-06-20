package com.gms.gms_meal.Rate_Package;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.gms.gms_meal.R;
import com.gms.gms_meal.lib.FontAwesomeText;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RateRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  ArrayList<RateItemData> rateItemDataArrayList;


  static final int TYPE_HEADER = 0;
  static final int TYPE_CELL = 1;

  Context context;

  public static class BigCardRate extends RecyclerView.ViewHolder {
    public FontAwesomeText bigFontRate;
    public TextView bigRate;
    public FontAwesomeText bigFontNum;
    public TextView bigNum;
    public TextView bigDate;
    public RippleView bigRipple;

    public BigCardRate(View itemView) {
      super(itemView);

      bigFontRate = (FontAwesomeText) itemView.findViewById(R.id.bigFontRate);
      bigRate = (TextView) itemView.findViewById(R.id.bigRate);
      bigFontNum = (FontAwesomeText) itemView.findViewById(R.id.bigFontNum);
      bigNum = (TextView) itemView.findViewById(R.id.bigNum);
      bigDate = (TextView) itemView.findViewById(R.id.bigDate);
      bigRipple = (RippleView) itemView.findViewById(R.id.bigRipple);
    }
  }

  public static class SmallCardRate extends RecyclerView.ViewHolder {
    public FontAwesomeText smallFontRate;
    public TextView smallRate;
    public FontAwesomeText smallFontNum;
    public TextView smallNum;
    public TextView smallDate;

    public SmallCardRate(View itemView) {
      super(itemView);

      smallFontRate = (FontAwesomeText) itemView.findViewById(R.id.smallFontRate);
      smallRate = (TextView) itemView.findViewById(R.id.smallRate);
      smallFontNum = (FontAwesomeText) itemView.findViewById(R.id.smallFontNum);
      smallNum = (TextView) itemView.findViewById(R.id.smallNum);
      smallDate = (TextView) itemView.findViewById(R.id.smallDate);
    }
  }

  public RateRecyclerViewAdapter(ArrayList<RateItemData> rateItemDataArrayList, Context context) {
    this.rateItemDataArrayList = rateItemDataArrayList;
    this.context = context;

  }

  @Override
  public int getItemViewType(int position) {
    switch (position) {
      case 0:
        return TYPE_HEADER;
      default:
        return TYPE_CELL;
    }
  }

  @Override
  public int getItemCount() {
    return rateItemDataArrayList.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {
      case TYPE_HEADER: {
        return new BigCardRate(LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_item_card_big, parent, false));

      }
      case TYPE_CELL: {
        return new SmallCardRate(LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_item_card_small, parent, false));

      }
    }

    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case TYPE_HEADER:
        BigCardRate bigCardRate = (BigCardRate) holder;
        bigCardRate.bigFontRate.startFlashing(context, true, FontAwesomeText.AnimationSpeed.MEDIUM);

        bigCardRate.bigRate.setText(rateItemDataArrayList.get(position).getRate());
        bigCardRate.bigNum.setText(rateItemDataArrayList.get(position).getNum());
        bigCardRate.bigDate.setText(rateItemDataArrayList.get(position).getDate());

        bigCardRate.bigRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
          @Override
          public void onComplete(RippleView rippleView) {
            Toast.makeText(context, "bigRipple", Toast.LENGTH_SHORT).show();

            RateDialog rateDialog = new RateDialog(context);
            rateDialog.show();
          }
        });
        break;
      case TYPE_CELL:

        SmallCardRate smallCardRate = (SmallCardRate) holder;
        smallCardRate.smallFontRate.startFlashing(context, true, FontAwesomeText.AnimationSpeed.MEDIUM);
        smallCardRate.smallRate.setText(rateItemDataArrayList.get(position).getRate());
        smallCardRate.smallNum.setText(rateItemDataArrayList.get(position).getNum());
        smallCardRate.smallDate.setText(rateItemDataArrayList.get(position).getDate());
        break;
    }


  }


}