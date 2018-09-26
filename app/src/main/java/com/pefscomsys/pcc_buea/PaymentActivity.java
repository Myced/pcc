package com.pefscomsys.pcc_buea;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;


public class PaymentActivity extends AppCompatActivity {

    TextView captionText, priceText;
    EditText phonenumber;
    Button mPaymentButton;
    private String Reason;
    private int Amount;
    PaymentProcessor paymentProcessor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);
        SharedPreferences sharedPreferences = getSharedPreferences(PAYMENT_PREFS,MODE_PRIVATE);
        Log.d("Payment", sharedPreferences.getString(Reason, "NOT_PAID"));
        if(sharedPreferences.getString(Reason, "NOT_PAID").equals("PAID")){
            this.finish();
        }
        captionText = findViewById(R.id.caption);
        priceText = findViewById(R.id.amount_view);
        phonenumber = findViewById(R.id.momo_number);
        captionText.setText("Payment for "+ Reason);
        priceText.setText("Amount: "+ Amount);
        mPaymentButton = findViewById(R.id.momo_button);

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                //Request for sms permission if it not giving

                if(!checkPermission(android.Manifest.permission.RECEIVE_SMS)) {
                    ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.RECEIVE_SMS}, 222);
                }

                String number = phonenumber.getText().toString();
                paymentProcessor = new PaymentProcessor(Amount, number, ""+Reason, PaymentActivity.this);
                paymentProcessor.processPayment();

                Log.d("Payment", " MSG: "+ paymentProcessor.message);

            }
        });

    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

}
