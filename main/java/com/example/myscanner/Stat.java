package com.example.myscanner;


import android.os.Bundle;
import android.util.Log;
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

public class Stat extends AppCompatActivity {

    public EditText Request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://34.241.26.73/students";

        Request = findViewById(R.id.etlogins);

        String req_url = url + "/" + Request.getText();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET, req_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAGG", response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            if (data.length() == 0) {
                                Toast.makeText(getApplicationContext(), "ID Doesn't Exist", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject obj = data.getJSONObject(0);
                                Toast.makeText(getApplicationContext(), "" + obj.getString("username"), Toast.LENGTH_LONG).show();
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
}