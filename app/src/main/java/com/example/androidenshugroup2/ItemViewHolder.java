package com.example.androidenshugroup2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView itemDataView;
    public TextView oneDatePriceTextView;
    public ItemViewHolder(View itemView) {
        super(itemView);
        itemDataView = (TextView) itemView.findViewById(R.id.oneDataNameTextView);
        oneDatePriceTextView = (TextView) itemView.findViewById(R.id.oneDatePriceTextView);
    }

}
