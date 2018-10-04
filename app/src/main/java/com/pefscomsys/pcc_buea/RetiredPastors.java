package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class RetiredPastors extends AppCompatActivity {

    private RecyclerView pastorView, pastorListView;
    private List<RetiredPresbytary> retiredPresbytaries;
    private List<PastorList> pastorLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retired_pastors);
        setTitle(R.string.retired_pastors);

        pastorView = findViewById(R.id.pastor_view);
        pastorListView = findViewById(R.id.pastor_list_view);



    }
}
