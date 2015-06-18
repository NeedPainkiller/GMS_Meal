package com.gms.gms_meal.Rate_Package;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.gms.gms_meal.R;
import com.gms.gms_meal.lib.ColoredRatingBar;
import com.gms.gms_meal.lib.FontAwesomeText;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RateRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<RateItemData> rateItemDataArrayList;

    AdapterView.OnItemClickListener listener;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public static class BigCardRate extends RecyclerView.ViewHolder {
        public ColoredRatingBar bigRatingBar;
        public TextView bigRate;
        public FontAwesomeText bigFontNum;
        public TextView bigNum;
        public TextView bigDate;

        public BigCardRate(View itemView) {
            super(itemView);

            bigRatingBar = (ColoredRatingBar) itemView.findViewById(R.id.bigRatingBar);
            bigRate = (TextView) itemView.findViewById(R.id.bigRate);
            bigFontNum = (FontAwesomeText) itemView.findViewById(R.id.bigFontNum);
            bigNum = (TextView) itemView.findViewById(R.id.bigNum);
            bigDate = (TextView) itemView.findViewById(R.id.bigDate);
        }
    }

    public static class SmallCardRate extends RecyclerView.ViewHolder {
        public ColoredRatingBar smallRatingBar;
        public TextView smallRate;
        public FontAwesomeText smallFontNum;
        public TextView smallNum;
        public TextView smallDate;

        public SmallCardRate(View itemView) {
            super(itemView);

            smallRatingBar = (ColoredRatingBar) itemView.findViewById(R.id.smallRatingBar);
            smallRate = (TextView) itemView.findViewById(R.id.smallRate);
            smallFontNum = (FontAwesomeText) itemView.findViewById(R.id.smallFontNum);
            smallNum = (TextView) itemView.findViewById(R.id.smallNum);
            smallDate = (TextView) itemView.findViewById(R.id.smallDate);
        }
    }

    public RateRecyclerViewAdapter(ArrayList<RateItemData> rateItemDataArrayList) {
        this.rateItemDataArrayList = rateItemDataArrayList;
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
                bigCardRate.bigRatingBar.setRating(Float.parseFloat(rateItemDataArrayList.get(position).getRate()));

                bigCardRate.bigRate.setText(rateItemDataArrayList.get(position).getRate());
                bigCardRate.bigNum.setText(rateItemDataArrayList.get(position).getNum()+"Έν");
                bigCardRate.bigDate.setText(rateItemDataArrayList.get(position).getDate());
                break;
            case TYPE_CELL:

                SmallCardRate smallCardRate = (SmallCardRate) holder;
                smallCardRate.smallRatingBar.setRating(Float.parseFloat(rateItemDataArrayList.get(position).getRate()));
                smallCardRate.smallRate.setText(rateItemDataArrayList.get(position).getRate());
                smallCardRate.smallNum.setText(rateItemDataArrayList.get(position).getNum());
                smallCardRate.smallDate.setText(rateItemDataArrayList.get(position).getDate());
                break;
        }


    }


}