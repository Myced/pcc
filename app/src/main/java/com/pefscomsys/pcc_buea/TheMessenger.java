package com.pefscomsys.pcc_buea;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TheMessenger extends AppCompatActivity {

    TabLayout messengerTabLayout;
    ViewPager viewPager;
    TabAdapter messengerTabAdapter;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_messenger_activity);
        mAuth = FirebaseAuth.getInstance();
        messengerTabAdapter = new TabAdapter(getSupportFragmentManager());
        messengerTabLayout = findViewById(R.id.messenger_tab_layout);
        viewPager = findViewById(R.id.viewPager);


        if(isNetworkAvailable()){
            if (mAuth.getCurrentUser() == null){
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
            else{
                currentUser = mAuth.getCurrentUser();
                isStoragePermissionGranted();
            }
            messengerTabAdapter.addFragment(new MessengersFragment(), "MESENGER'S");
            messengerTabAdapter.addFragment(new MyMessengerFragment(), "DOWNLOAD'S");


        }else {

            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            messengerTabAdapter.addFragment(new MyMessengerFragment(), "DOWNLOAD'S");
            messengerTabAdapter.addFragment(new MyMessengerFragment(), "DOWNLOAD'S");
            //viewPager.setCurrentItem(1);
        }

        viewPager.setAdapter(messengerTabAdapter);
        messengerTabLayout.setupWithViewPager(viewPager);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean STATE = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            STATE = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return STATE;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission: ","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Permission ","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
