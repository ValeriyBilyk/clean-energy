package com.example.advokat.cleanenergy.fragments;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.adapters.DataAdapter;

public class CostsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ListView lview;
    private Spinner spinnerConstant;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_costs, container, false);
        //initListView(v);
        TabHost tabs = (TabHost) v.findViewById(android.R.id.tabhost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");

        spec.setContent(R.id.tab_constant);
        spec.setIndicator("Постійні");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab_steady);
        spec.setIndicator("Непостійні");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.costs);
        }

        switch (tabs.getCurrentTab()) {
            case 0: {
                String str1[] = {"тирса суха",
                        "тирса волога",
                        "лушпиння соняшника",
                        "відходи соняшника",
                        "газ",
                        "бензин",
                        "шнек 36 мм",
                        "шнек 40 мм",
                        "носик 32 мм",
                        "носик 36 мм",
                        "ствол",
                        "нігрол",
                        "літол",
                        "мішки 25 кг",
                        "мішки 50 кг",
                        "нитки для мішкозашивки",
                        "стретч-плівка",
                        "бандажна стрічка",
                        "скоби бандажні",
                        "тени оригінальні",
                        "тени товсті з нержавійки",
                        "тени тонкі",
                        "біг-беги",
                        "мішки 100 кг"
                };
                spinnerConstant = (Spinner) v.findViewById(R.id.spinner_constant);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item, str1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerConstant.setAdapter(adapter);
                spinnerConstant.setOnItemSelectedListener(this);
            }
        }
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().setTitle(R.string.app_name);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
