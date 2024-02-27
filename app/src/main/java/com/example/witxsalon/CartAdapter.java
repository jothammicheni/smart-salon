package com.example.witxsalon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.witxsalon.data.ProductInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private List<ProductInfo> productInfoList;

    public CartAdapter(List<ProductInfo> productInfoList){
     this.productInfoList=productInfoList;
    }




    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_rv_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ProductInfo productInfo=productInfoList.get(position);

        // Load product image using Picasso
        Picasso.get().load(productInfo.getImageUrl()).into(holder.productImageView);

        holder.tvName.setText(productInfo.getProductName());
        holder.tvDescription.setText(productInfo.getProductDescription());
        holder.tvPrice.setText(productInfo.getProductPrice());
        holder.tvCategory.setText(productInfo.getProductCategory());

        //delete CartItem

        holder.btnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=productInfo.getProductId();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("cartdata");

                databaseReference.child(key).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Delete failed"+ e, Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {return productInfoList.size();}


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImageView;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;
        private TextView tvCategory;
      private Button btnDeleteCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            tvName = itemView.findViewById(R.id.TVname);
            tvDescription = itemView.findViewById(R.id.TVdescription);
            tvPrice = itemView.findViewById(R.id.TVprice);
            tvCategory = itemView.findViewById(R.id.TVcategory);
            btnDeleteCart=itemView.findViewById(R.id.btnDel);

        }
    }
}
