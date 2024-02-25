package com.bl4nk.psutracerstudy.question;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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

public class Question6 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    TextView countManagerialLevelTextView;
    TextView countSupervisoryLevelTextView;
    TextView countRankAndFileTextView;
    TextView countOtherTextView;

    public Question6() {
        // Required empty public constructor
    }

    public static Question6 newInstance() {
        return new Question6();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countManagerialLevelTextView = view.findViewById(R.id.countManagerialLevelTextView);
        countSupervisoryLevelTextView = view.findViewById(R.id.countSupervisoryLevelTextView);
        countRankAndFileTextView = view.findViewById(R.id.countRankAndFileTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> managerialLevelTask = fStore.collection("SurveyAnswer").whereEqualTo("question6", "Managerial level").get();
        Task<QuerySnapshot> supervisoryLevelTask = fStore.collection("SurveyAnswer").whereEqualTo("question6", "Supervisory level").get();
        Task<QuerySnapshot> rankAndFileTask = fStore.collection("SurveyAnswer").whereEqualTo("question6", "Rank and File").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question6", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                managerialLevelTask,
                supervisoryLevelTask,
                rankAndFileTask,
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
                                String question1Value = document.getString("question6");
                                if (question1Value.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countManagerialLevel = managerialLevelTask.getResult().size();
                                        int countSupervisoryLevel = supervisoryLevelTask.getResult().size();
                                        int countRankAndFile = rankAndFileTask.getResult().size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countManagerialLevel + countSupervisoryLevel + countRankAndFile + countOther;

                                        float percentageManagerialLevel = ((float) countManagerialLevel / totalCount) * 100;
                                        float percentageSupervisoryLevel = ((float) countSupervisoryLevel / totalCount) * 100;
                                        float percentageRankAndFile = ((float) countRankAndFile / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countManagerialLevelTextView.setText(String.valueOf(countManagerialLevel));
                                        countSupervisoryLevelTextView.setText(String.valueOf(countSupervisoryLevel));
                                        countRankAndFileTextView.setText(String.valueOf(countRankAndFile));
                                        countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageManagerialLevel, "Managerial level"));
                                        entries.add(new PieEntry(percentageSupervisoryLevel, "Supervisory level"));
                                        entries.add(new PieEntry(percentageRankAndFile, "Rank and File"));
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
