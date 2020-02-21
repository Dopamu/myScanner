package com.example.myscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
//    EditText etFullName, etStudent_ID, etUsername;
//    localstore userlocalStore;
    private Session session;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        etFullName = findViewById(R.id.etFullName);
//        etStudent_ID = findViewById(R.id.etStudent_Id);
//        etUsername = findViewById(R.id.etUsername);
        bLogout = findViewById(R.id.bLogout);

//
//        userlocalStore = new localstore(this);
        session = new Session(this);
        if (!session.loggedin()){
            logout();
        }

        bLogout.setOnClickListener(this);
    }
//
//    @Override
//    protected void onStart () {
//        super.onStart();
//        if (authenticate()) {
//           displayUserDetails();
//        }
//    }

    public boolean authenticate () {
        return localstore.getUserLoggedIn();
    }
//
//    private void displayUserDetails ()
//    {
//        User user = userlocalStore.getLoggedIn();
//        etFullName.setText(user.fullname);
//        etStudent_ID.setText(user.student_id);
//        etUsername.setText(user.username);
//    }

    @Override
    public void onClick (View v) {
//        switch (v.getId()) {
//            case R.id.bLogout:
                logout();
                //userlocalStore.setloggedIn(false);

//                startActivity(new Intent(this, login.class));
//                break;
        }
//    }

    private void logout(){
        session.clearUserData();
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this, login.class));
        }
    }

