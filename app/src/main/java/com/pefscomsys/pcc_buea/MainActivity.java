package com.pefscomsys.pcc_buea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private InfoFragment infoFragment;
    private BooksFragment booksFragment;
    private HymesFragment hymesFragment;
    private ScripturesFragment scripturesFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //get the menu item id.
            int id = item.getItemId();

            //switch to each view for a different menu item
            if (id == R.id.navigation_home) {
                //start home fragment
                setFragment(homeFragment);
                return true;
            } else if (id == R.id.navigation_hymns) {
                //start hymn fragment
                setFragment(hymesFragment);
                return true;
            } else if (id == R.id.navigation_books) {
                //start book fragment
                setFragment(booksFragment);
                return true;
            } else if (id == R.id.navigation_scriptures) {
                //start scripture fragment
                setFragment(scripturesFragment);
                return true;
            } else if (id == R.id.navigation_info) {
                //start scripture fragment
                setFragment(infoFragment);
                return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        infoFragment = new InfoFragment();
        booksFragment = new BooksFragment();
        hymesFragment = new HymesFragment();
        scripturesFragment = new ScripturesFragment();

        setFragment(homeFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

//    public static String html2text(String html) {
//        return Jsoup.parse(html).text();
//    }


    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
    }
}
