package com.example.witxsalon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.witxsalon.data.ProductInfo;
import com.squareup.picasso.Picasso; // Import Picasso library for image loading
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductInfo> productInfoList;

    public ProductAdapter(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductInfo productInfo = productInfoList.get(position);

        // Load product image using Picasso
        Picasso.get().load(productInfo.getImageUrl()).placeholder(R.drawable.ic_menu_camera).into(holder.productImageView);

        holder.tvName.setText(productInfo.getProductName());
        holder.tvDescription.setText(productInfo.getProductDescription());
        holder.tvPrice.setText(productInfo.getProductPrice());
        holder.tvCategory.setText(productInfo.getProductCategory());
    }

    @Override
    public int getItemCount() {
        return productInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;
        private TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            tvName = itemView.findViewById(R.id.TVname);
            tvDescription = itemView.findViewById(R.id.TVdescription);
            tvPrice = itemView.findViewById(R.id.TVprice);
            tvCategory = itemView.findViewById(R.id.TVcategory);
        }
    }
}
