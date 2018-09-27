package com.pefscomsys.pcc_buea;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;

public class PaymentProcessor
{
    //our mobile money confirguration herer
    private final String momoEmail = "tncedric@yahoo.com";
    private int amount; // the amount to be paid
    public String number; // the number for the person who wants to pay

    //class specific private variables
    private String result;
    public boolean status;
    public String message;
    private Context context;
    private String reason;

    public static boolean diary;
    public static String diaryYear;

    public MomoResult paymentResult;
    private ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver;

    public  PaymentProcessor(int amt, String num, String reson, Context ctx)
    {
        amount = amt;
        number = num;
        context = ctx;
        reason = reson;
    }

    public void processPayment()
    {
        String url  = "https://developer.mtn.cm/OnlineMomoWeb/faces/transaction/transactionRequest.xhtml" +
                "?idbouton=2&typebouton=PAIE&_amount=" + this.amount + "&_tel=" + this.number + "&_clP=Cpadmin@bhco123" +
                "&_email=" + this.momoEmail + "&submit.x=104&submit.y=70";

        //perform a get request to this url and get the json result
        Log.d("PCC", url);

        //now we will use the loopj ascyn library

        //start with the request parameters
        RequestParams params = new RequestParams();


        AsyncHttpClient request = new AsyncHttpClient();

        request.setResponseTimeout(120000);


        broadcastReceiver = new SmsReceiver();
        final IntentFilter intentFilter = new IntentFilter();
        context.registerReceiver(broadcastReceiver, intentFilter, Manifest.permission.RECEIVE_SMS,null);

        request.get(url, params, new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                super.onStart();
                Log.d("Payment", "Payment started");
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Making payment\n Dial *126# and confirm");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            //now override success and failure methods
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                Log.d("Payment", "Payment successful");
                super.onSuccess(statusCode,headers, response);
                progressDialog.dismiss();
                //request made. now check if the payment was successful
                MomoResult momo = new MomoResult(response);
                momo.context = context; //set the context before saving
                momo.saveLog();

                Log.d("PCCAPP", response.toString());


                Log.d("PAYMENT PROCESSOR ", "STATUSCODE: " + momo.success + " HEADER: "+ Arrays.toString(headers) +" RESPONSE: " + response.toString());
                paymentResult = momo;
                status = momo.success;
                message = momo.message;

                //process the dairy payment here
                if(diary == true)
                {
                    //save the scripture in the database
                    Log.d("PCCAPP", "updating the diary part");
                    momo.updateDiary(diaryYear);
                }

                //log results
                SharedPreferences paymentPref = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = paymentPref.edit();

                if(momo.success){
                    editor.putString(reason, "PAID");
                }
                else {
                    editor.putString(reason, "NOT_PAID");
                }

                editor.apply();

                if(momo.success == true)
                {
                    Toast.makeText(context, "PAYMENT SUCCESSFUL", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, "PAYMENT Failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, momo.message, Toast.LENGTH_LONG).show();
                }
                context.unregisterReceiver(broadcastReceiver);
                Intent newIntent = new Intent(context, MainActivity.class);
                context.startActivity(newIntent);

                Log.d("PCCAPP", paymentResult.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
            {
                progressDialog.dismiss();
                Log.d("Payment", "Payment failure");
                status = false;
                message = "Could not make Payment. Please check your internet connection";

                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                context.unregisterReceiver(broadcastReceiver);
                Intent newIntent = new Intent(context, MainActivity.class);
                context.startActivity(newIntent);
                //print error message in the log cat
                Log.d("PCCAPP: ", e.toString());
            }
        });

    }



}
