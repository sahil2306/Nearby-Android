package com.example.webproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class resrvations extends AppCompatActivity {

    private ArrayList<bookingData> dataList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resrvations);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<>();
        setData();
        setAdapter();

    }

    private void setAdapter() {
        bookingAdapter adapter = new bookingAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
}

    private void setData() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String email = sh.getString("email","");
        String date = sh.getString("date","");
        String time = sh.getString("time","");
        String name = sh.getString("name","");
        String id = sh.getString("id","");
//        String no = String.valueOf(dataList.size());
        String no = "1";

        Log.d("---Shared---","a:- "+email+date+time+name+id+no);

        dataList.add(new bookingData(email,name,date,time,id,no));
//        if(name.equals("Braazo Pizza")){
//            dataList.add(new bookingData("sahil@usc.edu","Dulce","11/12/2022","11:00","abcd","1"));
//            dataList.add(new bookingData("mor@usc.edu","Braazo Pizza","09/12/2022","15:00","abc","2"));
//        }
//        else{
//            dataList.add(new bookingData("sahil@usc.edu","Dulce","11/12/2022","11:00","abcd","1"));
//        }

//        dataList.add(new bookingData("sahil@usc.edu","Dulce","11/12/2022","11:00","abcd","1"));
//        dataList.add(new bookingData("mor@usc.edu","Braazo Pizza","09/12/2022","15:00","abc","2"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}