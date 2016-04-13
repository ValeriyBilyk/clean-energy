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
import com.example.advokat.cleanenergy.activities.DetailsActivity;
import com.example.advokat.cleanenergy.adapters.ExpenditureAdapter;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.cost.Cost;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.AuthRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenditureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ProgressBar progressBar;
    private ExpenditureAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenditure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Витрати");

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabAddData = (FloatingActionButton) view.findViewById(R.id.fab_add_data);
        fabAddData.setOnClickListener(this);

        adapter = new ExpenditureAdapter();
        recyclerView.setAdapter(adapter);

        loadItems();
        loadCategoryItems();
        String m = "asdfs";
    }

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllCosts(new AuthRequest(App.getUser().getKey())).enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                if (response.isSuccessful()) {
                    adapter.addAll(response.body().getExpenditures());
                } else {
                    try {
                        ApiClient.onError(response.errorBody().string(), getActivity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                ApiClient.onError(t.getMessage(), getActivity());
            }
        });

    }

    private void loadCategoryItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getCurrentAsset().enqueue(new Callback<CurrentAsset>() {
            @Override
            public void onResponse(Call<CurrentAsset> call, Response<CurrentAsset> response) {
                if (response.isSuccessful()) {
                    App.setCurrentAsset(response.body());
                } else {
                    try {
                        ApiClient.onError(response.errorBody().string(), getActivity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CurrentAsset> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                ApiClient.onError(t.getMessage(), getActivity());
            }
        });
    }

    @Override
    public void onRefresh() {
        loadItems();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_data:
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
        }
    }
}
