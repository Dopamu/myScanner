package com.example.myscanner;

import android.content.Intent;
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
    EditText etUsername, etPassword, etStudent_Id;
    TextView tvRegisterLink;
    private Session session;
    private DbHelper db;
    localstore userlocalStore;

    public RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = findViewById(R.id.bLogin);
        etPassword = findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        //etStudent_Id = findViewById(R.id.etStudent_Id);
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

        if (db.getUser(username, password)) {
            session.setLoggedin(true);
            Toast.makeText(getApplicationContext(), "Welcome back "+ username,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(login.this, Homepage.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong username/password",Toast.LENGTH_SHORT).show();
        }
    }
}

//
//
//    private void Submit(String data) {
//        final String savedata = data;
//        String URL = "http://34.241.26.73/log";
//        URL = URL.replaceAll(" ", "%20");
//
//
//        //requestQueue.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject objres = new JSONObject(response);
//                    Toast.makeText(getApplicationContext(), objres.toString(), Toast.LENGTH_LONG).show();
//
//
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
//
//                }
//                //Log.i("VOLLEY", response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                //Log.v("VOLLEY", error.toString());
//            }
//        })
////        @Override
////        protected Map<String,String> getParams(){
////            Map<String,String> params = new HashMap<>();
////            params.put(KEY_USERNAME,username);
////            params.put(KEY_PASSWORD,password);
////            return params;
////        }
//        {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return savedata == null ? null : savedata.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
//                    return null;
//                }
//            }
//              @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        String username = etUsername.getText().toString();
//                        String password = etPassword.getText().toString();
//
//                        Map<String, String> postParam = new HashMap<String, String>();
//
//                        postParam.put("username", username);
//                        postParam.put("password", password);
//
//
//                        return postParam;
//                    }
//        };
//        requestQueue.add(stringRequest);
//
//
//    }



//global data #just here so I can print it from the console for testing purposes.
//    data = request.get_json()
//    print(data)


//
//
//        bLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String username = etUsername.getText().toString();
//                final String password = etPassword.getText().toString();
//
//                // Response received from the server
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//
//                            if (success) {
//                                String name = jsonResponse.getString("name");
//                                int student_id = jsonResponse.getInt("student_id");
//
//                                Intent intent = new Intent(login.this, Homepage.class);
//                                intent.putExtra("name", name);
//                                intent.putExtra("student_id", student_id);
//                                intent.putExtra("username", username);
//                                login.this.startActivity(intent);
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
//                                builder.setMessage("Login Failed")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(login.this);
//                queue.add(loginRequest);
//            }
//        });
//    }
//}



