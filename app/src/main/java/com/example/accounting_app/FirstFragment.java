package com.example.accounting_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting_app.databinding.FragmentFirstBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import android.content.Context;



public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private String YearToday=new String("");
    private String MonthToday=new String("");
    private String DayToday=new String("");
    private billDatabase billdb;
    private ArrayList<arrayListDef> BillsFromDB;
    private RecyclerViewAdapter RVA;
    private RecyclerView billRV;
    private String YYYY_MM="";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //設定顯示日期
        getAndSetDate();

        //按下fun_list切換頁面
        binding.funList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
            }
        });

        //按下 addbill 切換介面
        binding.AddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_AddbillFragment);
            }
        });

        //按下 setting 切換介面
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SettingFragment);
            }
        });

        ListBillOfThisMonth();


    }

    private void getAndSetDate(){
        //取得日期 Instance
        Calendar myCal=Calendar.getInstance();

        //取得年月日
        String dateFormat="yyyy/MM/dd";
        SimpleDateFormat dFormat=new SimpleDateFormat(dateFormat);
        String todayDate=dFormat.format(myCal.getTime());

        //設定年月日
        for(int i=0;i<4;i++){
            YearToday+=todayDate.charAt(i);
        }
        for(int i=5;i<7;i++){
            MonthToday +=todayDate.charAt(i);
        }
        for(int i=8;i<10;i++){
            DayToday +=todayDate.charAt(i);
        }
        YYYY_MM=YearToday+"_"+MonthToday;
        //設定日期顯示
        String dateFormatForSet=new String("");
        dateFormatForSet+=(YearToday+"/");
        dateFormatForSet+=MonthToday;
        binding.Month.setText(dateFormatForSet);

    }

    private void ListBillOfThisMonth(){
        //show bill variables
        BillsFromDB=new ArrayList<>();
        billdb=new billDatabase(getContext());
        //get bills
        String tableName="Bill_"+YYYY_MM;
        if(!billdb.isTableExists(tableName)){
            billdb.createNewTable(tableName);
        }
        BillsFromDB=billdb.showItem(tableName);
        //BillsFromDB 傳給 RecyclerViewAdapter
        RVA=new RecyclerViewAdapter(BillsFromDB,getContext());
        billRV=binding.idRVBills;
        //幫 RecyclerView 設定 layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        billRV.setLayoutManager(linearLayoutManager);
        // setting our adapter to recycler view.
        billRV.setAdapter(RVA);
        RVA.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                arrayListDef sendToInfoPage=BillsFromDB.get(position);
                SingleBillInfoFragment singleBill=new SingleBillInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("Id", sendToInfoPage.getId());
                bundle.putString("Date", sendToInfoPage.getDate());
                bundle.putString("InOrOut", sendToInfoPage.getInOrOut());
                bundle.putString("Tag", sendToInfoPage.getTag());
                bundle.putDouble("Money", sendToInfoPage.getMoney());
                bundle.putString("Ps", sendToInfoPage.getPs());
                singleBill.setArguments(bundle);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SingleBillInfoFragment,bundle);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



