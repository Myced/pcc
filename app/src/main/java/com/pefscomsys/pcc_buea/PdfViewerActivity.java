package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    //public static final String SAMPLE_FILE = "android_tutorial.pdf"; //your file path
    PDFView pdfView;
    Integer pageNumber = 0;
    String FILE_LOCATION;
    String TEMP_PATH = Environment.getExternalStorageDirectory() +"/tmp/";
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    //String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        Intent newIntent = getIntent();
        FILE_LOCATION = newIntent.getStringExtra("FILE_LOCATION");

        SecretKeySpec sks = new SecretKeySpec("PCCAPP@Passw1234".getBytes(), "AES");
        try {

            File file = new File(TEMP_PATH);
            if(file.exists()){
            }
            else{
                if(file.mkdirs()){
                    Log.d("Creating File", TEMP_PATH);

                }
            }
            fileInputStream = new FileInputStream(FILE_LOCATION);
            fileOutputStream = new FileOutputStream(TEMP_PATH + "ENOS.pdf");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            final byte[] decryptedData = new byte[4096];
            int decryptedRead;
            while ((decryptedRead = cipherInputStream.read(decryptedData)) >= 0){
                fileOutputStream.write(decryptedData, 0, decryptedRead);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            cipherInputStream.close();
            fileInputStream.close();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfView= (PDFView)findViewById(R.id.pdfView);
        displayFromFile(TEMP_PATH + "ENOS.pdf");
    }

    private void displayFromFile(String FILE) {


        pdfView.fromFile(new File(FILE))
                .defaultPage(pageNumber)
                .swipeHorizontal(true)
                .onPageChange(this)
                .enableDoubletap(true)
                .enableAnnotationRendering(true)
                .enableAntialiasing(true)
                .scrollHandle(null)
                .onLoad(this)
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        File dir = new File(Environment.getExternalStorageDirectory()+"/tmp/");
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
        finish();
    }
}
