package com.example.seniorproject;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Application class for initializing global application state.
 * This class is used to initialize Firebase when the application starts.
 *
 * <p>Extends the Android {@link Application} class and overrides the {@link #onCreate()} method
 * to perform one-time setup, such as initializing Firebase services.</p>
 *
 * @author Roni Zuckerman
 */
public class SeniorProjectApplication extends Application {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects have been created.
     * This method initializes Firebase for the entire application.
     *
     * <p>It is recommended to perform application-wide initializations here.</p>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase for the application context
        FirebaseApp.initializeApp(this);
    }
}