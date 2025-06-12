package com.example.seniorproject;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a user in the application, including their credentials and list of plants.
 * Handles loading and saving user data to Firestore.
 *
 * @author Roni Zuckerman
 */
public class User {

    /** Name of the Firestore collection for users. */
    final static String COLLECTION_NAME = "Users";

    /** Unique user ID. */
    String uid;
    /** Username of the user. */
    String username;
    /** User's password. */
    String password;
    /** User's email address. */
    String email;
    /** List of plants associated with the user. */
    List<plant> plants;

    /** Firestore database instance. */
    FirebaseFirestore db;

    /**
     * Gets the unique user ID.
     *
     * @return the user's UID
     */
    public String getuid() {
        return uid;
    }

    /**
     * Constructs a User and loads user data from Firestore using the given UID.
     * Populates username, password, email, and plants list.
     *
     * @param uid the unique user ID
     */
    public User(String uid) {
        this.uid = uid;
        this.plants = new ArrayList<>(); // Initialize the plants list
        db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_NAME).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                username = task.getResult().getString("username");
                password = task.getResult().getString("password");
                email = task.getResult().getString("email");

                // Fetch and add plants from Firestore
                List<Map> fetchedPlants = (List<Map>)task.getResult().get("plants");
                for(Map m: fetchedPlants){
                    plants.add(new plant(m));
                }
            }
        });
    }

    /**
     * Default constructor for User.
     * Use {@link #loadData(String)} to load user data.
     */
    public User(){
        //nothing
    }

    /**
     * Loads user data asynchronously from Firestore for the given UID.
     *
     * @param uid the unique user ID
     * @return a CompletableFuture that completes with the loaded User object
     */
    public CompletableFuture<User> loadData(String uid){
        this.uid = uid;
        CompletableFuture<User> future = new CompletableFuture<>();
        this.plants = new ArrayList<>(); // Initialize the plants list
        db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_NAME).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                username = task.getResult().getString("username");
                password = task.getResult().getString("password");
                email = task.getResult().getString("email");
                List<Map> fetchedPlants = (List<Map>)task.getResult().get("plants");

                for(Map m: fetchedPlants){
                    plants.add(new plant(m));
                }
            }else{
                Log.d("Firebase", "Failed to load user data: " + task.getException());
                future.completeExceptionally(task.getException());
                return;
            }
            future.complete(this);
        });
        return future;
    }

    /**
     * Constructs a User with all fields initialized.
     *
     * @param uid the unique user ID
     * @param username the username
     * @param password the password
     * @param email the email address
     */
    public User(String uid, String username, String password, String email) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.plants = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Adds a plant to the user's plant list and saves the updated data to Firestore.
     *
     * @param plant the plant to add
     */
    public void addPlant(plant plant) {
        plants.add(plant);
        saveData();
    }

    /**
     * Saves the user's data (username, password, email, uid, and plants) to Firestore.
     */
    public void saveData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("email", email);
        data.put("uid", uid);
        data.put("plants", plants);
        db.collection(COLLECTION_NAME).document(uid).set(data).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
               Log.d("Firebase","User data saved successfully.");
            } else {
                Log.d("Firebase","User data faild to be saved." + task.getException());

            }
        });
    }

    /**
     * Gets the list of plants associated with the user.
     *
     * @return the list of plants
     */
    public List<plant> getPlants() {
        return plants;
    }
}