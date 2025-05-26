package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Handles the user sign-up process, including UI input and Firebase authentication.
 * On successful registration, saves user data and navigates to the home screen.
 *
 * @author Roni Zuckerman
 */
public class sign_up_screen extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * Sets up the UI and handles window insets for edge-to-edge display.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Handles the sign-up button click event.
     * Collects user input from EditText fields and initiates the sign-up process.
     *
     * @param view The view that was clicked.
     */
    public void onSignupClick(View view) {
        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        signUp(username, email, password);
    }

    /**
     * Registers a new user with Firebase Authentication and saves user data to Firestore.
     * On success, navigates to the home screen. On failure, shows a Toast message.
     *
     * @param username The username entered by the user.
     * @param email The email address entered by the user.
     * @param password The password entered by the user.
     */
    public void signUp(String username, String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Create and save the user in Firestore
                        User user = new User(mAuth.getCurrentUser().getUid(), username, password, email);
                        user.saveData();
                        // Navigate to home screen
                        Intent intent = new Intent(this, home_screen.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else {
                        // Show error message if authentication fails
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}