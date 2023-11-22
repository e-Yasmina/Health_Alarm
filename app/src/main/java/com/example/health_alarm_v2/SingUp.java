package com.example.health_alarm_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SingUp extends AppCompatActivity {
    FirebaseFirestore fr;
    AppCompatButton b;
    String un;
    String pw;
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        fr=FirebaseFirestore.getInstance();




        Intent i=getIntent();
        b=findViewById(R.id.singupb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout usernameLayout = findViewById(R.id.username);
                TextInputEditText usernameEditText = usernameLayout.findViewById(R.id.usernametext);
                un = usernameEditText.getText().toString().trim();
                TextInputLayout pwLayout = findViewById(R.id.password);
                TextInputEditText pwEditText = pwLayout.findViewById(R.id.passwordInput);
                pw = pwEditText.getText().toString().trim();
                TextInputLayout emailLayout = findViewById(R.id.email);
                TextInputEditText emailEditText = emailLayout.findViewById(R.id.emailInput);
                e = emailEditText .getText().toString().trim();


                if(un.isEmpty() || pw.isEmpty() || e.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all information needed",Toast.LENGTH_LONG).show();

                }else{
                    Map<String ,Object> user= new HashMap<>();
                    user.put("Email",e);
                    user.put("Login", un);
                    user.put("Password",pw);
                    fr.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failure",Toast.LENGTH_LONG).show();
                        }
                    });


                    i.putExtra("un",un);
                    i.putExtra("pw",pw);
                    i.putExtra("e",e);
                    setResult(RESULT_OK,i);
                    finish();
                }



            }
        });

    }
}