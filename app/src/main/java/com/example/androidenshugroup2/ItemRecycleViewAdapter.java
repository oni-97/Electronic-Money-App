package com.example.androidenshugroup2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidenshugroup2.R;

import java.util.ArrayList;
import java.util.List;

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private static List<ItemData> list;
    private  onItemClickListener listener;
    public ItemRecycleViewAdapter(ArrayList<ItemData> list) {
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent,false);
        ItemViewHolder vh = new ItemViewHolder(inflate);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vh.getAdapterPosition();//位置取得
                final int id =list.get(position).getId();//ID取得
                listener.onClick(v,id,position);
            }

        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.itemDataView.setText(list.get(position).getItemName());
        holder.oneDatePriceTextView.setText(String.valueOf(list.get(position).getItemPrice())+"円");
    }
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener {
        void onClick(View view, int id,int position);
    }

}