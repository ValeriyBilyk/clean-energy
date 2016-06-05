package com.example.advokat.cleanenergy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.advokat.cleanenergy.entities.IncomeDTO;
import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;
import com.example.advokat.cleanenergy.entities.income.BuyerList;
import com.example.advokat.cleanenergy.entities.income.IncomeCategory;
import com.example.advokat.cleanenergy.entities.income.IncomeList;
import com.example.advokat.cleanenergy.entities.income.IncomeSourceList;
import com.example.advokat.cleanenergy.entities.income.IncomeTypesList;
import com.example.advokat.cleanenergy.entities.income.ProductTypesList;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.requests.IncomeRequest;
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

public class DetailsIncomeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final int OPERATION_ID_1 = 1;
    private static final int OPERATION_ID_5 = 5;

    private static final int NEW_INCOME = -1;

    private Spinner spinnerOperation;
    private Spinner spinnerTypeOfOperation;
    private Spinner spinnerTypeOfIncome;
    private Switch switchIncome;
    private Spinner spinnerConstantBuyer;
    private Spinner spinnerReceiverMoney;
    private LinearLayout layoutConstantBuyer;
    private LinearLayout layoutVolatileBuyer;
    private EditText editTextVolatileBuyer;
    private EditText editTextLocation;
    private EditText editTextAmount;
    private EditText editTextCost;
    private Spinner spinnerUnitOfMeasurement;
    private EditText editTextAmountOfBoxes;
    private EditText dateOfCost;
    private EditText editTextDescription;
    private Button sendData;

    private CurrentAsset currentAsset;
    private IncomeList incomeList;
    private IncomeCategory incomeCategory;

    private boolean isEditing;
    private long id;

    private Map<String, Integer> mapOperation;
    private Map<Long, Long> mapOperationId;

    private Map<String, Integer> mapTypeOfOperation;
    private Map<Long, Long> mapTypeOfOperationId;

    private Map<String, Integer> mapTypeOfIncome;
    private Map<Long, Long> mapTypeOfIncomeId;

    private Map<String, Integer> mapConstantBuyer;
    private Map<Long, Long> mapConstantBuyerId;

    private Map<String, Integer> mapReceiverMoney;
    private Map<Long, Long> mapReceiverMoneyId;

    private Map<String, Integer> mapMeasureUnit;
    private Map<Long, Long> mapMeasureUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_income);

        setTitle("Додати");

        id = getIntent().getLongExtra(IncomeList.class.getName(), -1);
        findIncomeByIdInRealm(id);
        findIncomeCategoryInRealm();
        findCurrentAsset();

        initLayout();
        initSpinners();
        createMeasureUnitMap();
        createOperationMap();
        createConstantBuyerMap();
        createReceiverMoneyMap();
        createTypeOfIncomeMap();
        createTypeOfOperationMap();

        if (incomeList != null) {
            isEditing = true;
        }

        if (isEditing) {
            setTitle("Редагувати");
            if (incomeList.getBuyerId() != null) {
                switchIncome.setChecked(true);
                spinnerConstantBuyer.setSelection(mapConstantBuyer.get(incomeList.getBuyerId().getName()));
            } else {
                switchIncome.setChecked(false);
                editTextVolatileBuyer.setText(incomeList.getBuyer());
                editTextLocation.setText(incomeList.getLocations().getName());
            }
            verifyChangeIncome();
            initCurrentIncome();
        } else {
            dateOfCost.setText(getCurrentDate());
        }

        if ((switchIncome != null) && (layoutConstantBuyer != null) && (layoutVolatileBuyer != null)) {
            switchIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    verifyChangeIncome();
                }
            });
        }

        dateOfCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DetailsIncomeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        sendData.setOnClickListener(this);
        spinnerOperation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               /* String s = String.valueOf(mapOperationId.get((long)i));
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();*/
                verifyOperationUI(mapOperationId.get((long) i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void verifyOperationUI(long id) {
        if (id == OPERATION_ID_1) {
            spinnerTypeOfIncome.setEnabled(true);
            editTextAmount.setEnabled(true);
            spinnerUnitOfMeasurement.setEnabled(true);
            editTextAmountOfBoxes.setEnabled(true);
            editTextAmountOfBoxes.setEnabled(true);
        }

        if ((id != OPERATION_ID_1) && (id != OPERATION_ID_5)) {
            spinnerTypeOfIncome.setEnabled(false);
            editTextAmount.setEnabled(false);
            spinnerUnitOfMeasurement.setEnabled(false);
            editTextAmountOfBoxes.setEnabled(false);
        } else if (id == OPERATION_ID_5) {
            editTextAmount.setEnabled(true);
            editTextAmountOfBoxes.setEnabled(false);
        }
    }

    private void initCurrentIncome() {
        spinnerOperation.setSelection(mapOperation.get(incomeList.getIncomeSources().getName()));
        spinnerTypeOfOperation.setSelection(mapTypeOfOperation.get(incomeList.getIncomeTypes().getName()));
        if (incomeList.getMeasureUnit() != null) {
            spinnerUnitOfMeasurement.setSelection(mapMeasureUnit.get(incomeList.getMeasureUnit().getName()));
        }
        if (incomeList.getProductTypesTypes() != null) {
            spinnerTypeOfIncome.setSelection(mapTypeOfIncome.get(incomeList.getProductTypesTypes().getName()));
        }
        spinnerReceiverMoney.setSelection(mapReceiverMoney.get(incomeList.getPayer().getName()));
        editTextAmount.setText(String.valueOf(incomeList.getAmount()));
        editTextCost.setText(String.valueOf(incomeList.getMoney()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(incomeList.getIncomeDate());
        dateOfCost.setText(s);
        editTextAmountOfBoxes.setText(String.valueOf(incomeList.getBags()));
        editTextDescription.setText(incomeList.getComment());
    }

    private void findCurrentAsset() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        currentAsset = realm.where(CurrentAsset.class).findFirst();
        realm.commitTransaction();
    }

    private void findIncomeByIdInRealm(long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        incomeList = realm.where(IncomeList.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
    }

    private void findIncomeCategoryInRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        incomeCategory = realm.where(IncomeCategory.class).findFirst();
        realm.commitTransaction();
    }

    private void verifyChangeIncome() {
        if (switchIncome.isChecked()) {
            layoutConstantBuyer.setVisibility(View.VISIBLE);
            layoutVolatileBuyer.setVisibility(View.GONE);
            switchIncome.setText("Постійний покупець");
        } else {
            layoutConstantBuyer.setVisibility(View.GONE);
            layoutVolatileBuyer.setVisibility(View.VISIBLE);
            switchIncome.setText("Непостійний покупець");
        }
    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_income);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerOperation = (Spinner) findViewById(R.id.spinner_operation);
        spinnerTypeOfOperation = (Spinner) findViewById(R.id.spinner_type_of_operation);
        spinnerTypeOfIncome = (Spinner) findViewById(R.id.spinner_type_of_income);
        switchIncome = (Switch) findViewById(R.id.switch_income);
        spinnerConstantBuyer = (Spinner) findViewById(R.id.spinner_constant_buyer);
        spinnerReceiverMoney = (Spinner) findViewById(R.id.spinner_receiver_money);
        layoutConstantBuyer = (LinearLayout) findViewById(R.id.constant_buyer);
        layoutVolatileBuyer = (LinearLayout) findViewById(R.id.volatile_buyer);
        editTextVolatileBuyer = (EditText) findViewById(R.id.edit_text_volatile_buyer);
        editTextLocation = (EditText) findViewById(R.id.edit_text_location);
        editTextAmount = (EditText) findViewById(R.id.edit_text_amount);
        editTextCost = (EditText) findViewById(R.id.edit_text_cost);
        spinnerUnitOfMeasurement = (Spinner) findViewById(R.id.unit_of_measurement);
        editTextAmountOfBoxes = (EditText) findViewById(R.id.edit_text_amount_of_boxes);
        dateOfCost = (EditText) findViewById(R.id.date_of_cost);
        editTextDescription = (EditText) findViewById(R.id.edit_text_description);
        sendData = (Button) findViewById(R.id.button_send_data_income);
    }

    private void initSpinners() {
        initSpinnerArrayAdapter(spinnerConstantBuyer, ListUtil.getBuyers(incomeCategory.getBuyerList()));
        initSpinnerArrayAdapter(spinnerOperation, ListUtil.getIncomeSources(incomeCategory.getIncomeSourceList()));
        initSpinnerArrayAdapter(spinnerReceiverMoney, ListUtil.getPayers(currentAsset.getPayer()));
        initSpinnerArrayAdapter(spinnerTypeOfOperation, ListUtil.getIncomeTypes(incomeCategory.getIncomeTypesList()));
        initSpinnerArrayAdapter(spinnerTypeOfIncome, ListUtil.getProductTypes(incomeCategory.getProductTypesList()));
        initSpinnerArrayAdapter(spinnerUnitOfMeasurement, ListUtil.getMeasureNames(currentAsset.getMeasureUnit()));
    }

    private void initSpinnerArrayAdapter(Spinner spinner, String[] dataAdapter) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, dataAdapter);
        spinner.setAdapter(adapter);
    }

    private void createMeasureUnitMap() {
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

    private void createOperationMap() {
        mapOperation = new HashMap<>();
        mapOperationId = new HashMap<>();
        Iterator<IncomeSourceList> iterator = incomeCategory.getIncomeSourceList().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            IncomeSourceList sourceList = iterator.next();
            mapOperation.put(sourceList.getName(), (int) i);
            mapOperationId.put(i, sourceList.getId());
            i++;
        }
    }

    private void createTypeOfOperationMap() {
        mapTypeOfOperation = new HashMap<>();
        mapTypeOfOperationId = new HashMap<>();
        Iterator<IncomeTypesList> iterator = incomeCategory.getIncomeTypesList().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            IncomeTypesList typesList = iterator.next();
            mapTypeOfOperation.put(typesList.getName(), (int) i);
            mapTypeOfOperationId.put(i, typesList.getId());
            i++;
        }
    }

    private void createTypeOfIncomeMap() {
        mapTypeOfIncome = new HashMap<>();
        mapTypeOfIncomeId = new HashMap<>();
        Iterator<ProductTypesList> iterator = incomeCategory.getProductTypesList().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            ProductTypesList productTypesList = iterator.next();
            mapTypeOfIncome.put(productTypesList.getName(), (int) i);
            mapTypeOfIncomeId.put(i, productTypesList.getId());
            i++;
        }
    }

    private void createConstantBuyerMap() {
        mapConstantBuyer = new HashMap<>();
        mapConstantBuyerId = new HashMap<>();
        Iterator<BuyerList> iterator = incomeCategory.getBuyerList().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            BuyerList buyerList = iterator.next();
            mapConstantBuyer.put(buyerList.getName(), (int) i);
            mapConstantBuyerId.put(i, buyerList.getId());
            i++;
        }
    }

    private void createReceiverMoneyMap() {
        mapReceiverMoney = new HashMap<>();
        mapReceiverMoneyId = new HashMap<>();
        Iterator<Payer> iterator = currentAsset.getPayer().iterator();
        long i = 0;
        while (iterator.hasNext()) {
            Payer payer = iterator.next();
            mapReceiverMoney.put(payer.getName(), (int) i);
            mapReceiverMoneyId.put(i, payer.getId());
            i++;
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

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send_data_income:
                if (!verifyIncomeFields(mapOperationId.get(spinnerOperation.getSelectedItemId())))
                    return;
                if (!isEditing) {
                    addIncome(createIncome(NEW_INCOME));
                } else {
                    updateIncome(createIncome(incomeList.getId()));
                }
                break;
        }
    }

    private String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date.getTime());
    }

    private IncomeRequest createIncome(long id) {
        AccessKeyDto accessKeyDto = new AccessKeyDto(PreferenceManager.getAccessToken());
        IncomeDTO incomeDTO = new IncomeDTO();

        if (id != NEW_INCOME) {
            incomeDTO.setId(id);
        }

        incomeDTO.setAmount(Double.parseDouble(editTextAmount.getText().toString()));
        incomeDTO.setBags(Double.parseDouble(editTextAmountOfBoxes.getText().toString()));
        incomeDTO.setBuyerId(mapConstantBuyerId.get(spinnerConstantBuyer.getSelectedItemId()));
        incomeDTO.setComment(editTextDescription.getText().toString());
        incomeDTO.setPayer(mapReceiverMoneyId.get(spinnerReceiverMoney.getSelectedItemId()));
        incomeDTO.setIncomeSourceId(mapOperationId.get(spinnerOperation.getSelectedItemId()));
        incomeDTO.setIncomeTypeId(mapTypeOfOperationId.get(spinnerTypeOfOperation.getSelectedItemId()));
        incomeDTO.setMeasureUnitId(mapMeasureUnitId.get(spinnerUnitOfMeasurement.getSelectedItemId()));
        incomeDTO.setMoney(Double.parseDouble(String.valueOf(editTextCost.getText())));
        incomeDTO.setProductTypeId(mapTypeOfIncomeId.get(spinnerTypeOfIncome.getSelectedItemId()));
        incomeDTO.setRecepient(mapReceiverMoneyId.get(spinnerReceiverMoney.getSelectedItemId()));
        incomeDTO.setIncomeDate(dateOfCost.getText().toString());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setIncomeDTO(incomeDTO);
        incomeRequest.setAccessKeyDto(accessKeyDto);

        return incomeRequest;
    }

    private void updateIncome(IncomeRequest incomeRequest) {
        ApiClient.retrofit().getMainService().updateIncome(incomeRequest).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verifyIncomeFields(long id) {
        if (id == OPERATION_ID_1) {
            if (editTextAmount.getText().toString().equals("")) {
                editTextAmount.setText("1");
            }

            if (editTextAmountOfBoxes.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Перевірте поле - Упаковок", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if ((id != OPERATION_ID_1) && (id != OPERATION_ID_5)) {
            if (!editTextAmount.isEnabled()) {
                editTextAmount.setText("1");
            }
            if (!editTextAmountOfBoxes.isEnabled()) {
                editTextAmountOfBoxes.setText("1");
            }

        }

        if (id == OPERATION_ID_5) {
            if (editTextAmount.getText().toString().equals("")) {
                editTextAmount.setText("1");
            }
        }

        if (editTextCost.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Перевірте поле - Вартість", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addIncome(IncomeRequest incomeRequest) {
        ApiClient.retrofit().getMainService().sendIncome(incomeRequest).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
