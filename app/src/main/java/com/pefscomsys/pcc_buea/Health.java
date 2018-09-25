package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class Health extends AppCompatActivity {

    //create a global variable for the Health Secretary information
    HealthSecretary mHealthSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mHealthSec = new HealthSecretary(getApplicationContext());

        //set the information

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        //set the title to Healt information
        setTitle(R.string.health);

//        //the set information for the healt secreatary
//        TextView mHealthName = findViewById(R.id.healt_secretary);
//        mHealthName.setText(mHealthSec.getmName());
//
//        TextView mHealthAddress = findViewById(R.id.health_address);
//        mHealthAddress.setText(mHealthSec.getmAddress());
//
//        TextView mCell = findViewById(R.id.health_cell);
//        mCell.setText(mHealthSec.getmCell());
//
//        TextView mTell = findViewById(R.id.health_tel);
//        mTell.setText(mHealthSec.getmTel());
//
//        TextView mHealthEmail = findViewById(R.id.health_email);
//        mHealthEmail.setText(mHealthSec.getmEmail());

        //now get the list of the hospitals
        List<HealthInstitution> hospitals = mHealthSec.getHealthInstitutions();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);

        //prepare the RV adaptier
        RVAdapter institutionsAdapter = new RVAdapter(hospitals);
        rv.setAdapter(institutionsAdapter);

    }
}
