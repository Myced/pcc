package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class Address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        setTitle(R.string.church_address);

        //prepare the recycler view
        RecyclerView addressView = findViewById(R.id.address_view);

        //prepare a layout manager
        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(getApplicationContext());

        //get the list of church addresses
        SchoolProcessor processor = new SchoolProcessor(getApplicationContext());
        List<ChurchAddress> addresses = processor.getChurchAddresses();

        //create an address view holder
        ChurchAddressAdapter addressAdapter = new ChurchAddressAdapter(addresses);

        //now set up the view
        addressView.setLayoutManager(addressLayoutManager);
        addressView.setAdapter(addressAdapter);
    }
}
