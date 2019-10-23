package com.pefscomsys.pcc_buea;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScripturesFragment extends Fragment {

    //declare private global variables
    Spinner day;
    Spinner month;
    Spinner year;
    Button findButton, buyScripture, getDiaryButton;

    String selectedMonth;
    String selectedDay;
    String selectedYear;

    String activationYear;

    String dayOfWeek;

    String currentYear;
    String currentMonth;
    String currentDay;
    String currentMonthName;

    TextView readingPsalms1, readingOne1, readingTwo1, readingText1;
    LinearLayout withPsalms, withoutPsalms, getDiaryLayout;

    RelativeLayout psalmsLayer;

    TextView dayDate, monthYear, getDiaryText;
    String dayDateString, monthYearString;

    TextView readingPsalms, readingOne, readingTwo, readingText, errorMessage;
    LinearLayout errorLayout, scriptureLayer;

    //daily labels like Palm Sunday
    RelativeLayout labelLayout, labelLayout2;
    TextView label, label2;

    //scripture variable

    Scripture reading;

    //GregorianCalendar calender;
    ScriptureDBHandler scriptureDb;

    public ScripturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scriptures, container, false);

        //we have to set the selected dates to represent the date of today
        //get the view components to update them as required
        readingPsalms = view.findViewById(R.id.psalms_content);
        readingPsalms1 = view.findViewById(R.id.psalms_content1);

        readingOne = view.findViewById(R.id.reading_one);
        readingOne1 = view.findViewById(R.id.reading_one1);

        readingTwo = view.findViewById(R.id.reading_two);
        readingTwo1 = view.findViewById(R.id.reading_two1);

        readingText = view.findViewById(R.id.reading_text);
        readingText1 = view.findViewById(R.id.reading_text1);

        scriptureLayer = view.findViewById(R.id.scripture_layer);
        errorLayout = view.findViewById(R.id.error_layer);
        errorMessage = view.findViewById(R.id.error_message);

        withPsalms = view.findViewById(R.id.with_spalms);
        withoutPsalms = view.findViewById(R.id.without_spalms);

        psalmsLayer = view.findViewById(R.id.psalms_layout);

        buyScripture = view.findViewById(R.id.buy_scripture);

        getDiaryButton = view.findViewById(R.id.get_diary);
        getDiaryLayout = view.findViewById(R.id.get_diary_layer);
        getDiaryText = view.findViewById(R.id.get_diary_text);

        dayDate = view.findViewById(R.id.day_date);
        monthYear = view.findViewById(R.id.month_year);

        //work with the labels
        labelLayout = (RelativeLayout) view.findViewById(R.id.labelLayout);
        labelLayout2 = view.findViewById(R.id.labelLayout2);

        label = (TextView) view.findViewById(R.id.label);
        label2 = (TextView) view.findViewById(R.id.label2);

        //get the current date
        //create an instance of MyDate
        MyDate date = new MyDate(getContext());

        //now check avtivation for this date
        final Activation activation = new Activation(getContext());

        //check the activation this year
        activationYear = date.getCurrentYear();
        boolean activationResult = activation.checkDiary(this.activationYear);

        //check the result and see if the product has been activated or not
        if(activationResult == true)
        {
            //hide the error layout
            //now lets hide the error layer
            this.errorLayout.setVisibility(View.GONE);
            this.scriptureLayer.setVisibility(View.VISIBLE);

        }
        else
        {
            String error = "You have not bought the " + activationYear + " Diary \n " +
                    "Please click on the button below to buy it. \n\n" +
                    "Price: " + Prices.SCRIPTURE + "XAF";

            errorMessage.setText(error);

            scriptureLayer.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }

        //now get the current day, month and year
        currentDay = date.getCurrentDay();
        currentMonth = date.getCurrentMonth();
        currentYear = date.getCurrentYear();
        currentMonthName = date.getCurrentMonthName();
        dayOfWeek = date.getDayOfTheWeek();

        //get the dyaname and monty year
        dayDateString = date.getCurrentDayAndName();
        monthYearString = date.getCurrentMonthYear();

        //set the results on the text view.
        dayDate.setText(dayDateString);
        monthYear.setText(monthYearString);

        reading = new Scripture("", "", "", "", "");

        //prepare our date and get readings for this date
        String dbDate = currentDay + '/' + currentMonth + '/' + currentYear;

        //now with the date already prepared.
        int theCurrentYearInt = Integer.parseInt(currentYear);
        final int theFollowingYear = theCurrentYearInt + 1;

        //checking if the diary for the following year is available.

        //set the view to invisible
        getDiaryLayout.setVisibility(View.GONE);

        checkAvailable(new SimpleFirebaseCallBack<Boolean>() {
            @Override
            public void isAvailable(Boolean status) {
                Log.d("STATS", status.toString());
                Log.d("PCCAPP", status.toString());
                if (status) {
                    // true was returned
                    //Perform the action you want to perform if its available inside year
                    //then check if its available
                    boolean activationRes = activation.checkDiary(Integer.toString(theFollowingYear));

                    if(activationRes)
                    {
                        Log.d("PCCAPP", "activated and is available");
                        getDiaryLayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        Log.d("PCCAPP", "NOT activated and is available");
                        String yYear = Integer.toString(theFollowingYear) + " Now Available. \n " +
                                "Click on the button below to get it";
                        getDiaryText.setText(yYear);

                        getDiaryLayout.setVisibility(View.VISIBLE);
                    }

                } else {
                    // false was returned
                    //if its not available perform it inside here

                    Log.d("PCCAPP", "Diary is not available");
                    getDiaryLayout.setVisibility(View.GONE);
                }

            }
        }, Integer.toString(theFollowingYear));

        //if this is true. then check if the year has been activated

        //now get it from the db with the Scripture DB Helper
        scriptureDb = new ScriptureDBHandler(getContext());


        scriptureDb.openDataBase();


        //now get the reading array list for that day
        List<Scripture> ourScripture = scriptureDb.getReadings(dbDate);


        for(int i = 0; i < ourScripture.size(); i++)
        {
            Scripture currentReading = ourScripture.get(i);

            //now set the reading to those from currentReading
            reading.setPsalms(currentReading.getPsalms());
            reading.setReadingText(currentReading.getReadingText());
            reading.setReadingTwo(currentReading.getReadingTwo());
            reading.setReadingOne(currentReading.getReadingOne());
            reading.setLabel(currentReading.getLabel());
        }

        //check if the label should be blank or not
        if(reading.getLabel() != null)
        {
            //check that it is not empty
            if(reading.getLabel().equals(""))
            {
                //then make it invisible
                labelLayout.setVisibility(View.GONE);
                labelLayout2.setVisibility(View.GONE);
            }
            else
            {
                //set the label for that day
                label.setText(reading.getLabel().replaceAll("\\\\n", System.getProperty("line.separator")));
                labelLayout.setVisibility(View.VISIBLE);

                label2.setText(reading.getLabel().replaceAll("\\\\n", System.getProperty("line.separator")));
                labelLayout2.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            labelLayout.setVisibility(View.GONE);
            labelLayout2.setVisibility(View.GONE);
        }


        //before setting the reading. make them clickable

        if(reading.getReadingOne() != null)
        {
            final String oneString = reading.getReadingOne();
            SpannableString oneSpan = new SpannableString(oneString);
            ClickableSpan clickOne = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent readingOneIntent = new Intent(getContext(), ReadingOneActivity.class);
                    readingOneIntent.putExtra("READING_ONE", oneString);

                    Log.d("MYPCC", oneString);

                    //start the activity only if the reading is not empty
                    if(!oneString.equals("") && ! oneString.equals(" ") && ! oneString.equals(null))
                    {
                        Log.d("MYPCC", oneString);
                        startActivity(readingOneIntent);
                    }
                    else
                    {
                        Log.d("MYPCC", oneString);
                    }

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            };
            oneSpan.setSpan(clickOne, 0, oneString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //now set the text for the reading
            readingOne.setText(oneSpan);
            readingOne.setMovementMethod(LinkMovementMethod.getInstance());

            readingOne1.setText(oneSpan);
            readingOne1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            readingOne.setText("");
            readingOne1.setText("");
        }


        //do the same for reading two
        if(reading.getReadingTwo() != null)
        {
            final String twoString = reading.getReadingTwo();
            SpannableString twoSpan = new SpannableString(twoString);
            ClickableSpan clickTwo = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    Intent readingTwoIntent = new Intent(getContext(), ReadingTwoActivity.class);
                    readingTwoIntent.putExtra("READING_TWO", twoString);
                    if( ! twoString.equals(""))
                        startActivity(readingTwoIntent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            };
            twoSpan.setSpan(clickTwo, 0, twoString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            readingTwo.setText(twoSpan);
            readingTwo.setMovementMethod(LinkMovementMethod.getInstance());

            readingTwo1.setText(twoSpan);
            readingTwo1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            readingTwo.setText("");
            readingTwo1.setText("");
        }

        //reading text
        if(reading.getReadingText() != null)
        {
            final String textString = reading.getReadingText();
            SpannableString textSpan = new SpannableString(textString);
            ClickableSpan clickText = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent readingTextIntent = new Intent(getContext(), ReadingTextActivity.class);
                    readingTextIntent.putExtra("READING_TEXT", textString);
                    startActivity(readingTextIntent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            };
            textSpan.setSpan(clickText, 0, textString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            readingText.setText(textSpan);
            readingText.setMovementMethod(LinkMovementMethod.getInstance());

            readingText1.setText(textSpan);
            readingText1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            readingText.setText("");
            readingText.setText("");
        }


        //psalms introit
        if(reading.getPsalms() != null)
        {
            final String psalmsString = reading.getPsalms();
            SpannableString psalmsSpan = new SpannableString(psalmsString);
            ClickableSpan psalmsText = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent readingPsalmIntent = new Intent(getContext(), PsalmsReadingActivity.class);
                    readingPsalmIntent.putExtra("READING_PSALM", psalmsString);
                    startActivity(readingPsalmIntent);

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            };
            psalmsSpan.setSpan(psalmsText, 0, psalmsString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            readingPsalms.setText(psalmsSpan);
            readingPsalms.setMovementMethod(LinkMovementMethod.getInstance());

            readingPsalms1.setText(psalmsSpan);
            readingPsalms1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            readingPsalms.setText("");
            readingPsalms1.setText("");
        }


        //now if the reading psalms is empty
        //then don't show it and also don't show the reading lables
        if(reading.getPsalms() == null)
        {
            //set the reading with psalms invicible
            withPsalms.setVisibility(View.GONE);
            withoutPsalms.setVisibility(View.VISIBLE);
        }
        else
        {
            if(reading.getPsalms().equals(""))
            {
                //set the reading with psalms invicible
                withPsalms.setVisibility(View.GONE);
                withoutPsalms.setVisibility(View.VISIBLE);
            }
            else if(reading.getPsalms().equals(" "))
            {
                //set the reading with psalms invicible
                withPsalms.setVisibility(View.GONE);
                withoutPsalms.setVisibility(View.VISIBLE);
            }
            else
            {
                //set the reading with psalms invicible
                withPsalms.setVisibility(View.VISIBLE);
                withoutPsalms.setVisibility(View.GONE);
            }
        }


        //get the current year
        int startYear = 2018;
        int theCurrentYear = Integer.valueOf(this.currentYear);

        //now lets prepare an arraylist of years
        List<Integer> years = new ArrayList<>();

        for(int i = startYear; i <= theCurrentYear+1; i++)
        {
            years.add(i);
        }


        //initialise the spinners
        day = view.findViewById(R.id.day_spinner);
        month = view.findViewById(R.id.month_spinner);
        year = view.findViewById(R.id.year_spinner);
        findButton = view.findViewById(R.id.find_btn);

        //initialising the days adapter
        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.days, R.layout.date_spinner_row);

        day.setAdapter(daysAdapter);

        //prepare the months adapter too.
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.months, R.layout.date_spinner_row);

        month.setAdapter(monthsAdapter);

        //year resources id

//        ArrayAdapter<CharSequence> yearsAdapterss = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
//                R.array.years, R.layout.date_spinner_row);
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext(), R.layout.date_spinner_row, years);

        year.setAdapter(yearsAdapter);

        //set the default selected items
        day.setSelection(daysAdapter.getPosition(currentDay));
        month.setSelection(monthsAdapter.getPosition(currentMonthName));
        year.setSelection(yearsAdapter.getPosition(Integer.valueOf(currentYear)));

        //set and onclick listener to the button
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now get the dates selected
                selectedMonth = (String) month.getSelectedItem();
                String selectedMonthId = getMonthId(selectedMonth);
                selectedDay = String.valueOf(day.getSelectedItem());
                selectedYear = String.valueOf(year.getSelectedItem());

                //prepare a date instance
                MyDate date = new MyDate(getContext(), selectedDay, selectedMonthId, selectedYear);

                //get the dyaname and monty year
                dayDateString = date.getDayAndName();
                monthYearString = date.getMonthYear();

                //set the results on the text view.
                dayDate.setText(dayDateString);
                monthYear.setText(monthYearString);

                //set the activation year
                activationYear = selectedYear;

                //check activation for this year
                //now check avtivation for this date
                Activation activation = new Activation(getContext());

                //check the activation this year
                boolean activationResult2 = activation.checkDiary(activationYear);

                //check the result and see if the product has been activated or not
                if(activationResult2)
                {
                    //hide the error layout
                    //now lets hide the error layer
                    errorLayout.setVisibility(View.GONE);
                    scriptureLayer.setVisibility(View.VISIBLE);
                }
                else
                {
                    String error = "You have not bought the " + activationYear + " Diary \n " +
                            "Please click on the button below to buy it. \n\n" +
                            "Price: " + Prices.SCRIPTURE + "XAF";

                    errorMessage.setText(error);

                    scriptureLayer.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);

                }

                String prepareDate = selectedDay + "/" + selectedMonthId + "/" + selectedYear;

                Scripture reading = new Scripture("", "", "", "", "");

                //now get it from the db with the Scripture DB Helper
                scriptureDb = new ScriptureDBHandler(getContext());

                try {
                    scriptureDb.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                scriptureDb.openDataBase();

                //now get the reading array list for that day
                List<Scripture> ourScripture = scriptureDb.getReadings(prepareDate);

                for(int i = 0; i < ourScripture.size(); i++)
                {
                    Scripture currentReading = ourScripture.get(i);

                    //now set the reading to those from currentReading
                    reading.setPsalms(currentReading.getPsalms());
                    reading.setReadingText(currentReading.getReadingText());
                    reading.setReadingTwo(currentReading.getReadingTwo());
                    reading.setReadingOne(currentReading.getReadingOne());
                    reading.setLabel(currentReading.getLabel());
                }

                //diable the layut view if necessary for label
                //check if the label should be blank or not

                if(reading.getLabel() != null)
                {
                    //check that it is not empty
                    if(reading.getLabel().equals(""))
                    {
                        //then make it invisible
                        labelLayout.setVisibility(View.GONE);
                        labelLayout2.setVisibility(View.GONE);
                    }
                    else
                    {
                        //set the label for that day
                        label.setText(reading.getLabel().replaceAll("\\\\n", System.getProperty("line.separator")));
                        labelLayout.setVisibility(View.VISIBLE);

                        label2.setText(reading.getLabel().replaceAll("\\\\n", System.getProperty("line.separator")));
                        labelLayout2.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    labelLayout.setVisibility(View.GONE);
                    labelLayout2.setVisibility(View.GONE);
                }

//                now set the text for the reading
                //psalms introit
                if(!(reading.getPsalms() == null))
                {
                    final String psalmsStringFiltered = reading.getPsalms();
                    SpannableString psalmsSpan = new SpannableString(psalmsStringFiltered);
                    ClickableSpan psalmsText = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Intent readingPsalmIntent = new Intent(getContext(), PsalmsReadingActivity.class);
                            readingPsalmIntent.putExtra("READING_PSALM", psalmsStringFiltered);
                            startActivity(readingPsalmIntent);

                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.BLUE);
                        }
                    };
                    psalmsSpan.setSpan(psalmsText, 0, psalmsStringFiltered.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    readingPsalms.setText(psalmsSpan);
                    readingPsalms.setMovementMethod(LinkMovementMethod.getInstance());

                    readingPsalms1.setText(psalmsSpan);
                    readingPsalms1.setMovementMethod(LinkMovementMethod.getInstance());
                }
                else
                {
                    readingPsalms.setText("");
                    readingPsalms1.setText("");
                }



                //make reading one clickable.
                if(!(reading.getReadingOne() == null))
                {
                    final String oneStringFiltered = reading.getReadingOne();

                    SpannableString oneSpan = new SpannableString(oneStringFiltered);
                    ClickableSpan clickOne = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Log.d("MYPCC", oneStringFiltered);
                            Intent readingOneIntent = new Intent(getContext(), ReadingOneActivity.class);
                            readingOneIntent.putExtra("READING_ONE", oneStringFiltered);

                            if( ! oneStringFiltered.equals("") && ! oneStringFiltered.equals(" ") && ! oneStringFiltered.equals(null))
                            {
                                startActivity(readingOneIntent);
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.BLUE);
                        }
                    };
                    oneSpan.setSpan(clickOne, 0, oneStringFiltered.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //now set the text for the reading
                    readingOne.setText(oneSpan);
                    readingOne.setMovementMethod(LinkMovementMethod.getInstance());

                    readingOne1.setText(oneSpan);
                    readingOne1.setMovementMethod(LinkMovementMethod.getInstance());
                }
                else
                {
                    readingOne.setText("");
                    readingOne1.setText("");
                }


                if(reading.getReadingTwo() != null)
                {
                    //do the same for reading two
                    final String twoString = reading.getReadingTwo();
                    final SpannableString twoSpan = new SpannableString(twoString);
                    ClickableSpan clickTwo = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {

                            Intent readingTwoIntent = new Intent(getContext(), ReadingTwoActivity.class);
                            readingTwoIntent.putExtra("READING_TWO", twoString);

                            if( ! twoString.equals("") && ! twoString.equals(" ") && ! twoString.equals(null))
                            {
                                startActivity(readingTwoIntent);
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.BLUE);
                        }
                    };
                    twoSpan.setSpan(clickTwo, 0, twoString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    readingTwo.setText(twoSpan);
                    readingTwo.setMovementMethod(LinkMovementMethod.getInstance());

                    readingTwo1.setText(twoSpan);
                    readingTwo1.setMovementMethod(LinkMovementMethod.getInstance());
                }
                else
                {
                    readingTwo.setText("");
                    readingTwo1.setText("");
                }


                //reading text
                if(reading.getReadingText() != null)
                {
                    final String textString = reading.getReadingText();

                    SpannableString textSpan = new SpannableString(textString);
                    ClickableSpan clickText = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Intent readingTextIntent = new Intent(getContext(), ReadingTextActivity.class);
                            readingTextIntent.putExtra("READING_TEXT", textString);
                            if(!textString.equals("") && ! textString.equals(" ") && ! textString.equals(null))
                            {
                                startActivity(readingTextIntent);
                            }

                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.BLUE);
                        }
                    };
                    textSpan.setSpan(clickText, 0, textString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    readingText.setText(textSpan);
                    readingText.setMovementMethod(LinkMovementMethod.getInstance());

                    readingText1.setText(textSpan);
                    readingText1.setMovementMethod(LinkMovementMethod.getInstance());
                }
                else
                {

                    readingText.setText("");
                    readingText1.setText("");
                }


                //now if the reading psalms is empty
                //then don't show it and also don't show the reading lables
                if(reading.getPsalms() == null)
                {
                    //set the reading with psalms invicible
                    withPsalms.setVisibility(View.GONE);
                    withoutPsalms.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(reading.getPsalms().equals(""))
                    {
                        //set the reading with psalms invicible
                        withPsalms.setVisibility(View.GONE);
                        withoutPsalms.setVisibility(View.VISIBLE);
                    }
                    else if(reading.getPsalms().equals(" "))
                    {
                        //set the reading with psalms invicible
                        withPsalms.setVisibility(View.GONE);
                        withoutPsalms.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        //set the reading with psalms invicible
                        withPsalms.setVisibility(View.VISIBLE);
                        withoutPsalms.setVisibility(View.GONE);
                    }
                }

                //get the month code
                String monthID = getMonthId(selectedMonth);

                //now get the scriptures for this particular day

            }
        });

        //set the onclick listener for the button to buy the diary
        buyScripture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //prepare the payment for the diary
                int diaryPrice = Prices.SCRIPTURE;
                String diaryYear = activationYear;

                String paymentString = "Diary " + diaryYear;

                //put the data in an intent
