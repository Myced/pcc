package com.pefscomsys.pcc_buea;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PresbyterianEcho extends AppCompatActivity {

    TabLayout presEchoTabLayout;
    ViewPager presEchoViewPager;
    TabAdapter presEchoTabAdapter;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presbyterian_echo);
        mAuth = FirebaseAuth.getInstance();
        presEchoTabAdapter = new TabAdapter(getSupportFragmentManager());
        presEchoTabLayout = findViewById(R.id.pressEcho_tab_layout);
        presEchoViewPager = findViewById(R.id.presEchoViewPager);


        if(isNetworkAvailable()){
            if (mAuth.getCurrentUser() == null){
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
            else{
                currentUser = mAuth.getCurrentUser();
                
                try {
                    isStoragePermissionGranted();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Failed to request permission", Toast.LENGTH_SHORT).show();
                }
            }
            presEchoTabAdapter.addFragment(new EchoFragment(), "ECHO'S");
            presEchoTabAdapter.addFragment(new EchoDownloadFragment(), "DOWNLOAD'S");
        }else{

            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            presEchoTabAdapter.addFragment(new EchoDownloadFragment(), "DOWNLOAD'S");
            presEchoTabAdapter.addFragment(new EchoDownloadFragment(), "DOWNLOAD'S");
            //presEchoViewPager.setCurrentItem(1);
        }

        presEchoViewPager.setAdapter(presEchoTabAdapter);
        presEchoTabLayout.setupWithViewPager(presEchoViewPager);
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

        //if permission array is not empty
        if(grantResults.length > 0)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.v("Permission ","Permission: "+permissions[0]+ "was "+grantResults[0]);
                //resume tasks needing this permission
            }
        }
    }
}
