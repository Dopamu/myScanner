package com.example.myscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAttendance extends AppCompatActivity implements View.OnClickListener {

    String Qrcodescanned;

    Button bSend;
    Button bReceive;
    Button bView;
    Button bStat;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        bSend = findViewById(R.id.sendnotification);
        bReceive = findViewById(R.id.viewnotification);
        bView = findViewById(R.id.viewAttendance);
        Button bStat = findViewById(R.id.stats);

//        bSend.setOnClickListener(this);
//        bReceive.setOnClickListener(this);
        bView.setOnClickListener(this);
        bStat.setOnClickListener(this);

    }

    @Override
    public void onClick (View v) {
        if (v.getId() == R.id.viewAttendance) {
            startActivity(new Intent(this, ViewATT.class));
            //            case R.id.stats:
//                startActivity(new Intent(this, Stat.class));
        }
    }



}
