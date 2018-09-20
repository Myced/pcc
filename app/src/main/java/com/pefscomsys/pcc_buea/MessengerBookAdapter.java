package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MessengerBookAdapter extends RecyclerView.Adapter <MessengerBookAdapter.MyBookHolder> {
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> booksNames;
    private ArrayList<String> downloadUrls;
    ProgressDialog mProgressDialog;



    public MessengerBookAdapter(Context ctx, ArrayList<String> booksNames, ArrayList<String> downloadUrls, RecyclerView recyclerView){
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
                    int position = recyclerView.getChildLayoutPosition(view);

                    final DownloadTask downloadTask = new DownloadTask(context, booksNames.get(position), mProgressDialog, "messenger");
                    downloadTask.execute(String.valueOf(Uri.parse(downloadUrls.get(position))));
                    //context.startActivity(newIntent);

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
