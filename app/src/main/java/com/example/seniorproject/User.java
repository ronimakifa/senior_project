package com.example.seniorproject;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class User {

    final static String COLLECTION_NAME = "Users";

    String uid;
    String username;
    String password;
    String email;
    List<plant> plants;

    FirebaseFirestore db;

    public String getuid() {
        return uid;
    }

    public User(String uid) {
        this.uid = uid;
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

            }
        });
    }

    public User(){
        //nothing
    }

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
            }
            future.complete(this);
        });
        return future;
    }

    public User(String uid, String username, String password, String email) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.plants = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    public void addPlant(plant plant) {
        plants.add(plant);
        saveData();
    }

    public void saveData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("email", email);
        data.put("uid", uid);
        data.put("plants", plants);
        db.collection(COLLECTION_NAME).document(uid).update(data);
    }

    public List<plant> getPlants() {
        return plants;
    }
}
