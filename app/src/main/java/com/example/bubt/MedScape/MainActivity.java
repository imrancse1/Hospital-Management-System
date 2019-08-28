package com.example.bubt.MedScape;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    AlertDialog.Builder alartDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void patient(View view) {
        Intent intent = new Intent(this,PatientManage.class);
        this.startActivity(intent);
    }

    public void doctors(View view) {
        Intent intent = new Intent(this,DoctorDetails.class);
        this.startActivity(intent);
    }

    public void logOut(View view){
        firebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    public void onBackPressed() {

        alartDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alartDialogBuilder.setTitle("EXIT");
        alartDialogBuilder.setMessage("Do you want to exit ?");
        alartDialogBuilder.setIcon(R.drawable.alart_icon);

        alartDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alartDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alartDialogBuilder.create();
        alertDialog.show();
    }

}




