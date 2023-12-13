package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Reminders extends AppCompatActivity {
    Spinner frequencySpinner;
    TimePicker timePicker;
    Button reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        frequencySpinner = findViewById(R.id.frequencySpinner);
        timePicker = findViewById(R.id.timePicker);
        reminder = findViewById(R.id.add_alert);

        String selectedFrequency = frequencySpinner.getSelectedItem().toString();

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHour = timePicker.getHour();
                int selectedMinute = timePicker.getMinute();

                // Get an instance of the AlarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                // Create an Intent for the BroadcastReceiver
                Intent intent;
                intent = new Intent(Reminders.this, MyReceiver.class);
                PendingIntent pendingIntent;
                pendingIntent = PendingIntent.getBroadcast(Reminders.this, 0, intent, PendingIntent.FLAG_MUTABLE);

                // Set the time for the alarm
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                calendar.set(Calendar.SECOND, 0);

                // Ensure that the alarm is set for a future time
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                // Set the alarm using AlarmManager
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent);
                //showNotification("Medicine Reminder", "Take your medicine now!");
                // Show a Toast or perform any other actions to indicate that the reminder is set
                Toast.makeText(Reminders.this, "Reminder set for " + selectedHour + ":" + selectedMinute + " every day", Toast.LENGTH_SHORT).show();


            }
        });


        // Set up the spinner with frequency options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.frequency_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);

        // Create notification channel
        createNotificationChannel();

        // Set a default time for the TimePicker (e.g., current time)
        setDefaultTime();

        // Set up a listener for item selection in the frequencySpinner
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected frequency
                handleFrequencySelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void setDefaultTime() {
        // Set a default time for the TimePicker (e.g., current time)
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        timePicker.setHour(currentHour);
        timePicker.setMinute(currentMinute);
    }

    private void handleFrequencySelection(int position) {
        switch (position) {
            case 0: // Every day
                // Schedule daily notification at the selected time
                //scheduleDailyNotification();
                break;
            case 1: // Every week
                // Show an additional UI element for selecting the day of the week
                // Implement this based on your UI design
                // For simplicity, you can use another spinner or a DatePicker
                // and schedule the weekly notification accordingly
                break;
            case 2: // Many times in a day
                // Show an additional UI element for entering multiple times
                // Implement this based on your UI design
                // For simplicity, you can use a dialog or another screen
                // and schedule notifications for each selected time
                break;
        }
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "ReminderChannel";
            CharSequence channelName = "Reminder Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

//    private void scheduleDailyNotification() {
//        int selectedHour = timePicker.getHour();
//        int selectedMinute = timePicker.getMinute();
//
//        // Get an instance of the AlarmManager
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        // Create an Intent for the BroadcastReceiver
//        Intent intent = new Intent(this, MyReceiver.class);
//        PendingIntent pendingIntent;
//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);
//
//        // Set the time for the alarm
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
//        calendar.set(Calendar.MINUTE, selectedMinute);
//        calendar.set(Calendar.SECOND, 0);
//
//        // Ensure that the alarm is set for a future time
//        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//        }
//
//        // Set the alarm using AlarmManager
//        alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY,
//                pendingIntent);
//
//        // Show a Toast or perform any other actions to indicate that the reminder is set
//        Toast.makeText(this, "Reminder set for " + selectedHour + ":" + selectedMinute + " every day", Toast.LENGTH_SHORT).show();
//    }



