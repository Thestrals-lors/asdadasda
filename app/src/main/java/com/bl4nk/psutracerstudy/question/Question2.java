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
import android.widget.Toast;

import com.bl4nk.psutracerstudy.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Question2 extends Fragment {
    FirebaseFirestore fStore;
    int countNot = 0;

    int[] colors;

    private PieChart questionPieChart;
    private TextView countNoConnectionsTextView;
    private TextView countNoJobOpportunityTextView;
    private TextView countFamilyConcernTextView;
    private TextView countFurtherStudyTextView;
    private TextView countEligibilityRequirementsTextView;
    private TextView countSeekJobTextView;
    private TextView countHealthReasonsTextView;
    private TextView countWorkExperienceTextView;
    private TextView countStartingPayTextView;
    private TextView countOtherTextView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question2() {
        // Required empty public constructor
    }

    public static Question2 newInstance(String param1, String param2) {
        Question2 fragment = new Question2();
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
        return inflater.inflate(R.layout.fragment_question2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countNoConnectionsTextView = view.findViewById(R.id.countNoConnections);
        countNoJobOpportunityTextView = view.findViewById(R.id.countNoJobOpportunity);
        countFamilyConcernTextView = view.findViewById(R.id.countFamilyConcern);
        countFurtherStudyTextView = view.findViewById(R.id.countEngagedInFurtherStudy);
        countEligibilityRequirementsTextView = view.findViewById(R.id.countLack);
        countSeekJobTextView = view.findViewById(R.id.countPlans);
        countHealthReasonsTextView = view.findViewById(R.id.countHealth);
        countWorkExperienceTextView = view.findViewById(R.id.countLackWork);
        countStartingPayTextView = view.findViewById(R.id.countStart);
        countOtherTextView = view.findViewById(R.id.countOther);




        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> noConnectionsTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "No connections").get();
        Task<QuerySnapshot> noJobOpportunityTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "No job opportunity").get();
        Task<QuerySnapshot> familyConcernTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Family concern").get();
        Task<QuerySnapshot> furtherStudyTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Engaged in further study").get();
        Task<QuerySnapshot> eligibilityRequirementsTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Lack of professional eligibility requirements").get();
        Task<QuerySnapshot> seekJobTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Have plans to seek a job out of the country").get();
        Task<QuerySnapshot> healthReasonsTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Health-related reasons").get();
        Task<QuerySnapshot> workExperienceTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Lack of work experience").get();
        Task<QuerySnapshot> startingPayTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Starting pay is too low").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereArrayContains("question2", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                noConnectionsTask,
                noJobOpportunityTask,
                familyConcernTask,
                furtherStudyTask,
                eligibilityRequirementsTask,
                seekJobTask,
                healthReasonsTask,
                workExperienceTask,
                startingPayTask,
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
                                List<String> question1Value = (List<String>) document.get("question2");
                                for (int i = 0; i < question1Value.size(); i++) {
                                    if (question1Value.get(i).contains("Other")) {
                                        countNot++;
                                        break;
                                    }
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {


                                        int countNoConnections = task.getResult().get(0).size();
                                        int countNoJobOpportunity = task.getResult().get(1).size();
                                        int countFamilyConcern = task.getResult().get(2).size();
                                        int countFurtherStudy = task.getResult().get(3).size();
                                        int countEligibilityRequirements = task.getResult().get(4).size();
                                        int countSeekJob = task.getResult().get(5).size();
                                        int countHealthReasons = task.getResult().get(6).size();
                                        int countWorkExperience = task.getResult().get(7).size();
                                        int countStartingPay = task.getResult().get(8).size();
//                    int countOther = task.getResult().get(9).size();
                                        int countOther = countNot;
//                                        Toast.makeText(getContext(), countNot + "", Toast.LENGTH_SHORT).show();

                                        int totalCount = countNoConnections + countNoJobOpportunity + countFamilyConcern +
                                                countFurtherStudy + countEligibilityRequirements + countSeekJob +
                                                countHealthReasons + countWorkExperience + countStartingPay + countOther;

                                        float percentageNoConnections = ((float) countNoConnections / totalCount) * 100;
                                        float percentageNoJobOpportunity = ((float) countNoJobOpportunity / totalCount) * 100;
                                        float percentageFamilyConcern = ((float) countFamilyConcern / totalCount) * 100;
                                        float percentageFurtherStudy = ((float) countFurtherStudy / totalCount) * 100;
                                        float percentageEligibilityRequirements = ((float) countEligibilityRequirements / totalCount) * 100;
                                        float percentageSeekJob = ((float) countSeekJob / totalCount) * 100;
                                        float percentageHealthReasons = ((float) countHealthReasons / totalCount) * 100;
                                        float percentageWorkExperience = ((float) countWorkExperience / totalCount) * 100;
                                        float percentageStartingPay = ((float) countStartingPay / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countNoConnectionsTextView.setText(String.valueOf(countNoConnections));
                                        countNoJobOpportunityTextView.setText(String.valueOf(countNoJobOpportunity));
                                        countFamilyConcernTextView.setText(String.valueOf(countFamilyConcern));
                                        countFurtherStudyTextView.setText(String.valueOf(countFurtherStudy));
                                        countEligibilityRequirementsTextView.setText(String.valueOf(countEligibilityRequirements));
                                        countSeekJobTextView.setText(String.valueOf(countSeekJob));
                                        countHealthReasonsTextView.setText(String.valueOf(countHealthReasons));
                                        countWorkExperienceTextView.setText(String.valueOf(countWorkExperience));
                                        countStartingPayTextView.setText(String.valueOf(countStartingPay));
//                    countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageNoConnections, "No connections"));
                                        entries.add(new PieEntry(percentageNoJobOpportunity, "No job opportunity"));
                                        entries.add(new PieEntry(percentageFamilyConcern, "Family concern"));
                                        entries.add(new PieEntry(percentageFurtherStudy, "Engaged in further study"));
                                        entries.add(new PieEntry(percentageEligibilityRequirements, "Lack of professional eligibility requirements"));
                                        entries.add(new PieEntry(percentageSeekJob, "Have plans to seek job out of the country"));
                                        entries.add(new PieEntry(percentageHealthReasons, "Health-related reasons"));
                                        entries.add(new PieEntry(percentageWorkExperience, "Lack of work experience"));
                                        entries.add(new PieEntry(percentageStartingPay, "Starting pay is too low"));
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
