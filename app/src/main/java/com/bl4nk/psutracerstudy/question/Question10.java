package com.bl4nk.psutracerstudy.question;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bl4nk.psutracerstudy.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Question10 extends Fragment {


    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    TextView countSalariesBenefitsTextView;
    TextView countCareerChallengeTextView;
    TextView countRelatedToSpecialSkillsTextView;
    TextView countProximityToResidenceTextView;
    TextView countOther;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question10() {}

    public static Question10 newInstance(String param1, String param2) {
        Question10 fragment = new Question10();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countSalariesBenefitsTextView = view.findViewById(R.id.countSalariesBenefits);
        countCareerChallengeTextView = view.findViewById(R.id.countCareerChallenge);
        countRelatedToSpecialSkillsTextView = view.findViewById(R.id.countRelatedToSpecialSkills);
        countProximityToResidenceTextView = view.findViewById(R.id.countProximityToResidence);
        countOther = view.findViewById(R.id.countOther);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> salariesBenefitsTask = fStore.collection("SurveyAnswer").whereArrayContains("question10", "Salaries & benefits").get();
        Task<QuerySnapshot> careerChallengeTask = fStore.collection("SurveyAnswer").whereArrayContains("question10", "Career challenge").get();
        Task<QuerySnapshot> relatedToSpecialSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question10", "Related to special skills").get();
        Task<QuerySnapshot> proximityToResidenceTask = fStore.collection("SurveyAnswer").whereArrayContains("question10", "Proximity to residence").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereArrayContains("question10", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                salariesBenefitsTask,
                careerChallengeTask,
                relatedToSpecialSkillsTask,
                proximityToResidenceTask,
                otherTask
        );

        fStore.collection("SurveyAnswer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            countNot = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Check if the value of "question1" contains "Not employed now"
                                List<String> question1Value = (List<String>) document.get("question10");
                                for (int i = 0; i < question1Value.size(); i++) {
                                    if (question1Value.get(i).contains("Other")) {
                                        countNot++;
                                        break;
                                    }
                                }
                            }
                            countOther.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countSalariesBenefits = task.getResult().get(0).size();
                                        int countCareerChallenge = task.getResult().get(1).size();
                                        int countRelatedToSpecialSkills = task.getResult().get(2).size();
                                        int countProximityToResidence = task.getResult().get(3).size();
                                        int countOther = countNot;

                                        int totalCount = countSalariesBenefits + countCareerChallenge + countRelatedToSpecialSkills +
                                                countProximityToResidence + countOther;

                                        float percentageSalariesBenefits = ((float) countSalariesBenefits / totalCount) * 100;
                                        float percentageCareerChallenge = ((float) countCareerChallenge / totalCount) * 100;
                                        float percentageRelatedToSpecialSkills = ((float) countRelatedToSpecialSkills / totalCount) * 100;
                                        float percentageProximityToResidence = ((float) countProximityToResidence / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countSalariesBenefitsTextView.setText(String.valueOf(countSalariesBenefits));
                                        countCareerChallengeTextView.setText(String.valueOf(countCareerChallenge));
                                        countRelatedToSpecialSkillsTextView.setText(String.valueOf(countRelatedToSpecialSkills));
                                        countProximityToResidenceTextView.setText(String.valueOf(countProximityToResidence));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageSalariesBenefits, "Salaries & benefits"));
                                        entries.add(new PieEntry(percentageCareerChallenge, "Career challenge"));
                                        entries.add(new PieEntry(percentageRelatedToSpecialSkills, "Related to special skills"));
                                        entries.add(new PieEntry(percentageProximityToResidence, "Proximity to residence"));
                                        entries.add(new PieEntry(percentageOther, "Other"));

                                        // Populate the PieChart with the data
                                        setupPieChart(entries);
                                    } else {
                                        Log.e("SurveySummary", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }



    private void setupPieChart(ArrayList<PieEntry> entries) {
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(colors);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%%", value);
            }
        });

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(13f); // Set the text size
        questionPieChart.setData(pieData);

        questionPieChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = questionPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(false);

        questionPieChart.getDescription().setEnabled(false);
        questionPieChart.animateY(1000);

//        questionPieChart.setExtraOffsets(30f, 0f, 0f, 0f);

        questionPieChart.invalidate();
    }

    private int[] generateColors() {
        int[] colors = new int[]{
                Color.rgb(255, 102, 0),  // Orange
                Color.rgb(255, 204, 0),  // Yellow
                Color.rgb(51, 153, 255), // Blue
                Color.rgb(204, 0, 204),  // Purple
                Color.rgb(255, 51, 153), // Pink
                Color.rgb(102, 255, 102),// Green
                Color.rgb(102, 0, 204),  // Indigo
                Color.rgb(255, 102, 255),// Violet
                Color.rgb(0, 204, 204),  // Cyan
                Color.rgb(0, 153, 0),    // Dark Green
                Color.rgb(102, 102, 102), //Cyan
                Color.rgb(255, 0, 0)     // Red
        };
        return colors;
    }
}
