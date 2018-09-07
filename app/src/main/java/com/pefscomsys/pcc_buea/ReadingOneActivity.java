package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ReadingOneActivity extends AppCompatActivity {
    String reading_one;
    TextView reading_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_one);
        Intent intent = getIntent();
        reading_one = intent.getStringExtra("READING_ONE");
        reading_title = findViewById(R.id.reading_one_title);
        reading_title.setText(reading_one);

    }
}
