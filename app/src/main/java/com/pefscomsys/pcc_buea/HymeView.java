package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static com.pefscomsys.pcc_buea.HymesFragment.HYMN_NUMBER;

public class HymeView extends AppCompatActivity {
    TextView hymeTitle;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyme_view);

        Intent intent = getIntent();
        String number = Integer.toString(intent.getIntExtra(HYMN_NUMBER, 0));
        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();

        hymeTitle = (TextView) findViewById(R.id.hymnViewTitle);
        hymeTitle.setText("HYME " + number);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.settings:
                Toast.makeText(getApplicationContext(), R.string.setting, Toast.LENGTH_SHORT).show();
                return true;
            case  R.id.about:
                Toast.makeText(getApplicationContext(), R.string.about, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
