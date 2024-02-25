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
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Question16 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;

    int[] colors;

    private PieChart questionPieChart;


    private TextView countProblemSolvingTextView;
    private TextView countCommunicationSkillsTextView;
    private TextView countCriticalThinkingSkillsTextView;
    private TextView countHumanRelationsSkillsTextView;
    private TextView countInformationTechnologySkillsTextView;
    private TextView countEntrepreneurialSkillsTextView;
    private TextView countCourseRelatedSkillsTextView;
    private TextView countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Question16() {
        // Required empty public constructor
    }

    public static Question16 newInstance(String param1, String param2) {
        Question16 fragment = new Question16();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question16, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countProblemSolvingTextView = view.findViewById(R.id.countProblemSolving);
        countCommunicationSkillsTextView = view.findViewById(R.id.countCommunicationSkills);
        countCriticalThinkingSkillsTextView = view.findViewById(R.id.countCriticalThinkingSkills);
        countHumanRelationsSkillsTextView = view.findViewById(R.id.countHumanRelationsSkills);
        countInformationTechnologySkillsTextView = view.findViewById(R.id.countInformationTechnologySkills);
        countEntrepreneurialSkillsTextView = view.findViewById(R.id.countEntrepreneurialSkills);
        countCourseRelatedSkillsTextView = view.findViewById(R.id.countCourseRelatedSkills);
        countOtherTextView = view.findViewById(R.id.countOther);


        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> problemSolvingTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Problem-solving skills").get();
        Task<QuerySnapshot> communicationSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Communication skills").get();
        Task<QuerySnapshot> criticalThinkingSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Critical Thinking skills").get();
        Task<QuerySnapshot> humanRelationsSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Human Relations skills").get();
        Task<QuerySnapshot> informationTechnologySkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Information Technology skills").get();
        Task<QuerySnapshot> entrepreneurialSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Entrepreneurial skills").get();
        Task<QuerySnapshot> courseRelatedSkillsTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Skills related to my course such as").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereArrayContains("question16", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                problemSolvingTask,
                communicationSkillsTask,
                criticalThinkingSkillsTask,
                humanRelationsSkillsTask,
                informationTechnologySkillsTask,
                entrepreneurialSkillsTask,
                courseRelatedSkillsTask,
                otherTask
        );

        fStore.collection("SurveyAnswer")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        countNot = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Check if the value of "question1" contains "Other"
                            List<String> question1Value = (List<String>) document.get("question16");
                            for (int i = 0; i < question1Value.size(); i++) {
                                if (question1Value.get(i).contains("Other")) {
                                    countNot++;
                                    break;
                                }
                            }
                        }
                        countOtherTextView.setText(String.valueOf(countNot));
                        allTasks.addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                int countProblemSolving = task1.getResult().get(0).size();
                                int countCommunicationSkills = task1.getResult().get(1).size();
                                int countCriticalThinkingSkills = task1.getResult().get(2).size();
                                int countHumanRelationsSkills = task1.getResult().get(3).size();
                                int countInformationTechnologySkills = task1.getResult().get(4).size();
                                int countEntrepreneurialSkills = task1.getResult().get(5).size();
                                int countCourseRelatedSkills = task1.getResult().get(6).size();
                                // No need to fetch countOther again as we already have it from above
                                // int countOther = task1.getResult().get(7).size();

                                int totalCount = countProblemSolving + countCommunicationSkills + countCriticalThinkingSkills +
                                        countHumanRelationsSkills + countInformationTechnologySkills + countEntrepreneurialSkills +
                                        countCourseRelatedSkills + countNot;

                                // Calculate percentages
                                float percentageProblemSolving = ((float) countProblemSolving / totalCount) * 100;
                                float percentageCommunicationSkills = ((float) countCommunicationSkills / totalCount) * 100;
                                float percentageCriticalThinkingSkills = ((float) countCriticalThinkingSkills / totalCount) * 100;
                                float percentageHumanRelationsSkills = ((float) countHumanRelationsSkills / totalCount) * 100;
                                float percentageInformationTechnologySkills = ((float) countInformationTechnologySkills / totalCount) * 100;
                                float percentageEntrepreneurialSkills = ((float) countEntrepreneurialSkills / totalCount) * 100;
                                float percentageCourseRelatedSkills = ((float) countCourseRelatedSkills / totalCount) * 100;
                                float percentageOther = ((float) countNot / totalCount) * 100;

                                // Update TextViews with counts
                                countProblemSolvingTextView.setText(String.valueOf(countProblemSolving));
                                countCommunicationSkillsTextView.setText(String.valueOf(countCommunicationSkills));
                                countCriticalThinkingSkillsTextView.setText(String.valueOf(countCriticalThinkingSkills));
                                countHumanRelationsSkillsTextView.setText(String.valueOf(countHumanRelationsSkills));
                                countInformationTechnologySkillsTextView.setText(String.valueOf(countInformationTechnologySkills));
                                countEntrepreneurialSkillsTextView.setText(String.valueOf(countEntrepreneurialSkills));
                                countCourseRelatedSkillsTextView.setText(String.valueOf(countCourseRelatedSkills));

                                // Add entries for pie chart
                                entries.add(new PieEntry(percentageProblemSolving, "Problem-solving skills"));
                                entries.add(new PieEntry(percentageCommunicationSkills, "Communication skills"));
                                entries.add(new PieEntry(percentageCriticalThinkingSkills, "Critical Thinking skills"));
                                entries.add(new PieEntry(percentageHumanRelationsSkills, "Human Relations skills"));
                                entries.add(new PieEntry(percentageInformationTechnologySkills, "Information Technology skills"));
                                entries.add(new PieEntry(percentageEntrepreneurialSkills, "Entrepreneurial skills"));
                                entries.add(new PieEntry(percentageCourseRelatedSkills, "Skills related to my course such as"));
                                entries.add(new PieEntry(percentageOther, "Other"));

                                // Populate the PieChart with the data
                                setupPieChart(entries);
                            } else {
                                Log.e("SurveySummary", "Error getting documents: ", task1.getException());
                            }
                        });
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
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
