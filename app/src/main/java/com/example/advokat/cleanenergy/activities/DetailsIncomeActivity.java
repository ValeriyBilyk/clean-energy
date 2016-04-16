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

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;
import com.example.advokat.cleanenergy.entities.income.BuyerList;
import com.example.advokat.cleanenergy.entities.income.IncomeList;
import com.example.advokat.cleanenergy.entities.income.IncomeSourceList;
import com.example.advokat.cleanenergy.entities.income.IncomeTypesList;
import com.example.advokat.cleanenergy.entities.income.ProductTypesList;
import com.example.advokat.cleanenergy.utils.ListUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetailsIncomeActivity extends AppCompatActivity {

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
    private IncomeList incomeList;
    private boolean isEditing;

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
        initLayout();
        initSpinners();
        createMeasureUnitMap();
        createOperationMap();
        createConstantBuyerMap();
        createReceiverMoneyMap();
        createTypeOfIncomeMap();
        createTypeOfOperationMap();

        incomeList = getIntent().getParcelableExtra(IncomeList.class.getName());
        isEditing = incomeList != null;

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
            spinnerOperation.setSelection(mapOperation.get(incomeList.getIncomeSources().getName()));
            spinnerTypeOfOperation.setSelection(mapTypeOfOperation.get(incomeList.getIncomeTypes().getName()));
            spinnerUnitOfMeasurement.setSelection(mapMeasureUnit.get(incomeList.getMeasureUnit().getName()));
            spinnerTypeOfIncome.setSelection(mapTypeOfIncome.get(incomeList.getProductTypesTypes().getName()));
            spinnerReceiverMoney.setSelection(mapReceiverMoney.get(incomeList.getPayer().getName()));
            editTextAmount.setText(String.valueOf(incomeList.getAmount()));
            editTextCost.setText(String.valueOf(incomeList.getMoney()));
            dateOfCost.setText(incomeList.getIncomeDate());
            editTextAmountOfBoxes.setText(String.valueOf(incomeList.getBags()));
            editTextDescription.setText(incomeList.getComment());
        }

        if ((switchIncome != null) && (layoutConstantBuyer != null) && (layoutVolatileBuyer != null)) {
            switchIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    verifyChangeIncome();
                }
            });
        }
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
        sendData = (Button) findViewById(R.id.button_send_data);
    }

    private void initSpinners() {
        initSpinnerArrayAdapter(spinnerConstantBuyer, ListUtil.getBuyers(App.getIncomeCategory().getBuyerList()));
        initSpinnerArrayAdapter(spinnerOperation, ListUtil.getIncomeSources(App.getIncomeCategory().getIncomeSourceList()));
        initSpinnerArrayAdapter(spinnerReceiverMoney, ListUtil.getPayers(App.getCurrentAsset().getPayer()));
        initSpinnerArrayAdapter(spinnerTypeOfOperation, ListUtil.getIncomeTypes(App.getIncomeCategory().getIncomeTypesList()));
        initSpinnerArrayAdapter(spinnerTypeOfIncome, ListUtil.getProductTypes(App.getIncomeCategory().getProductTypesList()));
        initSpinnerArrayAdapter(spinnerUnitOfMeasurement, ListUtil.getMeasureNames(App.getCurrentAsset().getMeasureUnit()));
    }

    private void initSpinnerArrayAdapter(Spinner spinner, String[] dataAdapter) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, dataAdapter);
        spinner.setAdapter(adapter);
    }

    private void createMeasureUnitMap() {
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

    private void createOperationMap() {
        mapOperation = new HashMap<>();
        mapOperationId = new HashMap<>();
        Iterator<IncomeSourceList> iterator = App.getIncomeCategory().getIncomeSourceList().iterator();
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
        Iterator<IncomeTypesList> iterator = App.getIncomeCategory().getIncomeTypesList().iterator();
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
        Iterator<ProductTypesList> iterator = App.getIncomeCategory().getProductTypesList().iterator();
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
        Iterator<BuyerList> iterator = App.getIncomeCategory().getBuyerList().iterator();
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
        Iterator<Payer> iterator = App.getCurrentAsset().getPayer().iterator();
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
}
