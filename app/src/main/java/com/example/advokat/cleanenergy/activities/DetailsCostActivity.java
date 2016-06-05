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
import com.example.advokat.cleanenergy.entities.AccessKeyDto;
import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.CurrentAssetsType;
import com.example.advokat.cleanenergy.entities.ExpenditureTypes;
import com.example.advokat.cleanenergy.entities.ExpendituresDTO;
import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.DataRequest;
import com.example.advokat.cleanenergy.utils.ListUtil;
import com.example.advokat.cleanenergy.utils.PreferenceManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsCostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final int COST_NOT_CREATED_NEGATIVE_ID = -1;

    private Spinner spinnerCurrentAssets;
    private Spinner spinnerExpenditure;
    private EditText nameOfCost;
    private Spinner unitOfMeasurement;
    private EditText amount;
    private EditText money;
    private Spinner payer;
    private EditText dateOfCost;
    private EditText description;
    private LinearLayout fixedCosts;
    private LinearLayout volatileCosts;
    private Switch changeCosts;
    private Button btnSendData;
    private boolean isEditing;

    private Expenditures expenditures;
    private CurrentAsset currentAsset;

    private long id;

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

        id = getIntent().getLongExtra(Expenditures.class.getName(), -1);
        findExpendituresById(id);
        findCurrentAsset();
        if (expenditures != null) {
            isEditing = true;
        }

        initLayout();
        initSpinners();
        createCurrentAssetsMap();
        createExpenditureMap();
        createMeasureUnit();
        createPayerMap();

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
            initCurrentCost();
        } else {
            dateOfCost.setText(getCurrentDate());
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

    private String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date.getTime());
    }

    private void initCurrentCost() {
        unitOfMeasurement.setSelection(mapMeasureUnit.get(expenditures.getMeasureUnit().getName()));
        payer.setSelection(mapPayer.get(expenditures.getPayer().getName()));
        amount.setText(String.valueOf(expenditures.getAmount()));
        money.setText(String.valueOf(expenditures.getMoney()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(expenditures.getExpenditureDate());
        dateOfCost.setText(s);
        description.setText(String.valueOf(expenditures.getDescription()));
    }

    private void findExpendituresById(long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        expenditures = realm.where(Expenditures.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
    }

    private void findCurrentAsset() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        currentAsset = realm.where(CurrentAsset.class).findFirst();
        realm.commitTransaction();
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
        Iterator<CurrentAssetsType> iterator = currentAsset.getCurrentAssetsType().iterator();
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
        Iterator<Payer> iterator2 = currentAsset.getPayer().iterator();
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
        Iterator<ExpenditureTypes> iterator = currentAsset.getExpenditureTypes().iterator();
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
        Iterator<MeasureUnit> iterator = currentAsset.getMeasureUnit().iterator();
        long g = 0;
        while (iterator.hasNext()) {
            MeasureUnit measureUnit = iterator.next();
            mapMeasureUnit.put(measureUnit.getName(), (int) g);
            mapMeasureUnitId.put(g, measureUnit.getId());
            g++;
        }
    }

    private void initSpinners() {
        initSpinnerArrayAdapter(spinnerCurrentAssets, ListUtil.getCurrentAssetsTypeNames(currentAsset.getCurrentAssetsType()));
        initSpinnerArrayAdapter(spinnerExpenditure, ListUtil.getExpenditureTypesNames(currentAsset.getExpenditureTypes()));
        initSpinnerArrayAdapter(unitOfMeasurement, ListUtil.getMeasureNames(currentAsset.getMeasureUnit()));
        initSpinnerArrayAdapter(payer, ListUtil.getPayers(currentAsset.getPayer()));
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
        money = (EditText) findViewById(R.id.edit_text_cost);
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

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private DataRequest createOrUpdateDataOfFixedCost(long id) {
        AccessKeyDto accessKeyDto = new AccessKeyDto(PreferenceManager.getAccessToken());
        ExpendituresDTO expendituresDTO = new ExpendituresDTO();
        if (id != -1) {
            expendituresDTO.setId(id);
        }
        expendituresDTO.setCurrentAssets(mapCurrentAssetsId.get(spinnerCurrentAssets.getSelectedItemId()));
        expendituresDTO.setMeasureUnitSm(mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()));
        expendituresDTO.setAmount(Double.parseDouble(amount.getText().toString()));
        expendituresDTO.setMoney(Double.parseDouble(money.getText().toString()));
        expendituresDTO.setPayer(mapPayerId.get(payer.getSelectedItemId()));
        expendituresDTO.setDateExpenditure(dateOfCost.getText().toString());
        expendituresDTO.setDescription(description.getText().toString());
        DataRequest dataRequest = new DataRequest();
        dataRequest.setAccessKeyDto(accessKeyDto);
        dataRequest.setExpendituresDTO(expendituresDTO);
        return dataRequest;
    }

    private DataRequest createOrUpdateDataOfVolatileCost(long id) {
        AccessKeyDto accessKeyDto = new AccessKeyDto(PreferenceManager.getAccessToken());
        ExpendituresDTO expendituresDTO = new ExpendituresDTO();
        if (id != -1) {
            expendituresDTO.setId(id);
        }
        expendituresDTO.setCategoryExpenditure(mapExpenditureId.get(spinnerExpenditure.getSelectedItemId()));
        expendituresDTO.setNameExpenditure(nameOfCost.getText().toString());
        expendituresDTO.setAmount(Double.parseDouble(amount.getText().toString()));
        expendituresDTO.setMoney(Double.parseDouble(money.getText().toString()));
        expendituresDTO.setMeasureUnitSm(mapMeasureUnitId.get(unitOfMeasurement.getSelectedItemId()));
        expendituresDTO.setPayer(mapPayerId.get(payer.getSelectedItemId()));
        expendituresDTO.setDateExpenditure(dateOfCost.getText().toString());
        expendituresDTO.setDescription(description.getText().toString());
        DataRequest dataRequest = new DataRequest();
        dataRequest.setAccessKeyDto(accessKeyDto);
        dataRequest.setExpendituresDTO(expendituresDTO);
        return dataRequest;
    }

    private void addVolatileCost(DataRequest dataRequest) {
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

    private void addFixedCost(DataRequest dataRequest) {
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
    }

    private void editFixedCost(DataRequest dataRequest) {
        ApiClient.retrofit().getMainService().editConstantData(dataRequest).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    goToMainActivity();
                    Toast.makeText(getApplicationContext(), "Успішно", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Не відправлено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editVolatileCost(DataRequest dataRequest) {
        ApiClient.retrofit().getMainService().editNotParmenent(dataRequest).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    goToMainActivity();
                    Toast.makeText(getApplicationContext(), "Успішно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Не успіх", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private boolean verifyFixedCost() {
        if (amount.getText().toString().equals("")) {
            amount.setText("1");
        }
        if (money.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Перевірте поле - Вартість", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean verifyVolatileCost() {
        if (amount.getText().toString().equals("")) {
            amount.setText("1");
        }
        if (nameOfCost.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Перевірте поле - Назва витрати", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (money.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Перевірте поле - Вартість", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send_data:
                if (changeCosts.isChecked()) {
                    if (!verifyFixedCost()) return;
                } else {
                    if (!verifyVolatileCost()) return;
                }
                if (!isEditing) {
                    if (changeCosts.isChecked()) {
                        addFixedCost(createOrUpdateDataOfFixedCost(COST_NOT_CREATED_NEGATIVE_ID));
                    } else {
                        addVolatileCost(createOrUpdateDataOfVolatileCost(COST_NOT_CREATED_NEGATIVE_ID));
                    }
                } else {
                    if (changeCosts.isChecked()) {
                        editFixedCost(createOrUpdateDataOfFixedCost(expenditures.getId()));
                    } else {
                        editVolatileCost(createOrUpdateDataOfVolatileCost(expenditures.getId()));
                    }
                }
                break;
        }
    }
}
