package com.ikspl.hps.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ikspl.hps.R;

import java.util.ArrayList;

public class AcademicsAdapter extends RecyclerView.Adapter<AcademicsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Integer> imgarray;
    public AcademicsAdapter(Context context, ArrayList<Integer> arrayimg)
    {
        this.context = context;
        imgarray = arrayimg;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.academicsadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        if(imgarray.get(i) != null)
        {
            myViewHolder.img.setBackgroundResource(imgarray.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return imgarray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.academicsimg);
        }
    }
}

