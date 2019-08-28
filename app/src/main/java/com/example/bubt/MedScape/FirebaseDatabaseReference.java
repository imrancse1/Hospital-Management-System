package com.example.bubt.MedScape;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseReference {
    private static DatabaseReference mainDatabaseReference = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference userDatabaseReference = mainDatabaseReference.child("Users");
}
