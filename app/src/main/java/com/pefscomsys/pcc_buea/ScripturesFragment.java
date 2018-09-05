package com.pefscomsys.pcc_buea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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


    GregorianCalendar calender;

    public ScripturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scriptures, container, false);

        //we have to set the selected dates to represent the date of today

        //start by initialising the timezone ids
        String[] ids = TimeZone.getAvailableIDs(+1 * 60 * 60 * 1000);

        if(ids.length == 0)
        {
            Toast.makeText(getContext(), "Could not get the time", Toast.LENGTH_SHORT);
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

//        Log.d("Date", currentDay);
//        Log.d("Date", currentMonth);
//        Log.d("Date", currentYear);

//        String res = "Day:" + currentDay + " Month:" + currentMonth + " Year:" + currentYear;
//
//        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();



        //initialise the spinners
        day = view.findViewById(R.id.day_spinner);
        month = view.findViewById(R.id.month_spinner);
        year = view.findViewById(R.id.year_spinner);
        findButton = view.findViewById(R.id.find_btn);

            //initialising the days adapter
        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.days, android.R.layout.simple_spinner_item);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day.setAdapter(daysAdapter);

        //prepare the months adapter too.
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.months, android.R.layout.simple_spinner_item);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        month.setAdapter(monthsAdapter);

        
        //year resources id

        ArrayAdapter<CharSequence> yearsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.years, android.R.layout.simple_spinner_item);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(yearsAdapter);

        //set and onclick listener to the button
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Filter Date string", Toast.LENGTH_SHORT).show();
                Log.d("Scripture:", "filter the scriptures");

                //now get the dates selected
                selectedMonth = (String) month.getSelectedItem();
                selectedDay = (String) day.getSelectedItem();
                selectedYear = (String) year.getSelectedItem();

                //get the month code
                String monthID = getMonthId(selectedMonth);

                String toast = "Month: " + selectedMonth + " Day: " + selectedDay + " Year: " + selectedYear + " MonthId: " + monthID;

                Log.d("Scripture", toast);

                Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();

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
