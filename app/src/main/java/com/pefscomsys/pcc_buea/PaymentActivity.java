package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
        newIntent.putExtra("AMOUNT", this.getPrice(Reason, "FCFA"));

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
        Intent newIntent = new Intent(this, OrangeMoneyPayment.class);
        newIntent.putExtra("REASON", Reason);
        newIntent.putExtra("AMOUNT", this.getPrice(Reason, "FCFA"));

        if(this.type != null)
        {
            if(this.type.equals("DIARY"))
            {
                newIntent.putExtra("YEAR", this.year);
                newIntent.putExtra("TYPE", "DIARY");
            }

        }
        startActivity(newIntent);
    }

    public void VisaPay(View view) {


        Intent newIntent = new Intent(this, VisaPayment.class);
        newIntent.putExtra("REASON", Reason);
        newIntent.putExtra("AMOUNT", this.getPrice(Reason, "US"));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                //put intent values

                NavUtils.navigateUpFromSameTask(this);
                return true;
            case  R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case  R.id.book_and_abbre:
                startActivity(new Intent(this, BookAbbreviationActivity.class));
                return true;
            case  R.id.feedback_menu:
                startActivity(new Intent(this, FeedBack.class));
                return true;
            case  R.id.feeplan_menu:
                startActivity(new Intent(this, FeePlan.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private int getPrice(String module, String currency)
    {
        int amount = 0;

        if(currency.equals("FCFA"))
        {
            //get the normal currency
            if(module.contains("Diary"))
            {
                //set the amount
                amount = Prices.SCRIPTURE;
            }
            else if(module.contains("Hymn"))
            {
                amount = Prices.HYMN_PRICE;
            }
            else if(module.contains("ECHO"))
            {
                amount = Prices.ECHO_PRICE;
            }
            else if(module.contains("MESSENGER"))
            {
                amount = Prices.THE_MESSENGER;
            }
            else
            {
                //unknow. so the amount should be zero
                amount = 10000;
            }

        }
        else if(currency.equals("US"))
        {
            //get the currency for Dollar
            if(module.contains("Diary"))
            {
                //set the amount
                amount = Prices.SCRIPTURE_US;
            }
            else if(module.contains("Hymn"))
            {
                amount = Prices.HYMN_PRICE_US;
            }
            else if(module.contains("ECHO"))
            {
                amount = Prices.ECHO_PRICE_US;
            }
            else if(module.contains("MESSENGER"))
            {
                amount = Prices.THE_MESSENGER_US;
            }
            else
            {
                //unknow. so the amount should be zero
                amount = 10000;
            }
        }
        else
        {
            amount = 10000;
        }

        //return the amount
        return amount;
    }
}
