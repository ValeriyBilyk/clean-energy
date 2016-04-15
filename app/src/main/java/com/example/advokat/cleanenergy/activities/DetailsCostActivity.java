package com.example.advokat.cleanenergy.activities;

import android.content.Intent;
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
import com.example.advokat.cleanenergy.entities.ExpenditureTypes;
import com.example.advokat.cleanenergy.entities.ExpendituresDTO;
import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.DataRequest;
import com.example.advokat.cleanenergy.utils.ListUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsCostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

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
    private boolean isEditing;
    private Expenditures expenditures;

    private Map<String, Integer> mapCurrentAssets;
    private Map<Long, Long> mapCurrentAssetsId;

    private Map<String, Integer> mapExpenditure;
    private Map<Long, Long> mapExpenditureId;

    private Map<String, Integer> mapMeasureUnit;
    private Map<Long, Long> mapMeasureUnitId;

    private Map<String, Integer> mapPayer;
    private Map<Long, Long> mapPayerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cost);

        setTitle("Додати");
        initLayout();
        initSpinners();
        createCurrentAssetsMap();
        createExpenditureMap();
        createMeasureUnit();
        createPayerMap();

        expenditures = getIntent().getParcelableExtra(Expenditures.class.getName());
        isEditing = expenditures != null;

        if (isEditing) {
            setTitle("Редагувати");
            if (expenditures.getCurrentAssetsTypeId() != null) {
                changeCosts.setChecked(true);
                spinnerCurrentAssets.setSelection(mapCurrentAssets.get(expenditures.getCurrentAssetsTypeId().getName()));
            } else {
                changeCosts.setChecked(false);
                nameOfCost.setText(String.valueOf(expenditures.getComment()));
                spinnerExpenditure.setSelection(mapExpenditure.get(expenditures.getExpenditureTypesId().getName()));
            }
            verifyChangeCosts();
            unitOfMeasurement.setSelection(mapMeasureUnit.get(expenditures.getMeasureUnit().getName()));
            payer.setSelection(mapPayer.get(expenditures.getPayer().getName()));
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
                        DetailsCostActivity.this,
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

    private void createCurrentAssetsMap() {
        mapCurrentAssets = new HashMap<>();
        mapCurrentAssetsId = new HashMap<>();
        Iterator<CurrentAssetsType> iterator = App.getCurrentAsset().getCurrentAssetsType().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            CurrentAssetsType currentAssetsType = iterator.next();
            mapCurrentAssets.put(currentAssetsType.getName(), (int) i);
            mapCurrentAssetsId.put(i, currentAssetsType.getId());
            i++;
        }
    }

    private void createPayerMap() {
        mapPayer = new HashMap<>();
        mapPayerId = new HashMap<>();
        Iterator<Payer> iterator2 = App.getCurrentAsset().getPayer().iterator();
        long k = 0;
        while (iterator2.hasNext()) {
            Payer payer = iterator2.next();
            mapPayer.put(payer.getName(), (int) k);
            mapPayerId.put(k, payer.getId());
            k++;
        }
    }

    private void createExpenditureMap() {
        mapExpenditure = new HashMap<>();
        mapExpenditureId = new HashMap<>();
        Iterator<ExpenditureTypes> iterator = App.getCurrentAsset().getExpenditureTypes().iterator();
        long m = 0;
        while (iterator.hasNext()) {
            ExpenditureTypes expenditureTypes = iterator.next();
            mapExpenditure.put(expenditureTypes.getName(), (int) m);
            mapExpenditureId.put(m, expenditureTypes.getId());
            m++;
        }
    }

    private void createMeasureUnit() {
        mapMeasureUnit = new HashMap<>();
        mapMeasureUnitId = new HashMap<>();
        Iterator<MeasureUnit> iterator = App.getCurrentAsset().getMeasureUnit().iterator();
        long g = 0;
        while (iterator.hasNext()) {
            MeasureUnit measureUnit = iterator.next();
            mapMeasureUnit.put(measureUnit.getName(), (int) g);
            mapMeasureUnitId.put(g, measureUnit.getId());
            g++;
        }
    }

    private void initSpinners() {
        initSpinnerArrayAdapter(spinnerCurrentAssets, ListUtil.getCurrentAssetsTypeNames(App.getCurrentAsset().getCurrentAssetsType()));
        initSpinnerArrayAdapter(spinnerExpenditure, ListUtil.getExpenditureTypesNames(App.getCurrentAsset().getExpenditureTypes()));
        initSpinnerArrayAdapter(unitOfMeasurement, ListUtil.getMeasureNames(App.getCurrentAsset().getMeasureUnit()));
        initSpinnerArrayAdapter(payer, ListUtil.getPayers(App.getCurrentAsset().getPayer()));
    }

    private void initLayout() {
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
                double amountText;
                double costText;
                if (amount.getText().toString().equals("")) {
                    amountText = 0;
                } else {
                    amountText = Double.parseDouble(String.valueOf(amount.getText()));
                }
                if (cost.getText().toString().equals("")) {
                    costText = 0;
                } else {
                    costText = Double.parseDouble(String.valueOf(cost.getText()));
                }
                if (!isEditing) {
                    if (changeCosts.isChecked()) {
                        expendituresDTO = new ExpendituresDTO(
                                mapCurrentAssetsId.get(spinnerCurrentAssets.getSelectedItemId()),
                                mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()),
                                amountText,
                                costText,
                                mapPayerId.get(payer.getSelectedItemId()),
                                description.getText().toString(),
                                dateOfCost.getText().toString()
                        );
                    } else {
                        expendituresDTO = new ExpendituresDTO(
                                mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()),
                                amountText,
                                costText,
                                mapPayerId.get(payer.getSelectedItemId()),
                                description.getText().toString(),
                                mapExpenditureId.get(spinnerExpenditure.getSelectedItemId()),
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
                                    goToMainActivity();
                                    Toast.makeText(getApplicationContext(), "Відправлено", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Помилка", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        ApiClient.retrofit().getMainService().sendDataParmenent(dataRequest).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    goToMainActivity();
                                    Toast.makeText(getApplicationContext(), "Відправлено", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Помилка", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    if (changeCosts.isChecked()) {
                        expendituresDTO = new ExpendituresDTO(
                                expenditures.getId(),
                                mapCurrentAssetsId.get(spinnerCurrentAssets.getSelectedItemId()),
                                mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()),
                                amountText,
                                costText,
                                mapPayerId.get(payer.getSelectedItemId()),
                                description.getText().toString(),
                                dateOfCost.getText().toString()
                        );
                    } else {
                        expendituresDTO = new ExpendituresDTO(
                                expenditures.getId(),
                                mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()),
                                amountText,
                                costText,
                                mapPayerId.get(payer.getSelectedItemId()),
                                description.getText().toString(),
                                mapExpenditureId.get(spinnerExpenditure.getSelectedItemId()),
                                nameOfCost.getText().toString(),
                                dateOfCost.getText().toString()
                        );
                    }
                    DataRequest dataRequest = new DataRequest();
                    dataRequest.setAccessKeyDto(accessKeyDto);
                    dataRequest.setExpendituresDTO(expendituresDTO);
                    if (changeCosts.isChecked()) {
                        ApiClient.retrofit().getMainService().editConstantData(dataRequest).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    goToMainActivity();
                                    Toast.makeText(getApplicationContext(), "Успіх", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Не успіх", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    } else {
                        ApiClient.retrofit().getMainService().editNotParmenent(dataRequest).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    goToMainActivity();
                                    Toast.makeText(getApplicationContext(), "Успіх", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Не успіх", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }
                }
                break;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
