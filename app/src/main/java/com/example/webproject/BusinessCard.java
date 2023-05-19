package com.example.webproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.webproject.ui.main.SectionsPagerAdapter;
import com.example.webproject.databinding.ActivityBusinessCardBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessCard extends AppCompatActivity {

    private ActivityBusinessCardBinding binding;
    String name,s,urlLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusinessCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        s = getIntent().getStringExtra("id").toString();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        String url = "https://assignment8backend123.wn.r.appspot.com/get_business?id="+s;
        //Log.d("outside---","a-"+url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url,null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        name = response.getString("name").toString();
                        urlLink = response.getString("Url").toString();;
                        TextView tt = findViewById(R.id.title);
                        tt.setText(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Log.d("---Card---","Response error");
                    error.printStackTrace();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void facebook(View view){
        String url_f = "http://www.facebook.com/sharer.php?src=sp&u="+urlLink;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_f));
        startActivity(browserIntent);
    }

    public void twitter(View v){
        String url_f = "https://twitter.com/intent/tweet?text=Check+Out+"+name+"+on+Yelp+"+urlLink;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_f));
        startActivity(browserIntent);
    }

    public void backButton(View v){
        finish();
    }

}