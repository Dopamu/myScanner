package com.example.myscanner;

import com.android.volley.VolleyError;

public interface Callback {
    void notifySuccess(String response);
    void notifyError(VolleyError error);
}

