package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class add_plant extends AppCompatActivity {

    private User user;
    private EditText nameEditText;
    private EditText wateringFrequencyEditText;
    private EditText soilTypeEditText;
    private EditText pictureEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        // Initialize the user (replace "userId" with the actual user ID)
        user = new User(FirebaseAuth.getInstance().getUid());

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        wateringFrequencyEditText = findViewById(R.id.wateringFrequencyEditText);
        soilTypeEditText = findViewById(R.id.soilTypeEditText);
        pictureEditText = findViewById(R.id.pictureEditText);
        addButton = findViewById(R.id.addButton);

        // Set click listener for the add button
        addButton.setOnClickListener(this::onClick);
    }

    // Method to handle button click
    public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String wateringFrequency = wateringFrequencyEditText.getText().toString();
        String soilType = soilTypeEditText.getText().toString();
        String picture = pictureEditText.getText().toString();

        plant newPlant = new plant(name, wateringFrequency, soilType, picture);
        user.addPlant(newPlant);


        // Pass the new plant data back to the plants activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newPlant", newPlant);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}