package com.example.webproject;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment3 extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String s = getActivity().getIntent().getStringExtra("id");

        String url = "https://assignment8backend123.wn.r.appspot.com/get_reviews?id="+s;
//        Log.d("----URL----",url);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,url,null,
                (Response.Listener<JSONArray>) response -> {

                    ArrayList list = new ArrayList();
                    try {
                        TableLayout tb = (TableLayout) getView().findViewById(R.id.tb);

                        for(int i=0;i<response.length();i++){
                            Map<String,String> mp= new HashMap<String,String>();

                            String dt = response.getJSONObject(i).getString("time_created").toString();
                            String[] date = dt.split(" ");

//                            Log.d("---Frag3--- ",response.getJSONObject(i).getJSONObject("user").getString("name").toString());
                            mp.put("text",response.getJSONObject(i).getString("text").toString());
                            mp.put("rating",response.getJSONObject(i).getString("rating").toString());
                            mp.put("name",response.getJSONObject(i).getJSONObject("user").getString("name").toString());
                            mp.put("date",date[0]);
                            list.add(mp);



                            //Table row
                            TableRow tableRow = new TableRow(getContext());
                            tableRow.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

                            TextView tv1 = new TextView(getContext());
                            tv1.setMaxWidth(1100);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv1.setText(Html.fromHtml("<b>"+mp.get("name")+"</b><p>Rating :"+mp.get("rating")+"/5<br><br>"+mp.get("text")+"<br><br>"+mp.get("date")+"</p>", Html.FROM_HTML_MODE_COMPACT));
                                tv1.setTextColor(Color.BLACK);
                            }

                            tableRow.addView(tv1);

                            View v = new View(getContext());
                            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 5));
                            v.setBackgroundColor(Color.BLACK);

                            tb.addView(tableRow);
                            if(i+1!=response.length()) {
                                tb.addView(v);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
