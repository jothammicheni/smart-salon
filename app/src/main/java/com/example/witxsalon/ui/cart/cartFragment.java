package com.example.witxsalon.ui.cart;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witxsalon.CartAdapter;
import com.example.witxsalon.data.ProductInfo;
import com.example.witxsalon.databinding.FragmentCartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cartFragment extends Fragment {

    private CartViewModel mViewModel;

    private CartAdapter cartAdapter;
    private List<ProductInfo> productInfoList;
    private RecyclerView cartRecyclerView;

    public static cartFragment newInstance() {
        return new cartFragment();
    }
    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        cartRecyclerView=binding.RVcartRecyclerView;
        GridLayoutManager gridLayoutManager=new GridLayoutManager(requireContext(),1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);

        try {
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference cartRef=database.getReference("cartdata");

            cartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productInfoList=new ArrayList<>();

                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                        ProductInfo productInfo=dataSnapshot.getValue(ProductInfo.class);
                         if(productInfo!=null){
                             productInfoList.add(productInfo);
                         }
                    }

                    cartAdapter=new CartAdapter(productInfoList);
                    cartRecyclerView.setAdapter(cartAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("error", String.valueOf(error));
                }
            });

        }
        catch (Exception e){
            Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show();
        }









        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}