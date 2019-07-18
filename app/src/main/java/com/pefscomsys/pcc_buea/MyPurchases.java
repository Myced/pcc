package com.pefscomsys.pcc_buea;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyPurchases extends AppCompatActivity
{
    public TableLayout purchaseTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_purcheses);

        setTitle("My Purchases");


        purchaseTable = findViewById(R.id.purchase_table);

        //intialise the purchased items.
        InitPurchasedItems items = new InitPurchasedItems(this);

        //set the headings.
        //prepare headings here
        TableRow headingRow = new TableRow(this);

        TextView countHeading = prepareHead(new TextView(this), "No");
        TextView dateHeading = prepareHead(new TextView(this), "Date Purchased");
        TextView nameHeading = prepareHead(new TextView(this), "Item Name");

        headingRow.addView(countHeading);
        headingRow.addView(dateHeading);
        headingRow.addView(nameHeading);

        purchaseTable.addView(headingRow);

        List<Purchase> data = this.getPurchases();

        for(int i = 0; i < data.size(); i++)
        {
            TableRow bodyRow = new TableRow(this);

            TextView countBody = prepareBody(new TextView(this), Integer.toString(i + 1));
            TextView nameBody = prepareBody(new TextView(this), data.get(i).getName());
            TextView dateBody = prepareBody(new TextView(this), data.get(i).getDate());

            bodyRow.addView(countBody);
            bodyRow.addView(dateBody);
            bodyRow.addView(nameBody);

            purchaseTable.addView(bodyRow);
        }


    }

    private List<Purchase> getPurchases()
    {
        List purchases = new ArrayList<>();

        //go to the database and see all activated purchases.
        ScriptureDBHandler db = new ScriptureDBHandler(this);
        db.openDataBase();

        String query = "SELECT * FROM `" + Purchase.TABLE_NAME + "` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            Purchase purchase = new Purchase();

            purchase.setId(result.getInt(0));
            purchase.setName(result.getString(1));
            purchase.setCode(result.getString(2));
            purchase.setYear(result.getString(3));
            purchase.setDate(result.getString(4));

            purchases.add(purchase);
        }

        result.close();

        db.close();

        return purchases;
    }

    private TextView prepareHead(TextView view, String text)
    {
        int headingColor = Color.BLACK;
        int headingPadding = 30;
        float headingTextSize = 25;

        view.setText(text);
        view.setTextSize(headingTextSize);
        view.setTextColor(headingColor);
        view.setTypeface(null, Typeface.BOLD);
        view.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        view.setBackgroundResource(R.drawable.border_square);

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
