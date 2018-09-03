package com.pefscomsys.pcc_buea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    //create the image view element
    ScrollView churchInfo;

    private Button historyBtn, congregationBtn, educationBtn, healthBtn, addressBtn, otherBtn;

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
        addressBtn = view.findViewById(R.id.more_address);
        otherBtn = view.findViewById(R.id.more_other);

        historyBtn.setOnClickListener(this);
        congregationBtn.setOnClickListener(this);
        educationBtn.setOnClickListener(this);
        healthBtn.setOnClickListener(this);
        addressBtn.setOnClickListener(this);
        otherBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_history:
                Toast.makeText(getActivity(), R.string.church_history, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.more_cong:
                Toast.makeText(getActivity(), R.string.congregation, Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_education:
                Toast.makeText(getActivity(), R.string.education, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.more_health:
                Toast.makeText(getActivity(), R.string.health, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.more_address:
                Toast.makeText(getActivity(), R.string.church_address, Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_other:
                Toast.makeText(getActivity(), R.string.other_info, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
