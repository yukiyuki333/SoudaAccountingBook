package com.example.accounting_app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private ArrayList<arrayListDef> RecieveTheBillFromDB;
    private Context context;
    private OnItemClickListener listener;

    // constructor
    public RecyclerViewAdapter(ArrayList<arrayListDef> RecieveTheBillFromDB, Context context) {
        this.RecieveTheBillFromDB = RecieveTheBillFromDB;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private TextView Day, inorout_text, WhatTag, HowMouchMoney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            Day = itemView.findViewById(R.id.Day);
            inorout_text = itemView.findViewById(R.id.inorout_text);
            WhatTag = itemView.findViewById(R.id.WhatTag);
            HowMouchMoney = itemView.findViewById(R.id.HowMouchMoney);
            itemView.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view,position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_the_bill_in_frg1, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        //Log.d("RecyclerViewAdapter", "position: " + position);
        arrayListDef ThisBill = RecieveTheBillFromDB.get(position);
        holder.Day.setText(ThisBill.getDate());
        holder.inorout_text.setText(ThisBill.getInOrOut());//income、outcome 換成中文
        holder.WhatTag.setText(ThisBill.getTag());
        holder.HowMouchMoney.setText(String.valueOf(ThisBill.getMoney()));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return RecieveTheBillFromDB.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
