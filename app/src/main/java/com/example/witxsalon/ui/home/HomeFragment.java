package com.example.witxsalon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.witxsalon.DisplayItemObjects;
import com.example.witxsalon.ProductAdapter;
import com.example.witxsalon.data.ProductInfo;
import com.example.witxsalon.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    private FragmentHomeBinding binding;

    //initialize the recyclerView

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<ProductInfo> productInfoList;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView productRecyclerView = binding.productRecyclerView;

        // Use GridLayoutManager to create a 2-column grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        productRecyclerView.setLayoutManager(gridLayoutManager);





        // Create a list of ProductReview items

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference productReviewsRef = database.getReference("itemdata");

            productReviewsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    productInfoList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProductInfo productInfo = snapshot.getValue(ProductInfo.class);
                        if (productInfo != null) {
                            productInfoList.add(productInfo);
                        }
                    }

                    productAdapter = new ProductAdapter(productInfoList);
                    productRecyclerView.setAdapter(productAdapter);


                    productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ProductInfo productInfo) {
                            Intent intent=new Intent(requireContext(), DisplayItemObjects.class);
                            intent.putExtra("productInfo",  productInfo);
                            startActivity(intent);

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    Log.e("HomeFragment", "Database error: " + error.getMessage());
                }
            });
        } catch (Exception e) {
            // Handle any exceptions
            Log.e("HomeFragment", "Exception: " + e.getMessage());
            Toast.makeText(requireContext(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
        }



        return root;




    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}