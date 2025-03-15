package com.example.seniorproject;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class login_screen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String email;
    String password;
    EditText emailField = findViewById(R.id.editTextTextEmailAddress2);
    EditText passwordField = findViewById(R.id.editTextTextPassword2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
       // mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}

