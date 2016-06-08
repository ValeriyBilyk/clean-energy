package com.example.advokat.cleanenergy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.DateFilter;
import com.example.advokat.cleanenergy.entities.RealmInteger;
import com.example.advokat.cleanenergy.fragments.IncomeFragment;
import com.example.advokat.cleanenergy.utils.DateUtil;
import com.example.advokat.cleanenergy.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class DateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final int DATE_START = 1;
    private static final int DATE_STOP = 2;
    private int operationId;

    private Date dateStart;
    private Date dateStop;

    private int flag;
    private EditText textDateStart;
    private EditText textDateStop;
    private Button btnApplyDate;
    private Button btnResetDate;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        String name = getIntent().getStringExtra("key");
        if (name.equals(IncomeFragment.class.getName())) {
            setTitle("Фільтр доходів за датою");
            operationId = Utils.INCOME;
        } else {
            setTitle("Фільтр витрат за датою");
            operationId = Utils.COST;
        }
        initLayout();

    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_date);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textDateStart = (EditText) findViewById(R.id.text_date_start);
        textDateStop = (EditText) findViewById(R.id.text_date_stop);
        btnApplyDate = (Button) findViewById(R.id.btn_apply_date_filter);
        btnResetDate = (Button) findViewById(R.id.btn_reset_date_filter);

        if (realm.where(DateFilter.class).equalTo("id", operationId).findFirst() != null) {
            textDateStart.setText(realm.where(DateFilter.class).equalTo("id", operationId).findFirst().getDateStart());
            textDateStop.setText(realm.where(DateFilter.class).equalTo("id", operationId).findFirst().getDateStop());
            initDates();
        }

        btnApplyDate.setOnClickListener(this);
        btnResetDate.setOnClickListener(this);

        textDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                flag = DATE_START;
            }
        });

        textDateStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                flag = DATE_STOP;
            }
        });
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String month = String.valueOf(monthOfYear + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        String date = year + "-" + month;
        switch (flag) {
            case 1:
                try {
                    dateStart = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textDateStart.setText(date);
                break;
            case 2:
                try {
                    dateStop = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textDateStop.setText(date);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_date_filter:
                String dStart = textDateStart.getText().toString();
                String dStop = textDateStop.getText().toString();
                if ((DateUtil.isValidStringBetweenDates(dStart, dStop)) || (DateUtil.isValidBetweenDates(dateStart, dateStop))) {
                    copyDateFilterToRealm(operationId);
                    this.finish();
                }
                break;
            case R.id.btn_reset_date_filter:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Ви дійсно бажаєте очистити фільтр по даті?");

        alertDialogBuilder.setPositiveButton("Так", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteDateFilterFromRealm();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Ні",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteDateFilterFromRealm() {
        realm.beginTransaction();
        realm.where(DateFilter.class).equalTo("id", operationId).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    private void copyDateFilterToRealm(int id) {
        DateFilter dateFilter = new DateFilter();
        RealmInteger realmYear;
        RealmInteger realmMonth;
        RealmList<RealmInteger> realmYearsList = new RealmList<>();
        RealmList<RealmInteger> realmMonthsList = new RealmList<>();

        realmYear = new RealmInteger();
        realmYear.setValue(DateUtil.getYear(dateStart));
        realmYearsList.add(realmYear);

        realmYear = new RealmInteger();
        realmYear.setValue(DateUtil.getYear(dateStop));
        realmYearsList.add(realmYear);

        realmMonth = new RealmInteger();
        realmMonth.setValue(DateUtil.getMonth(dateStart));
        realmMonthsList.add(realmMonth);

        realmMonth = new RealmInteger();
        realmMonth.setValue(DateUtil.getMonth(dateStop));
        realmMonthsList.add(realmMonth);

        if (id == Utils.COST) {
            dateFilter.setId(Utils.COST);
        } else {
            dateFilter.setId(Utils.INCOME);
        }
        dateFilter.setYear(realmYearsList);
        dateFilter.setMonth(realmMonthsList);
        dateFilter.setDateStart(textDateStart.getText().toString());
        dateFilter.setDateStop(textDateStop.getText().toString());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(dateFilter);
        realm.commitTransaction();
    }

    private void initDates() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            dateStart = simpleDateFormat.parse(textDateStart.getText().toString());
            dateStop = simpleDateFormat.parse(textDateStop.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
