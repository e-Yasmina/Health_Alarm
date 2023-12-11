package com.example.health_alarm_v2;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseUtils {

    // Obtenez une référence à la base de données Firebase
    private static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    // Méthode générique pour ajouter un élément
//    public static <T> void addItem(String collection, T item) {
//        getDatabaseReference().child(collection).push().setValue(item);
//    }
    public static <T> void addItem(String collection, T item , Context context) {
        DatabaseReference databaseReference = getDatabaseReference().child(collection).push();
        if (item instanceof Medicine) {
            // If the item is a Medicine instance, set the generated ID
            ((Medicine) item).setId(databaseReference.getKey());
        }
        databaseReference.setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show a Toast message indicating success
                        Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure, if needed
                        Toast.makeText(context, "Failed to add item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Méthode générique pour mettre à jour un élément
    public static <T> void updateItem(String collection, String itemId, T updatedItem) {
        getDatabaseReference().child(collection).child(itemId).setValue(updatedItem);
    }

    // Méthode générique pour supprimer un élément
    public static void deleteItem(String collection, String itemId) {
        getDatabaseReference().child(collection).child(itemId).removeValue();
    }
    public static <T> void readAllItems(String collection, ValueEventListener valueEventListener) {
        getDatabaseReference().child(collection).addListenerForSingleValueEvent(valueEventListener);
    }



    public static <T> void readAllItems(String collection, final FirebaseCallback<List<T>> callback, Class<T> valueType) {
        getDatabaseReference().child(collection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> itemList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert each data snapshot to the specified object type (e.g., Medicine)
                    T item = snapshot.getValue(valueType);
                    itemList.add(item);
                }
                callback.onSuccess(itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    // Callback interface to handle the result asynchronously
    public interface FirebaseCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}