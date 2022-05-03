package com.inoueken.electronicmoneyapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditItemDecoration extends RecyclerView.ItemDecoration{
    private Drawable mDivider; //ディバイダー


    public EditItemDecoration(Context context) {
        //ディバイダーの取得
//        mDivider = context.getResources().getDrawable(R.drawable.under_border);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //リサイクラービューのデータ数を取得
        int childCount = parent.getChildCount();

        int left = parent.getPaddingLeft()+10;                        //表示用ビューの左の余白を算出
        int right = parent.getWidth() - parent.getPaddingRight()-10;  //表示用ビューの右の余白を算出

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);  //表示用ビューの取得

            //レイアウトパラメータの取得
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;  //表示用ビューの上の余白を算出
            int bottom = top + mDivider.getIntrinsicHeight();   //表示用ビューの下の余白を算出
            mDivider.setBounds(left,top,right,bottom);          //余白の算出値を設定
            mDivider.draw(c);                                   //描画
        }
    }
}
