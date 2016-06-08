package com.example.advokat.cleanenergy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsCostActivity;
import com.example.advokat.cleanenergy.adapters.CostAdapter;
import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.cost.Cost;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.AuthRequest;
import com.example.advokat.cleanenergy.utils.PreferenceManager;
import com.example.advokat.cleanenergy.utils.Utils;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ProgressBar progressBar;
    private CostAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddData;

    private Realm realm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenditure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Витрати");

        setHasOptionsMenu(true);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
       /* recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        Utils.addHorizontalDivider(recyclerView, getContext(), Utils.MARGIN_LEFT_0, Utils.MARGIN_RIGHT_0);

        fabAddData = (FloatingActionButton) view.findViewById(R.id.fab_add_data);
        fabAddData.setOnClickListener(this);

        adapter = new CostAdapter();
        recyclerView.setAdapter(adapter);

//        if (realm.where(Expenditures.class).findFirst() == null) {
        loadItems();
        loadCategoryItems();
//        } else {
          /*  List<Expenditures> expenditures = realm.where(Expenditures.class).findAll();
            adapter.addAll(expenditures);
            progressBar.setVisibility(View.GONE);
        }*/
    }

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllCosts(new AuthRequest(PreferenceManager.getAccessToken())).enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                if (response.isSuccessful()) {
                    copyAllExpendituresToRealm(response.body().getExpenditures());
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
        ApiClient.retrofit().getMainService().getCurrentAsset(PreferenceManager.getAccessToken()).enqueue(new Callback<CurrentAsset>() {
            @Override
            public void onResponse(Call<CurrentAsset> call, Response<CurrentAsset> response) {
                if (response.isSuccessful()) {
                    copyAllExpendituresCategoriesToRealm(response.body());
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
        loadCategoryItems();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_data:
                Intent intent = new Intent(getContext(), DetailsCostActivity.class);
                startActivity(intent);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_choose_date, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_date:
                adapter.sortByDate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void copyAllExpendituresToRealm(List<Expenditures> expendituresList) {
        realm.beginTransaction();
        for (Expenditures expenditures : expendituresList) {
            realm.copyToRealmOrUpdate(expenditures);
        }
        realm.commitTransaction();
    }

    private void copyAllExpendituresCategoriesToRealm(CurrentAsset currentAsset) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(currentAsset);
        realm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
