package com.pefscomsys.pcc_buea;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import static android.support.v4.content.ContextCompat.checkSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 */
public class EchoDownloadFragment extends Fragment {


    public EchoDownloadFragment() {
        // Required empty public constructor
    }


    private RecyclerView mEchoDownloadRecyclerview;

    public final String STATE = Environment.getExternalStorageState();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_echo_download, container, false);
        mEchoDownloadRecyclerview = view.findViewById(R.id.my_echo_recyclerview);
        mEchoDownloadRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        EchoDownloadBookAdapter echoDownloadBookAdapter = new EchoDownloadBookAdapter(getContext(), new ArrayList<String>(), new ArrayList<String>(), mEchoDownloadRecyclerview);
        mEchoDownloadRecyclerview.setAdapter(echoDownloadBookAdapter);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String FILE_PATH = Environment.getExternalStorageDirectory() + "/PCC/media/echos/" + year + "/";
        if(Environment.MEDIA_MOUNTED.equals(STATE) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(STATE)){
            if (isStoragePermissionGranted()){
                getAllFilesOfDir(new File(FILE_PATH));
            }
        }
        return view;
    }

    private void getAllFilesOfDir(File directory) {

        Log.d("My Messenger Fragment", "Directory: " + directory.getAbsolutePath() + "\n");
        final File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if(file != null){
                    ((EchoDownloadBookAdapter)mEchoDownloadRecyclerview.getAdapter()).update(file.getName().replace(".enc", ""), file.getAbsolutePath());
                }
            }
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission: ","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Permission ","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }


}
