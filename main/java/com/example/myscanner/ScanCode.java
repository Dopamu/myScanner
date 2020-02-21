package com.example.myscanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode<listOfQrcodes> extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    ArrayList<String> listOfQrcodes = new ArrayList<String>();

    @Override
    //set view ad ScannerView instead of xml
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    public void handleResult(Result result) {
        String resultQrcode = result.getText();
//        HashMap<String, String> hashMap = (HashMap<String, String>)
        // whenever you capture a new qr code add it to the list
        listOfQrcodes.add(resultQrcode);
//
  //      Intent intent = new Intent(ScanCode.this, ViewATT.class); // send result to view attendance activity.
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("QR-codeString", listOfQrcodes);
//
//        intent.putExtras(bundle);
//
////        intent.putExtra("QR-codeString", listOfQrcode
//        startActivity(intent);

        Intent intent = new Intent(ScanCode.this, ViewATT.class );
        //data.putStringArrayList("SCAN_RESULTS", listOfQrcodes);
        intent.putStringArrayListExtra("SCAN_RESULTS", listOfQrcodes);
        startActivity(intent);

        scannerView.stopCamera();
        onBackPressed();
    }


    //goes back to homepage when back button is pressed

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        scannerView.stopCamera();
        setContentView(R.layout.activity_home_page);

    }


        //stops the camera on pause and returns home
    @Override
    public void onPause(){
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    public void onResume() {

        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

}

// how to save arraylist onshared prefrence and view it.