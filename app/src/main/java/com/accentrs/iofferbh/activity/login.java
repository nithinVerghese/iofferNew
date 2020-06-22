package com.accentrs.iofferbh.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class login extends AppCompatActivity {

    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        txtEmail = findViewById(R.id.email);
        txtName = findViewById(R.id.imgpro);
        circleImageView = findViewById(R.id.profile_image);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(login.this, "success: "+loginResult, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(login.this, "erroe:  "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken == null){
               // txtName.setText("");
                txtEmail.setText("");
                //circleImageView.setImageResource(0);
                Toast.makeText(login.this, "user logged out", Toast.LENGTH_LONG).show();
            }else {
                loaduser_profile(currentAccessToken);
            }

        }
    };

    private void loaduser_profile(AccessToken newAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {


                try {

                    //getting values
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    //String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    //value setting
                    txtName.setText(first_name+" "+last_name);
                    txtEmail.setText(email);
                    //Toast.makeText(login.this, id, Toast.LENGTH_SHORT).show();

//                    new SharedPreferencesData(login.this).setUserID(txtEmail.getText().toString());
//                    new SharedPreferencesData(login.this).setLogin_stat(true);
//                    Intent i = new Intent(login.this, CouponCategoryActivity.class);
//                    startActivity(i);
//                    finish();


                    new SharedPreferencesData(login.this).setLogin_stat(true);

                    new SharedPreferencesData(login.this).setUserID(txtEmail.getText().toString());



                    if(new SharedPreferencesData(login.this).getcart()){
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(login.this);
                        a_builder.setTitle("Payment Option").setCancelable(false).setMessage(new SharedPreferencesData(login.this).getMSG())
                                .setPositiveButton("Debit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent in = new Intent(login.this,paymentshare.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }).setNegativeButton("Credit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(login.this,paymentshare_c.class);
                                startActivity(in);
                                finish();
                            }
                        });

                        AlertDialog alert = a_builder.create();
                        alert.show();
                    }else{

                        Intent i = new Intent(login.this,HomeScreenActivity.class);
                        startActivity(i);
                        finish();}

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                    //Glide.with(login.this).load(image_url).into(circleImageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

}
