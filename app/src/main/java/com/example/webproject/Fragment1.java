package com.example.webproject;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fragment1 extends Fragment{

    String name,id;
    final Calendar myCalendar= Calendar.getInstance();
    TextView date_inp;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_1,container,false);
        return inf;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = getActivity().getIntent().getStringExtra("id");
        Log.d("---Data---","a "+s);
        id=s;
        String url = "https://assignment8backend123.wn.r.appspot.com/get_business?id="+s;
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url,null,
                (Response.Listener<JSONObject>) response -> {
                    Log.d("---get---","HIIIII");
                    try {
                        name = response.getString("name").toString();
                        String price = response.getString("Price").toString();
                        String status = response.getString("Status").toString();
                        String category = response.getString("Category").toString();
                        String url1 = response.getString("Url").toString();
                        String phone = response.getString("Phone").toString();
                        String add1 = response.getJSONArray("Address").get(0).toString();
                        String add2 = response.getJSONArray("Address").get(1).toString();
                        String address = add1+" , "+add2;

                        ArrayList<String> photos = new ArrayList<>();
                        JSONArray pics = response.getJSONArray("Photos");

                        for(int i=0;i<pics.length();i++){
                            photos.add(pics.get(i).toString());
                            Log.d("---pic---","a "+photos.get(i));
                        }
                        Log.d("--Fragment1--","all-"+address+price+phone+status+category+url1+(photos.toString()));
                        fillData(address,price,phone,status,category,url1,photos);

                    } catch (JSONException e) {
                        Log.d("---Frag1---","Try Error");
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Log.d("---Frag1---","Response Error");
                    error.printStackTrace();
                }
        );
        requestQueue.add(jsonObjectRequest);
        Button mButton = getView().findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("---hchjd---","Inside onClick");

                // Do something
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_layout);

                TextView buss_name = dialog.findViewById(R.id.buss_name);
                buss_name.setText(name);

                EditText email_inp = dialog.findViewById(R.id.email_inp);
                date_inp = dialog.findViewById(R.id.date_inp);
                EditText time_inp = dialog.findViewById(R.id.time_inp);

                Button button_cancel = dialog.findViewById(R.id.button_cancel);
                Button button_submit = dialog.findViewById(R.id.button_submit);


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                final String[] curr_date = new String[1];
                final String[] curr_time = new String[1];

                date_inp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month=month+1;
                                String date = month+"-"+day+"-"+year;
                                date_inp.setText(date);
                                curr_date[0] = date;
                                Log.i("RESULT DATE", date);
                            }
                        }, year, month, day);
                        StartTime.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                        StartTime.show();
                    }
                });

                time_inp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePick = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                String time = hour+":"+minute;
                                Log.d("---INSIDE FUNC---", String.valueOf(hour));
                                time_inp.setText(time);
                                curr_time[0] = time;
                                Log.d("---RESULT TIME---", time);
                            }
                        }, hour, minute, false);
                        timePick.show();
                    }
                });

                button_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Check Email

                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String em = String.valueOf(email_inp.getText());

                        boolean b1=false,b2=false,b3=false;
                        if(em.matches(emailPattern) && em.length() > 0)
                        {
                            b1=true;
                        }
                        else{
                            Toast.makeText(getContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                        }

                        b2=true;

                        String[] arrOfStr = curr_time[0].split(":", 2);

                        int hr = Integer.parseInt(arrOfStr[0]);
                        if(hr>=10 && hr<=17){
                            b3=true;
                        }
                        else{
                            Toast.makeText(getContext(),"Time should be between 10AM and 5PM",Toast.LENGTH_SHORT).show();
                        }

                        if(b1==true && b2==true && b3==true){
                            Toast.makeText(getContext(),"Reservation Booked",Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                            SharedPreferences.Editor myData = sharedPreferences.edit();
                            myData.putString("email",em);
                            myData.putString("date",curr_date[0]);
                            myData.putString("time",curr_time[0]);
                            myData.putString("id",id);
                            myData.putString("name",name);
                            myData.commit();
                        }

                        dialog.dismiss();
                    }
                });

                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void fillData(String address, String price, String phone, String status, String category, String url1, ArrayList<String> photos) {

        Log.d("---Frag`1---","a "+address);

        TextView add_view = getView().findViewById(R.id.add_view);
        add_view.setText(address);

        TextView add_price = getView().findViewById(R.id.price_view);
        add_price.setText(price);

        TextView add_phone = getView().findViewById(R.id.phone_view);
        add_phone.setText(phone);

        TextView add_status = getView().findViewById(R.id.status_view);
        if(status.equals("false")){
            add_status.setText("closed");
            add_status.setTextColor(getResources().getColor(R.color.red));
        }
        else{
            add_status.setText("Open Now");
            add_status.setTextColor(getResources().getColor(R.color.green));
        }

        TextView add_category = getView().findViewById(R.id.category_view);
        add_category.setText(category);

        TextView add_url = getView().findViewById(R.id.url_view);
        String linkedText = String.format("<u><a href=\"%s\">Business Link</a></u>", url1);
        add_url.setText(Html.fromHtml(linkedText));
        add_url.setMovementMethod(LinkMovementMethod.getInstance());

        Log.d("--Pics--","a-"+photos.get(0));

        ImageView img1 = getView().findViewById(R.id.pic1);
        Log.i("INSIDE TABLE IMAGE","IMAGE"+photos.get(0));
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(photos.get(0)).into(img1);

        ImageView img2 = getView().findViewById(R.id.pic2);
        Log.i("INSIDE TABLE IMAGE","IMAGE"+photos.get(1));
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(photos.get(1)).into(img2);

        ImageView img3 = getView().findViewById(R.id.pic3);
        Log.i("INSIDE TABLE IMAGE","IMAGE"+photos.get(2));
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(photos.get(2)).into(img3);

    }

}
