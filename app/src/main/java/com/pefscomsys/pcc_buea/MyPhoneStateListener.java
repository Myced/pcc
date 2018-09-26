package com.pefscomsys.pcc_buea;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener {
    public static boolean phoneRinging = false;
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("PHONE STATE", "IDLE");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("PHONE STATE", "RINGING");
                phoneRinging = true;
                break;
            case  TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("PHONE STATE", "OUTGOING CALL " + incomingNumber);
                phoneRinging = false;
                break;
        }
    }
}
