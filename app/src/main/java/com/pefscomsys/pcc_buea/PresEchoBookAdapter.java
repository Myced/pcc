package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;
import static com.pefscomsys.pcc_buea.Prices.ECHO_PRICE;
import static com.pefscomsys.pcc_buea.Prices.THE_MESSENGER;

public class PresEchoBookAdapter extends RecyclerView.Adapter <PresEchoBookAdapter.MyBookHolder> {
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> booksNames;
    private ArrayList<String> downloadUrls;
    ProgressDialog mProgressDialog;
    private SharedPreferences mPaymentPref;



    public PresEchoBookAdapter(Context ctx, ArrayList<String> booksNames, ArrayList<String> downloadUrls, RecyclerView recyclerView){
        this.context = ctx;
        this.booksNames = booksNames;
        this.recyclerView = recyclerView;
        this.downloadUrls = downloadUrls;
    }

    public void update(String displayName, String url){
        booksNames.add(displayName);
        downloadUrls.add(url);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_row_design, parent, false);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Downloading book please wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        return new MyBookHolder(view);
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
                    int position = recyclerView.getChildLayoutPosition(view);/*
                    Intent newIntent = new Intent();
                    newIntent.setType(Intent.ACTION_VIEW);
                    newIntent.setData(Uri.parse(downloadUrls.get(position)));*/

                    int year = Calendar.getInstance().get(Calendar.YEAR);

                    //context.startActivity(newIntent);
                    String nameOfBook =  booksNames.get(position);
                    final DownloadTask downloadTask = new DownloadTask(context, booksNames.get(position), mProgressDialog, "echos/"+year);
                    mPaymentPref = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);
                    if((mPaymentPref.getString(nameOfBook.toUpperCase(), "NOT_PAID").equals("PAID"))){
                        downloadTask.execute(String.valueOf(Uri.parse(downloadUrls.get(position))));
                    }
                    else{
                        Intent paymentIntent = new Intent(context, PaymentActivity.class);
                        paymentIntent.putExtra("REASON", ""+nameOfBook.toUpperCase());
                        paymentIntent.putExtra("AMOUNT", ECHO_PRICE);
                        context.startActivity(paymentIntent);
                    }

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            downloadTask.cancel(true);
                        }
                    });

                }
            });
        }
    }

}
