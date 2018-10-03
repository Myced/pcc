package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
    TextView reason;
    private String Reason;
    private int Amount;
    private String year;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        reason = findViewById(R.id.paymentCaption);
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);

        reason = findViewById(R.id.paymentCaption);
        reason.setText(Reason.toUpperCase());

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
    }

    public void MomoPay(View view) {
        Intent newIntent = new Intent(this, MomoPaymentActivity.class);
        newIntent.putExtra("REASON", Reason);
        newIntent.putExtra("AMOUNT", Amount);

        if(this.type != null)
        {
            if(this.type.equals("DIARY"))
            {
                newIntent.putExtra("YEAR", this.year);
                newIntent.putExtra("TYPE", "DIARY");
            }
            else
            {

            }
        }
        startActivity(newIntent);

    }

    public void OrangePay(View view) {
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void VisaPay(View view) {
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }
}
