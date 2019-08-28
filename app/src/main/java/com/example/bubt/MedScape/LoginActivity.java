package com.example.bubt.MedScape;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEt, loginPasswordEt;
    private Button loginLoginBtn,aboutUsBtn;
    private TextView loginSignInTv;
    private FirebaseAuth firebaseAuth;
    private AlertDialog.Builder alartDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        onClick();
    }


    private void onClick() {
        loginSignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AboutUs.class));
            }
        });


        loginLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailEt.getText().toString();
                String password = loginPasswordEt.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    loginEmailEt.setError("Enter a email");
                    loginEmailEt.requestFocus();
                    return;

                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    loginEmailEt.setError("Enter a valid email");
                    loginEmailEt.requestFocus();
                    return;

                }else if (TextUtils.isEmpty(password)) {
                    loginPasswordEt.setError("Enter a password");
                    loginPasswordEt.requestFocus();
                    return;

                }else if (password.length() < 8) {
                    loginPasswordEt.setError("Enter minimum 8 digit password");
                    loginPasswordEt.requestFocus();
                    return;

                }else {
                    loginWithEmailAndPassword(email, password);
                }
            }
        });
    }

    private void loginWithEmailAndPassword(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please waiting...");
        progressDialog.show();

        firebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialization() {
        loginEmailEt = findViewById(R.id.loginEmailEtId);
        loginPasswordEt = findViewById(R.id.loginPasswordEtId);

        loginLoginBtn = findViewById(R.id.loginLoginBtnId);
        aboutUsBtn= findViewById(R.id.aboutUsBtnId);

        loginSignInTv = findViewById(R.id.loginSignInTvId);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }



    }


    public void onBackPressed() {

        alartDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

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


