package com.example.advokat.cleanenergy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.CurrentAssetsType;
import com.example.advokat.cleanenergy.entities.ExpendituresDTO;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.DataRequest;
import com.example.advokat.cleanenergy.utils.ListUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

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
    private Button btnSendData;

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
        btnSendData = (Button) findViewById(R.id.button_send_data);

        Expenditures expenditures = getIntent().getParcelableExtra(Expenditures.class.getName());
        boolean isEditing = expenditures != null;

        initSpinnerArrayAdapter(spinnerCurrentAssets, ListUtil.getCurrentAssetsTypeNames(App.getCurrentAsset().getCurrentAssetsType()));
        initSpinnerArrayAdapter(spinnerExpenditure, ListUtil.getExpenditureTypesNames(App.getCurrentAsset().getExpenditureTypes()));
        initSpinnerArrayAdapter(unitOfMeasurement, ListUtil.getMeasureNames(App.getCurrentAsset().getMeasureUnit()));
        initSpinnerArrayAdapter(payer, ListUtil.getPayers(App.getCurrentAsset().getPayer()));

        List<CurrentAssetsType> list = App.getCurrentAsset().getCurrentAssetsType();

        if (isEditing) {
            setTitle("Редагувати");
            if (expenditures.getCurrentAssetsTypeId() != null) {
                changeCosts.setChecked(true);
                spinnerCurrentAssets.setSelection((int) (expenditures.getCurrentAssetsTypeId().getId() - 1));
            } else {
                changeCosts.setChecked(false);
                nameOfCost.setText(String.valueOf(expenditures.getComment()));
                spinnerExpenditure.setSelection((int) (expenditures.getExpenditureTypesId().getId() - 1));
            }
            verifyChangeCosts();
            unitOfMeasurement.setSelection((int) (expenditures.getMeasureUnit().getId() - 1));
            payer.setSelection((int) (expenditures.getPayer().getId() - 1));
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

        btnSendData.setOnClickListener(this);
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
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if (String.valueOf(monthOfYear).length() < 2) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf((monthOfYear + 1));
        }
        if (String.valueOf(dayOfMonth).length() < 2) {
            day = "0" + (dayOfMonth);
        } else {
            day = String.valueOf(dayOfMonth);
        }
        String date = year + "-" + month + "-" + day;
        dateOfCost.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_data:
                AccessKeyDto accessKeyDto = new AccessKeyDto(App.getUser().getKey());
                ExpendituresDTO expendituresDTO;
                if (changeCosts.isChecked()) {
                    expendituresDTO = new ExpendituresDTO(
                            spinnerCurrentAssets.getSelectedItemId() + 1,
                            unitOfMeasurement.getSelectedItemId() + 1,
                            Double.parseDouble(String.valueOf(amount.getText())),
                            Double.parseDouble(String.valueOf(cost.getText())),
                            payer.getSelectedItemId() + 1,
                            description.getText().toString(),
                            dateOfCost.getText().toString()
                    );
                } else {
                    expendituresDTO = new ExpendituresDTO(
                            unitOfMeasurement.getSelectedItemId() + 1,
                            Double.parseDouble(String.valueOf(amount.getText())),
                            Double.parseDouble(String.valueOf(cost.getText())),
                            payer.getSelectedItemId() + 1,
                            description.getText().toString(),
                            spinnerExpenditure.getSelectedItemId() + 1,
                            nameOfCost.getText().toString(),
                            dateOfCost.getText().toString()
                    );
                }
                DataRequest dataRequest = new DataRequest();
                dataRequest.setAccessKeyDto(accessKeyDto);
                dataRequest.setExpendituresDTO(expendituresDTO);
                if (changeCosts.isChecked()) {
                    ApiClient.retrofit().getMainService().sendDataConstant(dataRequest).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "1 - constant", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                } else {
                    ApiClient.retrofit().getMainService().sendDataParmenent(dataRequest).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                response.body();
                                response.code();
                                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
                break;
        }
    }
}
