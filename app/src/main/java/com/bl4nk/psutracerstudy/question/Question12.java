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

public class Question12 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    TextView countLessThanMonthTextView;
    TextView count1To6MonthsTextView;
    TextView count7To11MonthsTextView;
    TextView count1To2YearsTextView;
    TextView count2To3YearsTextView;
    TextView count3To4YearsTextView;
    TextView countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question12() {
        // Required empty public constructor
    }

    public static Question12 newInstance(String param1, String param2) {
        Question12 fragment = new Question12();
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
        return inflater.inflate(R.layout.fragment_question12, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countLessThanMonthTextView = view.findViewById(R.id.countLessThanMonthTextView);
        count1To6MonthsTextView = view.findViewById(R.id.count1To6MonthsTextView);
        count7To11MonthsTextView = view.findViewById(R.id.count7To11MonthsTextView);
        count1To2YearsTextView = view.findViewById(R.id.count1To2YearsTextView);
        count2To3YearsTextView = view.findViewById(R.id.count2To3YearsTextView);
        count3To4YearsTextView = view.findViewById(R.id.count3To4YearsTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> lessThanMonthTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "Less than a month").get();
        Task<QuerySnapshot> oneToSixMonthsTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "1-6 months").get();
        Task<QuerySnapshot> sevenToElevenMonthsTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "7-11 months").get();
        Task<QuerySnapshot> oneToTwoYearsTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "1 year to less than 2 years").get();
        Task<QuerySnapshot> twoToThreeYearsTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "2 years to less than 3 years").get();
        Task<QuerySnapshot> threeToFourYearsTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "3 years to less than 4 years").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question12", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                lessThanMonthTask,
                oneToSixMonthsTask,
                sevenToElevenMonthsTask,
                oneToTwoYearsTask,
                twoToThreeYearsTask,
                threeToFourYearsTask,
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
                                String question1Value = document.getString("question12");
                                if (question1Value.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countLessThanMonth = lessThanMonthTask.getResult().size();
                                        int count1To6Months = oneToSixMonthsTask.getResult().size();
                                        int count7To11Months = sevenToElevenMonthsTask.getResult().size();
                                        int count1To2Years = oneToTwoYearsTask.getResult().size();
                                        int count2To3Years = twoToThreeYearsTask.getResult().size();
                                        int count3To4Years = threeToFourYearsTask.getResult().size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countLessThanMonth + count1To6Months + count7To11Months +
                                                count1To2Years + count2To3Years + count3To4Years + countOther;

                                        float percentageLessThanMonth = ((float) countLessThanMonth / totalCount) * 100;
                                        float percentage1To6Months = ((float) count1To6Months / totalCount) * 100;
                                        float percentage7To11Months = ((float) count7To11Months / totalCount) * 100;
                                        float percentage1To2Years = ((float) count1To2Years / totalCount) * 100;
                                        float percentage2To3Years = ((float) count2To3Years / totalCount) * 100;
                                        float percentage3To4Years = ((float) count3To4Years / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countLessThanMonthTextView.setText(String.valueOf(countLessThanMonth));
                                        count1To6MonthsTextView.setText(String.valueOf(count1To6Months));
                                        count7To11MonthsTextView.setText(String.valueOf(count7To11Months));
                                        count1To2YearsTextView.setText(String.valueOf(count1To2Years));
                                        count2To3YearsTextView.setText(String.valueOf(count2To3Years));
                                        count3To4YearsTextView.setText(String.valueOf(count3To4Years));
                                        countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageLessThanMonth, "Less than a month"));
                                        entries.add(new PieEntry(percentage1To6Months, "1-6 months"));
                                        entries.add(new PieEntry(percentage7To11Months, "7-11 months"));
                                        entries.add(new PieEntry(percentage1To2Years, "1 year to less than 2 years"));
                                        entries.add(new PieEntry(percentage2To3Years, "2 years to less than 3 years"));
                                        entries.add(new PieEntry(percentage3To4Years, "3 years to less than 4 years"));
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
