package com.pefscomsys.pcc_buea;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MyMessengerBookAdapter extends RecyclerView.Adapter<MyMessengerBookAdapter.MyBookHolder>{
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> booksNames;
    private ArrayList<String> storageUrls;



    public MyMessengerBookAdapter(Context ctx, ArrayList<String> booksNames, ArrayList<String> storageUrls, RecyclerView recyclerView){
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
    public MyMessengerBookAdapter.MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_row_design, parent, false);

        return new MyMessengerBookAdapter.MyBookHolder(view);
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
                    viewPdf(Uri.fromFile(new File(storageUrls.get(position))));

                }
            });
        }
    }
    private void viewPdf(Uri file) {
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/pdf");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // No application to view, ask to download one
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Application Found");
            builder.setMessage("Download one from Android Market?");
            builder.setPositiveButton("Yes, Please",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent
                                    .setData(Uri
                                            .parse("market://details?id=com.adobe.reader"));
                            context.startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();
        }
    }

}
