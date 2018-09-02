package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScripturesFragment extends Fragment {


    public ScripturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scriptures, container, false);

//        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
//                String date = year + "/" + month + "/"+ dayOfMonth ;
//                Log.d("Calendar", "onSelectedDayChange: yyyy/mm/dd:" + date);
//                Intent intent = new Intent(Intent.ACTION_INSERT);
//                intent.putExtra("date",date);
//                startActivity(intent);
//
//            }
//        });


        return view;

    }

}
