package com.inoueken.androidenshugroup2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inoueken.androidenshugroup2.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private static List<PurchaseData> list;
    private  onItemClickListener listener;
    public HistoryAdapter(List<PurchaseData> list) {
        this.list = list;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_data, parent,false);
        HistoryViewHolder vh = new HistoryViewHolder(inflate);
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
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.historyDateView.setText(String.valueOf(list.get(position).getPurchaseDateTime().getMonthValue())
                + "/" + String.valueOf(list.get(position).getPurchaseDateTime().getDayOfMonth()));
        holder.historyItemView.setText(list.get(position).getItemName());
        holder.historyPriceView.setText(String.valueOf(list.get(position).getPurchasePrice()) + "円");
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