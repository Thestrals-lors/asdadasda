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

public class Question4 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    private TextView countComputerITTextView;
    private TextView countAccountingFinanceTextView;
    private TextView countAdminHRTextView;
    private TextView countServiceTextView;
    private TextView countManufacturingTextView;
    private TextView countSalesMarketingTextView;
    private TextView countEducationTrainingTextView;
    private TextView countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question4() {
        // Required empty public constructor
    }

    public static Question4 newInstance(String param1, String param2) {
        Question4 fragment = new Question4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countComputerITTextView = view.findViewById(R.id.countComputerITTextView);
        countAccountingFinanceTextView = view.findViewById(R.id.countAccountingFinanceTextView);
        countAdminHRTextView = view.findViewById(R.id.countAdminHRTextView);
        countServiceTextView = view.findViewById(R.id.countServiceTextView);
        countManufacturingTextView = view.findViewById(R.id.countManufacturingTextView);
        countSalesMarketingTextView = view.findViewById(R.id.countSalesMarketingTextView);
        countEducationTrainingTextView = view.findViewById(R.id.countEducationTrainingTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);


        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> computerITTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Computer/IT").get();
        Task<QuerySnapshot> accountingFinanceTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Accounting/Finance").get();
        Task<QuerySnapshot> adminHRTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Admin/HR").get();
        Task<QuerySnapshot> serviceTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Service").get();
        Task<QuerySnapshot> manufacturingTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Manufacturing").get();
        Task<QuerySnapshot> salesMarketingTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Sales/Marketing").get();
        Task<QuerySnapshot> educationTrainingTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Education/Training").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question4", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                computerITTask,
                accountingFinanceTask,
                adminHRTask,
                serviceTask,
                manufacturingTask,
                salesMarketingTask,
                educationTrainingTask,
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
                                String question1Value = document.getString("question4");
                                if (question1Value.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countComputerIT = computerITTask.getResult().size();
                                        int countAccountingFinance = accountingFinanceTask.getResult().size();
                                        int countAdminHR = adminHRTask.getResult().size();
                                        int countService = serviceTask.getResult().size();
                                        int countManufacturing = manufacturingTask.getResult().size();
                                        int countSalesMarketing = salesMarketingTask.getResult().size();
                                        int countEducationTraining = educationTrainingTask.getResult().size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countComputerIT + countAccountingFinance + countAdminHR + countService +
                                                countManufacturing + countSalesMarketing + countEducationTraining + countOther;

                                        float percentageComputerIT = ((float) countComputerIT / totalCount) * 100;
                                        float percentageAccountingFinance = ((float) countAccountingFinance / totalCount) * 100;
                                        float percentageAdminHR = ((float) countAdminHR / totalCount) * 100;
                                        float percentageService = ((float) countService / totalCount) * 100;
                                        float percentageManufacturing = ((float) countManufacturing / totalCount) * 100;
                                        float percentageSalesMarketing = ((float) countSalesMarketing / totalCount) * 100;
                                        float percentageEducationTraining = ((float) countEducationTraining / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countComputerITTextView.setText(String.valueOf(countComputerIT));
                                        countAccountingFinanceTextView.setText(String.valueOf(countAccountingFinance));
                                        countAdminHRTextView.setText(String.valueOf(countAdminHR));
                                        countServiceTextView.setText(String.valueOf(countService));
                                        countManufacturingTextView.setText(String.valueOf(countManufacturing));
                                        countSalesMarketingTextView.setText(String.valueOf(countSalesMarketing));
                                        countEducationTrainingTextView.setText(String.valueOf(countEducationTraining));
                                        countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageComputerIT, "Computer/IT"));
                                        entries.add(new PieEntry(percentageAccountingFinance, "Accounting/Finance"));
                                        entries.add(new PieEntry(percentageAdminHR, "Admin/HR"));
                                        entries.add(new PieEntry(percentageService, "Service"));
                                        entries.add(new PieEntry(percentageManufacturing, "Manufacturing"));
                                        entries.add(new PieEntry(percentageSalesMarketing, "Sales/Marketing"));
                                        entries.add(new PieEntry(percentageEducationTraining, "Education/Training"));
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
