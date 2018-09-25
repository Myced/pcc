package com.pefscomsys.pcc_buea;

import org.json.JSONException;
import org.json.JSONObject;

public class MomoResult
{
    public String receiverNumber;
    public String statusCode;
    public String amount;
    public String resultString;

    private JSONObject response;

    public boolean success;
    public String message; // will contain the message for the result


    public MomoResult(JSONObject object)
    {
        this.response = object;


        try {
            receiverNumber = object.getString("ReceiverNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            statusCode = object.getString("StatusCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            amount = object.getString("Amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        resultString = object.toString();

        //now process the result
        if(statusCode.equals("529"))
        {
            this.success = false;
            this.message = "You don't have enough money. Please recharge";
        }
        else if(statusCode.equals("100"))
        {
            this.success = false;
            this.message = "Transaction Failed";
        }
        else if(statusCode.equals("01"))
        {
            this.success = true;
            this.message = "Payment Successful";
        }
        else
        {
            this.success = false;
            this.message = "Unknown Error";
        }

        this.saveLog();

    }


    public void saveLog()
    {
        //save it to the local database;


        //then fire it up to firebase;

    }

    @Override
    public String toString() {
        return "MomoResult{" +
                "receiverNumber='" + receiverNumber + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", amount='" + amount + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
