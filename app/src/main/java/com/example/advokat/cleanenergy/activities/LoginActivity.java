package com.example.advokat.cleanenergy.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.utils.AeSimpleSHA1;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String password;
    private ProgressDialog pDialog;
    private Button btnSignIn;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);

        btnSignIn.setOnClickListener(this);
    }

    private class BackgroundTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
           /* pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected Integer doInBackground(String... params) {
            /*InputStream inputStream = null;
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
                *//* 200 represents HTTP OK *//*
                if (statusCode ==  200) {
                    *//* receive response as inputStream *//*
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
            return result;*/
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
                                String json_result = json.getString("result"); // Get the string "result" inside the Json-object
                                if (json_result.equalsIgnoreCase("ok")){ // Checks if the "result"-string is equals to "ok"
                                    // Result is "OK"
                                    int customer_id = json.getInt("customer_id"); // Get the int customer_id
                                    String customer_email = json.getString("customer_email"); // I don't need to explain this one, right?
                                } else {
                                    // Result is NOT "OK"
                                    String error = json.getString("error");
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show(); // This will show the user what went wrong with a toast
                                    Intent to_main = new Intent(getApplicationContext(), MainActivity.class); // New intent to MainActivity
                                    startActivity(to_main); // Starts MainActivity
                                    finish(); // Add this to prevent the user to go back to this activity when pressing the back button after we've opened MainActivity
                                }
                            } catch (JSONException ex){
                                // This method will run if something goes wrong with the json, like a typo to the json-key or a broken JSON.
                                ex.printStackTrace();
                            }
                        }
                    });
            return 127;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Not", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null != inputStream){
            inputStream.close();
        }
        return result;
    }

    public void parseResult(String result) {
        try{
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            for(int i=0; i< posts.length();i++ ){
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String getLogin() {
        return loginEditText.getText().toString();
    }

    private String getPassword() {
        try {
            password = AeSimpleSHA1.SHA1(passwordEditText.getText().toString());
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
                new BackgroundTask().execute(getLogin(), getPassword());
        }
    }
}
