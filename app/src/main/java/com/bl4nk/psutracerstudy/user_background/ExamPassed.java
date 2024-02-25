package com.bl4nk.psutracerstudy.user_background;

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

public class ExamPassed extends Fragment {

    private FirebaseFirestore fStore;

    private PieChart examPassedPieChart;
    private TextView countCivilServiceSubProfessionalTextView;
    private TextView countCivilServiceProfessionalTextView;
    private TextView countCivilServiceCESOTextView;
    private TextView countLETTextView;
    private TextView countNoneTextView;
    private TextView countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamPassed() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_exam_passed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        countCivilServiceSubProfessionalTextView = view.findViewById(R.id.countCivilServiceSubProfessionalTextView);
        countCivilServiceProfessionalTextView = view.findViewById(R.id.countCivilServiceProfessionalTextView);
        countCivilServiceCESOTextView = view.findViewById(R.id.countCivilServiceCESOTextView);
        countLETTextView = view.findViewById(R.id.countLETTextView);
        countNoneTextView = view.findViewById(R.id.countNoneTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        examPassedPieChart = view.findViewById(R.id.examPassedPieChart);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> academicDistinctionTask = fStore.collection("Users").whereEqualTo("userHonor", "Academic Distinction").get();
        Task<QuerySnapshot> summaCumLaudeTask = fStore.collection("Users").whereEqualTo("userHonor", "Summa Cum Laude").get();
        Task<QuerySnapshot> magnaCumLaudeTask = fStore.collection("Users").whereEqualTo("userHonor", "Magna Cum Laude").get();
        Task<QuerySnapshot> cumLaudeTask = fStore.collection("Users").whereEqualTo("userHonor", "Cum Laude").get();
        Task<QuerySnapshot> noneTask = fStore.collection("Users").whereEqualTo("userHonor", "None").get();
        Task<QuerySnapshot> otherTask = fStore.collection("Users").whereEqualTo("userHonor", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                academicDistinctionTask,
                summaCumLaudeTask,
                magnaCumLaudeTask,
                cumLaudeTask,
                noneTask,
                otherTask
        );

        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {
                    // Get counts from each query result
                    int countAcademicDistinction = task.getResult().get(0).size();
                    int countSummaCumLaude = task.getResult().get(1).size();
                    int countMagnaCumLaude = task.getResult().get(2).size();
                    int countCumLaude = task.getResult().get(3).size();
                    int countNone = task.getResult().get(4).size();
                    int countOther = task.getResult().get(5).size();

                    // Set counts to TextViews
                    countCivilServiceSubProfessionalTextView.setText(String.valueOf(countAcademicDistinction));
                    countCivilServiceProfessionalTextView.setText(String.valueOf(countSummaCumLaude));
                    countCivilServiceCESOTextView.setText(String.valueOf(countMagnaCumLaude));
                    countLETTextView.setText(String.valueOf(countCumLaude));
                    countNoneTextView.setText(String.valueOf(countNone));
                    countOtherTextView.setText(String.valueOf(countOther));

                    // Add entries for pie chart
                    entries.add(new PieEntry(countAcademicDistinction, "Academic Distinction"));
                    entries.add(new PieEntry(countSummaCumLaude, "Summa Cum Laude"));
                    entries.add(new PieEntry(countMagnaCumLaude, "Magna Cum Laude"));
                    entries.add(new PieEntry(countCumLaude, "Cum Laude"));
                    entries.add(new PieEntry(countNone, "None"));
                    entries.add(new PieEntry(countOther, "Other"));

                    // Setup PieChart with entries
                    setupPieChart(entries);
                } else {
                    Log.e("BatchYearFragment", "Error getting documents: ", task.getException());
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
        examPassedPieChart.setData(pieData);

        examPassedPieChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = examPassedPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(false);

        examPassedPieChart.getDescription().setEnabled(false);
        examPassedPieChart.animateY(1000);

//        questionPieChart.setExtraOffsets(30f, 0f, 0f, 0f);

        examPassedPieChart.invalidate();
    }
}