package com.bl4nk.psutracerstudy.question;

import android.annotation.SuppressLint;
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

public class Question1 extends Fragment {


    FirebaseFirestore fStore;

    PieChart question1PieChart;
    private TextView countYes, countNo, countNever;

    public Question1() {
        // Required empty public constructor
    }

    public static Question1 newInstance() {
        return new Question1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        question1PieChart = view.findViewById(R.id.question1PieChart);
        countYes = view.findViewById(R.id.countYes);
        countNo = view.findViewById(R.id.countNo);
        countNever = view.findViewById(R.id.countNever);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> yesTask = fStore.collection("SurveyAnswer").whereEqualTo("question1", "Yes").get();
        Task<QuerySnapshot> notEmployedNowTask = fStore.collection("SurveyAnswer").whereEqualTo("question1", "Not employed now").get();
        Task<QuerySnapshot> neverEmployedTask = fStore.collection("SurveyAnswer").whereEqualTo("question1", "Never Employed").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(yesTask, notEmployedNowTask, neverEmployedTask);
        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot yesResult = task.getResult().get(0);
                    QuerySnapshot notEmployedNowResult = task.getResult().get(1);
                    QuerySnapshot neverEmployedResult = task.getResult().get(2);

                    int totalCount = yesResult.size() + notEmployedNowResult.size() + neverEmployedResult.size();

                    float percentageYes = ((float) yesResult.size() / totalCount) * 100;
                    float percentageNotEmployedNow = ((float) notEmployedNowResult.size() / totalCount) * 100;
                    float percentageNeverEmployed = ((float) neverEmployedResult.size() / totalCount) * 100;

                    countYes.setText(String.valueOf(yesResult.size()));
                    entries.add(new PieEntry(percentageYes, "Yes"));

                    countNo.setText(String.valueOf(notEmployedNowResult.size()));
                    entries.add(new PieEntry(percentageNotEmployedNow, "Not employed now"));

                    countNever.setText(String.valueOf(neverEmployedResult.size()));
                    entries.add(new PieEntry(percentageNeverEmployed, "Never Employed"));

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
        question1PieChart.setData(pieData);

        question1PieChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = question1PieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(false);

        question1PieChart.getDescription().setEnabled(false);
        question1PieChart.animateY(1000);
        question1PieChart.invalidate();
    }
}
