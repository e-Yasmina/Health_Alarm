package com.example.health_alarm_v2;

import static android.os.Build.*;
import static com.google.protobuf.WireFormat.JavaType.INT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;


public class MainActivity extends AppCompatActivity {
    private static int splash_screen=3000;
    private int STORAGE_PERMISSION_CODE = 1;

    Animation top, bottom;
    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed because of this and that")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Permission for sdk between 23 and 29
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }

                        // Permission storage for sdk 30 or above
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {

                            if(!Environment.isExternalStorageManager()) {

                                try{

                                    Intent intent=new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);

                                    intent.addCategory("android.intent.category.DEFAULT");

                                    intent.setData(Uri.parse(String.format("package: %s", getApplicationContext().getPackageName())));
                                    startActivityIfNeeded(intent, 101);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i=new Intent(MainActivity.this,Login.class);
                                            Pair[] pairs=new Pair[2];
                                            pairs[0]=new Pair<View,String>(iv,"primaryImg");
                                            pairs[1]=new Pair<View,String>(tv,"primaryText");
                                            ActivityOptions optins=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                                            startActivity(i,optins.toBundle());

                                            //finish();
                                        }
                                    },splash_screen);

                                }catch (Exception exception) {

                                    Intent intent = new Intent();

                                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);

                                }
                            }
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        iv=findViewById(R.id.img1);
        tv=findViewById(R.id.text1);

        iv.setAnimation(top);
        tv.setAnimation(bottom);

        // Check if the READ_EXTERNAL_STORAGE permission is granted
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();

        } else {

        }




    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {



        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}