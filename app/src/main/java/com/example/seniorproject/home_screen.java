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

public class home_screen extends AppCompatActivity {
    Button LOGOUT;
    Button MYGARDEN;
    Button GUIDE;
    Button ALARM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        TextView usernameTextView = findViewById(R.id.textView3);
        usernameTextView.setText("Hello " + username + "!");

        LOGOUT = findViewById(R.id.logout_id);
        MYGARDEN = findViewById(R.id.my_garden_id);
        GUIDE = findViewById(R.id.plant_guide_id);
        ALARM = findViewById(R.id.alarm_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


    }
    public void goto_MYGARDEN(View view) {
        Intent intent = new Intent(this, my_plants.class);
        startActivity(intent);
    }

    public void gotoCreateAlarm(View view) {
        Intent intent = new Intent(this, add_alarm.class);
        startActivity(intent);
    }

    public void goto_guide(View view) {
        Intent intent = new Intent(this, guide.class);
        startActivity(intent);
    }
}