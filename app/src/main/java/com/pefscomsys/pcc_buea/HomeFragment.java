package com.pefscomsys.pcc_buea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button diaryBtn, hymnBtn, magBtn, infoBtn;
    ScripturesFragment scripturesFragment;
    HymesFragment hymesFragment;
    BooksFragment booksFragment;
    InfoFragment infoFragment;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        diaryBtn = (Button) view.findViewById(R.id.diaryBtn);
        hymnBtn = view.findViewById(R.id.hymnBookBtn);
        magBtn = view.findViewById(R.id.studyBookBtn);
        infoBtn = view.findViewById(R.id.infoBtn);


        diaryBtn.setOnClickListener(this);
        hymnBtn.setOnClickListener(this);
        magBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diaryBtn:
                scripturesFragment = new ScripturesFragment();
                setFragment(scripturesFragment);
                break;
            case R.id.hymnBookBtn:
                hymesFragment = new HymesFragment();
                setFragment(hymesFragment);
                break;
            case R.id.studyBookBtn:
                booksFragment = new BooksFragment();
                setFragment(booksFragment);
                break;
            case R.id.infoBtn:
                infoFragment = new InfoFragment();
                setFragment(infoFragment);
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
