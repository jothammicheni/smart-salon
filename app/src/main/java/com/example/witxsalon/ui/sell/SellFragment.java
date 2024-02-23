package com.example.witxsalon.ui.sell;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witxsalon.AdminAuth.AdminLogin;
import com.example.witxsalon.R;
import com.example.witxsalon.adminTasks.AddNewProduct;
import com.example.witxsalon.databinding.FragmentHomeBinding;
import com.example.witxsalon.databinding.FragmentSellBinding;
import com.example.witxsalon.ui.home.HomeViewModel;

public class SellFragment extends Fragment {

    private SellViewModel mViewModel;



    public static SellFragment newInstance() {
        return new SellFragment();
    }


    private FragmentSellBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      SellViewModel sellViewModel =new ViewModelProvider(this).get(SellViewModel.class);

        binding = FragmentSellBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final Button  loginBtn=binding.btnLogin;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("state","clicked");
                Intent intent = new Intent(requireContext(), AddNewProduct.class);                startActivity(intent);

                Toast.makeText(requireContext(), "logged in", Toast.LENGTH_SHORT).show();
            }


        });


        final RelativeLayout adminLoginLayout = binding.RLadminlogins;
        sellViewModel.getIsAdminLoginVisible().observe(getViewLifecycleOwner(), isAdminLoginVisible -> {
            if (isAdminLoginVisible) {
                adminLoginLayout.setVisibility(View.VISIBLE);
            } else {
                adminLoginLayout.setVisibility(View.GONE);
            }
        });




   return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}