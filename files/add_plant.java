package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for adding a new plant to the user's collection.
 * Collects plant details from user input and returns the new plant to the calling activity.
 *
 * <p>This activity allows the user to enter the plant's name, watering frequency, soil type, and picture URL/path.
 * When the add button is clicked, the plant is created and passed back to the previous activity.</p>
 *
 * @author Roni Zuckerman
 * @version 1.0
 * @since 2024-06-07
 */
public class add_plant extends AppCompatActivity {

    /** The user object representing the current user. */
    private User user;
    /** EditText for entering the plant's name. */
    private EditText nameEditText;
    /** EditText for entering the plant's watering frequency. */
    private EditText wateringFrequencyEditText;
    /** EditText for entering the plant's soil type. */
    private EditText soilTypeEditText;
    /** EditText for entering the plant's picture URL or path. */
    private EditText pictureEditText;
    /** Button for adding the plant. */
    private Button addButton;

    /**
     * Called when the activity is starting.
     * Initializes the UI components and sets up the add button click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     * @author Roni Zuckerman
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        // Initialize the user with the current Firebase user ID
        user = new User(FirebaseAuth.getInstance().getUid());

        // Initialize views for plant details input
        nameEditText = findViewById(R.id.nameEditText);
        wateringFrequencyEditText = findViewById(R.id.wateringFrequencyEditText);
        soilTypeEditText = findViewById(R.id.soilTypeEditText);
        pictureEditText = findViewById(R.id.pictureEditText);
        addButton = findViewById(R.id.addButton);

        // Set click listener for the add button
        addButton.setOnClickListener(this::onClick);
    }

    /**
     * Handles the add button click event.
     * Collects input, creates a new plant, adds it to the user, and returns it to the calling activity.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void onClick(View view) {
        // Get plant details from input fields
        String name = nameEditText.getText().toString();
        String wateringFrequency = wateringFrequencyEditText.getText().toString();
        String soilType = soilTypeEditText.getText().toString();
        String picture = pictureEditText.getText().toString();

        // Create a new plant object
        plant newPlant = new plant(name, wateringFrequency, soilType, picture);

        // Add the new plant to the user's collection
        user.addPlant(newPlant);

        // Pass the new plant data back to the plants activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newPlant", newPlant);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}