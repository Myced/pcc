package com.pefscomsys.pcc_buea;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    //create the image view element
    ScrollView churchInfo;

    private Button historyBtn, congregationBtn, educationBtn, healthBtn, addressBtn, otherBtn;
    private Button calendarBtn, communicationBtn, pastorBtn;

    public InfoFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //find view by id and animate it
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        churchInfo = view.findViewById(R.id.church_info_layer);


        Animation animSlideUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_up);
        churchInfo.startAnimation(animSlideUp);

        historyBtn = view.findViewById(R.id.more_history);
        congregationBtn = view.findViewById(R.id.more_cong);
        educationBtn = view.findViewById(R.id.more_education);
        healthBtn = view.findViewById(R.id.more_health);
        calendarBtn = view.findViewById(R.id.more_calendar);
        communicationBtn = view.findViewById(R.id.more_communication);
        pastorBtn = view.findViewById(R.id.more_pastors);

        historyBtn.setOnClickListener(this);
        congregationBtn.setOnClickListener(this);
        educationBtn.setOnClickListener(this);
        healthBtn.setOnClickListener(this);
        calendarBtn.setOnClickListener(this);
        communicationBtn.setOnClickListener(this);
        pastorBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_history:
                Intent mHistoryIntent = new Intent(getContext(), History.class);
                startActivity(mHistoryIntent);
                break;
            case  R.id.more_cong:
                Toast.makeText(getActivity(), R.string.congregation, Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_education:
                Intent mEducationIntent = new Intent(getContext(), Education.class);
                startActivity(mEducationIntent);
                break;
            case  R.id.more_health:
                Intent mHealthIntent = new Intent(getContext(), Health.class);
                startActivity(mHealthIntent);
                break;
            case  R.id.more_calendar:
                Intent mCalendarIntent = new Intent(getContext(), ChurchCalendar.class);
                startActivity(mCalendarIntent);
                break;
            case  R.id.more_communication:
                Intent mComIntent = new Intent(getContext(), Communication.class);
                startActivity(mComIntent);
                break;
            case  R.id.more_pastors:
                Intent mPastIntent = new Intent(getContext(), RetiredPastors.class);
                startActivity(mPastIntent);
                break;
//            case  R.id.more_address:
//                Intent mChurchAddressesIntent = new Intent(getContext(), Address.class);
//                startActivity(mChurchAddressesIntent);
//                break;
//            case R.id.more_other:
//                Toast.makeText(getActivity(), R.string.other_info, Toast.LENGTH_SHORT).show();
//                break;
        }
    }
}
