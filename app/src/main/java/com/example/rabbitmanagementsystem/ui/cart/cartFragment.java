package com.example.rabbitmanagementsystem.ui.cart;

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

import com.example.rabbitmanagementsystem.CartAdapter;
import com.example.rabbitmanagementsystem.R;
import com.example.rabbitmanagementsystem.data.ProductInfo;
import com.example.rabbitmanagementsystem.databinding.FragmentCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cartFragment extends Fragment {

    private CartViewModel mViewModel;
    private FragmentCartBinding binding;

    private CartAdapter cartAdapter;
    private List<ProductInfo> productInfoList;
    private RecyclerView cartRecyclerView;

    private TextView TVtotalcost, TVcostDesc, TvcartDesc;
    private Button btnMpesa;

    private FirebaseAuth mAuth;

    public static cartFragment newInstance() {
        return new cartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        TVtotalcost = binding.TVtotalcost;
        TVcostDesc = binding.TVcostDesc;
        TvcartDesc = binding.TVcartDesc;
        btnMpesa = binding.btnMpesa;

        cartRecyclerView = binding.RVcartRecyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);

        // Get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                // Use email as the key in Firebase
                String key = userEmail.replace(".", "_");

                try {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference cartRef = database.getReference("cartdata").child(key);

                    cartRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (isAdded()) { // Check if fragment is attached to an activity
                                productInfoList = new ArrayList<>();
                                double totalCost = 0.0;

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    ProductInfo productInfo = dataSnapshot.getValue(ProductInfo.class);
                                    if (productInfo != null) {
                                        productInfoList.add(productInfo);
                                        totalCost += Double.parseDouble(productInfo.getProductPrice());
                                    }
                                }

                                // Update RecyclerView
                                cartAdapter = new CartAdapter(requireContext(), productInfoList);
                                cartRecyclerView.setAdapter(cartAdapter);

                                // Update total cost
                                updateTotalCost(totalCost);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Firebase Error", error.getMessage());
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase Exception", e.getMessage());
                    }
                }
            }
        }

        return root;
    }

    // Method to update total cost displayed
    private void updateTotalCost(double totalCost) {
        TVtotalcost.setText(String.format("Ksh %.2f", totalCost));
        TVtotalcost.setVisibility(View.VISIBLE);
        TVcostDesc.setVisibility(View.VISIBLE);
        TvcartDesc.setVisibility(View.VISIBLE);
        btnMpesa.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
