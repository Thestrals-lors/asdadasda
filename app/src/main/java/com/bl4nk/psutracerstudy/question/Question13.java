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

public class Question13 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    TextView countArrangedBySchoolTextView;
    TextView countResponseToAdTextView;
    TextView countWalkInApplicantTextView;
    TextView countRecommendedBySomeoneTextView;
    TextView countInformationFromFriendsTextView;
    TextView countJobFairTextView;
    TextView countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Question13() {
        // Required empty public constructor
    }

    public static Question13 newInstance(String param1, String param2) {
        Question13 fragment = new Question13();
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
        return inflater.inflate(R.layout.fragment_question13, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countArrangedBySchoolTextView = view.findViewById(R.id.countArrangedBySchoolTextView);
        countResponseToAdTextView = view.findViewById(R.id.countResponseToAdTextView);
        countWalkInApplicantTextView = view.findViewById(R.id.countWalkInApplicantTextView);
        countRecommendedBySomeoneTextView = view.findViewById(R.id.countRecommendedBySomeoneTextView);
        countInformationFromFriendsTextView = view.findViewById(R.id.countInformationFromFriendsTextView);
        countJobFairTextView = view.findViewById(R.id.countJobFairTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> arrangedBySchoolTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Arranged by school's job placement officer").get();
        Task<QuerySnapshot> responseToAdTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Response to an advertisement").get();
        Task<QuerySnapshot> walkInApplicantTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "As walk-in applicant").get();
        Task<QuerySnapshot> recommendedBySomeoneTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Recommended by someone").get();
        Task<QuerySnapshot> informationFromFriendsTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Information from friends").get();
        Task<QuerySnapshot> jobFairTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Job Fair").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question13", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                arrangedBySchoolTask,
                responseToAdTask,
                walkInApplicantTask,
                recommendedBySomeoneTask,
                informationFromFriendsTask,
                jobFairTask,
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
                                // Check if the value of "question13" contains "Other"
                                String questionValue = document.getString("question13");
                                if (questionValue.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countArrangedBySchool = arrangedBySchoolTask.getResult().size();
                                        int countResponseToAd = responseToAdTask.getResult().size();
                                        int countWalkInApplicant = walkInApplicantTask.getResult().size();
                                        int countRecommendedBySomeone = recommendedBySomeoneTask.getResult().size();
                                        int countInformationFromFriends = informationFromFriendsTask.getResult().size();
                                        int countJobFair = jobFairTask.getResult().size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countArrangedBySchool + countResponseToAd + countWalkInApplicant +
                                                countRecommendedBySomeone + countInformationFromFriends + countJobFair + countOther;

                                        float percentageArrangedBySchool = ((float) countArrangedBySchool / totalCount) * 100;
                                        float percentageResponseToAd = ((float) countResponseToAd / totalCount) * 100;
                                        float percentageWalkInApplicant = ((float) countWalkInApplicant / totalCount) * 100;
                                        float percentageRecommendedBySomeone = ((float) countRecommendedBySomeone / totalCount) * 100;
                                        float percentageInformationFromFriends = ((float) countInformationFromFriends / totalCount) * 100;
                                        float percentageJobFair = ((float) countJobFair / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countArrangedBySchoolTextView.setText(String.valueOf(countArrangedBySchool));
                                        countResponseToAdTextView.setText(String.valueOf(countResponseToAd));
                                        countWalkInApplicantTextView.setText(String.valueOf(countWalkInApplicant));
                                        countRecommendedBySomeoneTextView.setText(String.valueOf(countRecommendedBySomeone));
                                        countInformationFromFriendsTextView.setText(String.valueOf(countInformationFromFriends));
                                        countJobFairTextView.setText(String.valueOf(countJobFair));
                                        countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageArrangedBySchool, "Arranged by school's job placement officer"));
                                        entries.add(new PieEntry(percentageResponseToAd, "Response to an advertisement"));
                                        entries.add(new PieEntry(percentageWalkInApplicant, "As walk-in applicant"));
                                        entries.add(new PieEntry(percentageRecommendedBySomeone, "Recommended by someone"));
                                        entries.add(new PieEntry(percentageInformationFromFriends, "Information from friends"));
                                        entries.add(new PieEntry(percentageJobFair, "Job Fair"));
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
