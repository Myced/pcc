package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadingTwoActivity extends AppCompatActivity {
    String reading_two;
    TextView reading_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_two);
        Intent intent = getIntent();
        reading_two = intent.getStringExtra("READING_TWO");
        reading_title = findViewById(R.id.reading_two_title);
        reading_title.setText(reading_two);
    }
}
