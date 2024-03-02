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
import com.example.accounting_app.databinding.FragmentThirdBinding;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

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
        binding.funList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);////////////////////////////////////
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}