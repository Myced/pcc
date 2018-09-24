package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PsalmsReadingActivity extends AppCompatActivity {

    String reading_psalms;
    TextView reading_title;
    TextView readingContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_psalms_reading);

        Intent intent = getIntent();
        reading_psalms = intent.getStringExtra("READING_PSALM");

        reading_title = findViewById(R.id.reading_psalms_title);
        reading_title.setText(reading_psalms);
        readingContent = findViewById(R.id.reading_psalms_content);

        //now get the scriptureal reading from our scripture class
        ScriptureTextHandler myText = new ScriptureTextHandler(reading_psalms, getApplicationContext());

        //now set the text content to what is returned from the database
        readingContent.setText(myText.getReading());


    }
}
