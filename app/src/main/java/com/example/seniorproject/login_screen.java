package com.example.seniorproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

/**
 * Activity for handling user login.
 * Authenticates the user with Firebase Authentication and retrieves user data from Firestore.
 * On successful login, navigates to the home screen and passes the username.
 *
 * @author Roni Zuckerman
 * @version 1.0
 * @since 2024-06-07
 */
public class login_screen extends AppCompatActivity {

    /** Firebase Authentication instance for handling sign-in. */
    private FirebaseAuth mAuth;
    /** User's email address input. */
    String email;
    /** User's password input. */
    String password;
    /** EditText field for email input. */
    EditText emailField;
    /** EditText field for password input. */
    EditText passwordField;

    /**
     * Called when the activity is starting.
     * Sets up the UI, initializes FirebaseAuth, and handles window insets for edge-to-edge display.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        // Handle window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Handles the login button click event.
     * Collects user input from EditText fields and initiates the login process.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void onLogInclick(View view){
        emailField = findViewById(R.id.editTextTextEmailAddress);
        passwordField = findViewById(R.id.editTextTextPassword);
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        login(email, password);
    }

    /**
     * Authenticates the user with Firebase Authentication using the provided email and password.
     * On success, retrieves the user's username from Firestore and navigates to the home screen.
     * On failure, displays a Toast message.
     *
     * @param email The email address entered by the user.
     * @param password The password entered by the user.
     * @return void
     * @author Roni Zuckerman
     */
    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            // Retrieve user data from Firestore
                            db.collection("Users").document(userId).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            String username = documentSnapshot.getString("username");
                                            // Navigate to home screen and pass username
                                            Intent intent = new Intent(this, home_screen.class);
                                            intent.putExtra("username", username);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Error retrieving user data.", Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        // Authentication failed
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}