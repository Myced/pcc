package com.pefscomsys.pcc_buea;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import static com.pefscomsys.pcc_buea.Purchase.TABLE_NAME;

public class PaymentProcessor
{
    //our mobile money confirguration herer
    private final String momoEmail = "pcc.cameroonapp2018@gmail.com";
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

    ScriptureDBHandler db;

    public  PaymentProcessor(int amt, String num, String reson, Context ctx)
    {
        amount = amt;
        number = num;
        context = ctx;
        reason = reson;

        db = new ScriptureDBHandler(ctx);
    }


    public void processPayment()
    {
        String url  = "https://developer.mtn.cm/OnlineMomoWeb/faces/transaction/transactionRequest.xhtml" +
                "?idbouton=2&typebouton=PAIE&_amount=" + this.amount + "&_tel=" + this.number + "&_clP=tncedric" +
                "&_email=" + this.momoEmail + "&submit.x=104&submit.y=70";

        //make a request tot he fake address for testing
//        url = "http://192.168.43.55/momoserver/success.php";

        //perform a get request to this url and get the json result
        Log.d("PCC", url);

        //now we will use the loopj ascyn library

        //create a configuration to ignore (or accept all certificates)
//        AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
//                .setSSLContext(createSslContext())
//                .build();

        //start with the request parameters
        RequestParams params = new RequestParams();


        AsyncHttpClient request = new AsyncHttpClient(true, 80, 443);

        request.setResponseTimeout(130000);

        final IntentFilter intentFilter = new IntentFilter();
        request.get(url, params, new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                super.onStart();
                Log.d("PCC", "Payment started");

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
                Log.d("PCC", "Payment successful");
                super.onSuccess(statusCode,headers, response);
                progressDialog.dismiss();
                //request made. now check if the payment was successful
                MomoResult momo = new MomoResult(response);
                momo.context = context; //set the context before saving
                momo.saveLog();

                Log.d("PCC", response.toString());


                Log.d("PCC ", "STATUSCODE: " + momo.success + " HEADER: "+ Arrays.toString(headers) +" RESPONSE: " + response.toString());
                paymentResult = momo;
                status = momo.success;
                message = momo.message;

                //process the dairy payment here
                if(diary)
                {
                    //save the scripture in the database
                    Log.d("PCC", "updating the diary part");
                    //save the diary to the purchases table too
                    logDiary(diaryYear);
                    momo.updateDiary(diaryYear);
                }

                //log results
                SharedPreferences paymentPref = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = paymentPref.edit();

                if(momo.success){
                    editor.putString(reason, "PAID");

                    //if its the hymn then log hymn else log as book
                    if(reason.equals(context.getString(R.string.HYMN_STATUS)))
                    {
                        logHymns();
                    }
                    else
                    {
                        logBook(reason);
                    }
                }
                else {
                    editor.putString(reason, "NOT_PAID");
                }

                editor.apply();

                if(momo.success)
                {
                    Toast.makeText(context, "PAYMENT SUCCESSFUL", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, "PAYMENT Failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, momo.message, Toast.LENGTH_LONG).show();
                }

                Intent newIntent = new Intent(context, MainActivity.class);
                context.startActivity(newIntent);

                Log.d("PCC", paymentResult.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
            {
                progressDialog.dismiss();
                status = false;
                message = "Could not make Payment. Please check your internet connection. Or There may be a problem with MTN";

                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                Intent newIntent = new Intent(context, MainActivity.class);
                context.startActivity(newIntent);
                //print error message in the log cat
                Log.d("PCCAPP: ", e.toString());
            }
        });

    }

    //loggin the payments to the database;

    private void logHymns()
    {
        //Open the database connection
        this.db.openDataBase();

        if(!this.hymnIsLogged())
        {
            this.insertHymn();
        }

        this.db.close();
    }

    private boolean hymnIsLogged() {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `code` = '" + Purchase.HYMN + "' ";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertHymn()
    {
        //get the date of the purchase
        MyDate date = new MyDate(this.context);

        String currentDate = date.getCurrentDay() + "/" + date.getCurrentMonth() + "/" + date.getCurrentYear();

        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", "Church Hymn Book");
        values.put("code", Purchase.HYMN);
        values.put("year", "0000");
        values.put("date", currentDate);

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

    //logging for books
    private void logBook(String bookName)
    {
        this.db.openDataBase();

        if(!this.bookIsLogged(bookName))
        {
            this.insertBook(bookName);
        }

        this.db.close();
    }

    private boolean bookIsLogged(String bookName) {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `name` = '" + bookName + "' ";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertBook(String bookName)
    {
        //get the date of the purchase
        MyDate date = new MyDate(this.context);

        String currentDate = date.getCurrentDay() + "/" + date.getCurrentMonth() + "/" + date.getCurrentYear();

        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", bookName);
        values.put("code", Purchase.BOOK);
        values.put("year", "0000");
        values.put("date", currentDate);

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

    //logging for the diaries.
    private void logDiary(String year)
    {
        this.db.openDataBase();

        if(!this.diaryIsLogged(year))
        {
            this.insertDiary(year);
        }

        this.db.close();
    }

    private boolean diaryIsLogged(String year) {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `code` = '" + Purchase.DIARY + "' AND `year` = '" + year + "'";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertDiary(String year)
    {
        //get the date of the purchase
        MyDate date = new MyDate(this.context);

        String currentDate = date.getCurrentDay() + "/" + date.getCurrentMonth() + "/" + date.getCurrentYear();

        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", "Diary " + year);
        values.put("code", Purchase.DIARY);
        values.put("year", year);
        values.put("date", currentDate);

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

}
