package com.example.advokat.cleanenergy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.CurrentAssetsType;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.utils.ListUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Spinner spinnerCurrentAssets;
    private Spinner spinnerExpenditure;
    private EditText nameOfCost;
    private Spinner unitOfMeasurement;
    private EditText amount;
    private EditText cost;
    private Spinner payer;
    private EditText dateOfCost;
    private EditText description;
    private LinearLayout fixedCosts;
    private LinearLayout volatileCosts;
    private Switch changeCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("Додати");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fixedCosts = (LinearLayout) findViewById(R.id.fixed_costs);
        volatileCosts = (LinearLayout) findViewById(R.id.volatile_costs);

        changeCosts = (Switch) findViewById(R.id.switch_cost);

        spinnerCurrentAssets = (Spinner) findViewById(R.id.spinner_current_assets);
        spinnerExpenditure = (Spinner) findViewById(R.id.spinner_expenditure);
        nameOfCost = (EditText) findViewById(R.id.edit_text_name_of_cost);
        unitOfMeasurement = (Spinner) findViewById(R.id.unit_of_measurement);
        amount = (EditText) findViewById(R.id.edit_text_amount);
        cost = (EditText) findViewById(R.id.edit_text_cost);
        payer = (Spinner) findViewById(R.id.spinner_payer);
        dateOfCost = (EditText) findViewById(R.id.date_of_cost);
        description = (EditText) findViewById(R.id.edit_text_description);

        Expenditures expenditures = getIntent().getParcelableExtra(Expenditures.class.getName());
        boolean isEditing = expenditures != null;

        List<CurrentAssetsType> list = App.getCurrentAsset().getCurrentAssetsType();

        if (isEditing) {
            setTitle("Редагувати");
            if (expenditures.getCurrentAssetsTypeId() != null) {
                changeCosts.setChecked(true);
                initSpinnerArrayAdapter(spinnerCurrentAssets, ListUtil.getCurrentAssetsTypeNames(App.getCurrentAsset().getCurrentAssetsType()));
            } else {
                changeCosts.setChecked(false);
            }
            verifyChangeCosts();
            amount.setText(String.valueOf(expenditures.getAmount()));
            cost.setText(String.valueOf(expenditures.getMoney()));
            dateOfCost.setText(String.valueOf(expenditures.getExpenditureDate()));
            description.setText(String.valueOf(expenditures.getDescription()));
        }

        dateOfCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DetailsActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        if ((changeCosts != null) && (volatileCosts != null) && (fixedCosts != null)) {
            changeCosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    verifyChangeCosts();
                }
            });
        }

    }

    private void verifyChangeCosts() {
        if (changeCosts.isChecked()) {
            fixedCosts.setVisibility(View.VISIBLE);
            volatileCosts.setVisibility(View.GONE);
            changeCosts.setText("Постійні витрати");
        } else {
            fixedCosts.setVisibility(View.GONE);
            volatileCosts.setVisibility(View.VISIBLE);
            changeCosts.setText("Непостійні витрати");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initSpinnerArrayAdapter(Spinner spinner, String[] dataAdapter) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, dataAdapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        if (String.valueOf(monthOfYear).length() < 2) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf((monthOfYear + 1));
        }
        String date = year + "-" + month + "-" + dayOfMonth;
        dateOfCost.setText(date);
    }
}
