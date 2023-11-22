package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    String recievedun;
    String recievedpw;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tv=findViewById(R.id.recievedtext);

        recievedun=getIntent().getExtras().getString("username");
        recievedpw=getIntent().getExtras().getString("password");
        tv.setText("Welcome"+" "+ recievedun);
    }
}