package com.example.witxsalon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.witxsalon.databinding.ActivityRegisterBinding;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class Register extends AppCompatActivity {
    TextView backToLogin;
    EditText editName, editPassword, editEmail;
    Button btnRegister;
    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize DatabaseReference (replace "users" with your desired path)
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Set components
        btnRegister = findViewById(R.id.btn_signup);
        backToLogin = findViewById(R.id.back_to_login);
        editEmail = findViewById(R.id.email);
        editName = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    signupNewUser(email, password, name);
                } else {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
    }

    private void signupNewUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User registered successfully, now add user details to the database
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            @SuppressLint("RestrictedApi") User user = new User(email);

                            databaseReference.child(userId).setValue(user);

                            // Move to the login activity
                          //  startActivity(new Intent(Register.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
