package com.example.myscanner;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewATT extends AppCompatActivity {

    Bundle listofQRrcode;

    String qrcodescanned;
    private Session session;
    private DbHelper db;

    SharedPreferences shared;
    static ArrayList<String> completeList = new ArrayList<>();
    static ArrayList<String> complete = new ArrayList<>();
    TextView textView;
    Button bSave;
    //ListView lV = (ListView) findViewById(R.id.my_list);

//    private ExampleAdapter mAdapter;


    @SuppressLint({"Assert", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_att);


        ListView lV = (ListView) findViewById(R.id.my_list);
        db = new DbHelper(this);

        bSave = findViewById(R.id.button_save);

        // load the saved data 
        // send qr code captured to website
        
        loadData();
        SendData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<String> listOfQrcodes = bundle.getStringArrayList("SCAN_RESULTS");
            if (listOfQrcodes != null) {
                completeList.addAll(listOfQrcodes);
//                SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("Qr_code", listOfQrcodes);
//                editor.apply();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewATT.this, android.R.layout.simple_list_item_1, completeList);
                lV.setAdapter(adapter);
                bSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveData();
                    }
                });
            }
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(completeList);
        editor.putString("code list", json);

        editor.apply();
//        sharedPreferences.g("code list", 0);
        Log.d("TAG", "jsonCodes = " + json);
        Toast.makeText(getApplicationContext(), "Saved code: " + json, Toast.LENGTH_LONG).show();
    }

    private void SendData() {
            final RequestQueue queue = Volley.newRequestQueue(this);
            SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
            String Username  = sharedPreferences.getString("Username", null);
            String Module  = sharedPreferences.getString("Module code", null);
            String Qr_code = sharedPreferences.getString("Qr_codes", null);
            String url = "http://34.241.26.73/flags";
            JSONObject postparams = new JSONObject();
            try {
                postparams.put("Username", Username);
                postparams.put("Class", Module);
                postparams.put("Qr code",Qr_code);

            } catch (
                    JSONException e) {
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
        }

        
        // load save data here

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("code list", null);
        //textView = (findViewById(R.id.textView));
        //textView.setText(json);
        ListView lV = (ListView) findViewById(R.id.stay_list);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        complete = gson.fromJson(json, type);
        if (complete != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewATT.this, android.R.layout.simple_list_item_1, complete);
            lV.setAdapter(adapter);
        }
    }}
    









