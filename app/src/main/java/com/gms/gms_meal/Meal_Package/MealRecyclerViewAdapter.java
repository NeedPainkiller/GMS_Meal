package com.gms.gms_meal.Meal_Package;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.gms.gms_meal.R;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MealRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  static final int TYPE_HEADER = 0;
  static final int TYPE_CELL = 1;
  ArrayList<MealItemData> mealItemDataArrayList;
  private AdapterView.OnItemClickListener listener;

  public MealRecyclerViewAdapter(ArrayList<MealItemData> mealItemDataArrayList) {
    this.mealItemDataArrayList = mealItemDataArrayList;
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
    View view = null;

    switch (viewType) {
      case TYPE_HEADER: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.meal_item_card_big, parent, false);
//                return new RecyclerView.ViewHolder(view) {
//                };
        return new BigCardMeal(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_card_big, parent, false));
      }
      case TYPE_CELL: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.meal_item_card_small, parent, false);
//                return new RecyclerView.ViewHolder(view) {
//                };
        return new SmallCardMeal(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_card_small, parent, false));

      }
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case TYPE_HEADER:
        BigCardMeal bigCardMeal = (BigCardMeal) holder;
        bigCardMeal.bigMealTextView.setText(mealItemDataArrayList.get(position).getDetail());

        break;
      case TYPE_CELL:
        SmallCardMeal smallCardMeal = (SmallCardMeal) holder;
        smallCardMeal.smallMealTextView.setText(mealItemDataArrayList.get(position).getDetail());
        smallCardMeal.mealDate.setText(mealItemDataArrayList.get(position).getDate());
        break;
    }
  }

  public static class BigCardMeal extends RecyclerView.ViewHolder {

    public TextView bigMealTextView;

    public BigCardMeal(View itemView) {
      super(itemView);
      bigMealTextView = (TextView) itemView.findViewById(R.id.mealBig);
    }

  }

  public static class SmallCardMeal extends RecyclerView.ViewHolder {

    public TextView smallMealTextView;
    public TextView mealDate;

    public SmallCardMeal(View itemView) {
      super(itemView);
      smallMealTextView = (TextView) itemView.findViewById(R.id.mealSmall);
      mealDate = (TextView) itemView.findViewById(R.id.mealdate);
    }

  }
}