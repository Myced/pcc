package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import static com.pefscomsys.pcc_buea.HymesFragment.HYMN;
import static com.pefscomsys.pcc_buea.HymesFragment.HYMN_NUMBER;

public class HymeView extends AppCompatActivity {
    TextView hymeTitle, hymnText;
    private ScaleGestureDetector mScaleGestureDetector;
    String number;
    CharSequence text;
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyme_view);

        Intent intent = getIntent();
        number = Integer.toString(intent.getIntExtra(HYMN_NUMBER, 0));
        //text = (CharSequence) intent.getStringExtra(HYMN);
        text = intent.getCharSequenceExtra(HYMN);

        hymeTitle = (TextView) findViewById(R.id.hymnViewTitle);
        hymnText = (TextView) findViewById(R.id.hymnText);
        hymeTitle.setText(getString(R.string.chb).toUpperCase()+" " + number);
        hymnText.setText(text);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        hymnText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount() == 1){
                    //stuff for 1 pointer
                }else{ //when 2 pointers are present
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            mScaleGestureDetector.onTouchEvent(event);
                            break;

                        case MotionEvent.ACTION_MOVE:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            mScaleGestureDetector.onTouchEvent(event);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hyme_view_toolbar, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
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

    private void shareIt(CharSequence text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("HYMN " + number);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float size = hymnText.getTextSize();
            float mScaleFactor = detector.getScaleFactor();
            int increase = 0;
            if (mScaleFactor > 0.1f){
                increase = 2;
            }
            else {
                increase = -2;
            }
            size += increase;
            hymnText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
