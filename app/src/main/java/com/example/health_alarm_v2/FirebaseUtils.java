package com.example.health_alarm_v2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseUtils {

    // Obtenez une référence à la base de données Firebase
    private static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    // Méthode générique pour ajouter un élément
    public static <T> void addItem(String collection, T item) {
        getDatabaseReference().child(collection).push().setValue(item);
    }

    // Méthode générique pour mettre à jour un élément
    public static <T> void updateItem(String collection, String itemId, T updatedItem) {
        getDatabaseReference().child(collection).child(itemId).setValue(updatedItem);
    }

    // Méthode générique pour supprimer un élément
    public static void deleteItem(String collection, String itemId) {
        getDatabaseReference().child(collection).child(itemId).removeValue();
    }
}