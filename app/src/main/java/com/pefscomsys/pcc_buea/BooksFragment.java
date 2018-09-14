package com.pefscomsys.pcc_buea;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment implements View.OnClickListener {


    public BooksFragment() {
        // Required empty public constructor
    }

    CardView pressEcho, messengerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        //Getting button views by their id's in the layout file
        //cmfButton = (Button) view.findViewById(R.id.cmfButton);
        pressEcho = (CardView) view.findViewById(R.id.presEcho);
        messengerButton = (CardView) view.findViewById(R.id.messengerButton);

        //Setting onClick listeners to button
       // cmfButton.setOnClickListener(this);
        pressEcho.setOnClickListener(this);
        messengerButton.setOnClickListener(this);


       return view;
    }



    //Handles click events for buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            /*case R.id.cmfButton:
                Toast.makeText(getActivity(), R.string.cmf_books, Toast.LENGTH_SHORT).show();
                break;*/
            /*case R.id.cwfButton:
                Toast.makeText(getActivity(), R.string.cwf_books, Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.presEcho:
                startActivity(new Intent(getContext(), PresbyterianEcho.class));
                break;
            case R.id.messengerButton:
                Intent messengerIntent = new Intent(getContext(), TheMessenger.class);
                startActivity(messengerIntent);
                break;

        }
    }
}
