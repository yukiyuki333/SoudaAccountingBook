package com.example.accounting_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.accounting_app.databinding.FragmentSingleBillInfoBinding;

public class SingleBillInfoFragment extends Fragment {

    private FragmentSingleBillInfoBinding binding;
    Bundle bundle;
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
        //回去F1
        binding.backToF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SingleBillInfoFragment.this)
                        .navigate(R.id.action_SingleBillInfoFragment_to_FirstFragment);
            }
        });




    }
    private void showInfo(){
        bundle = getArguments();
        if(bundle!=null){
            long billID=bundle.getLong("Id");
            binding.InfoDate.setText(bundle.getString("Date"));
            binding.Infoinorout.setText(bundle.getString("InOrOut"));
            binding.Infotag.setText(bundle.getString("Tag"));
            binding.Infomoney.setText(String.valueOf(bundle.getDouble("Money")));
            binding.InfoPs.setText(bundle.getString("Ps"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}