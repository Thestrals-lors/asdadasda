package com.bl4nk.psutracerstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCredentials extends AppCompatActivity {

    private Intent intent;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userAddress;
    private String userAge;
    private String userBirthday;
    private String userMobileNumber;
    private String userBatchYear;
    private String userSex;
    private String userCivilStatus;
    private String userHonor;
    private String userExamPassed;
    private EditText userEmail;
    private EditText pass;
    private EditText confirmPass;
    private Button registerBtn;
    private ImageButton backBtn;
    private ImageView registerImage;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_credentials);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        init();

        intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        middleName = intent.getStringExtra("middleName");
        lastName = intent.getStringExtra("lastName");
        userAddress = intent.getStringExtra("userAddress");
        userAge = intent.getStringExtra("userAge");
        userBirthday = intent.getStringExtra("userBirthday");
        userMobileNumber = intent.getStringExtra("userMobileNumber");
        userBatchYear = intent.getStringExtra("userBatchYear");
        userSex = intent.getStringExtra("userSex");
        userCivilStatus = intent.getStringExtra("userCivilStatus");
        userHonor = intent.getStringExtra("userHonor");
        userExamPassed = intent.getStringExtra("userExamPassed");

        clickListener();

    }

    private void init() {
        userEmail = findViewById(R.id.userEmail);
        pass = findViewById(R.id.pass);
        confirmPass = findViewById(R.id.confirmPass);
        registerImage = findViewById(R.id.registerImage);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);
    }

    private void clickListener() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterCredentials.this, Register.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("middleName", middleName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("userAddress", userAddress);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userBirthday", userBirthday);
                intent.putExtra("userMobileNumber", userMobileNumber);
                intent.putExtra("userBatchYear", userBatchYear);
                intent.putExtra("userSex", userSex);
                intent.putExtra("userCivilStatus", userCivilStatus);
                intent.putExtra("userHonor", userHonor);
                intent.putExtra("userExamPassed", userExamPassed);
                startActivity(intent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        String email = userEmail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confirmPassword = confirmPass.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Enter a valid email address");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            pass.setError("Password must be at least 6 characters long");
            pass.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)) {
            confirmPass.setError("Passwords do not match");
            confirmPass.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            String userName =firstName + " " + middleName + " " + lastName;

                            String image = "https://www.icmetl.org/wp-content/uploads/2020/11/user-icon-human-person-sign-vector-10206693.png";
                            //Update display name of user
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userName).setPhotoUri(Uri.parse(image)).build();
                            user.updateProfile(profileChangeRequest);

                            DocumentReference userRef = fStore.collection("Users").document(user.getUid());

                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("userId", user.getUid());
                            userInfo.put("firstName", firstName);
                            userInfo.put("middleName", middleName);
                            userInfo.put("lastName", lastName);
                            userInfo.put("userAddress", userAddress);
                            userInfo.put("userAge", userAge);
                            userInfo.put("userBirthday", userBirthday);
                            userInfo.put("userMobileNumber", userMobileNumber);
                            userInfo.put("userBatchYear", userBatchYear);
                            userInfo.put("userSex", userSex);
                            userInfo.put("userCivilStatus", userCivilStatus);
                            userInfo.put("userHonor", userHonor);
                            userInfo.put("userExamPassed", userExamPassed);
                            userInfo.put("email", email);
                            userInfo.put("userType", "User");

                            // Set the user information to the document
                            userRef.set(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterCredentials.this, "User information saved to Database.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterCredentials.this, "Failed to save user information to Database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(RegisterCredentials.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            registerImage.setVisibility(View.VISIBLE);

                            Intent intent = new Intent(RegisterCredentials.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterCredentials.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}