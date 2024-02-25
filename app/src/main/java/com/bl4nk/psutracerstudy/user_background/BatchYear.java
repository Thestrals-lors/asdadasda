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

public class BatchYear extends Fragment {

    private TextView countBatch2019TextView, countBatch2022TextView, countBatch2023TextView;
    private FirebaseFirestore fStore;
    private PieChart batchYearPieChart;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public BatchYear() {
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
        return inflater.inflate(R.layout.fragment_batch_year, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countBatch2019TextView = view.findViewById(R.id.countBatch2019TextView);
        countBatch2022TextView = view.findViewById(R.id.countBatch2022TextView);
        countBatch2023TextView = view.findViewById(R.id.countBatch2023TextView);
        batchYearPieChart = view.findViewById(R.id.batchYearPieChart);

        fStore = FirebaseFirestore.getInstance();

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> batch2019Task = fStore.collection("Users").whereEqualTo("userBatchYear", "2019").get();
        Task<QuerySnapshot> batch2022Task = fStore.collection("Users").whereEqualTo("userBatchYear", "2022").get();
        Task<QuerySnapshot> batch2023Task = fStore.collection("Users").whereEqualTo("userBatchYear", "2023").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                batch2019Task,
                batch2022Task,
                batch2023Task
        );

        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {
                    int countBatch2019 = task.getResult().get(0).size();
                    int countBatch2022 = task.getResult().get(1).size();
                    int countBatch2023 = task.getResult().get(2).size();

                    countBatch2019TextView.setText(String.valueOf(countBatch2019));
                    countBatch2022TextView.setText(String.valueOf(countBatch2022));
                    countBatch2023TextView.setText(String.valueOf(countBatch2023));

                    entries.add(new PieEntry(countBatch2019, "Batch 2019"));
                    entries.add(new PieEntry(countBatch2022, "Batch 2022"));
                    entries.add(new PieEntry(countBatch2023, "Batch 2023"));

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
        batchYearPieChart.setData(pieData);

        batchYearPieChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = batchYearPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(false);

        batchYearPieChart.getDescription().setEnabled(false);
        batchYearPieChart.animateY(1000);

//        questionPieChart.setExtraOffsets(30f, 0f, 0f, 0f);

        batchYearPieChart.invalidate();
    }
}