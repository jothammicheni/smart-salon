package com.example.witxsalon.adminTasks;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.witxsalon.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddNewProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

 Spinner  categorySpinner;
 Button btnSelectImage,btnUploadProduct;
 ImageView imageView;
 String[] categories={"Long","short","Medium"};
 private Uri filePath;
 private ActivityResultLauncher<String>  pickImageLauncher;
 private final int PICK_IMAGE_REQUEST=22;

   FirebaseStorage storage;
   StorageReference   storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        imageView=findViewById(R.id.productImage);
       btnSelectImage=findViewById(R.id.btnUploadImage);
       btnUploadProduct=findViewById(R.id.btnSubmitProduct);
        categorySpinner=findViewById(R.id.spinnerProductCategory);
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


        // button to uplaod the image

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(
                Color.parseColor("#0F9D58"));
        actionBar.setBackgroundDrawable(colorDrawable);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //select image from gallary
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 pickImageLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(),results->{
                     if(results!=null){
                         imageView.setImageURI(results);
                         btnUploadProduct.setVisibility(View.VISIBLE);
                         btnSelectImage.setVisibility(View.GONE);

                     }
                     else {
                         Toast.makeText(AddNewProduct.this, "No image found", Toast.LENGTH_SHORT).show();

                     }

                 });


                Uploadmage();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUploadProduct.setVisibility(View.GONE);
                btnSelectImage.setVisibility(View.VISIBLE);
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

   pickImageLauncher.launch("image/*");

    }

}