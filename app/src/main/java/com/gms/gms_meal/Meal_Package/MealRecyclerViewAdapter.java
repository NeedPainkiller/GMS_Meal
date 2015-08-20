package com.gms.gms_meal.Meal_Package;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.gms.gms_meal.R;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MealRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  static final int TYPE_HEADER = 0;
  static final int TYPE_CELL = 1;
  ArrayList<MealItemData> mealItemDataArrayList;
  Context context;

  public MealRecyclerViewAdapter(ArrayList<MealItemData> mealItemDataArrayList,Context context) {
    this.mealItemDataArrayList = mealItemDataArrayList;
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
    return mealItemDataArrayList.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case TYPE_HEADER: {
//
        return new BigCardMeal(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_card_big, parent, false));
      }
      case TYPE_CELL: {
//
        return new SmallCardMeal(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_card_small, parent, false));

      }
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    switch (getItemViewType(position)) {
      case TYPE_HEADER:
        BigCardMeal bigCardMeal = (BigCardMeal) holder;

        bigCardMeal.bigMealTextView.setText(mealItemDataArrayList.get(position).getDetail());
        bigCardMeal.bigMealRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
          @Override
          public void onComplete(RippleView rippleView) {
            Intent msg = new Intent(Intent.ACTION_SEND);

            msg.addCategory(Intent.CATEGORY_DEFAULT);

            msg.putExtra(Intent.EXTRA_SUBJECT, mealItemDataArrayList.get(position).getDate()+"일의 급식정보");

            msg.putExtra(Intent.EXTRA_TEXT, mealItemDataArrayList.get(position).getDetail());

            msg.putExtra(Intent.EXTRA_TITLE, mealItemDataArrayList.get(position).getDate()+"일의 급식정보");

            msg.setType("text/plain");

            context.startActivity(Intent.createChooser(msg, "공유"));
          }
        });

        break;
      case TYPE_CELL:
        SmallCardMeal smallCardMeal = (SmallCardMeal) holder;
        smallCardMeal.smallMealTextView.setText(mealItemDataArrayList.get(position).getDetail());
        smallCardMeal.mealDate.setText(mealItemDataArrayList.get(position).getDate());
        smallCardMeal.smallMealRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
          @Override
          public void onComplete(RippleView rippleView) {
            Intent msg = new Intent(Intent.ACTION_SEND);

            msg.addCategory(Intent.CATEGORY_DEFAULT);

            msg.putExtra(Intent.EXTRA_SUBJECT, mealItemDataArrayList.get(position).getDate()+"일의 급식정보");

            msg.putExtra(Intent.EXTRA_TEXT, mealItemDataArrayList.get(position).getDetail());

            msg.putExtra(Intent.EXTRA_TITLE, mealItemDataArrayList.get(position).getDate()+"일의 급식정보");

            msg.setType("text/plain");

            context.startActivity(Intent.createChooser(msg, "공유"));
          }
        });
        break;
    }
  }

  public static class BigCardMeal extends RecyclerView.ViewHolder {

    public TextView bigMealTextView;
    public RippleView bigMealRipple;

    public BigCardMeal(View itemView) {
      super(itemView);
      bigMealTextView = (TextView) itemView.findViewById(R.id.mealBig);
      bigMealRipple = (RippleView)itemView.findViewById(R.id.mealBigRipple);

    }

  }

  public static class SmallCardMeal extends RecyclerView.ViewHolder {

    public TextView smallMealTextView;
    public TextView mealDate;
    public RippleView smallMealRipple;

    public SmallCardMeal(View itemView) {
      super(itemView);
      smallMealTextView = (TextView) itemView.findViewById(R.id.mealSmall);
      mealDate = (TextView) itemView.findViewById(R.id.mealdate);
      smallMealRipple = (RippleView)itemView.findViewById(R.id.mealSmallRipple);
    }

  }
}