package com.example.rabbitmanagementsystem.ui.home;

import android.content.Intent;
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

import com.example.rabbitmanagementsystem.DisplayItemObjects;
import com.example.rabbitmanagementsystem.ProductAdapter;
import com.example.rabbitmanagementsystem.R;
import com.example.rabbitmanagementsystem.data.ProductInfo;
import com.example.rabbitmanagementsystem.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private HomeViewModel mViewModel;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private FragmentHomeBinding binding;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<ProductInfo> productInfoList = new ArrayList<>(); // Initialize with an empty list
    private List<ProductInfo> filteredProductInfoList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productRecyclerView = binding.productRecyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        productRecyclerView.setLayoutManager(gridLayoutManager);

        Button btnAll = root.findViewById(R.id.btnall);
        Button btnFeeds = root.findViewById(R.id.btnfeeds);
        Button btnHay = root.findViewById(R.id.btnhay);
        Button btnWaterers = root.findViewById(R.id.btnwaterers);
        Button btnCages = root.findViewById(R.id.btncages);
        Button btnMedicine = root.findViewById(R.id.btnmedicine);
        TextView TVproduct=root.findViewById(R.id.TVproducts);

        // Set click listeners for each button


        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("ALL");
            }
        });

        btnFeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("FEEDS");
            }
        });

        btnHay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("HAY");
            }
        });

        btnWaterers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("WATERERS");
            }
        });

        btnCages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("CAGES");
            }
        });

        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProductsByCategory("MEDICINE");
            }
        });

        if(Category=="ALL"||Category==""){
            TVproduct.setText("Available products ");
        }else{
            TVproduct.setText("Available "+Category);
        }


        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference productReviewsRef = database.getReference("itemdata");

            productReviewsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    productInfoList.clear(); // Clear the list before adding new data
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProductInfo productInfo = snapshot.getValue(ProductInfo.class);
                        if (productInfo != null) {
                            productInfoList.add(productInfo);
                        }
                    }

                    // Initially display all products
                    filterProductsByCategory("ALL");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("HomeFragment", "Database error: " + error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("HomeFragment", "Exception: " + e.getMessage());
            Toast.makeText(requireContext(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    public String Category="";
    private void filterProductsByCategory(String category) {
         Category=category;
        if (productInfoList == null) {
            productInfoList = new ArrayList<>(); // Ensure the list is initialized
        }

        if (category.equals("ALL")) {
            filteredProductInfoList = new ArrayList<>(productInfoList);
        } else {
            filteredProductInfoList = productInfoList.stream()
                    .filter(product -> product.getProductCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());


        }

        productAdapter = new ProductAdapter(filteredProductInfoList);
        productRecyclerView.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductInfo productInfo) {
                Intent intent = new Intent(requireContext(), DisplayItemObjects.class);
                intent.putExtra("productInfo", productInfo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
