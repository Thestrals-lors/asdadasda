package com.bl4nk.psutracerstudy.question;

import static android.icu.lang.UCharacter.IndicPositionalCategory.BOTTOM;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bl4nk.psutracerstudy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Question17 extends Fragment {

    private BarChart mapBarChart;

    private FirebaseFirestore fStore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question17() {
        // Required empty public constructor
    }

    public static Question17 newInstance(String param1, String param2) {
        Question17 fragment = new Question17();
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
        return inflater.inflate(R.layout.fragment_question17, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        mapBarChart = view.findViewById(R.id.barChart);

        // Initialize barEntries1 list
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();

        // Fetching data from Firestore
        Task<QuerySnapshot> question1 = fStore.collection("SurveyAnswer").whereEqualTo("question171", "1").get();
        Task<QuerySnapshot> question2 = fStore.collection("SurveyAnswer").whereEqualTo("question172", "1").get();
        Task<QuerySnapshot> question3 = fStore.collection("SurveyAnswer").whereEqualTo("question173", "1").get();
        Task<QuerySnapshot> question4 = fStore.collection("SurveyAnswer").whereEqualTo("question174", "1").get();
        Task<QuerySnapshot> question5 = fStore.collection("SurveyAnswer").whereEqualTo("question175", "1").get();

        Task<QuerySnapshot> A2question1 = fStore.collection("SurveyAnswer").whereEqualTo("question171", "2").get();
        Task<QuerySnapshot> A2question2 = fStore.collection("SurveyAnswer").whereEqualTo("question172", "2").get();
        Task<QuerySnapshot> A2question3 = fStore.collection("SurveyAnswer").whereEqualTo("question173", "2").get();
        Task<QuerySnapshot> A2question4 = fStore.collection("SurveyAnswer").whereEqualTo("question174", "2").get();
        Task<QuerySnapshot> A2question5 = fStore.collection("SurveyAnswer").whereEqualTo("question175", "2").get();

        Task<QuerySnapshot> A3question1 = fStore.collection("SurveyAnswer").whereEqualTo("question171", "3").get();
        Task<QuerySnapshot> A3question2 = fStore.collection("SurveyAnswer").whereEqualTo("question172", "3").get();
        Task<QuerySnapshot> A3question3 = fStore.collection("SurveyAnswer").whereEqualTo("question173", "3").get();
        Task<QuerySnapshot> A3question4 = fStore.collection("SurveyAnswer").whereEqualTo("question174", "3").get();
        Task<QuerySnapshot> A3question5 = fStore.collection("SurveyAnswer").whereEqualTo("question175", "3").get();

        Task<QuerySnapshot> A4question1 = fStore.collection("SurveyAnswer").whereEqualTo("question171", "4").get();
        Task<QuerySnapshot> A4question2 = fStore.collection("SurveyAnswer").whereEqualTo("question172", "4").get();
        Task<QuerySnapshot> A4question3 = fStore.collection("SurveyAnswer").whereEqualTo("question173", "4").get();
        Task<QuerySnapshot> A4question4 = fStore.collection("SurveyAnswer").whereEqualTo("question174", "4").get();
        Task<QuerySnapshot> A4question5 = fStore.collection("SurveyAnswer").whereEqualTo("question175", "4").get();

        Task<QuerySnapshot> A5question1 = fStore.collection("SurveyAnswer").whereEqualTo("question171", "5").get();
        Task<QuerySnapshot> A5question2 = fStore.collection("SurveyAnswer").whereEqualTo("question172", "5").get();
        Task<QuerySnapshot> A5question3 = fStore.collection("SurveyAnswer").whereEqualTo("question173", "5").get();
        Task<QuerySnapshot> A5question4 = fStore.collection("SurveyAnswer").whereEqualTo("question174", "5").get();
        Task<QuerySnapshot> A5question5 = fStore.collection("SurveyAnswer").whereEqualTo("question175", "5").get();

        // Fetching data from Firestore for each group (A1 to A5)
        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(
                question1,
                question2,
                question3,
                question4,
                question5,
                A2question1,
                A2question2,
                A2question3,
                A2question4,
                A2question5,
                A3question1,
                A3question2,
                A3question3,
                A3question4,
                A3question5,
                A4question1,
                A4question2,
                A4question3,
                A4question4,
                A4question5,
                A5question1,
                A5question2,
                A5question3,
                A5question4,
                A5question5
        );

        allTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<BarEntry> barEntriesA1 = new ArrayList<>();
                ArrayList<BarEntry> barEntriesA2 = new ArrayList<>();
                ArrayList<BarEntry> barEntriesA3 = new ArrayList<>();
                ArrayList<BarEntry> barEntriesA4 = new ArrayList<>();
                ArrayList<BarEntry> barEntriesA5 = new ArrayList<>();

                for (int i = 0; i < 5; i++) {
                    // Counting the number of responses for each question in each group
                    int countAnswer1 = task.getResult().get(i).size(); // For A1
                    int countAnswer2 = task.getResult().get(i + 5).size(); // For A2
                    int countAnswer3 = task.getResult().get(i + 10).size(); // For A3
                    int countAnswer4 = task.getResult().get(i + 15).size(); // For A4
                    int countAnswer5 = task.getResult().get(i + 20).size(); // For A5

                    // Adding data entries to the respective ArrayLists
                    barEntriesA1.add(new BarEntry(i + 1, countAnswer1));
                    barEntriesA2.add(new BarEntry(i + 1, countAnswer2));
                    barEntriesA3.add(new BarEntry(i + 1, countAnswer3));
                    barEntriesA4.add(new BarEntry(i + 1, countAnswer4));
                    barEntriesA5.add(new BarEntry(i + 1, countAnswer5));
                }

                // Creating BarDataSet objects for each group
                BarDataSet barDataSetA1 = new BarDataSet(barEntriesA1, "A1");
                barDataSetA1.setColor(Color.BLUE);

                BarDataSet barDataSetA2 = new BarDataSet(barEntriesA2, "A2");
                barDataSetA2.setColor(Color.GREEN);

                BarDataSet barDataSetA3 = new BarDataSet(barEntriesA3, "A3");
                barDataSetA3.setColor(Color.RED);

                BarDataSet barDataSetA4 = new BarDataSet(barEntriesA4, "A4");
                barDataSetA4.setColor(Color.YELLOW);

                BarDataSet barDataSetA5 = new BarDataSet(barEntriesA5, "A5");
                barDataSetA5.setColor(Color.CYAN);

                // Creating BarData objects with the BarDataSet objects
//                BarData barDataA1 = new BarData(barDataSetA1);
//                BarData barDataA2 = new BarData(barDataSetA2);
//                BarData barDataA3 = new BarData(barDataSetA3);
//                BarData barDataA4 = new BarData(barDataSetA4);
//                BarData barDataA5 = new BarData(barDataSetA5);
//
//                // Adding BarData objects to the BarChart
//                mapBarChart.setData(barDataA1);
//                mapBarChart.setData(barDataA2);
//                mapBarChart.setData(barDataA3);
//                mapBarChart.setData(barDataA4);
//                mapBarChart.setData(barDataA5);
                BarData barData = new BarData(barDataSetA1, barDataSetA2, barDataSetA3, barDataSetA4, barDataSetA5);
                mapBarChart.setData(barData);

                String[] labels = new String[]{"Q1", "Q2", "Q3", "Q4", "Q5"};
                XAxis xAxis = mapBarChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setCenterAxisLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularityEnabled(true);

                mapBarChart.setDrawBarShadow(false);
                mapBarChart.setVisibleXRangeMaximum(5);

                xAxis.setDrawGridLines(false); // Disable drawing grid lines

                // Customize Y-axis appearance
                YAxis leftAxis = mapBarChart.getAxisLeft();
                leftAxis.setDrawGridLines(false); // Disable drawing grid lines

                float barSpace = 0.08f;
                float groupSpace = 0.44f;

                int groupCount = 5;
                mapBarChart.getXAxis().setAxisMinimum(0);
                mapBarChart.getXAxis().setAxisMaximum(0 + mapBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                mapBarChart.getAxisLeft().setAxisMinimum(0);

                mapBarChart.groupBars(0, groupSpace, barSpace);
                mapBarChart.invalidate();

                // Refreshing and invalidating the chart
                mapBarChart.notifyDataSetChanged();
                mapBarChart.invalidate();
            }
        });
    }

}
