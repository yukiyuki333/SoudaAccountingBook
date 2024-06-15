package com.example.accounting_app;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.accounting_app.databinding.FragmentAddbillBinding;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import com.example.accounting_app.billDatabase; //2024/4/30新增

public class AddbillFragment extends Fragment {
    private FragmentAddbillBinding binding;
    DatePickerDialog.OnDateSetListener datePicker;
    Calendar calendar =Calendar.getInstance();
    billDatabase billdb;
    String DateOfThisBill,InOrOutOfThisBill,TagOfThisBill,PsOfThisBill;
    int MoneyOfThisBill;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddbillBinding.inflate(inflater,container,false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //回主畫面
        binding.backTomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddbillFragment.this)
                        .navigate(R.id.action_AddbillFragment_to_FirstFragment);
            }
        });

        //日期設定
        binding.date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                clickDateText();
            }
        });
        //收支選項
        binding.income.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                binding.outcome.setChecked(false);
                InOrOutOfThisBill="Income";
            }
        });
        binding.outcome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                binding.income.setChecked(false);
                InOrOutOfThisBill="Outcome";
            }
        });
        //儲存
        //先綁定資料庫
        billdb=new billDatabase(getContext());
        binding.save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //存資料
                MoneyOfThisBill=Integer.parseInt(binding.money.getText().toString());
                //Tag施工中
                TagOfThisBill="default";
                PsOfThisBill=binding.inputPS.getText().toString();
                billdb.insertItem(DateOfThisBill,InOrOutOfThisBill,TagOfThisBill,MoneyOfThisBill,PsOfThisBill);
                //ArrayList<arrayListDef> test=billdb.showItem();
                NavHostFragment.findNavController(AddbillFragment.this)
                        .navigate(R.id.action_AddbillFragment_to_FirstFragment);
            }

        });

    }

    void clickDateText(){
        datePicker=new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String dateFormat="yyyy/MM/dd";
                SimpleDateFormat sdf=new SimpleDateFormat(dateFormat, Locale.TAIWAN);
                binding.date.setText(sdf.format(calendar.getTime()));
                DateOfThisBill=sdf.format(calendar.getTime());
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
