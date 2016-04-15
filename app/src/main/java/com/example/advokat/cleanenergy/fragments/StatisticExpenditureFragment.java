package com.example.advokat.cleanenergy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.adapters.StatisticCostAdapter;
import com.example.advokat.cleanenergy.app.App;

public class StatisticExpenditureFragment extends Fragment {

    private StatisticCostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistic_expenditure,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Статистика витрат");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new StatisticCostAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addAll(App.getCost().getSumMoney());
    }

}