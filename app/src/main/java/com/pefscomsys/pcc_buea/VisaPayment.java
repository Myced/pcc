package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.HashMap;
import java.util.Map;

public class VisaPayment extends AppCompatActivity {

    TextView reasonView, amountView;
    private String Reason;
    private int Amount;
    private String year;
    private String type;
    private Button payButton;

    private final String STRIPE_SUCCESS_TOKEN = "tok_bypassPending";
    private final String SERVER_URL = "http://192.168.43.86:3000/charge";

    private Context context;

    //dialog box for progress bar
    ProgressDialog progressDialog;
    public String progressTitle;

    //my stripe keys
    private final String STRIPE_PUBLIC_KEY = "pk_test_4o4XUuHJFzxi2D5fSbKUIexq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_payment);
        setTitle("Visa Payment");

        context = getApplicationContext();

        reasonView = findViewById(R.id.visa_caption);
        amountView =findViewById(R.id.visa_amount);
        payButton = findViewById(R.id.visa_button);

        //collect data from intent
        Intent paymentIntent = getIntent();
        Reason = paymentIntent.getStringExtra("REASON");
        Amount = paymentIntent.getIntExtra("AMOUNT", 0);

        //extra for diary
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

        //after getting amuonts
        reasonView.setText(Reason.toUpperCase());

        String amountText = "Amount : $" + String.valueOf(Amount);
        amountView.setText(amountText);

        //prepare the progress dialog
        //start a progress dialog
        //start the dialog box
        progressDialog = new ProgressDialog(this);
        progressTitle = "Processing Visa Payment";
        progressDialog.setMessage(this.progressTitle);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        //when the button is clicked. perform payment here
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start the progress
                startProgress();

                //process the card
                //process here
                CardInputWidget mCardInputWidget =  findViewById(R.id.card_input_widget);

                Card card = mCardInputWidget.getCard();


                if (card == null) {
                    Log.d("PCCAPP", "card is null");
                    stopProgress();
                    Toast.makeText(getApplicationContext(), "Please fill out the Card Information", Toast.LENGTH_SHORT).show();
                }
                else if(!card.validateCard())
                {
                    Log.d("PCCAPP", "Card is not valid");
                    stopProgress();
                    Toast.makeText(getApplicationContext(), "Please Fill out the card information correctly", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    //create a token
                    Stripe stripe = new Stripe(context, STRIPE_PUBLIC_KEY);


                    stripe.createToken(card, new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            Log.d("PCCAPP", "cannot get stripe token");
                            stopProgress();
                            Toast.makeText(context, "Error Processing Card. Check internet connection", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(Token token) {
                            //save the token and charge directly
                            Log.d("PCCAPP", "totken gotten" + token.toString());

//                            String tokenID = token.getId();

                            //FOR TESTING, USE DEFAULT TEST TOKE
                            String tokenID = STRIPE_SUCCESS_TOKEN;

                            //MAKE AN AJAX REQUEST TO THE SERVER TO MAKE THE CHAREG
                            //and then parese the response
                            VisaPaymentProcessor processor = new VisaPaymentProcessor(context);


                            int theAmount = Amount * 100; //convert to cents. to append two zeros at the end

                            processor.setAmount(theAmount);
                            processor.setToken(tokenID);

                            //now check if the payment is for diary
                            if(type != null )
                            {
                                if(type.equals("DIARY"))
                                {
                                    //then get the year
                                    String diaryYear = year;
                                    String description = Reason;
                                    processor.isDiary = true;
                                    processor.diaryYear = diaryYear;
                                }

                            }

                            processor.setDescription(Reason);

                            //process now
                            processor.processPayment();

                            //end progress
                            stopProgress();

                            //make toast messages to indicates successful or failed transaction.
                            //if successful then redirect to home page
                            if(processor.success == true)
                            {
                                Intent newIntent = new Intent(context, MainActivity.class);
                                context.startActivity(newIntent);
                            }

                        }
                    });

                }
            }
        });

    }

    public void startProgress ()
    {
        progressDialog.show();
    }

    public void stopProgress()
    {
        progressDialog.dismiss();
    }

    public void changeProgressTitle(String title)
    {
        progressDialog.dismiss();
        progressTitle = title;
        progressDialog.show();
    }
}
