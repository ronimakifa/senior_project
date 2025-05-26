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

/**
 * MainActivity is the entry point of the application.
 * It provides navigation to the sign up and login screens.
 * Handles UI initialization and window insets for edge-to-edge display.
 *
 * @author Roni Zuckerman
 * @version 1.0
 * 
 */
public class MainActivity extends AppCompatActivity {

    /** Button for navigating to the sign up screen. */
    Button signup;

    /** Button for navigating to the login screen. */
    Button login;

    /**
     * Called when the activity is starting.
     * Sets up the UI, initializes buttons, and handles window insets.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display for modern UI
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize buttons for navigation
        signup = findViewById(R.id.new_user);
        login = findViewById(R.id.login);

        // Handle window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Navigates to the sign up screen when the sign up button is clicked.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void goto_signup(View view) {
        // Create intent to start the sign up activity
        Intent intent = new Intent(this, sign_up_screen.class);
        startActivity(intent);
    }

    /**
     * Navigates to the login screen when the login button is clicked.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void goto_login(View view) {
        // Create intent to start the login activity
        Intent intent1 = new Intent(this, login_screen.class);
        startActivity(intent1);
    }
}