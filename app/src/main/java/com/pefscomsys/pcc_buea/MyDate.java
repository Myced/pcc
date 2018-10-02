package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class MyDate
{
    public String currentDate;
    public String day;
    public String month;
    public String year;
    public String date;
    public String dayOfTheWeek;
    public String monthName;
    public String currentDay;
    public String currentMonth;
    public String currentYear;
    public String currentMonthName;

    private Context context;

    public MyDate(Context context)
    {
        this.context = context;

        //in this constructor, we initialise the currentDate
        //start by initialising the timezone ids
        String[] ids = TimeZone.getAvailableIDs(+1 * 60 * 60 * 1000);

        if(ids.length == 0)
        {
            Toast.makeText(this.context, "Could not get the time", Toast.LENGTH_SHORT).show();
        }

        //prepare the timezone
        SimpleTimeZone timeZone = new SimpleTimeZone(+1 * 60 * 60 * 1000, ids[0]);

        Calendar calendar = new GregorianCalendar(timeZone);
        Calendar myCal = Calendar.getInstance();

        Log.d("Calendar", "Printing Date and Time");

        //get the date of today.
        // the day, month and year
        java.util.Date today = Calendar.getInstance().getTime();

        //now format the date
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        String dayName = myCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        this.dayOfTheWeek = dayName;

        //now get the current day, month and year
        currentDay = dayFormat.format(today);
        currentMonth = monthFormat.format(today);
        currentYear = yearFormat.format(today);
        currentMonthName = getMonthName(currentMonth);
        monthName = getMonthName(currentMonth);
    }

    public MyDate(Context context, String day, String month, String year)
    {
        this(context);

        this.day = day;
        this.month = month;
        this.year = year;

        int dayInt = Integer.parseInt(day);
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);

        --monthInt;

        this.date = day + "/" + month + "/" + year;

        this.monthName = getMonthName(month);

        //prepare other parameters
        TimeZone timezone  = TimeZone.getDefault();

        Calendar calendar = new GregorianCalendar(timezone);

        calendar.set(yearInt, monthInt, dayInt,06, 00, 00);

        String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG,
                Locale.getDefault());

        this.dayOfTheWeek = dayName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public String getCurrentMonthName() {
        return currentMonthName;
    }

    public String getMonthYear ()
    {
        String monthYear = this.monthName + ", " + this.year;

        return monthYear;
    }

    String getDayAndName()
    {
        return this.dayOfTheWeek + " " + this.day;
    }

    public String getCurrentMonthYear()
    {
        return this.currentMonthName + ", " + this.currentYear;
    }

    public String getCurrentDayAndName()
    {
        return  this.dayOfTheWeek + " " + this.currentDay;
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
