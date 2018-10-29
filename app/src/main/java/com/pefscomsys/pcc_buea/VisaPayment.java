package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

public class VisaPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_payment);
        setTitle("Visa Payment");

        //process here
        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card card = mCardInputWidget.getCard();
        if (card == null) {
            Toast.makeText(getApplicationContext(), "Failed to process visa", Toast.LENGTH_SHORT);
        }
    }
}
