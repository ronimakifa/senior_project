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

/**
 * Activity for setting and managing alarms to remind the user to water their plants.
 * Allows the user to select a time and date, set or cancel alarms, and displays notifications.
 *
 * <p>This activity uses Android's AlarmManager and Notification system to schedule and show reminders.</p>
 *
 * @author Roni Zuckerman
 * @version 1.0
 * @since 2024-06-07
 */
public class add_alarm extends AppCompatActivity {

    /** Button for selecting the alarm time. */
    Button selectTime;
    /** Button for setting the alarm. */
    Button SetAlarm;
    /** Button for canceling the alarm. */
    Button cancelAlarm;
    /** TextView for displaying the selected time. */
    TextView timeTextView;
    /** TextView for displaying the selected date. */
    TextView dateTextView;

    /** Calendar instance for storing the selected date and time. */
    Calendar calendar = Calendar.getInstance();

    /** MaterialTimePicker for picking the alarm time. */
    private MaterialTimePicker picker;
    /** AlarmManager for scheduling alarms. */
    private AlarmManager alarmManager;
    /** PendingIntent for the alarm broadcast. */
    private PendingIntent pendingIntent;
    /** String for internal use (not currently used). */
    private String str;

    /**
     * Called when the activity is starting.
     * Initializes UI components, creates the notification channel, and sets up window insets.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied.
     * @author Roni Zuckerman
     */
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

        // Handle window insets for proper padding (edge-to-edge display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Creates a notification channel for alarm notifications (required for Android O and above).
     * Sets the channel name, description, and importance.
     * @author Roni Zuckerman
     */
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

    /**
     * Handles the click event for selecting the alarm time.
     * Shows the MaterialTimePicker dialog.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void setTime(View view) {
        if(timeTextView == null){
            Toast.makeText(this, "this is null", Toast.LENGTH_SHORT).show();
        }
        showTimePicker();
    }

    /**
     * Displays the MaterialTimePicker dialog for selecting the alarm time.
     * Updates the timeTextView and calendar with the selected time.
     * @author Roni Zuckerman
     */
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

    /**
     * Displays the MaterialDatePicker dialog for selecting the alarm date.
     * Updates the dateTextView and calendar with the selected date.
     * @author Roni Zuckerman
     */
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

    /**
     * Handles the click event for setting the alarm.
     * Calls the method to schedule the alarm.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void setAlarm(View view) {
        setAlarnBtn();
    }

    /**
     * Schedules the alarm using AlarmManager and shows a Toast confirmation.
     * Sets a repeating alarm for the selected time.
     * @author Roni Zuckerman
     */
    private void setAlarnBtn() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the click event for canceling the alarm.
     * Cancels the scheduled alarm and shows a Toast confirmation.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
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

    /**
     * Handles the click event for selecting the alarm date.
     * Shows the MaterialDatePicker dialog.
     *
     * @param view The view that was clicked.
     * @author Roni Zuckerman
     */
    public void setDate(View view) {
        showDatePicker();
    }
};