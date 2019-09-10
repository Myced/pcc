package com.pefscomsys.pcc_buea;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;
import static com.pefscomsys.pcc_buea.Prices.HYMN_PRICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button diaryBtn, hymnBtn, magBtn, infoBtn;
    ScripturesFragment scripturesFragment;
    HymesFragment hymesFragment;
    BooksFragment booksFragment;
    InfoFragment infoFragment;
    BottomNavigationView bottomNavigationView;
    FirebaseUser currentUser;

    public ProgressDialog dialog;

    public HomeFragment() {
        // Required empty public constructor

        //if true then initialise the app

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


        //Initialise the application
        AppInitialiser init = new AppInitialiser(getContext());
        boolean firstRun = init.isFirstRun();

        if(firstRun)
        {
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Initialising The Application");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            //then initialise app
            init.initialiseApp();

            dialog.dismiss();
        }

        bottomNavigationView = getActivity().findViewById(R.id.navigation);
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

//                Toast.makeText(this.getContext(), "Changing to diary", Toast.LENGTH_SHORT).show();
                scripturesFragment = new ScripturesFragment();
                bottomNavigationView.setSelectedItemId(R.id.navigation_scriptures);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if(isNetworkAvailable()){
                    if (mAuth.getCurrentUser() == null){
                        Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                        startActivity(loginIntent);
                    }
                    else{
                        currentUser = mAuth.getCurrentUser();
                        setFragment(scripturesFragment);
                    }
                }
                else
                {
                    setFragment(scripturesFragment);
                }

                break;

            case R.id.hymnBookBtn:
                hymesFragment = new HymesFragment();
                bottomNavigationView.setSelectedItemId(R.id.navigation_hymns);
                SharedPreferences mPaymentPref = getActivity().getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                if(!(mPaymentPref.getString(getString(R.string.HYMN_STATUS), "NOT_PAID").equals("NOT_PAID"))){
                    setFragment(hymesFragment);
                }
                else{
                    Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
                    paymentIntent.putExtra("REASON", getString(R.string.HYMN_STATUS));
                    paymentIntent.putExtra("AMOUNT", Prices.HYMN_PRICE);
                    startActivity(paymentIntent);
                }
                break;
            case R.id.studyBookBtn:
                booksFragment = new BooksFragment();
                bottomNavigationView.setSelectedItemId(R.id.navigation_books);

                setFragment(booksFragment);
                break;
            case R.id.infoBtn:
                infoFragment = new InfoFragment();
                bottomNavigationView.setSelectedItemId(R.id.navigation_info);
                setFragment(infoFragment);
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean STATE = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            STATE = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return STATE;
    }
}
