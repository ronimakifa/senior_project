package com.example.seniorproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    /** Button for adding the plant. */
    private Button addButton;
    private Button addImageButton;
    private final String SERVER_URL = "gs://senior-project-46729.firebasestorage.app";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private static final int REQUEST_IMAGE_PICK = 103;

    private Uri imageUri;
    private Uri cameraImageUri;




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
        addImageButton = findViewById(R.id.selectImageButton);
        addButton = findViewById(R.id.addButton);

        // Set click listener for the add button
        addButton.setOnClickListener(this::onClick);
        addImageButton.setOnClickListener(this::addImage);
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

        // Create a new plant object


        if(imageUri == null){
            // If no image is selected, set a default image or handle accordingly
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "Please select an image for the plant", Toast.LENGTH_SHORT).show();
        }else{
            CompletableFuture<String> imageUploadFuture = uploadImageToFirebase(imageUri);
            imageUploadFuture.thenAccept((result) -> {
                        plant newPlant = new plant(name, wateringFrequency, soilType, result);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("newPlant", newPlant);
                        resultIntent.putExtra("imageUri", imageUri.toString());
                        setResult(RESULT_OK, resultIntent);
                        finish();
            }
            );

        }

        // Add the new plant to the user's collection

        // Pass the new plant data back to the plants activity

    }

    public void addImage(View view) {
        boolean isValid = true;
        try{
            checkPremission();
        }catch (RuntimeException e){
            isValid = false;
            //make a toast
            Toast.makeText(this, "Please grant permissions to access camera and storage", Toast.LENGTH_SHORT).show();
        }

        if(isValid){
                showImagePickerOptions();
        }

    }


    private void showImagePickerOptions() {
        String[] options = {"Take Photo", "Choose from Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Select Image")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        dispatchTakePictureIntent(); // camera
                    } else {
                        pickImageFromGallery(); // gallery
                    }
                })
                .show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = File.createTempFile(
                        "plant_photo_" + System.currentTimeMillis(), ".jpg", getExternalFilesDir("Pictures"));
                cameraImageUri = FileProvider.getUriForFile(
                        this, getPackageName() + ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, REQUEST_IMAGE_PICK);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imageUri = cameraImageUri; // Use the saved Uri
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
            }
        }
    }

    public CompletableFuture<String> uploadImageToFirebase(@NonNull Uri imageUri) {
        // Create a unique filename under the user's folder
        String fileName = "plants/" + FirebaseAuth.getInstance().getUid() + "/" + System.currentTimeMillis() + ".jpg";
        StorageReference storageRef = FirebaseStorage.getInstance(SERVER_URL).getReference().child(fileName);
        CompletableFuture<String> urlToReturn = new CompletableFuture<>();

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the downloadable URL
                    storageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String downloadUrl = uri.toString();
                                urlToReturn.complete(downloadUrl);// ✅ Return the link here
                            })
                            .addOnFailureListener((Exception e) -> {;
                                urlToReturn.completeExceptionally(e);
                            });
                })
                .addOnFailureListener((Exception e) -> {;
                    urlToReturn.completeExceptionally(e);
                });
    return urlToReturn;
    }


    public void checkPremission() throws RuntimeException{
            String[] permissions = {
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            };

            List<String> neededPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    neededPermissions.add(permission);
                }
            }

            if (!neededPermissions.isEmpty()) {
                requestPermissions(neededPermissions.toArray(new String[0]), PERMISSION_REQUEST_CODE);
            }


    }




 }