package com.example.androidenshugroup2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder{
    public TextView historyDateView;
    public TextView historyItemView;
    public TextView historyPriceView;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        historyDateView = (TextView) itemView.findViewById(R.id.history_date);
        historyItemView = (TextView) itemView.findViewById(R.id.history_item);
        historyPriceView = (TextView) itemView.findViewById(R.id.history_price);
    }

}
