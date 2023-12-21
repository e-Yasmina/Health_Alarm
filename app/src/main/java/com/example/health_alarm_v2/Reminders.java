package com.example.health_alarm_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class Reminders extends AppCompatActivity {
    String medicineId;
    TextView medicine_title;
    ImageView photo;
    Spinner frequencySpinner;
    TimePicker timePicker;
    Button reminder;
  int hour, minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        medicine_title=findViewById(R.id.title);
        photo=findViewById(R.id.image);
        Intent intent = getIntent();
        if(intent!=null){
            medicineId = intent.getStringExtra("MEDICINE_ID");
            FirestoreUtils.readById("Medicines", medicineId, Medicine.class, new OnSuccessListener<Medicine>() {
                @Override
                public void onSuccess(Medicine medicine) {
                    if (medicine != null) {
                        medicine_title.setText(medicine.getName());
                        if (medicine.getPhoto() != null && !medicine.getPhoto().equals("Not available")) {
                            //Uri imageUri = Uri.parse();
                            Uri imageUri = Uri.fromFile(new File(medicine.getPhoto()));
                            Glide.with(Reminders.this)
                                    .load(imageUri)
                                    //.error(R.drawable.img_3) // Set your default image resource in case of error
                                    .into(photo);
                        }


                    } else {
                        Toast.makeText(Reminders.this, "Medicine not found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.print(e);
                }
            });
        }

        frequencySpinner = findViewById(R.id.frequencySpinner);
        timePicker = findViewById(R.id.timePicker);
        reminder = findViewById(R.id.add_alert);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour= hourOfDay;
                minutes = minute;
            }
        });

        String selectedFrequency = frequencySpinner.getSelectedItem().toString();

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer();
                // Create notification channel
                notification();
                Toast.makeText(Reminders.this, "Reminder set for " +hour + ":" + minutes + " every day", Toast.LENGTH_SHORT).show();


            }
        });


        // Set up the spinner with frequency options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.frequency_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);


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
    private void notification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Alarm Reminders";
            String description = "Hey, Wake Up!!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel  = new NotificationChannel("Notify", name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void setTimer() {
        AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minutes);
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(Reminders.this, MyReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(Reminders.this, 0, i, 0);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(Reminders.this, 0, i, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(Reminders.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent);
    }
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "ReminderChannel";
//            CharSequence channelName = "Reminder Channel";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//    }
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



