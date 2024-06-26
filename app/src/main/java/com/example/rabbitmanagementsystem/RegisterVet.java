package com.example.rabbitmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rabbitmanagementsystem.Chats.DisplayUsers;
import com.example.rabbitmanagementsystem.adminTasks.AdminPanel;
import com.example.rabbitmanagementsystem.data.UserDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterVet extends AppCompatActivity {
    EditText editName, editEmail, editPassword, editPhone,editCity;
    Button btnRegister;
    TextView Tvback,Tvlogout;
    ProgressBar PBprogress;
    RadioButton radioFarmer, radioVetOfficer;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        btnRegister = findViewById(R.id.btnSignUp);
        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editCity=findViewById(R.id.editCity);
        PBprogress = findViewById(R.id.progress);
        radioFarmer = findViewById(R.id.radioFarmer);
        radioVetOfficer = findViewById(R.id.radioVetOfficer);
        Tvback=findViewById(R.id.TVback);
        Tvlogout=findViewById(R.id.TVLogout);


        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("userDetails");

        // Register button click listener
        btnRegister.setOnClickListener(v -> RegisterNewUser());


        Tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getApplicationContext(), AdminPanel.class);
                startActivity(intent);
            }
        });

        Tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }

    public void RegisterNewUser() {
        String username = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String city=editCity.getText().toString().trim();
        // String userCategory = radioFarmer.isChecked() ? "Farmer" : "Veterinary Officer";
        String userCategory="Veterinary Officer";
        // Validate phone number format
        String regex = "(07|01)\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);

        if (username.isEmpty()) {
            editName.setError("Enter a valid name");
            editName.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email");
            editEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editPhone.setError("Phone number can't be empty");
            editPhone.requestFocus();
            return;
        }
        if (city.isEmpty()) {
            editCity.setError("Phone number can't be empty");
            editCity.requestFocus();
            return;
        }
        if (phone.length() != 10 || !matcher.matches()) {
            editPhone.setError("Enter a valid 10-digit phone number starting with 07 or 01");
            editPhone.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            editPassword.setError("Enter at least 6 characters");
            editPassword.requestFocus();
            return;
        }

        PBprogress.setVisibility(View.VISIBLE);

        // Create user with email and password using Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Successfully created user, now store user details in Firebase Realtime Database
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference userRef = databaseReference.child(userId);
                        UserDetails user = new UserDetails(username, email, password,phone, userCategory,city);
                        userRef.setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterVet.this, "User registered", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                        startActivity(intent);
                                        PBprogress.setVisibility(View.GONE);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterVet.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        PBprogress.setVisibility(View.GONE);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterVet.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        PBprogress.setVisibility(View.GONE);
                    }
                });
    }
}
