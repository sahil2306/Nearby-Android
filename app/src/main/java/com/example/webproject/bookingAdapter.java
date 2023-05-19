package com.example.webproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.MyViewHolder> {

    private ArrayList<bookingData> dataList;

    public bookingAdapter(ArrayList<bookingData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public bookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull bookingAdapter.MyViewHolder holder, int position) {

        String name = dataList.get(position).getRes_name();
        holder.res_name.setText(name);

        String time = dataList.get(position).getRes_time();
        holder.res_time.setText(time);

        String date = dataList.get(position).getRes_date();
        holder.res_date.setText(date);

        String email = dataList.get(position).getRes_email();
        holder.res_email.setText(email);

        String no = dataList.get(position).getSr_no();
        holder.sr_no.setText(no);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView res_name,res_email,res_date,res_time,sr_no;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            res_name = itemView.findViewById(R.id.res_name);
            res_email = itemView.findViewById(R.id.res_email);
            res_date = itemView.findViewById(R.id.res_date);
            res_time = itemView.findViewById(R.id.res_time);
            sr_no = itemView.findViewById(R.id.serial_no);
        }
    }
}

