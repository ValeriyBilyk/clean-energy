package com.example.advokat.cleanenergy.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.adapters.DataAdapter;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.utils.ListUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class CostsFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private ProgressDialog pDialog;
    private EditText dateEditText;
    private TextView thatBoughtText;
    private ListView lview;
    private Spinner spinnerFixed;
    private Spinner spinnerVolatile;
    private Spinner spinnerMeasurement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_costs, container, false);

        TabHost tabs = (TabHost) v.findViewById(android.R.id.tabhost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");

        spec.setContent(R.id.tab_constant);
        String constant = getString(R.string.constant);
        spec.setIndicator(constant);
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        String mVolatile = getString(R.string.m_volatile);
        spec.setContent(R.id.tab_steady);
        spec.setIndicator(mVolatile);
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.costs);
        }

        switch (tabs.getCurrentTab()) {
            case 0: {
                spinnerFixed = (Spinner) v.findViewById(R.id.spinner_constant);
                //initSpinnerArrayAdapter(v, spinnerFixed, ListUtil.getCurrentAssetsTypeNames(App.getCurrentAsset().getCurrentAssetsType()));
                spinnerFixed.setOnItemSelectedListener(this);
            }

            case 1: {
                spinnerVolatile = (Spinner) v.findViewById(R.id.spinner_category_expenses);
             //   initSpinnerArrayAdapter(v, spinnerVolatile, ListUtil.getExpenditureTypesNames(App.getCurrentAsset().getExpenditureTypes()));
                spinnerVolatile.setOnItemSelectedListener(this);
            }
        }
        dateEditText = (EditText) v.findViewById(R.id.date_edit_text);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CostsFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        spinnerMeasurement = (Spinner) v.findViewById(R.id.spinner_unit_of_measurement);
        if (App.getCurrentAsset() != null) {
            initSpinnerArrayAdapter(v, spinnerMeasurement, ListUtil.getMeasureNames(App.getCurrentAsset().getMeasureUnit()));
        }
        spinnerMeasurement.setOnItemSelectedListener(this);

        thatBoughtText = (TextView) v.findViewById(R.id.that_bought_text);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Light.ttf");
        thatBoughtText.setTypeface(typeface);

        return v;
    }

    private void initSpinnerArrayAdapter(View v, Spinner spinner, String[] dataAdapter) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, dataAdapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().setTitle(R.string.app_name);
    }

    private void initListView(View v) {
        lview = (ListView) v.findViewById(R.id.list_view);
        DataAdapter adapter = new DataAdapter(v.getContext().getApplicationContext(), getDataVolatileCosts());
        lview.setAdapter(adapter);
    }

    private String[] getDataVolatileCosts() {
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

    private String[] getDataFixedCosts() {
        String list[] = {"тирса суха",
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
        return list;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dateEditText.setText(date);
    }
}
