package com.example.myscanner;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewATT extends AppCompatActivity {

//    Bundle listofQRrcode;
    String qrcodescanned;

    

    @SuppressLint("Assert")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_att);

        ListView lV = (ListView) findViewById(R.id.my_list);
        Bundle bundle= getIntent().getExtras();
        if (bundle != null) {
            ArrayList<String> listOfQrcodes = bundle.getStringArrayList("SCAN_RESULTS");
            if (listOfQrcodes != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewATT.this, android.R.layout.simple_list_item_1, listOfQrcodes);
                lV.setAdapter(adapter);
            }
        }
    }
}
//,
////        Intent qrcodescanned = getIntent();//.getExtras();
////

//        ArrayAdapter<String> aA;
////        lV.setAdapter(aA);
////
////        Bundle callingIntent= getIntent().getExtras();
////        listofQRrcode = callingIntent;
////
////
//       List<Intent> completeList = null;
////        if(qrcodescanned != null){
//           // aA = new ArrayAdapter<String>(this, android.R.layout.activity_list_item,completeList);
////            HashMap<String, String> hashMap;
////            lV = (ListView) findViewById(R.id.my_list);
////            lV.setAdapter(aA);
//
//        getIntent();
////        myObject = (MyObject) getIntent().getExtras().getSerializable("my object");
////        ArrayList<String> listOfBarcode = callingIntent.getSerializableExtra("Barcodes");
//        Intent listOfQrcode = getIntent();
//        ArrayList<String> getSerializableExtra;
//        if(null != qrcodescanned){
//            if (true) throw new AssertionError();
//                completeList.add(listOfQrcode);
//                aA = new ArrayAdapter<String>(this, android.R.layout.activity_list_item,completeList);
//                lV = (ListView) findViewById(R.id.my_list);
//                lV.setAdapter(aA);
//
//        }





