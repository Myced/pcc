package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FeePlan extends AppCompatActivity {

    private TableLayout table;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_plan);
        setTitle(R.string.fee_plan);

        table = findViewById(R.id.feeplan_table);
        context = getApplicationContext();

        //start by creating fee heading.
        TableRow headingRow = new TableRow(context);
        TableRow headingRow2 = new TableRow(context);

        //create the text views
        TextView featureHeading = prepareHead(new TextView(context), "Feature");
        TextView feeHeading = prepareHeadColumn(new TextView(context), "Fee", 3);
        TextView payPlanHeading = prepareHead(new TextView(context), "Payment Plan");

        headingRow2.addView(prepareHead(new TextView(context), " "));
        headingRow2.addView(prepareHead(new TextView(context), "FCFA"));
        headingRow2.addView(prepareHead(new TextView(context), "Dollar ($)"));
        headingRow2.addView(prepareHead(new TextView(context), "Euro "));
        headingRow2.addView(prepareHead(new TextView(context), " "));



        //add the views to the row
        headingRow.addView(featureHeading);
        headingRow.addView(feeHeading);
        headingRow.addView(payPlanHeading);

        //prepare the bodies

        //start with diary
        TableRow diaryRow = new TableRow(context);
        TextView diaryFeature = prepareBody(new TextView(context), "Diary");
        TextView diaryFcfa = prepareBody(new TextView(context), String.valueOf(Prices.SCRIPTURE));
        TextView diaryDollar = prepareBody(new TextView(context), String.valueOf(Prices.SCRIPTURE_US));
        TextView diaryEuro = prepareBody(new TextView(context), String.valueOf(Prices.SCRIPTURE_EU));
        TextView diaryPlan = prepareBody(new TextView(context), "Yearly");

        diaryRow.addView(diaryFeature);
        diaryRow.addView(diaryFcfa);
        diaryRow.addView(diaryDollar);
        diaryRow.addView(diaryEuro);
        diaryRow.addView(diaryPlan);

        //Church Hymn book
        TableRow hymnRow = new TableRow(context);
        TextView hymnFeature = prepareBody(new TextView(context), "Church Hymn Book");
        TextView hymnFcfa = prepareBody(new TextView(context), String.valueOf(Prices.HYMN_PRICE));
        TextView hymnDollar = prepareBody(new TextView(context), String.valueOf(Prices.HYMN_PRICE_US));
        TextView hymnEuro = prepareBody(new TextView(context), String.valueOf(Prices.HYMN_PRICE_EU));
        TextView hymnPlan = prepareBody(new TextView(context), "Once");

        hymnRow.addView(hymnFeature);
        hymnRow.addView(hymnFcfa);
        hymnRow.addView(hymnDollar);
        hymnRow.addView(hymnEuro);
        hymnRow.addView(hymnPlan);

        //church Info
        TableRow churchInfoRow = new TableRow(context);

        churchInfoRow.addView(prepareBody(new TextView(context), "Church Info"));
        churchInfoRow.addView(prepareBody(new TextView(context), "Free"));
        churchInfoRow.addView(prepareBody(new TextView(context), "Free"));
        churchInfoRow.addView(prepareBody(new TextView(context), "Free"));
        churchInfoRow.addView(prepareBody(new TextView(context), " -- "));

        //The Messenger
        TableRow messengerRow = new TableRow(context);

        messengerRow.addView(prepareBody(new TextView(context), "The Messenger"));
        messengerRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.THE_MESSENGER)));
        messengerRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.THE_MESSENGER_US)));
        messengerRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.THE_MESSENGER_EU)));
        messengerRow.addView(prepareBody(new TextView(context), "Semi Annual"));

        //presbytarian echo
        TableRow echoRow = new TableRow(context);

        echoRow.addView(prepareBody(new TextView(context), "Presbyterian Echo"));
        echoRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.ECHO_PRICE)));
        echoRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.ECHO_PRICE_US)));
        echoRow.addView(prepareBody(new TextView(context), String.valueOf(Prices.ECHO_PRICE_EU)));
        echoRow.addView(prepareBody(new TextView(context), "Monthly"));

        //add the rows to the body
        table.addView(headingRow);
        table.addView(headingRow2);
        table.addView(diaryRow);
        table.addView(hymnRow);
        table.addView(churchInfoRow);
        table.addView(messengerRow);
        table.addView(echoRow);


    }

    private TextView prepareHead(TextView view, String text)
    {
        int headingColor = Color.BLACK;
        int headingPadding = 30;
        float headingTextSize = 20;

        view.setText(text);
        view.setTextSize(headingTextSize);
        view.setTextColor(headingColor);
        view.setTypeface(null, Typeface.BOLD);
        view.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        view.setBackgroundResource(R.drawable.border_square);

        return view;
    }


    private TextView prepareHeadColumn(TextView view, String text, int span)
    {
        int headingColor = Color.BLACK;
        int headingPadding = 30;
        float headingTextSize = 20;

        //layout parameters to make a column span
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = span;

        view.setText(text);
        view.setTextSize(headingTextSize);
        view.setTextColor(headingColor);
        view.setTypeface(null, Typeface.BOLD);
        view.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        view.setBackgroundResource(R.drawable.border_square);
        view.setGravity(View.TEXT_ALIGNMENT_CENTER);
        view.setLayoutParams(params);

        return view;
    }

    private TextView prepareBody(TextView view, String text)
    {
        int bodyColor = Color.BLACK;
        int bodyPadding = 25;
        float bodyTextSize = 16;

        view.setText(text);
        view.setTextColor(bodyColor);
        view.setTextSize(bodyTextSize);
        view.setPadding(bodyPadding, bodyPadding, bodyPadding, bodyPadding);
        view.setBackgroundResource(R.drawable.border_square);

        return view;
    }
}
