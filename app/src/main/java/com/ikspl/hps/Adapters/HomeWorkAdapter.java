package com.ikspl.hps.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikspl.hps.Models.HomeWorkModel;
import com.ikspl.hps.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyViewHolder> {
    Context context;
    ArrayList<HomeWorkModel> arrayList;
    String classname,studentname;

    public HomeWorkAdapter(Context context,ArrayList<HomeWorkModel> arrayList,String classname,String name)
    {
        this.context = context;
        this.arrayList = arrayList;
        this.classname = classname;
        studentname = name;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.homeworkadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        myViewHolder.datee.setText("Date :- "+sdf.format(arrayList.get(i).getUploadDate()));
        myViewHolder.classname.setText("Class :- "+classname);
        myViewHolder.studentname.setText("Student Name :- "+studentname);
        myViewHolder.homework.setText("Homework Detail :- "+arrayList.get(i).getHomeWorkDetails());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView datee,classname,studentname,homework;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            datee = itemView.findViewById(R.id.datee);
            classname = itemView.findViewById(R.id.classname);
            studentname = itemView.findViewById(R.id.stdname);
            homework = itemView.findViewById(R.id.homework);

        }
    }
}
