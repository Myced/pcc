package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
class DownloadTask extends AsyncTask<String, Integer, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;
    private String fileName, mCategory;

    private String FILE_PATH;
    private PowerManager.WakeLock mWakeLock;

    public DownloadTask(Context context, String filename, ProgressDialog progressDialog, String category) {
        this.context = context;
        this.fileName = filename;
        this.mProgressDialog = progressDialog;
        this.mCategory = category;
        this.FILE_PATH =  Environment.getExternalStorageDirectory() + "/PCC/media/"+this.mCategory+"/";
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        CipherOutputStream cipherOutput = null;


        //Creating New file if it does not exist
        File file = new File(FILE_PATH);
        if(file.exists()){
        }
        else{
            if(file.mkdirs()){
                Log.d("Creating File", FILE_PATH);
            }
        }

        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            //Log.d("Download: ", "Response Code: " +String.valueOf(connection.getResponseCode()));
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
               // Log.d("Download: ", "Download failed: " +String.valueOf(connection.getResponseMessage()));
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            // download the file
            input = connection.getInputStream();
            //output = new FileOutputStream(FILE_PATH + fileName+".pdf");

            output = new FileOutputStream(FILE_PATH + fileName+ ".enc");
            //Log.d("Download: ", "Output: " + output.toString());
            SecretKeySpec sks = new SecretKeySpec("PCCAPP@Passw1234".getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            cipherOutput = new CipherOutputStream(output, cipher);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                cipherOutput.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (cipherOutput != null)
                    cipherOutput.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();

        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null)
            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
    }
}
