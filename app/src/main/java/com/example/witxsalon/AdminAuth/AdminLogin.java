package com.example.witxsalon.AdminAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.witxsalon.R;
import com.example.witxsalon.adminTasks.AddNewProduct;

public class AdminLogin extends AppCompatActivity {

Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         loginButton=findViewById(R.id.btn_admin_login);

         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d("state","clicked");
                 Intent intent = new Intent(AdminLogin.this, AddNewProduct.class);
                 startActivity(intent);

                 Toast.makeText(AdminLogin.this, "logged in", Toast.LENGTH_SHORT).show();
             }


         });
        }

    }

