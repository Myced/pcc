package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScripturesFragment extends Fragment {

    //declare private global variables
    Spinner day;
    Spinner month;
    Spinner year;
    Button findButton;

    String selectedMonth;
    String selectedDay;
    String selectedYear;

    String currentYear;
    String currentMonth;
    String currentDay;
    String currentMonthName;

    TextView readingPsalms, readingOne, readingTwo, readingText;

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
         readingOne = view.findViewById(R.id.reading_one);
         readingTwo = view.findViewById(R.id.reading_two);
         readingText = view.findViewById(R.id.reading_text);

        //set them

        //start by initialising the timezone ids
        String[] ids = TimeZone.getAvailableIDs(+1 * 60 * 60 * 1000);

        if(ids.length == 0)
        {
            Toast.makeText(getContext(), "Could not get the time", Toast.LENGTH_SHORT).show();
        }

        //prepare the timezone
        SimpleTimeZone timeZone = new SimpleTimeZone(+1 * 60 * 60 * 1000, ids[0]);

        Calendar calendar = new GregorianCalendar(timeZone);

        Log.d("Calendar", "Printing Date and Time");

        //get the date of today.
        // the day, month and year
        Date today = Calendar.getInstance().getTime();

        //now format the date
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        //now get the current day, month and year
        currentDay = dayFormat.format(today);
        currentMonth = monthFormat.format(today);
        currentYear = yearFormat.format(today);
        currentMonthName = getMonthName(currentMonth);

        reading = new Scripture("", "", "", "", "");

        reading.setPsalms("Psalms 40: 1-45");
        reading.setReadingText("Mt 5: 1-3");
        reading.setReadingTwo("John 3:3");
        reading.setReadingOne("Luke 4: 5");

        //prepare our date and get readings for this date
        String dbDate = currentDay + '/' + currentMonth + '/' + currentYear;


        //now get it from the db with the Scripture DB Helper
         scriptureDb = new ScriptureDBHandler(getContext());


         //create the database
        try {
            scriptureDb.createDataBase();
        } catch (IOException e) {
            Log.d("Database", "Cannot get database");
            e.printStackTrace();
        }

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
                    startActivity(readingOneIntent);
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
        }
        else
        {
            readingOne.setText("");
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
        }
        else
        {
            readingTwo.setText("");
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
        }
        else
        {
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
        }
        else
        {
            readingPsalms.setText("");
        }







        //initialise the spinners
        day = view.findViewById(R.id.day_spinner);
        month = view.findViewById(R.id.month_spinner);
        year = view.findViewById(R.id.year_spinner);
        findButton = view.findViewById(R.id.find_btn);

            //initialising the days adapter
        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.days, R.layout.date_spinner_row);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day.setAdapter(daysAdapter);

        //prepare the months adapter too.
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.months, R.layout.date_spinner_row);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        month.setAdapter(monthsAdapter);

        //year resources id

        ArrayAdapter<CharSequence> yearsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.years, R.layout.date_spinner_row);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(yearsAdapter);

        //set the default selected items
        day.setSelection(daysAdapter.getPosition(currentDay));
        month.setSelection(monthsAdapter.getPosition(currentMonthName));
        year.setSelection(yearsAdapter.getPosition(currentYear));

        //set and onclick listener to the button
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Filter Date string", Toast.LENGTH_SHORT).show();
                Log.d("Scripture:", "filter the scriptures");

                //now get the dates selected
                selectedMonth = (String) month.getSelectedItem();
                String selectedMonthId = getMonthId(selectedMonth);
                selectedDay = (String) day.getSelectedItem();
                selectedYear = (String) year.getSelectedItem();

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

                Log.d("RESULT", Integer.toString(ourScripture.size()));

                for(int i = 0; i < ourScripture.size(); i++)
                {
                    Scripture currentReading = ourScripture.get(i);

                    //now set the reading to those from currentReading
                    reading.setPsalms(currentReading.getPsalms());
                    reading.setReadingText(currentReading.getReadingText());
                    reading.setReadingTwo(currentReading.getReadingTwo());
                    reading.setReadingOne(currentReading.getReadingOne());
                }

//                now set the text for the reading
                //psalms introit
                if(!(reading.getPsalms() == null))
                {
                    Log.d("Database", "pslams is null");
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
                }
                else
                {
                    readingPsalms.setText("");
                }



                //make reading one clickable.
                if(!(reading.getReadingOne() == null))
                {
                    final String oneStringFiltered = reading.getReadingOne();

                    SpannableString oneSpan = new SpannableString(oneStringFiltered);
                    ClickableSpan clickOne = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Intent readingOneIntent = new Intent(getContext(), ReadingOneActivity.class);
                            readingOneIntent.putExtra("READING_ONE", oneStringFiltered);
                            startActivity(readingOneIntent);
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
                }
                else
                {
                    readingOne.setText("");
                }


                if(reading.getReadingTwo() != null)
                {
                    //do the same for reading two
                    final String twoString = reading.getReadingTwo();
                    SpannableString twoSpan = new SpannableString(twoString);
                    ClickableSpan clickTwo = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {

                            Intent readingTwoIntent = new Intent(getContext(), ReadingTwoActivity.class);
                            readingTwoIntent.putExtra("READING_TWO", twoString);
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
                }
                else
                {
                    readingTwo.setText("");
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
                }
                else
                {
                    readingText.setText("");
                }




                //get the month code
                String monthID = getMonthId(selectedMonth);

                String toast = "Month: " + selectedMonth + " Day: " + selectedDay + " Year: " + selectedYear + " MonthId: " + monthID;

                Log.d("Scripture", toast);

                //now get the scriptures for this particular day



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


}
