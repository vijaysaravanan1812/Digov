package com.example.digov;

import android.content.DialogInterface;
import android.content.Intent;  
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

public class ScanFragment extends Fragment {

    public ScanFragment(){

    }


    private ScannerLiveView camera;
    private TextView scannedTV;
    /**
     * Object to connect Database
     */
    Database database;
    boolean success;

    String price;



    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Myview =  inflater.inflate(R.layout.fragment_scan , container , false);
        // check permission method is to check that the
        // camera permission is granted by user or not.
        // request permission method is to request the
        // camera permission if not given.
        if (checkPermission()) {
            // if permission is already granted display a toast message
//            Toast.makeText(getActivity(), "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        // initialize scannerLiveview and textview.
        scannedTV = Myview.findViewById(R.id.idTVscanned);
        camera = (ScannerLiveView) Myview.findViewById(R.id.camview);
    //    billFragment.setAdapter();
        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                // method is called when scanner is started
//                Toast.makeText(getActivity(), "Scanner Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stopped.
                Toast.makeText(getActivity(), "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                Toast.makeText(getActivity(), "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                // method is called when camera scans the
                // qr code and the data from qr code is
                // stored in data in string format.
                scannedTV.setText(data);
                /**
                 * invoking database
                 */
                        price = data.substring(data.length()- 4 , data.length());
                        int price_int = Integer.parseInt(price);
                        insert(data , price_int);
            }


        });


        return Myview;
    }
    @Override
    public void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        // below method will set secoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    public void onPause() {
        // on app pause the
        // camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }

    private void requestPermission() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);

    }

    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;

    }

    public void insert(String data , int price){
        database = new Database(getActivity());
        success =  database.addOne(data ,  price);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                Toast.makeText(getActivity(), "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission Denined \n You cannot use app without providing permission", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
