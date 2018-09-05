package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import static com.pefscomsys.pcc_buea.HymesFragment.HYMN;
import static com.pefscomsys.pcc_buea.HymesFragment.HYMN_NUMBER;

public class HymeView extends AppCompatActivity {
    TextView hymeTitle, hymnText;
    String number, text;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyme_view);

        Intent intent = getIntent();
        number = Integer.toString(intent.getIntExtra(HYMN_NUMBER, 0));
        text = intent.getStringExtra(HYMN);

        hymeTitle = (TextView) findViewById(R.id.hymnViewTitle);
        hymnText = (TextView) findViewById(R.id.hymnText);
        hymeTitle.setText("HYME " + number);
        hymnText.setText(text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hyme_view_toolbar, menu);
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
            case R.id.favorite:
                Toast.makeText(getApplicationContext(), "Favorite Added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sound:
                Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                shareIt(text);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void shareIt(String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HYMN " + number);
    }
}
