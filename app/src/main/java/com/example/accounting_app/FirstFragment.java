package com.example.accounting_app;

import android.app.DatePickerDialog;
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
import java.util.Locale;

import android.content.Context;
import android.widget.DatePicker;


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
    private DatePickerDialog.OnDateSetListener datePicker;
    private Calendar calendar =Calendar.getInstance();

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
        //show bill variables
        BillsFromDB=new ArrayList<>();
        billdb=new billDatabase(getContext());

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

        //按左右按鈕切換月份
        binding.Leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrevMonth();
            }
        });

        binding.Rightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextMonth();
            }
        });

        //按日期處切換月份
        binding.Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDate();
            }
        });



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

    private void showPrevMonth(){
        int tmp0=Integer.parseInt(MonthToday);
        if(tmp0==1){
            int tmp=Integer.parseInt(YearToday);
            if(tmp>1000){
                //年分>=0
                tmp--;
                YearToday=String.valueOf(tmp);
            }
            tmp0=12;
        }
        else{
            tmp0--;
        }
        if(tmp0<10){
            MonthToday="0"+String.valueOf(tmp0);
        }
        else{
            MonthToday=String.valueOf(tmp0);
        }
        YYYY_MM=YearToday+"_"+MonthToday;
        //設定日期顯示
        String dateFormatForSet=new String("");
        dateFormatForSet+=(YearToday+"/");
        dateFormatForSet+=MonthToday;
        binding.Month.setText(dateFormatForSet);

        ListBillOfThisMonth();
    }

    private void showNextMonth(){
        int tmp0=Integer.parseInt(MonthToday);
        if(tmp0==12){
            int tmp=Integer.parseInt(YearToday);
            if(tmp<9999){
                tmp++;
                YearToday=String.valueOf(tmp);
            }
            tmp0=1;
        }
        else{
            tmp0++;
        }
        if(tmp0<10){
            MonthToday="0"+String.valueOf(tmp0);
        }
        else{
            MonthToday=String.valueOf(tmp0);
        }
        YYYY_MM=YearToday+"_"+MonthToday;
        //設定日期顯示
        String dateFormatForSet=new String("");
        dateFormatForSet+=(YearToday+"/");
        dateFormatForSet+=MonthToday;
        binding.Month.setText(dateFormatForSet);

        ListBillOfThisMonth();
    }

    private void ListBillOfThisMonth(){
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

    //選擇日期切換月份
    private void ChangeDate(){
        datePicker=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String dateFormat="yyyy/MM";
                SimpleDateFormat sdf=new SimpleDateFormat(dateFormat, Locale.TAIWAN);
                String DateToShow=sdf.format(calendar.getTime());
                binding.Month.setText(DateToShow);
                //更改一些變數值
                String tmpY="",tmpM="";
                for(int i=0;i<4;i++){
                    tmpY+=DateToShow.charAt(i);
                }
                for(int i=5;i<7;i++){
                    tmpM+=DateToShow.charAt(i);
                }
                YearToday=tmpY;
                MonthToday=tmpM;
                YYYY_MM=YearToday+"_"+MonthToday;
                Log.d("First",YearToday+" "+MonthToday+" "+YYYY_MM);

                ListBillOfThisMonth();
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(getContext(),
                datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

