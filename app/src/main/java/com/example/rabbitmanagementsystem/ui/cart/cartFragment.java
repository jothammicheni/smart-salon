package com.example.witxsalon.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.witxsalon.CartAdapter;
import com.example.witxsalon.MpeseIntegration.MpesaConfig;
import com.example.witxsalon.MpeseIntegration.MpesaService;
import com.example.witxsalon.data.ProductInfo;
import com.example.witxsalon.databinding.FragmentCartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cartFragment extends Fragment {

    private CartViewModel mViewModel;
    private FragmentCartBinding binding;

    private CartAdapter cartAdapter;
    private List<ProductInfo> productInfoList;
    private RecyclerView cartRecyclerView;

    private TextView TVtotalcost, TVcostDesc, TvcartDesc;
    private Button btnMpesa;

    public static cartFragment newInstance() {
        return new cartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TVtotalcost = binding.TVtotalcost;
        TVcostDesc = binding.TVcostDesc;
        TvcartDesc = binding.TVcartDesc;
        btnMpesa = binding.btnMpesa;

        cartRecyclerView = binding.RVcartRecyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference cartRef = database.getReference("cartdata");

            cartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productInfoList = new ArrayList<>();
                    double totalCost = 0.0;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ProductInfo productInfo = dataSnapshot.getValue(ProductInfo.class);
                        if (productInfo != null) {
                            productInfoList.add(productInfo);
                            totalCost += Double.parseDouble(productInfo.getProductPrice());
                        }
                    }

                    cartAdapter = new CartAdapter(productInfoList);
                    cartRecyclerView.setAdapter(cartAdapter);

                    // Update total cost
                    TVtotalcost.setText(String.format("Ksh %.2f", totalCost));
                    TVtotalcost.setVisibility(View.VISIBLE);
                    TVcostDesc.setVisibility(View.VISIBLE);
                    TvcartDesc.setVisibility(View.VISIBLE);
                    btnMpesa.setVisibility(View.VISIBLE);

                    // Set click listener for Mpesa button
                    double finalTotalCost = totalCost;
                    btnMpesa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String confirmationMessage = String.format("Confirm Payment of goods worth Ksh %.2f to Witx salons", finalTotalCost);

                            // Show AlertDialog for payment confirmation
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                            dialogBuilder.setTitle("Mpesa Payment Confirmation")
                                    .setMessage(confirmationMessage)
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Call method to generate access token and initiate payment
                                            //generateAccessTokenAndInitiatePayment(finalTotalCost);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Firebase Error", error.getMessage());
                    Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
            Log.e("Firebase Exception", e.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to generate access token and initiate payment

}
