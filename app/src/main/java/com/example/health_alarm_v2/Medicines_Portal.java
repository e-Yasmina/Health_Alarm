package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Medicines_Portal extends AppCompatActivity {
    ImageView add_medicine;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter1;
    CustomAdapter adapter;
    List<Medicine> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_portal);
        add_medicine=findViewById(R.id.add_medicine);
        add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Medicines_Portal.this, Add_Medicine.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Read all documents in the collection
        FirestoreUtils.readCollection("Medicines", Medicine.class,
                userList -> {
                    for (Medicine m : userList) {
                        itemList.add(m);
                        Log.d("FirestoreUtils", "Read document: " + m.toString());
                    }
                },
                e -> Log.e("FirestoreUtils", "Error reading documents: " + e.getMessage()));

        adapter = new CustomAdapter(itemList);
        recyclerView.setAdapter(adapter);
//        FirebaseUtils.readAllItems("Medicines", new FirebaseUtils.FirebaseCallback<List<Medicine>>() {
//            @Override
//            public void onSuccess(List<Medicine> result) {
//
//                itemList.addAll(result);
//                adapter.notifyDataSetChanged();
//                for (Medicine medicine : result) {
//                    Toast.makeText(getApplicationContext(), medicine.getName(),Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(getApplicationContext(), "Read Operation failed",Toast.LENGTH_LONG).show();
//
//            }
//        }, Medicine.class);

    }
}