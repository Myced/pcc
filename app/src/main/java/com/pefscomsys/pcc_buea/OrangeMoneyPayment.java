package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class OrangeMoneyPayment extends AppCompatActivity {

    //intent variables
    TextView orangeCaption, amountView;
    private String Reason;
    private int Amount;
    private String year;
    private String type;

    Button orangeButton;
    EditText orangeNumber;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orange_money_payment);

        orangeNumber = findViewById(R.id.orange_number);
        orangeButton = findViewById(R.id.orange_button);
        orangeCaption = findViewById(R.id.orange_caption);
        amountView = findViewById(R.id.orange_amount_view);

        //get variables from the intent
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);

        String amountText = Integer.toString(Amount) + "FCFA";

        orangeCaption.setText(Reason.toUpperCase());
        amountView.setText(amountText);

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

        //set onclick listener on the button
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startDialog();

                String number = orangeNumber.getText().toString();

                //validate phone number
                if(number.length() != 9)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Phone number", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                }
                else
                {
                    Log.d("PCCAPP", "lse");
                    //process orange money here
                    String url = "https://jsonplaceholder.typecode.com/todos/1"; //make a get request to google
                    //start with the request parameters

                    //prepare to make an http request
                    RequestParams params = new RequestParams();
                    AsyncHttpClient request = new AsyncHttpClient();

                    //make the request
                    request.setResponseTimeout(30000);
                    request.get(url, params, new JsonHttpResponseHandler(){

                        @Override
                        public void onStart()
                        {
                            super.onStart();
                            Log.d("PCCAPP", "Http Started");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                        {
                            //no processing. just toast an error forn now
                            Log.d("PCCAPP", "Faieled http");
                            Toast.makeText(getApplicationContext(), "Error Processing Orange Money", Toast.LENGTH_SHORT).show();
                            dismissDialog();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
                        {
                            Log.d("PCCAPP", "Failed");
                            Toast.makeText(getApplicationContext(), "Error contacting Orange. Check internet connection", Toast.LENGTH_SHORT).show();
                            dismissDialog();
                        }
                    });
                }

            }
        });
    }

    public void startDialog()
    {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Processing");
        dialog.setMessage("Payment In Progress");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dismissDialog()
    {
        dialog.dismiss();
    }
}
