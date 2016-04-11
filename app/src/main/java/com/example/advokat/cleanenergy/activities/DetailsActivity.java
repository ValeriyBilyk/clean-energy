package com.example.advokat.cleanenergy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;

public class DetailsActivity extends AppCompatActivity {

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

        Expenditures expenditure = getIntent().getParcelableExtra(Expenditures.class.getName());
        boolean isEditing = expenditure != null;

        if (isEditing) {
            setTitle("Редагувати");
            if (expenditure.getCurrentAssetsTypeId() != null) {
                changeCosts.setChecked(true);
               /* ArrayAdapter<String> adapter;
                String item = expenditure.getCurrentAssetsTypeId().getName();
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, Integer.parseInt(item));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCurrentAssets.setAdapter(adapter);*/
            } else {
                changeCosts.setChecked(false);
            }
            verifyChangeCosts();
            amount.setText(String.valueOf(expenditure.getAmount()));
            cost.setText(String.valueOf(expenditure.getMoney()));
            dateOfCost.setText(String.valueOf(expenditure.getExpenditureDate()));
            description.setText(String.valueOf(expenditure.getDescription()));
        }

        if ((changeCosts != null) && (volatileCosts != null) && (fixedCosts != null)) {
            changeCosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    verifyChangeCosts();
                }
            });
        }

    }

    public void verifyChangeCosts() {
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
}
