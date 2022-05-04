package com.inoueken.androidenshugroup2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.LocalDateTime;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemDataRecycleViewHolder> {
    private List<ItemData> itemDataList;
    private PurchaseByGenreActivity activity;

    public ItemRecyclerAdapter(List<ItemData> itemDataList, PurchaseByGenreActivity activity) {
        this.itemDataList = itemDataList;
        this.activity = activity;
    }

    @Override
    public ItemDataRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        final ItemDataRecycleViewHolder viewHolder = new ItemDataRecycleViewHolder(inflater);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //クリックされたアイテムを取得
                final int position = viewHolder.getAdapterPosition();
                ItemData clickedItem = itemDataList.get(position);

                //購入確認画面を表示
                String itemName = clickedItem.getItemName();
                int price = clickedItem.getItemPrice();
                new AlertDialog.Builder(activity)
                        .setTitle("購入内容の確認")
                        .setMessage(itemName + " : " + price + " 円\n")
                        .setPositiveButton("購入", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PurchaseData purchaseData = makePurchaseData(clickedItem);

                                PurchaseDatabaseHelper purchaseDatabaseHelper = new PurchaseDatabaseHelper(activity.getApplication(), activity);
                                purchaseDatabaseHelper.insertData(purchaseData);

                            }
                        })
                        .setNegativeButton("キャンセル", null)
                        .show();
            }
        });
        return viewHolder;
    }

    private PurchaseData makePurchaseData(ItemData itemData) {
        LocalDateTime localDateTime = LocalDateTime.now();
        PurchaseData purchaseData =
                new PurchaseData(0, this.activity.getUserName(), itemData.getItemName(), itemData.getItemPrice(), localDateTime, activity.getGenre());
        return purchaseData;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDataRecycleViewHolder holder, int position) {
        holder.getItemName().setText(itemDataList.get(position).getItemName());
        holder.getItemPrice().setText(itemDataList.get(position).getItemPrice() + " 円");
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }
}
