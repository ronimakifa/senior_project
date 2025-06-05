package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Home screen activity shown after user login.
 * Displays a greeting with the username and provides navigation to the user's garden,
 * plant guide, and alarm features.
 *
 * @author Roni Zuckerman
 * @version 1.0
 *
 */
public class home_screen extends AppCompatActivity {

    /** Button for logging out (not yet implemented). */
    Button LOGOUT;
    /** Button for navigating to the user's garden (my plants). */
    Button MYGARDEN;
    /** Button for navigating to the plant guide. */
    Button GUIDE;
    /** Button for navigating to the alarm feature. */
    Button ALARM;

    /**
     * Called when the activity is starting.
     * Sets up the UI, displays the username, and initializes navigation buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        // Retrieve the username from the intent and display a greeting
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        TextView usernameTextView = findViewById(R.id.textView3);
        usernameTextView.setText("Hello " + username + "!");

        // Initialize navigation buttons
        LOGOUT = findViewById(R.id.logout_id);
        MYGARDEN = findViewById(R.id.my_garden_id);
        GUIDE = findViewById(R.id.plant_guide_id);
        ALARM = findViewById(R.id.alarm_id);

        // Handle window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Navigates to the user's garden (my plants) screen.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void goto_MYGARDEN(View view) {
        Intent intent = new Intent(this, my_plants.class);
        startActivity(intent);
    }

    /**
     * Navigates to the alarm creation screen.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void gotoCreateAlarm(View view) {
        Intent intent = new Intent(this, add_alarm.class);
        startActivity(intent);
    }

    /**
     * Navigates to the plant guide screen.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void goto_guide(View view) {
        Intent intent = new Intent(this, guide.class);
        startActivity(intent);
    }
}