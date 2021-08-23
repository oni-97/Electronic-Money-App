package com.example.androidenshugroup2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDataRecycleViewHolder extends RecyclerView.ViewHolder {
    private TextView itemName;
    private TextView itemPrice;

    public ItemDataRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.tvItemName);
        itemPrice = (TextView) itemView.findViewById(R.id.tvPrice);
    }

    public TextView getItemName() {
        return itemName;
    }

    public TextView getItemPrice() {
        return itemPrice;
    }
}
