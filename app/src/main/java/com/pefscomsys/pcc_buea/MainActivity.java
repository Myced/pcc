package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.pefscomsys.pcc_buea.Prices.HYMN_PRICE;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private InfoFragment infoFragment;
    private BooksFragment booksFragment;
    private HymesFragment hymesFragment;
    private ScripturesFragment scripturesFragment;
  
    public static final String PAYMENT_PREFS = "PaymentPref";
    SharedPreferences mPaymentPref;
    boolean doubleBackToExitPressedOnce = false;

    FirebaseUser currentUser;

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

                mPaymentPref = getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                if(!(mPaymentPref.getString(getString(R.string.HYMN_STATUS), "NOT_PAID").equals("NOT_PAID"))){
                    setFragment(hymesFragment);
                }
                else{
                    Intent paymentIntent = new Intent(getApplicationContext(), PaymentActivity.class);
                    paymentIntent.putExtra("REASON", getString(R.string.HYMN_STATUS));
                    paymentIntent.putExtra("AMOUNT", HYMN_PRICE);
                    startActivity(paymentIntent);
                }
                return true;
            } else if (id == R.id.navigation_books) {
                //start book fragment
                setFragment(booksFragment);
                return true;
            } else if (id == R.id.navigation_scriptures) {
                //start scripture fragment

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if(isNetworkAvailable()){
                    if (mAuth.getCurrentUser() == null){
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }
                    else{
                        currentUser = mAuth.getCurrentUser();
                        setFragment(scripturesFragment);
                    }
                }
                else{
                    setFragment(scripturesFragment);
                }
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

        getOverflowMenu();

        //Initialise the application
        AppInitialiser init = new AppInitialiser(this);
        init.initialiseApp();



        //pull scripture updates

        //this is not the best idea
        /// but we don't have time


        MyDate today = new MyDate(getApplicationContext());

        String currentYear = today.currentYear;
        int currentYearInt = Integer.parseInt(currentYear);
        int nextYear = currentYearInt+1;

        ScriptureRetrieval updatesThisYear = new ScriptureRetrieval(currentYearInt);
        updatesThisYear.context = getApplicationContext();
        Object changes = updatesThisYear.pullScriptures(); //check for upates




        ScriptureRetrieval updatesNextYear = new ScriptureRetrieval(nextYear);
        changes  = updatesNextYear.pullScriptures();



        mPaymentPref = getSharedPreferences(PAYMENT_PREFS, MODE_PRIVATE);
        Log.d("Preference", String.valueOf(mPaymentPref.getAll().values()));

        if(mPaymentPref.getAll().size() == 0){
            SharedPreferences.Editor editor = mPaymentPref.edit();
            editor.putString(getString(R.string.HYMN_STATUS), "NOT_PAID");
            editor.apply();
        }

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

            case  R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
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

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean STATE = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            STATE = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return STATE;
    }
}
