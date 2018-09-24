package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView captionText, priceText;
    Button mPaymentButton;
    private String Reason;
    private int Amount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);

        captionText = findViewById(R.id.caption);
        priceText = findViewById(R.id.amount_view);

        captionText.setText("Payment for "+ Reason);
        priceText.setText("Amount: "+ Amount);
        mPaymentButton = findViewById(R.id.momo_button);

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();0
    }
}
