package com.example.myscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;


public class login extends AppCompatActivity implements View.OnClickListener {


    Callback apiCallback = null;
    Button bLogin;
    EditText etUsername, etPassword, etModule;
    TextView tvRegisterLink;
    private Session session;
    private DbHelper db;

    public RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = findViewById(R.id.bLogin);
        etPassword = findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etModule = findViewById(R.id.etModule);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        db = new DbHelper(this);
        session = new Session(this);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

        if(session.loggedin()){
            startActivity(new Intent(login.this,Homepage.class));
            finish();
        }
        //userlocalStore = new localstore(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:

                //// for testing and debuging //
                String URL = "http://34.241.26.73/log";
                requestQueue = Volley.newRequestQueue(this);
                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                        URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                    JSONObject objres = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), objres.toString(), Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();

                }

                            }
                        }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String username = etUsername.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        Map<String, String> postParam = new HashMap<String, String>();

                        postParam.put("username", username);
                        postParam.put("password", password);


                        return postParam;
                    }

                };
                requestQueue.add(jsonObjRequest);
                log();

                break;
            case R.id.tvRegisterLink:  // links the register page to the login page
                startActivity(new Intent(this, Register.class));
                break;
            default:

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void log() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String module = etModule.getText().toString();

        if (db.getUser(username, password, module)) {
            session.setLoggedin(true);
            Toast.makeText(getApplicationContext(), "Welcome back "+ username,Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Username", username);
            editor.putString("Module code", module);
            editor.apply();

            startActivity(new Intent(login.this, Homepage.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong username/password",Toast.LENGTH_SHORT).show();
        }
    }
}
