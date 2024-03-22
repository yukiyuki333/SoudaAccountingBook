package com.example.accounting_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

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
import java.util.Calendar;
import android.content.Context;



public class FirstFragment extends Fragment { //////////////////////

    private FragmentFirstBinding binding;  ////////////////////////////
    private String YearToday=new String("");
    private String MonthToday=new String("");
    private String DayToday=new String("");
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater,container,false); /////////////////////////
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //設定顯示日期
        getAndSetDate();

        //按下fun_list切換頁面
        /*binding.funList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);////////////////////////////////////
            }
        });*/

        //按下 addbill 切換介面
        /*binding.AddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_AddbillFragment);////////////////////////////////////
            }
        });*/

        //按下 setting 切換介面
        /*binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SettingFragment);////////////////////////////////////
            }
        });*/


        binding.funList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename="/data/data/com.example.accounting_app/billRecord.txt";
                File saveFile=new File(filename);
                if(saveFile.delete()){
                    System.out.println("Deleted");
                }
                else{
                    System.out.println("Delete fail");
                }
            }
        });
        binding.AddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    storeBillInfo();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }

            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    readBill();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
        //設定日期顯示
        String dateFormatForSet=new String("");
        dateFormatForSet+=(YearToday+"/");
        dateFormatForSet+=MonthToday;
        binding.Month.setText(dateFormatForSet);

    }


    //存資料，此函式之後要移去新增 bill 的頁面
    private void storeBillInfo() throws FileNotFoundException {
        String filename="/data/data/com.example.accounting_app/billRecord.txt";
        //create file
        File saveBill=new File(filename);
        if(!saveBill.exists()){
            try{
                saveBill.createNewFile();
                System.out.println("Create success");
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Fuck");
            }
        }

        //read file
        FileInputStream billInfo=new FileInputStream(saveBill);
        BufferedReader reader = new BufferedReader(new InputStreamReader(billInfo));
        String BillList="";
        try{
            String line;
            while((line=reader.readLine())!=null){
                BillList+=(line+"\n");
                //System.out.println(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        //write file
        FileOutputStream writeBill=new FileOutputStream(saveBill);
        try{
            BillList+="test\n";
            writeBill.write(BillList.getBytes());
            writeBill.close();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    //Read file
    private void readBill() throws FileNotFoundException {
        String filename="/data/data/com.example.accounting_app/billRecord.txt";
        File saveBill=new File(filename);
        //read file
        FileInputStream billInfo=new FileInputStream(saveBill);
        BufferedReader reader = new BufferedReader(new InputStreamReader(billInfo));
        try{
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}