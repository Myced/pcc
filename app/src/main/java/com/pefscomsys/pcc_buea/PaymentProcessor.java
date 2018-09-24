package com.pefscomsys.pcc_buea;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

import cz.msebera.android.httpclient.Header;

public class PaymentProcessor
{
    //our mobile money confirguration herer
    public final String momoEmail = "tncedric@yahoo.com";
    public int amount; // the amount to be paid
    public String number; // the number for the person who wants to pay

    //class specific private variables
    private String result;
    public boolean status;
    public String errorMessage;

    public MomoResult paymentResult;


    public  PaymentProcessor(int amount, String number)
    {
        this.amount = amount;
        this.number = number;
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

        request.get(url, params, new JsonHttpResponseHandler(){

            //now override success and failure methods
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                //request made. now check if the payment was successful
                MomoResult momo = new MomoResult(response);

                paymentResult = momo;
                status = momo.success;
                errorMessage = momo.message;

                //log results
                Log.d("PCC", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
            {
                status = false;
                errorMessage = "Could not make Payment. Please check your internet connection";

                //print error message in the log cat
                Log.d("PCC: ", e.toString());
            }
        });
    }


}
