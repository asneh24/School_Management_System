package com.ikspl.hps.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikspl.hps.Models.EventModel;
import com.ikspl.hps.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    Context context;
    ArrayList<EventModel> arrayList;

    public EventAdapter(Context context,ArrayList<EventModel> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.eventadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        myViewHolder.datee.setText("Date :- "+sdf.format(arrayList.get(i).getUploadDate()));
        myViewHolder.homework.setText("Event Details :- "+arrayList.get(i).getEventDetails());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView datee,homework;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            datee = itemView.findViewById(R.id.datee);
            homework = itemView.findViewById(R.id.homework);
        }
    }
}
