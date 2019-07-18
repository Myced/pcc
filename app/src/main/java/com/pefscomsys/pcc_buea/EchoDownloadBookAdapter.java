package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;
import static com.pefscomsys.pcc_buea.Prices.ECHO_PRICE;

public class EchoDownloadBookAdapter extends RecyclerView.Adapter<EchoDownloadBookAdapter.MyBookHolder>{
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> booksNames;
    private ArrayList<String> storageUrls;
    private SharedPreferences mPaymentPref;



    public EchoDownloadBookAdapter(Context ctx, ArrayList<String> booksNames, ArrayList<String> storageUrls, RecyclerView recyclerView){
        this.context = ctx;
        this.booksNames = booksNames;
        this.recyclerView = recyclerView;
        this.storageUrls = storageUrls;
    }

    public void update(String displayName, String url){
        booksNames.add(displayName);
        storageUrls.add(url);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EchoDownloadBookAdapter.MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_row_design, parent, false);

        return new EchoDownloadBookAdapter.MyBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookHolder holder, int position) {
        holder.nameOfFile.setText(booksNames.get(position));
    }


    @Override
    public int getItemCount() {
        return booksNames.size();
    }

    public class MyBookHolder extends RecyclerView.ViewHolder {
        TextView nameOfFile;
        public MyBookHolder(View itemView) {
            super(itemView);
            nameOfFile = itemView.findViewById(R.id.name_of_file);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildLayoutPosition(view);
                    String nameOfBook =  booksNames.get(position);
                    mPaymentPref = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                    if((mPaymentPref.getString(nameOfBook.toUpperCase(), "NOT_PAID").equals("PAID"))){
                        Intent newIntent = new Intent(context, PdfViewerActivity.class);
                        newIntent.putExtra("FILE_LOCATION", storageUrls.get(position));
                        context.startActivity(newIntent);
                    }
                    else {
                        Intent paymentIntent = new Intent(context, PaymentActivity.class);
                        paymentIntent.putExtra("REASON", ""+nameOfBook.toUpperCase());
                        paymentIntent.putExtra("AMOUNT", ECHO_PRICE);
                        context.startActivity(paymentIntent);
                    }
                    //viewPdf(Uri.fromFile(new File(storageUrls.get(position))));

                }
            });
        }
    }


}
