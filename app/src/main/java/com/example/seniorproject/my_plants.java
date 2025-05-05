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

    private static final int ADD_PLANT_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private plantAdapter plantAdapter;
    private User user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        // Initialize the user (replace "userId" with the actual user ID)
        CompletableFuture<User> future = (new User()).loadData(FirebaseAuth.getInstance().getUid());

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPlants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load plants and set adapter
        future.thenAccept(user -> {
            this.user = user;
            loadPlants();
        }).exceptionally(e -> {
            Log.e("LoadUser", "Error loading user data", e);
            return null;
        });
    }

    private void loadPlants() {
        if ( user.getPlants() == null) {
            user.plants = new ArrayList<>();
        }
        // Assuming user.plants is already populated
        List<plant> plantList = user.getPlants();



        plantAdapter = new plantAdapter(this, plantList);
        recyclerView.setAdapter(plantAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PLANT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            plant newPlant = (plant) data.getSerializableExtra("newPlant");
            if (newPlant != null) {
                // Add the new plant to the local list
                user.addPlant(newPlant);

                // Convert the list of plants to a list of maps
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
                db.collection("users").document(user.getuid()).set(Collections.singletonMap("plants", plantList))
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

    // Method to start add_plant activity
    public void startAddPlantActivity(View view) {
        Intent intent = new Intent(this, add_plant.class);
        startActivityForResult(intent, ADD_PLANT_REQUEST_CODE);
    }
}