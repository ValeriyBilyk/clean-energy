package com.example.advokat.cleanenergy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.User;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.services.MainService;
import com.example.advokat.cleanenergy.utils.PreferenceManager;
import com.example.advokat.cleanenergy.utils.SHA1Converter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String password;
    private Button btnSignIn;
    private EditText loginEditText;
    private EditText passwordEditText;
    private CheckBox checkBoxStayInSystem;

    private MainService mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);
        checkBoxStayInSystem = (CheckBox) findViewById(R.id.checkbox_keep_in_system);

        mainService = ApiClient.retrofit().getMainService();

        btnSignIn.setOnClickListener(this);

    }

    private String getLogin() {
        return loginEditText.getText().toString();
    }

    private String getPassword() {
        try {
            password = SHA1Converter.SHA1(passwordEditText.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return password;
    }

    private boolean isCheckedCheckBoxStayInSystem() {
        return checkBoxStayInSystem.isChecked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                mainService.login(getLogin(), getPassword()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if ((response.isSuccessful()) && (!response.body().getKey().equals("error"))) {
                            PreferenceManager.setAccessToken(response.body().getKey());
                            PreferenceManager.setStayInSystem(isCheckedCheckBoxStayInSystem());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.incorrect_login_or_password, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}
