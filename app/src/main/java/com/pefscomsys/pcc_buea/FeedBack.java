package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        setTitle(R.string.feedback);
    }
}
