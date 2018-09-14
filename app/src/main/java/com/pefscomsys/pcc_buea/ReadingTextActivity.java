package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadingTextActivity extends AppCompatActivity {
    String reading_text;
    TextView reading_title;
    TextView readingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading_text);

        Intent intent = getIntent();
        reading_text = intent.getStringExtra("READING_TEXT");
        reading_title = findViewById(R.id.reading_text_title);
        reading_title.setText(reading_text);

        readingContent = findViewById(R.id.reading_text_content);

        //now get the scriptureal reading from our scripture class
        ScriptureTextHandler myText = new ScriptureTextHandler(reading_text, getApplicationContext());

        //now set the text content to what is returned from the database
        readingContent.setText(myText.getReading());


    }
}
