package com.example.bubt.MedScape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bubt.MedScape.FirebaseDatabaseReference;
import com.example.bubt.MedScape.UsersPojo;
import com.example.bubt.MedScape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText signUpEmailEt, signUpPasswordEt, signUpConfirmPasswordEt;
    private Button signUpSignUpBtn;
    private TextView signUpLoginTv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialization();
        onClick();
    }

    private void onClick() {
        signUpLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        signUpSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpEmailEt.getText().toString();
                String password = signUpPasswordEt.getText().toString();
                String confirmPassword = signUpConfirmPasswordEt.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    signUpEmailEt.setError("Enter a mail");
                    signUpEmailEt.requestFocus();
                    return;

                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    signUpEmailEt.setError("Enter a valid email");
                    signUpEmailEt.requestFocus();
                    return;

                }else if (TextUtils.isEmpty(password)) {
                    signUpPasswordEt.setError("Enter a password");
                    signUpPasswordEt.requestFocus();
                    return;

                }else if (password.length()<8) {
                    signUpPasswordEt.setError("Enter minimum 8 digit password");
                    signUpPasswordEt.requestFocus();
                    return;

                }else if (!password.equals(confirmPassword)) {
                    signUpConfirmPasswordEt.setError("Password don't match");
                    signUpConfirmPasswordEt.requestFocus();
                    return;

                }else {
                    signUpWithEmailAndPassword(email, password);

                }
            }
        });
    }

    private void signUpWithEmailAndPassword(final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = firebaseAuth.getCurrentUser().getUid();

                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Please waiting...");
                    progressDialog.show();

                    UsersPojo usersPojo = new UsersPojo(email, password);
                    FirebaseDatabaseReference.userDatabaseReference.child(userId).setValue(usersPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Thank you for sign up!", Toast.LENGTH_SHORT).show();

                                /*startActivity(new Intent(SignUpActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));*/

                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialization() {
        signUpEmailEt = findViewById(R.id.signUpEmailEtId);
        signUpPasswordEt = findViewById(R.id.signUpPasswordEtId);
        signUpConfirmPasswordEt = findViewById(R.id.signUpConfirmPasswordEtId);

        signUpSignUpBtn = findViewById(R.id.signUpSignUpBtnId);

        signUpLoginTv = findViewById(R.id.signUpLoginTvId);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}