package com.example.webproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessDetails extends AppCompatActivity {

    Float lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        AutoCompleteTextView actv = findViewById(R.id.keyword);
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lat= Float.parseFloat("34.00");
                lon = Float.parseFloat("-118.2863");


                updateTerms(s.toString(),lat,lon);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Spinner sp = findViewById(R.id.dropdownMenu);
        ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(this, R.array.dropdown, android.R.layout.simple_spinner_item);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adpt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(BusinessDetails.this, resrvations.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void updateTerms(String term, Float lat, Float lon) {
        String url = "https://assignment8backend123.wn.r.appspot.com/get_autocomplete?word="+term+"&lat="+lat+"&lon="+lon;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,url,null,
                (Response.Listener<JSONArray>) response -> {
                    String temp = response.toString();
                    temp = temp.substring(2,temp.length()-2);
                    List<String> terms = new ArrayList<String>(Arrays.asList(temp.split("\",\"")));

                    AutoCompleteTextView auto = findViewById(R.id.keyword);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, terms);
                    auto.setAdapter(adapter);
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    public void clear(View view) {
        AutoCompleteTextView auto = findViewById(R.id.keyword);
        EditText distance = findViewById(R.id.distance);
        Spinner sp = findViewById(R.id.dropdownMenu);
        EditText loc = findViewById(R.id.location);
        CheckBox cb = findViewById(R.id.checkBox);

        TableLayout tb = (TableLayout) findViewById(R.id.table);
        tb.removeAllViews();

        auto.setText("");
        distance.setText("");
        sp.setSelected(false);
        loc.setVisibility(View.VISIBLE);
        loc.setText("");
        cb.setChecked(false);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        EditText loc = findViewById(R.id.location);
        if(loc.getVisibility()==View.INVISIBLE) {
            loc.setVisibility(View.VISIBLE);
        }
        else {
            loc.setVisibility(View.INVISIBLE);
        }
    }

    public void submit(View view) {

        AutoCompleteTextView auto = findViewById(R.id.keyword);
        EditText distance = findViewById(R.id.distance);
        Spinner sp = findViewById(R.id.dropdownMenu);
        EditText loc = findViewById(R.id.location);

        boolean st1=false,st2=false,st3=false;

        String key = auto.getText().toString();
        if(key.length()==0){
            auto.setError("This field is required");
            st1=false;
        }
        else{
            auto.setError(null);
            st1=true;
        }

        String dist = distance.getText().toString();
        if(dist.length()==0){
            distance.setError("This field is required");
            st2=false;
        }
        else{
            distance.setError(null);
            st2=true;
        }

        String category = sp.getSelectedItem().toString();
        String location = "";

        if(loc.getVisibility() == View.VISIBLE){
            location = loc.getText().toString();

            if(location.length()==0){
                loc.setError("This field is required");
                st3=false;
            }
            else{
                loc.setError(null);
                st3=true;
            }

        }
        else{
            st3=true;
        }

        if(st1==true && st2==true && st3==true){
            if(loc.getVisibility() == View.VISIBLE){
                //TODO : Get Location from Google Maps API
                Log.d("----Manual----", key+dist+category+location);
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+location+[API_KEY];
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,url,null,
                        (Response.Listener<JSONObject>) response -> {
                            Log.d("TAG--------------", "submit: ");
                            try {
                                lat = Float.parseFloat(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                lon = Float.parseFloat(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                                getSearchBusinesses(key,dist,category,lat,lon);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            },
                        (Response.ErrorListener) error -> {
                            error.printStackTrace();
                        }
                );
                requestQueue.add(jsonObjectRequest);
            }
            else{
                //TODO: Get Location from IPInfo API
                String url = "https://ipinfo.io/json?token=+[API_KEY]";

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,url,null,
                        (Response.Listener<JSONObject>) response -> {
                            try {
                                String latlon = response.getString("loc");
                                lat=Float.parseFloat(latlon.split(",")[0]);
                                lon=Float.parseFloat(latlon.split(",")[1]);

                                getSearchBusinesses(key,dist,category,lat,lon);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        (Response.ErrorListener) error -> {
                            error.printStackTrace();
                        }
                );
                requestQueue.add(jsonObjectRequest);
                Log.d("----Auto----",key+dist+category+ lat + lon);
            }
        }
    }

    private void getSearchBusinesses(String key, String dist, String category, Float lat, Float lon) {
//        Log.d("",key+dist+category+lat+lon);
        String url = "https://assignment8backend123.wn.r.appspot.com/get_search?keyword="+key+"&distance="+dist+"&category="+category+"&lat="+lat+"&lon="+lon;
        Log.d("---url---",url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url,null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        populateTable(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void populateTable(JSONObject response) throws JSONException {

        Log.d("---Populate---","Inside populate table");

        TableLayout tb = (TableLayout) findViewById(R.id.table);

        ArrayList<JSONObject> data = new ArrayList<JSONObject>();

        for(int i=0;i<response.getJSONArray("businesses").length();i++){
            data.add((JSONObject) response.getJSONArray("businesses").get(i));
        }

        tb.removeAllViews();

        for(int i=0;i<data.size();i++){

            JSONObject js = data.get(i);
            String id = js.getString("id");
            String name = js.getString("name");
            String rating = js.getString("rating");
            String distance = js.getString("distance");
            String Photo = js.getString("image_url");

            final TextView id1 = new TextView(this);
            id1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            id1.setGravity(Gravity.CENTER);
            id1.setText(String.valueOf(i+1));
            id1.setWidth(150);
            id1.setPadding(10,10,10,10);

            final ImageView img = new ImageView(this);
            img.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            Picasso.get().load(Photo).resize(200,200).into(img);
            img.setPadding(10,10,10,10);

            final TextView name1 = new TextView(this);
            name1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            name1.setGravity(Gravity.CENTER);
            name1.setText(name);
            name1.setWidth(250);
            name1.setPadding(30,10,10,10);

            final TextView distance1 = new TextView(this);
            distance1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            distance1.setGravity(Gravity.CENTER);
            Float floatdistance= Float.valueOf(distance);
            Integer dist = (int) (floatdistance/1609);
            distance1.setText(dist.toString());
            distance1.setWidth(200);
            distance1.setPadding(30,30,30,30);

            final TextView rating1 = new TextView(this);
            rating1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            rating1.setGravity(Gravity.CENTER);
            rating1.setText(rating);
            rating1.setWidth(200);
            rating1.setPadding(30,30,30,30);

            final TableRow tr = new TableRow(this);
            tr.setId(i+1);
            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trParams);
            tr.setBackgroundColor(Color.GRAY);
            tr.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.tableui, null));
            tr.addView(id1);
            tr.addView(img);
            tr.addView(name1);
            tr.addView(distance1);
            tr.addView(rating1);
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(BusinessDetails.this,BusinessCard.class);
                    in.putExtra("id",id);
                    startActivity(in);
                }
            });

            tb.addView(tr,trParams);

        }
    }
}

