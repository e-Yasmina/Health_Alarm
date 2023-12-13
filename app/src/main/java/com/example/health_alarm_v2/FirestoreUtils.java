package com.example.health_alarm_v2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirestoreUtils {

    private static FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();

//    public static void add(String collectionName, Object dataObject, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
//        firestoreInstance.collection(collectionName)
//                .add(dataObject)
//                .addOnSuccessListener(onSuccessListener)
//                .addOnFailureListener(onFailureListener);
//    }
    public static void add(String collectionName, Object dataObject,  OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
       

        String  customId = generateCustomId(); // Replace with your custom ID generation logic
        
        if (dataObject instanceof Medicine) {
            ((Medicine) dataObject).setId(customId);
        }

        // Add the object to Firestore
        firestoreInstance.collection(collectionName)
                .document(customId)
                .set(dataObject)
                .addOnSuccessListener(aVoid -> {
                    onSuccessListener.onSuccess(null); // passing null since DocumentReference is not available
                })
                .addOnFailureListener(onFailureListener);
    }

    // Replace this method with your custom ID generation logic
    private static String generateCustomId() {
        // Implement your custom ID generation logic here
        // For example, you can use UUID.randomUUID().toString() for a unique ID
        return UUID.randomUUID().toString();
    }


    public static void update(String collectionName, String documentId, Object dataObject, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        firestoreInstance.collection(collectionName)
                .document(documentId)
                .set(dataObject)
                .addOnSuccessListener(aVoid -> onSuccessListener.onSuccess(null)) // No document reference
                .addOnFailureListener(onFailureListener);
    }

    public static void delete(String collectionName, String documentId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        firestoreInstance.collection(collectionName)
                .document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> onSuccessListener.onSuccess(null)) // No document reference
                .addOnFailureListener(onFailureListener);
    }


    // Generic method to read data
    public static <T> void readCollection(String collectionName, Class<T> documentClass, OnSuccessListener<List<T>> onSuccessListener, OnFailureListener onFailureListener) {
        firestoreInstance.collection(collectionName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<T> documentList = new ArrayList<>();
                    for (T document : queryDocumentSnapshots.toObjects(documentClass)) {
                        documentList.add(document);
                    }
                    onSuccessListener.onSuccess(documentList);
                })
                .addOnFailureListener(onFailureListener);
    }
    public static <T> void readById(String collectionName, String documentId, Class<T> documentClass, OnSuccessListener<T> onSuccessListener, OnFailureListener onFailureListener) {
        firestoreInstance.collection(collectionName)
                .document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        T document = documentSnapshot.toObject(documentClass);
                        onSuccessListener.onSuccess(document);
                    } else {
                        onSuccessListener.onSuccess(null); // Document with the given ID does not exist
                    }
                })
                .addOnFailureListener(onFailureListener);
    }
}
