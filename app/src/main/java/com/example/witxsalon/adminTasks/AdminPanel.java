package com.example.witxsalon.adminTasks;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.witxsalon.databinding.ActivityAdminPanelBinding;

import com.example.witxsalon.R;

public class AdminPanel extends AppCompatActivity {

   LinearLayout addNewItem;
    LinearLayout viewAppointments;
    LinearLayout updateItems;
    LinearLayout viewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        addNewItem=findViewById(R.id.LLaddNewItem);

        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddNewProduct.class);
                startActivity(intent);
            }
        });
    }

}