
package com.example.health_alarm_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Login extends AppCompatActivity {
    FirebaseFirestore fr;
    String un;
    TextInputLayout un1;
    String pw;
    AppCompatButton b;
    Button bf;
    Button bsu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED,WindowManager.LayoutParams.FLAGS_CHANGED);
        setContentView(R.layout.activity_login);

        fr=FirebaseFirestore.getInstance();
        CollectionReference c=fr.collection("Users");

        b=findViewById(R.id.loginb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout usernameLayout = findViewById(R.id.username);
                TextInputEditText usernameEditText = usernameLayout.findViewById(R.id.usernametext);
                un = usernameEditText.getText().toString().trim();
                TextInputLayout pwLayout = findViewById(R.id.password);
                TextInputEditText pwEditText = pwLayout.findViewById(R.id.passwordInput);
                pw = pwEditText.getText().toString().trim();

                Query query =c
                        .whereEqualTo("Login", un )
                        .whereEqualTo("Password", pw);

                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Check if any documents match the query
                        if (!task.getResult().isEmpty()) {
                            // User found, proceed with login logic
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            Intent i1= new Intent(Login.this, Dashboard.class);

                            i1.putExtra("username",un);
                            i1.putExtra("password",pw);
                            startActivity(i1);
                            finish();
                            //User user = document.toObject(User.class);
//                            String userId = document.getId();
//                            String userEmail = document.getString("email");



                        } else {
                            // No matching user found
                            Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle errors
                        Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        bf= findViewById(R.id.forgtedPw);
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bsu=findViewById(R.id.newuser);
        bsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sui=new Intent(Login.this,SingUp.class);
                startActivityForResult(sui,1);
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                un= data.getStringExtra("un");
                pw=data.getStringExtra("pw");

                TextInputLayout usernameLayout = findViewById(R.id.username);
                TextInputEditText usernameEditText = usernameLayout.findViewById(R.id.usernametext);

                usernameEditText.setText(un);
                TextInputLayout pwLayout = findViewById(R.id.password);
                TextInputEditText pwEditText = pwLayout.findViewById(R.id.passwordInput);

                pwEditText.setText(pw);

            }
        }
    }
}