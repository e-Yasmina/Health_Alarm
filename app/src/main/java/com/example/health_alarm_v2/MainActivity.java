package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static int splash_screen=3000;

    Animation top, bottom;
    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        iv=findViewById(R.id.img1);
        tv=findViewById(R.id.text1);

        iv.setAnimation(top);
        tv.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,Medicines_Portal.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(iv,"primaryImg");
                pairs[1]=new Pair<View,String>(tv,"primaryText");
                ActivityOptions optins=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(i,optins.toBundle());

                //finish();
            }
        },splash_screen);
    }
}