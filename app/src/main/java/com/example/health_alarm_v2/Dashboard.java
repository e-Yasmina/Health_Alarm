package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    String recievedun;
    String recievedpw;
    TextView tv;
    ImageView medicineImg;
    TextView  medicinet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        medicineImg= findViewById(R.id.medcines_portal_img);
        medicinet=findViewById(R.id.medcines_portal_text);
        medicineImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the behavior when the ImageView is clicked
                Intent intent = new Intent(Dashboard.this, Medicines_Portal.class);
                startActivity(intent);
            }
        });
        medicinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the behavior when the ImageView is clicked
                Intent intent = new Intent(Dashboard.this, Medicines_Portal.class);
                startActivity(intent);
            }
        });


//        tv=findViewById(R.id.recievedtext);
//
//        recievedun=getIntent().getExtras().getString("username");
//        recievedpw=getIntent().getExtras().getString("password");
//        tv.setText("Welcome"+" "+ recievedun);
    }
}