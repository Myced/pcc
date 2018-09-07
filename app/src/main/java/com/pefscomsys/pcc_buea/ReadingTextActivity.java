package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadingTextActivity extends AppCompatActivity {
    String reading_text;
    TextView reading_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_text);
        Intent intent = getIntent();
        reading_text = intent.getStringExtra("READING_ONE");
        reading_title = findViewById(R.id.reading_text_title);
        reading_title.setText(reading_text);
    }
}
