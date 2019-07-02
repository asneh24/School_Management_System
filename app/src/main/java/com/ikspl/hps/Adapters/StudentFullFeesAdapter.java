package com.ikspl.hps.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikspl.hps.Models.FeesDetailsModel;
import com.ikspl.hps.R;

import java.util.ArrayList;

public class StudentFullFeesAdapter extends RecyclerView.Adapter<StudentFullFeesAdapter.MyViewHolder> {

    Context context;
    ArrayList<FeesDetailsModel> arrayList;

    public StudentFullFeesAdapter(Context context, ArrayList<FeesDetailsModel> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public StudentFullFeesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.fullfeeadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentFullFeesAdapter.MyViewHolder myViewHolder, int i) {

        Log.e("MyTag","kkkRow21 = "+arrayList.get(i).getFees_HeadName());
        Log.e("MyTag","kkkRow22 = "+arrayList.get(i).getHeadAmounts());
        Log.e("MyTag","kkkRow23 = "+arrayList.get(i).getInstallments());
        Log.e("MyTag","kkkRow24 = "+arrayList.get(i).getDiscount());
        Log.e("MyTag","kkkRow25 = "+arrayList.get(i).getTotal());

        myViewHolder.name.setText("Head Name :- "+arrayList.get(i).getFees_HeadName());
        myViewHolder.amount.setText("Amount :- "+String.valueOf(arrayList.get(i).getHeadAmounts()));
        myViewHolder.installment.setText("Installment :- "+String.valueOf(arrayList.get(i).getInstallments()));
        myViewHolder.discount.setText("Discount :- "+String.valueOf(arrayList.get(i).getDiscount()));
        myViewHolder.total.setText("Total Amount :- "+String.valueOf(arrayList.get(i).getTotal()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,amount,installment,discount,total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amt);
            installment = itemView.findViewById(R.id.installment);
            discount = itemView.findViewById(R.id.discount);
            total = itemView.findViewById(R.id.total);
        }
    }
}
