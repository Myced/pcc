package com.pefscomsys.pcc_buea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    //create the image view element
    LinearLayout churchInfo;


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

        return view;
    }

}
