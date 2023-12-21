package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button ib;
    Button opb;
    Button edb;

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

        // Read all documents in the collection
        FirestoreUtils.readCollection("Medicines", Medicine.class,
                userList -> {
                    for (Medicine m : userList) {
                        itemList.add(m);
                        adapter.notifyDataSetChanged();
                        Log.d("FirestoreUtils", "Read document: " + m.toString());
                    }
                    Toast.makeText(getApplicationContext(), "item list filled with "+itemList,Toast.LENGTH_LONG).show();
                },
                e -> Log.e("FirestoreUtils", "Error reading documents: " + e.getMessage()));



        opb=findViewById(R.id.orall_pills);
        opb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreUtils.readByType("Medicines", "oral pills",Medicine.class,
                        userList -> {
                            itemList.clear(); // Clear the existing list before adding new items
                            itemList.addAll(userList); // Add all items of the specified type
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "item list filled with "+itemList,Toast.LENGTH_LONG).show();
                        },
                        e -> Log.e("FirestoreUtils", "Error reading documents: " + e.getMessage()));

            }
        });
        ib=findViewById(R.id.injections);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreUtils.readByType("Medicines", "injections",Medicine.class,
                        userList -> {
                            itemList.clear(); // Clear the existing list before adding new items
                            itemList.addAll(userList); // Add all items of the specified type
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "item list filled with "+itemList,Toast.LENGTH_LONG).show();
                        },
                        e -> Log.e("FirestoreUtils", "Error reading documents: " + e.getMessage()));

            }
        });
        edb=findViewById(R.id.eye_drops);
        edb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreUtils.readByType("Medicines", "eye drops",Medicine.class,
                        userList -> {
                            itemList.clear(); // Clear the existing list before adding new items
                            itemList.addAll(userList); // Add all items of the specified type
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "item list filled with "+itemList,Toast.LENGTH_LONG).show();
                        },
                        e -> Log.e("FirestoreUtils", "Error reading documents: " + e.getMessage()));

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }


}