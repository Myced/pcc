package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadingTwoActivity extends AppCompatActivity {
    String reading_two;
    TextView reading_title;
    TextView readingContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading_two);

        Intent intent = getIntent();
        reading_two = intent.getStringExtra("READING_TWO");
        reading_title = findViewById(R.id.reading_two_title);
        reading_title.setText(reading_two);

        readingContent = findViewById(R.id.reading_two_content);

        if(reading_two.contains("&"))
        {
            //now get the scriptureal reading from our scripture class
            ScriptureTextHandler myText = new ScriptureTextHandler(reading_two, getApplicationContext());

            //now set the text content to what is returned from the database
            readingContent.setText(myText.getReading());
        }
        else
        {
            CompoundScriptureHandler myText = new CompoundScriptureHandler(reading_two, getApplicationContext());
            readingContent.setText(myText.getFinalResult());
        }


    }
}
