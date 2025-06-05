package com.example.seniorproject;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

/**
 * Activity that provides a tabbed guide for plant care.
 * Hosts three fragments: illness guide, fertilizing guide, and DIY guide.
 * Handles tab selection and fragment switching.
 *
 * @author Roni Zuckerman
 * @version 1.0
 * @since 2024-06-07
 */
public class guide extends AppCompatActivity {
    /** FrameLayout that hosts the currently selected guide fragment. */
    FrameLayout frameLayout;
    /** TabLayout for switching between guide sections. */
    TabLayout tabLayout;

    /**
     * Called when the activity is starting.
     * Sets up the UI, initializes the tab layout and frame layout, and handles fragment switching.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);

        // Initialize the frame layout and tab layout
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Show the illness guide fragment by default
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new illness_guide())
                .addToBackStack(null)
                .commit();

        // Set up tab selection listener to switch fragments
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /**
             * Called when a tab enters the selected state.
             * Switches the displayed fragment based on the selected tab.
             *
             * @param tab The tab that was selected.
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new illness_guide();
                        break;
                    case 1:
                        fragment = new fertilizing_guide();
                        break;
                    case 2:
                        fragment = new DIY_guide();
                        break;
                }
                // Replace the current fragment with the selected one
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }

            /**
             * Called when a tab exits the selected state.
             * No action is taken here.
             *
             * @param tab The tab that was unselected.
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselection if needed
            }

            /**
             * Called when a tab that is already selected is chosen again by the user.
             * No action is taken here.
             *
             * @param tab The tab that was reselected.
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        // Handle window insets for proper padding (edge-to-edge display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}