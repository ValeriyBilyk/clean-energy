package com.example.advokat.cleanenergy.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class Utils {

    public static final int COST = 1;
    public static final int INCOME = 2;

    public static final int MARGIN_LEFT_0 = 0;
    public static final int MARGIN_RIGHT_0 = 0;

    public static void addHorizontalDivider(RecyclerView recyclerView, Context context, int marginLeft, int marginRight) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(context)
                        .color(Color.GRAY)
                        .margin(marginLeft, marginRight)
                        .showLastDivider()
                        .build());
    }
}
