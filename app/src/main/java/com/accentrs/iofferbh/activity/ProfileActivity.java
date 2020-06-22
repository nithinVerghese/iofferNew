package com.accentrs.iofferbh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;
    private Context context;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String idToken = account.getIdToken();
                    /*
                     Write to the logic send this id token to server using HTTPS
                     */
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.

                        Toast.makeText(this, "signInResult:failed code="+ e.getStatusCode(), Toast.LENGTH_SHORT).show();

                        //Log.w(Tag,"signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_text);
        profileEmail = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.profile_image);
        signOut=findViewById(R.id.sign_out);

        setDataOnView();

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.web_client_id))
                .build();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
          listener which will be invoked once the sign out is the successful
           */
//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .build();
                GoogleSignInClient googleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(ProfileActivity.this,gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //On Succesfull signout we navigate the user back to LoginActivity
                        Intent intent=new Intent(ProfileActivity.this,log__in.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });


    }

    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Picasso.with(this).load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
        profileName.setText(googleSignInAccount.getDisplayName());
        profileEmail.setText(googleSignInAccount.getEmail());
        Log.e("email", googleSignInAccount.getEmail());


        new SharedPreferencesData(ProfileActivity.this).setLogin_stat(true);

        new SharedPreferencesData(ProfileActivity.this).setUserID(googleSignInAccount.getEmail());



        if(new SharedPreferencesData(ProfileActivity.this).getcart()){

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setTitle("Payment Option").setCancelable(false).setMessage(new SharedPreferencesData(ProfileActivity.this).getMSG())
                    .setPositiveButton("Debit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(ProfileActivity.this,paymentshare.class);
                            startActivity(in);
                            finish();
                        }
                    }).setNegativeButton("Credit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent(ProfileActivity.this,paymentshare_c.class);
                    startActivity(in);
                    finish();
                }
            });

            AlertDialog alert = a_builder.create();
            alert.show();

//            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//            builder1.setMessage("Payment Gatway Option");
//            builder1.setCancelable(true);
//
//            builder1.setPositiveButton(
//                    "Debit",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface delivery_indo_dialog, int id) {
//                            Intent in = new Intent(ProfileActivity.this,paymentshare.class);
//                            startActivity(in);
//                            finish();
//                        }
//                    });
//
//            builder1.setNegativeButton(
//                    "Credit",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface delivery_indo_dialog, int id) {
//
//                            Intent in = new Intent(ProfileActivity.this,paymentshare_c.class);
//                            startActivity(in);
//                            finish();
//
//                        }
//                    });
//
//            AlertDialog alert11 = builder1.create();
//            alert11.show();

//            Intent in = new Intent(this,paymentshare.class);
//            startActivity(in);
//            finish();
        }else{

        Intent i = new Intent(this,HomeScreenActivity.class);
        startActivity(i);
        finish();}


    }

}
