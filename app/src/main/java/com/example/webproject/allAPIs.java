package com.example.webproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.AsyncRequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class allAPIs{
    public void getSearch() {
        String url = "https://assignment8backend123.wn.r.appspot.com/get_search?keyword=";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, url, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d("-----GetSearch-----", "jhbjh");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
//        requestQueue.add(jsonObjectRequest);
    }
    public void getBusiness() {}
}

