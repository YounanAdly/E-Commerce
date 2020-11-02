package com.example.android.e_commerce.ui.FirebaseClient;

import android.util.Log;

import com.example.android.e_commerce.ui.data.Product;
import com.example.android.e_commerce.ui.ui.Activity.ProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

import static com.example.android.e_commerce.ui.ViewModel.ProductViewModel.productsPageSize;

public class FireStoreClient {

    private FirebaseFirestore     firebaseFirestore     = FirebaseFirestore.getInstance();
    private FirebaseAuth          mAuth                 = FirebaseAuth.getInstance();
    private String TAG = "TEST";
    private DocumentSnapshot lastDoc;



    public Observable<List<Product>> getProducts() {

        return Observable.create(emitter -> {
            Query query = firebaseFirestore.collection("product").limit(productsPageSize);
            if (lastDoc != null) {
                query = query.startAfter(lastDoc);
            }
            query.get().addOnSuccessListener((documentSnapshots) -> {
                ArrayList<Product> list = new ArrayList<>();

                for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                    list.add(doc.toObject(Product.class));
                }
                if (documentSnapshots.size() > 0) {
                    lastDoc = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                }
                emitter.onNext(list);
                emitter.onComplete();
            });
        });
    }

    public Observable<String> setBookMark() {
        Map<String, Object> bookmark = new HashMap<>();
        bookmark.put("name", ProductActivity.productName);
        bookmark.put("price", ProductActivity.productPrice);
        bookmark.put("image", ProductActivity.productImage);
        bookmark.put("title", ProductActivity.productTitle);

        return Observable.create(emitter ->
                firebaseFirestore.collection("bookmark").document(mAuth.getCurrentUser().getUid())
                        .collection("product").add(bookmark)
                        .addOnSuccessListener(documentReference -> Log.d(TAG, "onSuccess: "))
                        .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e)));
    }

    public Observable<List<Product>> getBookMark() {
        return Observable.create(emitter ->
                firebaseFirestore.collection("bookmark").document(mAuth.getCurrentUser().getUid())
                        .collection("product").addSnapshotListener((documentSnapshots, e) -> {
                    ArrayList<Product> list = new ArrayList<>();
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            list.add(doc.getDocument().toObject(Product.class));
                        }
                    }
                    emitter.onNext(list);
                    emitter.onComplete();
                }));
    }

    public Observable<String> setCard() {

        Map<String, Object> card = new HashMap<>();
        card.put("name", ProductActivity.productName);
        card.put("price", ProductActivity.productPrice);
        card.put("image", ProductActivity.productImage);
        card.put("title", ProductActivity.productTitle);
        card.put("size", ProductActivity.productSize);
        card.put("priceCard", ProductActivity.productPriceCard);

        return Observable.create(emitter -> getCardCollection().add(card)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "onSuccess: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e)));
    }

    public Observable<List<Product>> getCard() {
        return Observable.create(emitter -> getCardCollection().addSnapshotListener((documentSnapshots, e) -> {

            ArrayList<Product> list = new ArrayList<>();
            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                if (doc.getType() == DocumentChange.Type.ADDED) {
                    list.add(doc.getDocument().toObject(Product.class));
                }
            }
            emitter.onNext(list);
            emitter.onComplete();
        }));
    }

    public Observable<Integer> getCardSize() {
        return Observable.create(emitter -> getCardCollection().addSnapshotListener((documentSnapshots, e) -> {
            ArrayList<Product> list = new ArrayList<>();
            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                if (doc.getType() == DocumentChange.Type.ADDED) {
                    list.add(doc.getDocument().toObject(Product.class));
                }
            }
            emitter.onNext(list.size());
            emitter.onComplete();
        }));
    }


    public Observable<List<Product>> getSearch(CharSequence searchText) {
        return Observable.create(emitter ->
                firebaseFirestore.collection("product").orderBy("name")
                        .startAt(searchText).endAt(searchText + "\uf8ff").get().addOnSuccessListener((documentSnapshots) -> {
                    ArrayList<Product> searchList = new ArrayList<>();

                    for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                        searchList.add(doc.toObject(Product.class));
                    }
                    Log.d(TAG, "getSearch: " + searchText + "LogList : " + searchList.size());
                    emitter.onNext(searchList);

                }));
    }


    public Observable<Boolean> login(String email, String password) {
        return Observable.create(emitter ->
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onNext(true);
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    public Observable<Boolean> createAccount(String email, String password) {
        return Observable.create(emitter ->
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onNext(true);
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }


    public Observable<Double> getTotalPrice() {
        return Observable.create(emitter -> getCardCollection().get().addOnSuccessListener(documentSnapshots -> {
                    double total = 0;
                    for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                        double itemCost = snapshot.getDouble("priceCard");
                        total += itemCost;
                    }
                    Log.d(TAG, String.valueOf(total));
                    emitter.onNext(total);
                    emitter.onComplete();
                })
        );
    }

    private CollectionReference getCardCollection() {

        return firebaseFirestore.collection("card").document(mAuth.getCurrentUser().getUid()).collection("card");
    }

    public void setFirebaseFirestore() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
    }
}
