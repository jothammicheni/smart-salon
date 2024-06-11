package com.example.rabbitmanagementsystem.adminTasks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.rabbitmanagementsystem.R;
import com.example.rabbitmanagementsystem.Register;
import com.example.rabbitmanagementsystem.RegisterVet;

public class AdminPanel extends AppCompatActivity {

  Button btnNewfarmer,BtnnewVet,btnAddNewitems;
    LinearLayout viewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

       btnNewfarmer=findViewById(R.id.btnNewfarmer);
       btnAddNewitems=findViewById(R.id.btnNewitems);
        BtnnewVet=findViewById(R.id.btnNewVet);

        BtnnewVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterVet.class);
                startActivity(intent);
            }
        });

        btnAddNewitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddNewProduct.class);
                startActivity(intent);
            }
        });

        btnNewfarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

}