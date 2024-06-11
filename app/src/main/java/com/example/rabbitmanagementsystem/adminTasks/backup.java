//package com.example.rabbitmanagementsystem;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.example.rabbitmanagementsystem.data.ProductInfo;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.squareup.picasso.Picasso;
//public class DisplayItemObjects extends AppCompatActivity {
//
//
//    //define the database reference to add items to cart database
//    DatabaseReference  databaseReference;
//
//
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_item_objects);
//
//
//        ProductInfo productInfo = getIntent().getParcelableExtra("productInfo");
//
//        // Set up views in the activity to display product details
//
//        ImageView productImageView = findViewById(R.id.productImageViewDetail);
//        TextView tvName = findViewById(R.id.TVnameDetail);
//        TextView tvDescription = findViewById(R.id.TVdescriptionDetail);
//        TextView tvPrice = findViewById(R.id.TVpriceDetail);
//        TextView tvCategory = findViewById(R.id.TVcategoryDetail);
//        TextView  tvbackToHome=findViewById(R.id.TVbackToHomePage);
//        TextView tvNavigateToCart=findViewById(R.id.TVnavigatioToCartItems);
//        Button  btnAddToCart=findViewById(R.id.AddTocart);
//
//
//
//        // Load product image using Picasso and other data
//        Picasso.get().load(productInfo.getImageUrl()).into(productImageView);
//        tvName.setText(productInfo.getProductName());
//        tvDescription.setText(productInfo.getProductDescription());
//        tvPrice.setText("Ksh"+""+productInfo.getProductPrice());
//        tvCategory.setText(productInfo.getProductCategory());
//
//        //navigate back to homepage
//
//        tvbackToHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayItemObjects.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        //back home
//        tvNavigateToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        // database refference instance
//
//        databaseReference= FirebaseDatabase.getInstance().getReference("cartdata");
//
//        btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String key=productInfo.getProductId();
//
//                ProductInfo cartInfo=new ProductInfo(productInfo.getProductId(),productInfo.getProductName(),productInfo.getProductCategory(),productInfo.getProductPrice(),productInfo.getProductDescription(),productInfo.getImageUrl());
//
//                databaseReference.child(key).setValue(cartInfo)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(DisplayItemObjects.this, "Item added to cart seccessfully", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(DisplayItemObjects.this, "Add to cart Failed", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//
//            }
//        });
//
//
//
//
//
//    }
//
//    public void addItemTocart(){
//
//
//
//
//    }
//
//
//
//
//};
