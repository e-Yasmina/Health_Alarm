package com.example.health_alarm_v2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreUtils {

    private static FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();

    public static void add(String collectionName, Object dataObject, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        firestoreInstance.collection(collectionName)
                .add(dataObject)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
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
}
