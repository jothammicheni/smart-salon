package com.example.rabbitmanagementsystem.adminTasks;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rabbitmanagementsystem.Chats.DisplayUsers;
import com.example.rabbitmanagementsystem.R;
import com.example.rabbitmanagementsystem.data.ProductInfo;
import com.example.rabbitmanagementsystem.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class AddNewProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

 Spinner  categorySpinner;
 Button btnSelectImage,btnUploadProduct;
 ProgressBar progressBar;
 EditText itemName,itemDescription,itemPrice;
 ImageView imageView;
 String[] categories={"FEEDS","HAY","WATERERS","CAGES","MEDICINE"};
 private Uri filePath;
 private ActivityResultLauncher<String>  pickImageLauncher;
 private final int PICK_IMAGE_REQUEST=22;
    FirebaseStorage storage;
    TextView Tvback,Tvlogout;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        //initializing the views
        imageView=findViewById(R.id.productImage);
        progressBar=findViewById(R.id.PBprogress);
       btnSelectImage=findViewById(R.id.btnUploadImage);
       btnUploadProduct=findViewById(R.id.btnSubmitProduct);
        categorySpinner=findViewById(R.id.spinnerProductCategory);
        itemDescription=findViewById(R.id.editTextProductDescription);
        itemName=findViewById(R.id.editTextProductName);
        itemPrice=findViewById(R.id.editTextProductPrice);
        Tvback=findViewById(R.id.TVback);
        Tvlogout=findViewById(R.id.TVLogout);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("itemimages");  // Specify the correct reference for Firebase Storage

        databaseReference = FirebaseDatabase.getInstance().getReference("itemdata");

      //initialize the adapter
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        // setting the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //setting adapter for spinner
        categorySpinner.setAdapter(adapter);
        // on below line we are adding click listener for our spinner
        categorySpinner.setOnItemSelectedListener(this);

        String selection = "Long";

        // on below line we are getting the position of the item by the item name in our adapter.
        int spinnerPosition = adapter.getPosition(selection);

        //setting selection to our spinner
        categorySpinner.setSelection(spinnerPosition);

    //setup image picker
        pickImageLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(),results->{
            if(results!=null){
                imageView.setImageURI(results);
                filePath=results;
                btnUploadProduct.setVisibility(View.VISIBLE);
                btnSelectImage.setVisibility(View.GONE);

            }
            else {
                Toast.makeText(AddNewProduct.this, "No image found", Toast.LENGTH_SHORT).show();

            }

        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUploadProduct.setVisibility(View.GONE);
                btnSelectImage.setVisibility(View.VISIBLE);
            }
        });
        btnSelectImage.setOnClickListener(view->Uploadmage());
        btnUploadProduct.setOnClickListener(View->uploadProductDetails());


        Tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AdminPanel.class);
                startActivity(intent);
            }
        });

        Tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, ""+categories[i]+"selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void Uploadmage(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        //if(user!=null) {
      //  if(user==null) {
            pickImageLauncher.launch("image/*");
//        }else{
//            Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//        }
    }

    public void uploadProductDetails(){

     // check loged in user

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


        //if(user!=null) {
//         if(user==null) {
         if (filePath != null) {
             String productId = UUID.randomUUID().toString().trim();
             String productName = itemName.getText().toString().trim();
             String productDescription = itemDescription.getText().toString().trim();
             String productPrice = itemPrice.getText().toString().trim();
             String productCategory = categorySpinner.getSelectedItem().toString().trim();
             StorageReference imageRef = storageReference.child("itemImages" + productId + ".jpg");

             if (TextUtils.isEmpty(productName)) {
                 Toast.makeText(this, "Enter item name", Toast.LENGTH_SHORT).show();
                 return;
             }
             if (TextUtils.isEmpty(productPrice)) {
                 Toast.makeText(this, "Enter item Price", Toast.LENGTH_SHORT).show();
                 return;
             }
             if (TextUtils.isEmpty(productDescription)) {
                 Toast.makeText(this, "Enter item Description", Toast.LENGTH_SHORT).show();
                 return;
             }
             if (TextUtils.isEmpty(productCategory)) {
                 Toast.makeText(this, "Enter item Category", Toast.LENGTH_SHORT).show();

             }

             progressBar.setVisibility(View.VISIBLE);
             imageRef.putFile(filePath)
                     .addOnSuccessListener(taskSnapshot -> {
                         imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                             ProductInfo productInfo = new ProductInfo(productId, productName, productCategory, productPrice, productDescription, uri.toString());
                             databaseReference.child(productId).setValue(productInfo);

                             //Reset UI
                             ResetUI();

                             Toast.makeText(this, "Product added Successfully", Toast.LENGTH_SHORT).show();
                         });

                     })
                     .addOnFailureListener(e -> {
                         Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                         Log.e("uploadError", "Upload Failed", e);
                     });


         }

//     }
//         else{
//         Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//     }



    }

    private void ResetUI() {
      //  imageView.setImageResource(0);
        btnUploadProduct.setVisibility(View.GONE);
        btnSelectImage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}