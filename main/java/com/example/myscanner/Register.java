package com.example.myscanner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    Button bRegister, bRequest;
    EditText etEmail, etClass, etUsername, etPassword;
    localstore userlocalStore;
    private DbHelper db;

    public RequestQueue requestQueue;
    public EditText login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        etClass= findViewById(R.id.etClass);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        bRegister = findViewById(R.id.bRegister);
        bRequest = findViewById(R.id.bRequest);
        login = findViewById(R.id.etlogins);


        //userlocalStore = new localstore(this);
        final RequestQueue queue = Volley.newRequestQueue(this);
        //final String url = "http://34.241.26.73/students";  // add user details to database

        //register();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://34.241.26.73/students";
                // make sure user is registered first
                register();
                // send their login details to website
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("Email", etEmail.getText().toString());
                    postparams.put("Username", etUsername.getText().toString());
                    postparams.put("Password", etPassword.getText().toString());
                    postparams.put("Class", etPassword.getText().toString());



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                queue.add(jsonObjReq);
                //register();
            }
            private void register () {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String CLASS = etClass.getText().toString();
                //int student_id = Integer.parseInt(etStudent_ID.getText().toString());
                // if (fullname.isEmpty())
                if (username.isEmpty() && password.isEmpty()) {
                    displayToast("Username/password field empty");
                } else {
                    db.addUser(email, username, password, CLASS);
                    displayToast("You have been registered");
                    finish();
                }
            }

            private void displayToast (String message){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // if login details button is pressed, display their details provided users input their id
        bRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://34.241.26.73/students";
                String req_url = url + "/" + login.getText();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET, req_url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAGG", response.toString());
                                try {
                                    JSONArray data = response.getJSONArray("details");
                                    if (data.length() == 0) {
                                        Toast.makeText(getApplicationContext(), "ID Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    } else {
                                        JSONObject obj = data.getJSONObject(0);
                                        Toast.makeText(getApplicationContext(), "" + obj.getString("Username"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                queue.add(jsonObjReq);

            }

        });
    }}




//                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                            }
//                        }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d("volley", "Error: " + error.getMessage());
//                        error.printStackTrace();
//                    }
//                }) {
//
//                    @Override
//                    public String getBodyContentType() {
//                        return "application/x-www-form-urlencoded; charset=UTF-8";
//                    }
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("username", etUsername.getText().toString().trim());
//                        params.put("password", etPassword.getText().toString().trim());
//                        return params;
//                    }
//
//                };
//                requestQueue.add(jsonObjRequest);

//                register();


//    private void register() {
//        // String fullname = etFullName.getText().toString();
//         String username = etUsername.getText().toString();
//         String password = etPassword.getText().toString();
////         int student_id = Integer.parseInt(etStudent_ID.getText().toString());
//        // if (fullname.isEmpty())
//         if(username.isEmpty() && password.isEmpty()){
//             displayToast("Username/password field empty");
//         }else{
//             db.addUser(username,password);
//             displayToast("You have been registered");
//             finish();
//         }
//     }
//
//    private void displayToast(String message){
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//     }
//    }

//}








