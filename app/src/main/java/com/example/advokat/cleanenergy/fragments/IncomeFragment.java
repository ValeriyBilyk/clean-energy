package com.example.advokat.cleanenergy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsIncomeActivity;
import com.example.advokat.cleanenergy.adapters.IncomeAdapter;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.income.IncomeCategory;
import com.example.advokat.cleanenergy.entities.income.Incomes;
import com.example.advokat.cleanenergy.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progressBar;
    private IncomeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Доходи");

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new IncomeAdapter();
        recyclerView.setAdapter(adapter);

        fabAddData = (FloatingActionButton) view.findViewById(R.id.fab_add_data);
        fabAddData.setOnClickListener(this);

        loadItems();
    }

    private void loadCategoryItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllIncomeCategory(App.getUser().getKey())
                .enqueue(new Callback<IncomeCategory>() {
                    @Override
                    public void onResponse(Call<IncomeCategory> call, Response<IncomeCategory> response) {

                    }

                    @Override
                    public void onFailure(Call<IncomeCategory> call, Throwable t) {

                    }
                });
    }

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllIncomes(App.getUser().getKey()).enqueue(new Callback<Incomes>() {
            @Override
            public void onResponse(Call<Incomes> call, Response<Incomes> response) {

                if (response.isSuccessful()) {
                    App.setIncomes(response.body());
                    adapter.addAll(response.body().getIncomeList());
                }

                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Incomes> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsIncomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadItems();
    }
}
