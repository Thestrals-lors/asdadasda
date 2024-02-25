package com.bl4nk.psutracerstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText userEmail;
    private EditText password;
    private Button loginBtn;
    private TextView createAccTv;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                    if (documentSnapshot.getString("userType") != null && documentSnapshot.getString("userType").equals("User")) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("UserType", "User");
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    }

                    if (documentSnapshot.getString("userType") != null && documentSnapshot.getString("userType").equals("Admin")) {
                        Intent intent = new Intent(getApplicationContext(), AdminMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("UserType", "Admin");
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    }
                    loginBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        init();

        clickListener();
    }

    private void init() {
        userEmail = findViewById(R.id.userEmail);
        password = findViewById(R.id.pass);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        createAccTv = findViewById(R.id.createAcc);
    }

    private void clickListener() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        createAccTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {
        String email = userEmail.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Email is required!");
            userEmail.requestFocus();
        } else if (TextUtils.isEmpty(pwd)) {
            password.setError("Password is required!");
            password.requestFocus();
        } else {
            Toast.makeText(this, email + "\n" + pwd, Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser(); // Update user object after successful login
                                if (user != null) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                                            if (documentSnapshot.getString("userType") != null && documentSnapshot.getString("userType").equals("User")) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else if (documentSnapshot.getString("userType") != null && documentSnapshot.getString("userType").equals("Admin")) {
                                                Intent intent = new Intent(getApplicationContext(), AdminMain.class);
                                                intent.putExtra("UserType", "Admin");
                                                startActivity(intent);
                                                finish();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(Login.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
