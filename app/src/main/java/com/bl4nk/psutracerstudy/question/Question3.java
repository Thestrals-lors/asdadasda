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

public class Question3 extends Fragment {
    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    private TextView countRegularPermanentTextView;
    private TextView countTemporaryContractualTextView;
    private TextView countSelfEmployedTextView;
    private TextView countOtherTextView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Question3() {
        // Required empty public constructor
    }

    public static Question3 newInstance(String param1, String param2) {
        Question3 fragment = new Question3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();

        countRegularPermanentTextView = view.findViewById(R.id.countRegularPermanentTextView);
        countTemporaryContractualTextView = view.findViewById(R.id.countTemporaryContractualTextView);
        countSelfEmployedTextView = view.findViewById(R.id.countSelfEmployedTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        questionPieChart = view.findViewById(R.id.questionPieChart);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> regularPermanentTask = fStore.collection("SurveyAnswer").whereEqualTo("question3", "Regular/Permanent").get();
        Task<QuerySnapshot> temporaryContractualTask = fStore.collection("SurveyAnswer").whereEqualTo("question3", "Temporary/Contractual").get();
        Task<QuerySnapshot> selfEmployedTask = fStore.collection("SurveyAnswer").whereEqualTo("question3", "Self-employed").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question3", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                regularPermanentTask,
                temporaryContractualTask,
                selfEmployedTask,
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
                                String question1Value = document.getString("question3");
                                if (question1Value.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countRegularPermanent = task.getResult().get(0).size();
                                        int countTemporaryContractual = task.getResult().get(1).size();
                                        int countSelfEmployed = task.getResult().get(2).size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countRegularPermanent + countTemporaryContractual + countSelfEmployed + countOther;

                                        float percentageRegularPermanent = ((float) countRegularPermanent / totalCount) * 100;
                                        float percentageTemporaryContractual = ((float) countTemporaryContractual / totalCount) * 100;
                                        float percentageSelfEmployed = ((float) countSelfEmployed / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countRegularPermanentTextView.setText(String.valueOf(countRegularPermanent));
                                        countTemporaryContractualTextView.setText(String.valueOf(countTemporaryContractual));
                                        countSelfEmployedTextView.setText(String.valueOf(countSelfEmployed));
                                        // Assuming countOtherTextView is the same as countNotTextView

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageRegularPermanent, "Regular/Permanent"));
                                        entries.add(new PieEntry(percentageTemporaryContractual, "Temporary/Contractual"));
                                        entries.add(new PieEntry(percentageSelfEmployed, "Self-employed"));
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
