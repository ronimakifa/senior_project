package com.example.seniorproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class  MainActivity extends AppCompatActivity {

    Button signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




            super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        signup =  findViewById(R.id.new_user);
        login = findViewById(R.id.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }

    public void goto_signup(View view) {
        Intent intent = new Intent (this, sign_up_screen.class);
        startActivity(intent);
    }

    public void goto_login(View view) {
        Intent intent1 = new Intent (this, login_screen.class);
        startActivity(intent1);
    }


}