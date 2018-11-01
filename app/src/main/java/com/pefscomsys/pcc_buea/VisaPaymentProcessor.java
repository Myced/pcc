package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;

public class VisaPaymentProcessor
{
    private Context context;

    private int amount;
    private String description;
    private String token;
    private String email  = "pcc.cameroon2018@gmail.com";

    public boolean success = false;
    public String reason;

    public boolean isDiary = false;
    public String diaryYear;

    //dialog box for progress bar
    ProgressDialog progressDialog;

    private final String SERVER_URL = "http://192.168.43.86:3000/charge";

    public VisaPaymentProcessor(Context context)
    {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public void processPayment()
    {
        //start with the request parameters
        RequestParams params = new RequestParams();
        params.put("amount", this.amount);
        params.put("token_from_stripe", this.token);
        params.put("specialNote", this.description);
        params.put("email", this.email);


        AsyncHttpClient request = new AsyncHttpClient();

        request.setResponseTimeout(45000);

        //now perform the post request
        request.post(this.SERVER_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart()
            {
                //start the dialog box
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Processing your Payment");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
            {
                //get the parameters from the result
                success = false;
                reason = "Could not make Payment. Check internet Connection";
                Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                Log.d("PCCAPP", reason);
//                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
//                progressDialog.dismiss();

                //check the json response
                try {
                    Object charge = response.get("charge");

                    if(charge instanceof JSONObject)
                    {
                        success = true;
                        reason = "Payment Successful";

                        //go ahead and set shared preferences
                        VisaResult  visaResult = new VisaResult(response, context);

                        //if this is dairy, get yeaqr;
                        if(isDiary == true)
                        {
                            visaResult.saveDiary(diaryYear);
                        }

                        //save it share preferences
                        //log results
                        SharedPreferences paymentPref = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = paymentPref.edit();

                        editor.putString(description, "PAID");


                        editor.apply();

                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                        Log.d("PCCAPP", reason);
                    }
                    else
                    {
                        //the transaction failed
                        success = false;

                        JSONObject errorObject = (JSONObject) response.get("error");
                        reason = errorObject.getString("message");

                        Toast.makeText(context, "Processing Failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                        Log.d("PCCAPP", reason);
                    }

                }
                catch (JSONException e )
                {
                    e.printStackTrace();
                }
            }
        });


    }

    private void processResult()
    {

    }

}