//                Intent paymentIntent = new Intent(getContext(), MomoPaymentActivity.class);

                Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);

                paymentIntent.putExtra("REASON", paymentString);
                paymentIntent.putExtra("AMOUNT", diaryPrice);
                paymentIntent.putExtra("YEAR", diaryYear);
                paymentIntent.putExtra("TYPE", "DIARY");

                //start payment activity;
                startActivity(paymentIntent);

            }
        });

        getDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //prepare the payment for the diary
                int diaryPrice = Prices.SCRIPTURE;

                String curYear = currentYear;
                int curYearInt = Integer.parseInt(curYear);

                int nYear = curYearInt + 1;


                String diaryYear = Integer.toString(nYear);

                String paymentString = "Diary " + diaryYear;

                //put the data in an intent
//                Intent paymentIntent = new Intent(getContext(), MomoPaymentActivity.class);

                Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);

                paymentIntent.putExtra("REASON", paymentString);
                paymentIntent.putExtra("AMOUNT", diaryPrice);
                paymentIntent.putExtra("YEAR", diaryYear);
                paymentIntent.putExtra("TYPE", "DIARY");

                //start payment activity;
                startActivity(paymentIntent);

            }
        });



        return view;

    }

    /**
     * @param month
     * @return monthID eg 02
     */
    private String getMonthId(String month)
    {
        String result = "";

        if(month.equals("January"))
        {
            result = "01";
        }
        else if (month.equals("February"))
        {
            result = "02";
        }
        else if(month.equals("March"))
        {
            result = "03";
        }
        else if(month.equals("April"))
        {
            result = "04";
        }
        else if(month.equals("May"))
        {
            result = "05";
        }
        else if(month.equals("June"))
        {
            result = "06";
        }
        else if(month.equals("July"))
        {
            result = "07";
        }
        else if(month.equals("August"))
        {
            result = "08";
        }
        else if(month.equals("September"))
        {
            result = "09";
        }
        else if(month.equals("October"))
        {
            result = "10";
        }
        else if(month.equals("November"))
        {
            result = "11";
        }
        else if(month.equals("December"))
        {
            result = "12";
        }
        else
        {
            result = "-00";
        }

        return result;
    }

    private String getMonthName(String monthId)
    {
        String result = "";

        if(monthId.equals("01"))
        {
            result = "January";
        }
        else if(monthId.equals("02"))
        {
            result = "February";
        }
        else if(monthId.equals("03"))
        {
            result = "March";
        }
        else if(monthId.equals("04"))
        {
            result = "April";
        }
        else if(monthId.equals("05"))
        {
            result = "May";
        }
        else if(monthId.equals("06"))
        {
            result = "June";
        }
        else if(monthId.equals("07"))
        {
            result = "July";
        }
        else if(monthId.equals("08"))
        {
            result = "August";
        }
        else if(monthId.equals("09"))
        {
            result = "September";
        }
        else if(monthId.equals("10"))
        {
            result = "October";
        }
        else if(monthId.equals("11"))
        {
            result = "November";
        }
        else if(monthId.equals("12"))
        {
            result = "December";
        }
        else
        {
            result = "-00";
        }

        return result;
    }

    public void checkAvailable(@NonNull final SimpleFirebaseCallBack<Boolean> statusCallback, final String year)
    {
        //see if the year value is ther.

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Available").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(year)){
                    statusCallback.isAvailable(true);
                }
                else{
                    statusCallback.isAvailable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.scripture_toolbar, menu);
    }
}