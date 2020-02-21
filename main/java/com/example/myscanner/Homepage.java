package com.example.myscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Homepage extends AppCompatActivity  implements View.OnClickListener {

    Button bScan;
    Button bViewAtt;
    private final int permission_code = 1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bScan = findViewById(R.id.bScan);
        bViewAtt = findViewById(R.id.bView_Att);


        bScan.setOnClickListener(this);
        bViewAtt.setOnClickListener(this);

    }

    // users must grant permission to open camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permission_code) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, permission_code);
            return;
        }
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.bScan:
                startActivity(new Intent(this, ScanCode.class));
                break;

            case R.id.bView_Att:
                startActivity(new Intent(this, ViewAttendance.class));
                break;
        }
    }
}