package com.example.witxsalon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.witxsalon.ProductReview;
//import com.squareup.picasso.Picasso; // Add Picasso library for image loading
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductReview> productReviewList;

    public ProductAdapter(List<ProductReview> productReviewList) {
        this.productReviewList = productReviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductReview productReview = productReviewList.get(position);

        // Load product image using Picasso
     //   Picasso.get().load(productReview.getImageUrl()).placeholder(R.drawable.placeholder_image).into(holder.productImageView);

        // Set other views
        holder.tvName.setText(productReview.getName());
        holder.tvDescription.setText(productReview.getDescription());
        holder.tvPrice.setText(productReview.getPrice());
    }

    @Override
    public int getItemCount() {
        return productReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            tvName = itemView.findViewById(R.id.TVname);
            tvDescription = itemView.findViewById(R.id.TVdescription);
            tvPrice = itemView.findViewById(R.id.TVprice);
        }
    }
}
