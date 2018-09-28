package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ReadingOneActivity extends AppCompatActivity {
    String reading_one;
    TextView reading_title;
    TextView readingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading_one);

        Intent intent = getIntent();
        reading_one = intent.getStringExtra("READING_ONE");
        reading_title = findViewById(R.id.reading_one_title);
        reading_title.setText(reading_one);

        readingContent = findViewById(R.id.reading_one_content);

        //check if the scripture is a compound scripture
        if(reading_one.contains("&"))
        {
            //now get the scriptureal reading from our scripture class
            ScriptureTextHandler myText = new ScriptureTextHandler(reading_one, getApplicationContext());

            //now set the text content to what is returned from the database
            readingContent.setText(myText.getReading());
        }
        else
        {
            CompoundScriptureHandler myText = new CompoundScriptureHandler(reading_one, getApplicationContext());
            readingContent.setText(myText.getFinalResult());
        }




    }
}
