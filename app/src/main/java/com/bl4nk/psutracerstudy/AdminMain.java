package com.bl4nk.psutracerstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bl4nk.psutracerstudy.admin_components.SurveySummary;
import com.bl4nk.psutracerstudy.admin_components.UserSummary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminMain extends AppCompatActivity {

    private CardView surveySummaryCard, exitCardView, userSummaryCard;
    private TextView totalNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Users");


        surveySummaryCard = findViewById(R.id.surveySummaryCard);
        userSummaryCard = findViewById(R.id.userSummaryCard);
        exitCardView = findViewById(R.id.exitCardView);
        totalNumber = findViewById(R.id.totalNum);

        surveySummaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this, SurveySummary.class);
                startActivity(intent);
                finish();
            }
        });

        userSummaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, UserSummary.class);
                startActivity(intent);
                finish();
            }
        });

        exitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminMain.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = task.getResult().size() - 1;
                    if(count == 0){
                        totalNumber.setText("No user.");
                    }
                    else {
                        totalNumber.setText(count+"");
                    }
                    Log.d("Firestore", "Total documents: " + count);
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
    }

}