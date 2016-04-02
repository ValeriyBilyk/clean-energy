package com.example.advokat.cleanenergy.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.User;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.services.UserService;
import com.example.advokat.cleanenergy.utils.SHA1СConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String password;
    private ProgressDialog pDialog;
    private Button btnSignIn;
    private EditText loginEditText;
    private EditText passwordEditText;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);

        userService = ApiClient.retrofit().create(UserService.class);

        btnSignIn.setOnClickListener(this);

    }

    /*private class BackgroundTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
           *//* pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*//*
        }

        @Override
        protected Integer doInBackground(String... params) {
            *//*InputStream inputStream = null;
            Integer result = 0;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://localhost:8080/user/api/login");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", params[0]));
                nameValuePairs.add(new BasicNameValuePair("password", params[1]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                int statusCode = response.getStatusLine().getStatusCode();
                *//**//* 200 represents HTTP OK *//**//*
                if (statusCode ==  200) {
                    *//**//* receive response as inputStream *//**//*
                    inputStream = response.getEntity().getContent();
                    String resp = convertInputStreamToString(inputStream);
                    parseResult(resp);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return result;*//*
            Ion.with(getApplicationContext())
                    .load("http://10.0.3.2:8080/user/api/login")
                    .setBodyParameter("username", params[0])
                    .setBodyParameter("password", params[1])
                    .asString()
                    .setCallback(new com.koushikdutta.async.future.FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            try {
                                JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                                String key = json.getString("key");
                                SharedPreferences sp = getSharedPreferences("MY_SETTINGS", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("key", key);
                                editor.apply();
                            } catch (JSONException ex) {

                            }
                        }
                    });
            return 127;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            if (result == 1) {
                if (pDialog.isShowing())
                    pDialog.dismiss();

            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("MY_SETTINGS", Context.MODE_PRIVATE);
                String key = sharedPreferences.getString("key", new String());
                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }

    public void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getLogin() {
        return loginEditText.getText().toString();
    }

    private String getPassword() {
        try {
            password = SHA1СConverter.SHA1(passwordEditText.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // new BackgroundTask().execute(getLogin(), getPassword());
                userService.login(getLogin(), getPassword()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}
