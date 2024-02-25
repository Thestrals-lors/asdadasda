package com.bl4nk.psutracerstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bl4nk.psutracerstudy.user_fragment.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShowAnswer extends AppCompatActivity {

    private TextView question1, question2, question3, question4, question5, question6,
            question7, question8, question9, question10, question11, question12,
            question13, question14, question15, question16, question171, question172, question173, question174, question175;

    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_show_answer);

        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        question6 = findViewById(R.id.question6);
        question7 = findViewById(R.id.question7);
        question8 = findViewById(R.id.question8);
        question9 = findViewById(R.id.question9);
        question10 = findViewById(R.id.question10);
        question11 = findViewById(R.id.question11);
        question12 = findViewById(R.id.question12);
        question13 = findViewById(R.id.question13);
        question14 = findViewById(R.id.question14);
        question15 = findViewById(R.id.question15);
        question16 = findViewById(R.id.question16);
        question171 = findViewById(R.id.question171);
        question172 = findViewById(R.id.question172);
        question173 = findViewById(R.id.question173);
        question174 = findViewById(R.id.question174);
        question175 = findViewById(R.id.question175);

        backBtn = findViewById(R.id.backBtn);

        DocumentReference df = fStore.collection("SurveyAnswer").document(user.getUid());

        df.get().addOnSuccessListener(doc -> {

            if(doc.exists()){
                        String answer1, answer3, answer4, answer5, answer6, answer7, answer9, answer12, answer13, answer14, answer15, answer171, answer172, answer173, answer174, answer175;

                        answer1 = doc.getString("question1");
                        List<String> answer2 = (List<String>) doc.get("question2");
                        answer3 = doc.getString("question3");
                        answer4 = doc.getString("question4");
                        answer5 = doc.getString("question5");
                        answer6 = doc.getString("question6");
                        answer7 = doc.getString("question7");
                        List<String> answer8 = (List<String>) doc.get("question8");
                        answer9 = doc.getString("question9");
                        List<String> answer10 = (List<String>) doc.get("question2");
                        List<String> answer11 = (List<String>) doc.get("question2");
                        answer12 = doc.getString("question12");
                        answer13 = doc.getString("question13");
                        answer14 = doc.getString("question14");
                        answer15 = doc.getString("question15");
                        List<String> answer16 = (List<String>) doc.get("question2");
                        answer171 = doc.getString("question171");
                        answer172 = doc.getString("question172");
                        answer173 = doc.getString("question173");
                        answer174 = doc.getString("question174");
                        answer175 = doc.getString("question175");

                        question1.setText(answer1);
                        question2.setText(answer2.toString());
                        question3.setText(answer3);
                        question4.setText(answer4);
                        question5.setText(answer5);
                        question6.setText(answer6);
                        question7.setText(answer7);
                        question8.setText(answer8.toString());
                        question9.setText(answer9);
                        question10.setText(answer10.toString());
                        question11.setText(answer11.toString());
                        question12.setText(answer12);
                        question13.setText(answer13);
                        question14.setText(answer14);
                        question15.setText(answer15);
                        question16.setText(answer16.toString());
                        question171.setText(answer171);
                        question172.setText(answer172);
                        question173.setText(answer173);
                        question174.setText(answer174);
                        question175.setText(answer175);
                    }
            else {
                Toast.makeText(this, "Answer the survey first.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onBackPressed();
                    }
                });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowAnswer.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}