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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Question15 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    private TextView countYesTextView, countNoTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Question15() {
        // Required empty public constructor
    }

    public static Question15 newInstance(String param1, String param2) {
        Question15 fragment = new Question15();
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
        return inflater.inflate(R.layout.fragment_question15, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countYesTextView = view.findViewById(R.id.countYes);
        countNoTextView = view.findViewById(R.id.countNo);

        ArrayList<PieEntry> entries = new ArrayList<>();

// Tasks to fetch data from Firestore
        Task<QuerySnapshot> yesTask = fStore.collection("SurveyAnswer").whereEqualTo("question15", "Yes").get();
        Task<QuerySnapshot> noTask = fStore.collection("SurveyAnswer").whereEqualTo("question15", "No").get();

// Combining all tasks
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(yesTask, noTask);

// Adding onCompleteListener to handle the result
        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {
                    // Retrieving results of each task
                    QuerySnapshot yesResult = task.getResult().get(0);
                    QuerySnapshot noResult = task.getResult().get(1);

                    // Calculating total count
                    int totalCount = yesResult.size() + noResult.size();

                    // Calculating percentages
                    float percentageYes = ((float) yesResult.size() / totalCount) * 100;
                    float percentageNo = ((float) noResult.size() / totalCount) * 100;

                    // Setting text and adding entries for "Yes"
                    countYesTextView.setText(String.valueOf(yesResult.size()));
                    entries.add(new PieEntry(percentageYes, "Yes"));

                    // Setting text and adding entries for "No"
                    countNoTextView.setText(String.valueOf(noResult.size()));
                    entries.add(new PieEntry(percentageNo, "No"));

                    // Setting up the pie chart
                    setupPieChart(entries);
                } else {
                    Log.e("SurveySummary", "Error getting documents: ", task.getException());
                }
            }
        });
    }



    private void setupPieChart(ArrayList<PieEntry> entries) {
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
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
