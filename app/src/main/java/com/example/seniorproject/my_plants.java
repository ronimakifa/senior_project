/**
 * Activity for displaying and managing the user's list of plants.
 * Loads plant data from Firestore, displays it in a RecyclerView, and allows adding new plants.
 * Handles the result from the add plant activity and updates Firestore accordingly.
 *
 * @author Roni Zuckerman
 */
package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class my_plants extends AppCompatActivity {

    /** Request code for starting the add plant activity. */
    private static final int ADD_PLANT_REQUEST_CODE = 1;

    /** RecyclerView for displaying the list of plants. */
    private RecyclerView recyclerView;
    /** Adapter for binding plant data to the RecyclerView. */
    private plantAdapter plantAdapter;
    /** The current user whose plants are displayed. */
    private User user;
    /** Firestore database instance. */
    private FirebaseFirestore db;

    /**
     * Called when the activity is starting.
     * Loads user data, initializes RecyclerView, and sets up the plant list.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        // Load user data asynchronously
        CompletableFuture<User> future = (new User()).loadData(FirebaseAuth.getInstance().getUid());

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPlants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load plants and set adapter when user data is ready
        future.thenAccept(user -> {
            this.user = user;
            loadPlants();
        }).exceptionally(e -> {
            Log.e("LoadUser", "Error loading user data", e);
            return null;
        });
    }

    /**
     * Loads the user's plants into the RecyclerView.
     * Initializes the adapter with the user's plant list.
     */
    private void loadPlants() {
        if (user.getPlants() == null) {
            user.plants = new ArrayList<>();
        }
        List<plant> plantList = user.getPlants();
        plantAdapter = new plantAdapter(this, plantList);
        recyclerView.setAdapter(plantAdapter);
    }

    /**
     * Handles the result from the add plant activity.
     * Adds the new plant to the user's list and updates Firestore.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult().
     * @param resultCode The integer result code returned by the child activity.
     * @param data An Intent, which can return result data to the caller.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PLANT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            plant newPlant = (plant) data.getSerializableExtra("newPlant");
            if (newPlant != null) {
                // Add the new plant to the local list
                user.addPlant(newPlant);

                // Convert the list of plants to a list of maps for Firestore
                List<Map<String, Object>> plantList = new ArrayList<>();
                for (plant p : user.plants) {
                    Map<String, Object> plantMap = new HashMap<>();
                    plantMap.put("name", p.getName());
                    plantMap.put("wateringFrequency", p.getWateringFrequency());
                    plantMap.put("soilType", p.getSoilType());
                    plantMap.put("picture", p.getPicture());
                    plantList.add(plantMap);
                }

                // Save the list to Firestore
                db.collection("Users").document(user.getuid()).set(Collections.singletonMap("plants", plantList))
                        .addOnSuccessListener(aVoid -> {
                            // Update the RecyclerView
                            plantAdapter.notifyItemInserted(user.plants.size() - 1);
                            Log.d("AddPlant", "Plants saved successfully");
                        })
                        .addOnFailureListener(e -> {
                            // Handle the error
                            Log.e("AddPlant", "Error saving plants", e);
                        });
            }
        }
    }

    /**
     * Starts the add plant activity to allow the user to add a new plant.
     *
     * @param view The view that was clicked.
     */
    public void startAddPlantActivity(View view) {
        Intent intent = new Intent(this, add_plant.class);
        startActivityForResult(intent, ADD_PLANT_REQUEST_CODE);
    }
}