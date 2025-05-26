package com.example.seniorproject;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.seniorproject.databinding.ActivityMainBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class add_alarm extends AppCompatActivity {
    Button selectTime;
    Button SetAlarm;
    Button cancelAlarm;
    TextView timeTextView;
    TextView dateTextView;

    Calendar calendar = Calendar.getInstance();



    private MaterialTimePicker picker;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        SetAlarm = findViewById(R.id.set_alarm_id2);
        cancelAlarm = findViewById(R.id.cancel_alarm_id3);
        selectTime = findViewById(R.id.selectTime);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_alarm);
        timeTextView = (TextView) findViewById(R.id.time_id);
        dateTextView = (TextView) findViewById(R.id.date_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AlarmNotificationChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void setTime(View view) {
        if(timeTextView == null){
            Toast.makeText(this, "this is null", Toast.LENGTH_SHORT).show();
        }
        showTimePicker();

    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(), "notifyLemubit");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(picker.getHour() > 12){
                     timeTextView.setText(String.format("%02d:%02d PM", picker.getHour(), picker.getMinute()));

            }else{
                     timeTextView.setText(String.format("%02d:%02d AM", picker.getHour(), picker.getMinute()));
                 }
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }

        });
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Alarm Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        datePicker.show(getSupportFragmentManager(), "notifyLemubitDate");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            calendar.setTimeInMillis(selection);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(calendar.HOUR, hour);
            calendar.set(calendar.MINUTE, minute);
            dateTextView.setText(String.format("%02d/%02d/%04d", day, month, year));
        });
    }


    public void setAlarm(View view) {
        setAlarnBtn();


        }

    private void setAlarnBtn() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(View view) {
        Intent intent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if(alarmManager ==  null){
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this,"Alarm is canceled", Toast.LENGTH_SHORT).show();
    }

    public void setDate(View view) {
        showDatePicker();
    }
};
