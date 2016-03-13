package com.example.advokat.cleanenergy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.adapters.DataAdapter;

public class ChooseCategoryFragment extends Fragment {

    private ListView lview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_category, container, false);
        initListView(v);
        return v;
    }

    private void initListView(View v) {
        lview = (ListView) v.findViewById(R.id.list_view);
        DataAdapter adapter = new DataAdapter(v.getContext().getApplicationContext(), getDataSet());
        lview.setAdapter(adapter);
    }

    private String[] getDataSet() {
        String[] mDataSet = new String[7];
        mDataSet[0] = "Закупівля сировини";
        mDataSet[1] = "Транспорт";
        mDataSet[2] = "Електроенергія";
        mDataSet[3] = "Паливо для теплогенератора";
        mDataSet[4] = "Експлуатаційні матеріали";
        mDataSet[5] = "Упаковка готової продукції";
        mDataSet[6] = "Накладні витрати";
        return mDataSet;
    }
}
