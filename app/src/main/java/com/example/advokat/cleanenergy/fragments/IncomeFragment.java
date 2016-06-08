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
import com.example.advokat.cleanenergy.activities.DateActivity;
import com.example.advokat.cleanenergy.activities.DetailsIncomeActivity;
import com.example.advokat.cleanenergy.adapters.IncomeAdapter;
import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.IncomesDateFilter;
import com.example.advokat.cleanenergy.entities.RealmInteger;
import com.example.advokat.cleanenergy.entities.income.IncomeCategory;
import com.example.advokat.cleanenergy.entities.income.IncomeList;
import com.example.advokat.cleanenergy.entities.income.Incomes;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.BetweenDateRequest;
import com.example.advokat.cleanenergy.utils.PreferenceManager;
import com.example.advokat.cleanenergy.utils.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progressBar;
    private IncomeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddData;

    IncomesDateFilter incomesDateFilter;

    private Realm realm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Доходи");

        setHasOptionsMenu(true);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Utils.addHorizontalDivider(recyclerView, getContext(), Utils.MARGIN_LEFT_0, Utils.MARGIN_RIGHT_0);

        adapter = new IncomeAdapter();
        recyclerView.setAdapter(adapter);

        fabAddData = (FloatingActionButton) view.findViewById(R.id.fab_add_data);
        fabAddData.setOnClickListener(this);

//        loadItems();
        if (getIncomesDateFilter() != null) {
            loadItemsBetweenDate();
        } else {
            loadItems();
        }
        loadCategoryItems();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private IncomesDateFilter getIncomesDateFilter() {
        realm.beginTransaction();
        IncomesDateFilter incomesDateFilter = realm.where(IncomesDateFilter.class).findFirst();
        realm.commitTransaction();
        return incomesDateFilter;
    }

    private void loadCategoryItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllIncomeCategory(PreferenceManager.getAccessToken())
                .enqueue(new Callback<IncomeCategory>() {
                    @Override
                    public void onResponse(Call<IncomeCategory> call, Response<IncomeCategory> response) {
                        if (response.isSuccessful()) {
                            copyAllIncomesCategoriesToRealm(response.body());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<IncomeCategory> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private BetweenDateRequest getBetweenDataRequest(IncomesDateFilter incomesDateFilter) {
        RealmList<RealmInteger> listYear = incomesDateFilter.getYear();
        Integer[] arrYear = new Integer[listYear.size()];
        int i = 0;
        for (RealmInteger realmInteger : listYear) {
            arrYear[i] = realmInteger.getValue();
            i++;
        }
        RealmList<RealmInteger> listMonth = incomesDateFilter.getMonth();
        Integer[] arrMonth = new Integer[listMonth.size()];
        int j = 0;
        for (RealmInteger realmInteger : listMonth) {
            arrMonth[j] = realmInteger.getValue() - 1;
            j++;
        }
        BetweenDateRequest betweenDateRequest = new BetweenDateRequest();
        AccessKeyDto accessKeyDto = new AccessKeyDto(PreferenceManager.getAccessToken());
        betweenDateRequest.setAccessKeyDto(accessKeyDto);
        betweenDateRequest.setYear(arrYear);
        betweenDateRequest.setMonth(arrMonth);
        return betweenDateRequest;
    }

    private void loadItemsBetweenDate() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getIncomesBetweenDate(getBetweenDataRequest(getIncomesDateFilter())).enqueue(new Callback<Incomes>() {
            @Override
            public void onResponse(Call<Incomes> call, Response<Incomes> response) {
                if ((response.isSuccessful()) && (response.body().getIncomeList() != null) ) {
                    copyAllIncomesToRealm(response.body().getIncomeList());
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

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.retrofit().getMainService().getAllIncomes(PreferenceManager.getAccessToken()).enqueue(new Callback<Incomes>() {
            @Override
            public void onResponse(Call<Incomes> call, Response<Incomes> response) {
                if (response.isSuccessful()) {
                    copyAllIncomesToRealm(response.body().getIncomeList());
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

    private void copyAllIncomesToRealm(List<IncomeList> incomeLists) {
        realm.beginTransaction();
        for (IncomeList incomeList : incomeLists) {
            realm.copyToRealmOrUpdate(incomeList);
        }
        realm.commitTransaction();
    }

    private void copyAllIncomesCategoriesToRealm(IncomeCategory incomeCategory) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(incomeCategory);
        realm.commitTransaction();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_choose_date, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_date:
                Intent intent = new Intent(getContext(), DateActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_data:
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailsIncomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
//        loadItems();
        if (getIncomesDateFilter() != null) {
            loadItemsBetweenDate();
        } else {
            loadItems();
        }
        loadCategoryItems();
    }
}
