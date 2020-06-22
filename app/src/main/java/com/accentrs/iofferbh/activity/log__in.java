package com.accentrs.iofferbh.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class log__in extends AppCompatActivity implements AuthenticationListener{

    private String token = null;
    private AppPreferences appPreferences = null;
    private AuthenticationDialog authenticationDialog = null;
    private Button button = null;
    private View info = null;

    private SignInButton googleSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        oNClick();

        button = findViewById(R.id.btn_login_in);
        info = findViewById(R.id.info);



        appPreferences = new AppPreferences(log__in.this);

        token = appPreferences.getString(AppPreferences.TOKEN);
        if (token != null) {
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            getUserInfoByAccessToken(token);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oNClick();
            }
        });
    }

    public void login() {
        button.setText("LOGOUT");
        info.setVisibility(View.VISIBLE);
        ImageView pic = findViewById(R.id.pic);
        Picasso.with(this).load(appPreferences.getString(AppPreferences.PROFILE_PIC)).into(pic);
        TextView id = findViewById(R.id.id);
        id.setText(appPreferences.getString(AppPreferences.USER_ID));
        TextView name = findViewById(R.id.name);
//        name.setText(appPreferences.getString(AppPreferences.USER_NAME));
//        new SharedPreferencesData(log__in.this).setUserID(id.getText().toString());
//        new SharedPreferencesData(log__in.this).setLogin_stat(true);
//        Intent i = new Intent(log__in.this, CouponCategoryActivity.class);
//        startActivity(i);
//        finish();

        new SharedPreferencesData(log__in.this).setLogin_stat(true);

        new SharedPreferencesData(log__in.this).setUserID(id.getText().toString());



        if(new SharedPreferencesData(log__in.this).getcart()){
            AlertDialog.Builder a_builder = new AlertDialog.Builder(log__in.this);
            a_builder.setTitle("Payment Option").setCancelable(false).setMessage(new SharedPreferencesData(log__in.this).getMSG())
                    .setPositiveButton("Debit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(log__in.this,paymentshare.class);
                            startActivity(in);
                            finish();
                        }
                    }).setNegativeButton("Credit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent(log__in.this,paymentshare_c.class);
                    startActivity(in);
                    finish();
                }
            });

            AlertDialog alert = a_builder.create();
            alert.show();
        }else{

            Intent i = new Intent(log__in.this,HomeScreenActivity.class);
            startActivity(i);
            finish();}

        
    }

    public void logout() {
        button.setText("INSTAGRAM LOGIN");
        token = null;
        info.setVisibility(View.GONE);
        appPreferences.clear();
    }

    @Override
    public void onTokenReceived(String auth_token) {

        if (auth_token == null)
            return;
        appPreferences.putString(AppPreferences.TOKEN, auth_token);
        token = auth_token;
        getUserInfoByAccessToken(token);

    }

    public void oNClick() {
        if(token!=null)
        {
            logout();
        }
        else {
            authenticationDialog = new AuthenticationDialog(log__in.this, log__in.this);
            authenticationDialog.setCancelable(true);
            authenticationDialog.show();
        }
    }

    private void getUserInfoByAccessToken(String token) {
        new RequestInstagramAPI().execute();
    }

    private class RequestInstagramAPI extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(getResources().getString(R.string.get_user_info_url) + token);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response", jsonObject.toString());
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    if (jsonData.has("id")) {

                        appPreferences.putString(AppPreferences.USER_ID, jsonData.getString("id"));
                        appPreferences.putString(AppPreferences.USER_NAME, jsonData.getString("username"));
                        appPreferences.putString(AppPreferences.PROFILE_PIC, jsonData.getString("profile_picture"));


                        login();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast toast = Toast.makeText(getApplicationContext(),"Login error!",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
