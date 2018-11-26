package com.pefscomsys.pcc_buea;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;


public class MomoPaymentActivity extends AppCompatActivity {

    TextView captionText, priceText;
    EditText phonenumber;
    Button mPaymentButton;
    private String Reason;
    private int Amount;
    private String year;
    private String type;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Activity myapp;

    PaymentProcessor paymentProcessor;

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            FirebaseUser mUser = mAuth.getCurrentUser();
        }
        else{

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);

        myapp = this;

        try
        {
            this.type = paymentIntent.getStringExtra("TYPE");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //now check if the payment is for diary
        if(this.type != null )
        {
            if(this.type.equals("DIARY"))
            {
                //then get the year
                this.year = paymentIntent.getStringExtra("YEAR");
            }

        }

        SharedPreferences sharedPreferences = getSharedPreferences(PAYMENT_PREFS,MODE_PRIVATE);
        Log.d("Payment", sharedPreferences.getString(Reason, "NOT_PAID"));
        if(sharedPreferences.getString(Reason, "NOT_PAID").equals("PAID")){
            this.finish();
        }
        captionText = findViewById(R.id.caption);
        priceText = findViewById(R.id.amount_view);
        phonenumber = findViewById(R.id.momo_number);
        captionText.setText("Payment for "+ Reason);
        priceText.setText("Amount: "+ Amount + "FCFA");
        mPaymentButton = findViewById(R.id.momo_button);

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //Request for sms permission if it not giving

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.v("Permission: ","Permission is granted");

                    } else {

                        Log.v("Permission","Permission is revoked");
                        ActivityCompat.requestPermissions(myapp, new String[]{Manifest.permission.RECEIVE_SMS}, 1);

                    }
                }
                else { //permission is automatically granted on sdk<23 upon installation
                    Log.v("Permission","Permission is granted");
                }

                String number = phonenumber.getText().toString();

                //Special case for the diary payment
                if(type != null)
                {
                    if(type.equals("DIARY"))
                    {
                        paymentProcessor = new PaymentProcessor(Amount, number, ""+Reason, MomoPaymentActivity.this);
                        paymentProcessor.diary = true;
                        paymentProcessor.diaryYear = year;
                        paymentProcessor.processPayment();
                    }

                }
                else
                {
                    paymentProcessor = new PaymentProcessor(Amount, number, ""+Reason, MomoPaymentActivity.this);
                    paymentProcessor.processPayment();
                }


                Log.d("Payment", " MSG: "+ paymentProcessor.message);

            }
        });

    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean STATE = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            STATE = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return STATE;
    }


}
