package com.example.accounting_app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.accounting_app.databinding.FragmentSingleBillInfoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SingleBillInfoFragment extends Fragment {

    private FragmentSingleBillInfoBinding binding;
    Bundle bundle;
    long billID;
    billDatabase db;
    boolean editionMode;
    //日期選擇相關變數
    DatePickerDialog.OnDateSetListener datePicker;
    Calendar calendar =Calendar.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSingleBillInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //展示資訊
        showInfo();
        //初始化為非編輯模式
        editionMode=false;
        whenCannotEdit();
        //初始化 db
        db=new billDatabase(getContext());

        //回去F1
        binding.backToF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SingleBillInfoFragment.this)
                        .navigate(R.id.action_SingleBillInfoFragment_to_FirstFragment);
            }
        });

        binding.CuteSoudaD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刪除帳單(非編輯模式)
                if(!editionMode) {
                    clickDeleteBill();
                }
                //取消(編輯模式)
                else if(editionMode) {
                    whenCannotEdit();
                    showInfo();
                    editionMode=false;
                }
            }
        });

        binding.CuteSoudaE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //啟用編輯(非編輯模式)
                if(!editionMode) {
                    editionMode = true;
                    whenCanEdit();
                }
                //儲存(編輯模式)
                else if(editionMode) {
                    storeInfoToUpdateBill();
                    whenCannotEdit();
                    editionMode=false;
                }
            }

        });
        //------------------------------------------------------------------------

        //編輯模式------------------------------------------------------------------
        //選擇日期
        binding.InfoDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(editionMode){
                    clickDateText();
                }
            }
        });

        //收支
        binding.Infooutcome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(binding.Infooutcome.isChecked()){
                    binding.Infoincome.setChecked(false);
                }
                else{
                    binding.Infoincome.setChecked(true);
                }
            }
        });

        binding.Infoincome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(binding.Infoincome.isChecked()){
                    binding.Infooutcome.setChecked(false);
                }
                else{
                    binding.Infooutcome.setChecked(true);
                }
            }
        });


        //-------------------------------------------------------------------------




    }
    private void showInfo(){
        bundle = getArguments();
        if(bundle!=null){
            billID=bundle.getLong("Id");
            binding.InfoDate.setText(bundle.getString("Date"));
            if(bundle.getString("InOrOut").equals("Outcome")){
                binding.Infooutcome.setChecked(true);
                binding.Infoincome.setChecked(false);
            }
            else{
                binding.Infooutcome.setChecked(false);
                binding.Infoincome.setChecked(true);
            }
            binding.Infotag.setText(bundle.getString("Tag"));
            binding.Infomoney.setText(String.valueOf(bundle.getDouble("Money")));
            binding.InfoPs.setText(bundle.getString("Ps"));
        }
    }

    private void clickDeleteBill(){
        db.deleteItem(billID);
        NavHostFragment.findNavController(SingleBillInfoFragment.this)
                .navigate(R.id.action_SingleBillInfoFragment_to_FirstFragment);
    }
    private void whenCannotEdit(){
        //不可編輯，可以複製
        binding.InfoDate.setFocusable(false);
        //不響應任何事件+不可點擊
        //binding.InfoDate.setEnabled(false);
        //不響應但可點擊
        //binding.InfoDate.setKeyListener(null);

        binding.Infotag.setFocusable(false);
        binding.Infomoney.setFocusable(false);
        binding.InfoPs.setFocusable(false);
        binding.Infoincome.setClickable(false);
        binding.Infooutcome.setClickable(false);
        binding.InfoEdit.setText("編輯");
        binding.InfoDelete.setText("刪除");
        if(binding.Infooutcome.isClickable()){
            Log.d("SingleBill","Clickable");
        }
    }

    private void whenCanEdit(){
        binding.InfoDate.setFocusable(true);

        binding.Infotag.setFocusable(true);
        binding.Infotag.setFocusableInTouchMode(true);

        binding.Infomoney.setFocusable(true);
        binding.Infomoney.setFocusableInTouchMode(true);

        binding.InfoPs.setFocusable(true);
        binding.InfoPs.setFocusableInTouchMode(true);

        binding.Infooutcome.setClickable(true);
        binding.Infoincome.setClickable(true);
        binding.InfoEdit.setText("儲存");
        binding.InfoDelete.setText("取消");

    }

    private void storeInfoToUpdateBill(){
        String newDate=binding.InfoDate.getText().toString();
        //收支
        String newInorOut=bundle.getString("InOrOut");
        if(binding.Infooutcome.isChecked()){
            newInorOut="Outcome";
        }
        else if(binding.Infoincome.isChecked()){
            newInorOut="Income";
        }
        String newTag=binding.Infotag.getText().toString();
        double newMoney=Double.parseDouble(binding.Infomoney.getText().toString());
        String newPs=binding.InfoPs.getText().toString();
        db.updateItem(billID,newDate,newInorOut,newTag,newMoney,newPs);//Bug
    }

    private void clickDateText(){
        datePicker=new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String dateFormat="yyyy/MM/dd";
                SimpleDateFormat sdf=new SimpleDateFormat(dateFormat, Locale.TAIWAN);
                binding.InfoDate.setText(sdf.format(calendar.getTime()));
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

//Bug
//初始收支按鈕還是可以按
//待辦事項
// 加入日期/金額輸入不是日期/數字的異常處理
