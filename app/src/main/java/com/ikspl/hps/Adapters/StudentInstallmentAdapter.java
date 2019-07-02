package com.ikspl.hps.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikspl.hps.Models.InstallmentDetailsModel;
import com.ikspl.hps.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentInstallmentAdapter extends RecyclerView.Adapter<StudentInstallmentAdapter.MyViewHolder> {
    Context context;
    ArrayList<InstallmentDetailsModel> arrayList;

    public StudentInstallmentAdapter(Context context,ArrayList<InstallmentDetailsModel> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StudentInstallmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.installmentadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentInstallmentAdapter.MyViewHolder myViewHolder, int i) {

        if(arrayList.get(i).getDuedate() == null || arrayList.get(i).getPaiddate() == null){
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

            myViewHolder.name.setText("Head Name :- "+arrayList.get(i).getHeadName());
            myViewHolder.installment.setText("Installment :- "+arrayList.get(i).getInstallment());
            myViewHolder.amount.setText("Amount :- "+arrayList.get(i).getAmount());
            myViewHolder.discount.setText("Discount :- "+arrayList.get(i).getDiscount());
            myViewHolder.latefee.setText("Late Fee :- "+arrayList.get(i).getLatefee());
            myViewHolder.payableamt.setText("Payable Amount :- "+arrayList.get(i).getPayamt());
            myViewHolder.paidamt.setText("Paid Amount :- "+arrayList.get(i).getPaidamt());
            myViewHolder.dueamt.setText("Due Amount :- "+arrayList.get(i).getDueamt());
            myViewHolder.duedate.setText("Due Date :- "+arrayList.get(i).getDuedate());
            myViewHolder.paiddate.setText("Paid Date :- "+arrayList.get(i).getPaiddate());
        }
        else
        {
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

            myViewHolder.name.setText("Head Name :- "+arrayList.get(i).getHeadName());
            myViewHolder.installment.setText("Installment :- "+arrayList.get(i).getInstallment());
            myViewHolder.amount.setText("Amount :- "+arrayList.get(i).getAmount());
            myViewHolder.discount.setText("Discount :- "+arrayList.get(i).getDiscount());
            myViewHolder.latefee.setText("Late Fee :- "+arrayList.get(i).getLatefee());
            myViewHolder.payableamt.setText("Payable Amount :- "+arrayList.get(i).getPayamt());
            myViewHolder.paidamt.setText("Paid Amount :- "+arrayList.get(i).getPaidamt());
            myViewHolder.dueamt.setText("Due Amount :- "+arrayList.get(i).getDueamt());
            myViewHolder.duedate.setText("Due Date :- "+sdf.format(arrayList.get(i).getDuedate()));
            myViewHolder.paiddate.setText("Paid Date :- "+sdf.format(arrayList.get(i).getPaiddate()));
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,installment,amount,discount,latefee,payableamt,paidamt,dueamt,duedate,paiddate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            installment = itemView.findViewById(R.id.installment);
            amount = itemView.findViewById(R.id.amount);
            discount = itemView.findViewById(R.id.discount);
            latefee = itemView.findViewById(R.id.latefee);
            payableamt = itemView.findViewById(R.id.payableamt);
            paidamt = itemView.findViewById(R.id.paidamt);
            dueamt = itemView.findViewById(R.id.dueamt);
            duedate = itemView.findViewById(R.id.duedate);
            paiddate = itemView.findViewById(R.id.paiddate);
        }
    }
}
