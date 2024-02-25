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

public class CivilStatus extends Fragment {
    FirebaseFirestore fStore;
    private PieChart civilStatusPieChart;

    // TextViews to display counts
    private TextView countSingleTextView;
    private TextView countMarriedTextView;
    private TextView countSeparatedTextView;
    private TextView countOtherTextView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CivilStatus() {
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
        return inflater.inflate(R.layout.fragment_civil_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        civilStatusPieChart = view.findViewById(R.id.civilStatusPieChart);

        countSingleTextView = view.findViewById(R.id.countSingleTextView);
        countMarriedTextView = view.findViewById(R.id.countMarriedTextView);
        countSeparatedTextView = view.findViewById(R.id.countSeparatedTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        ArrayList<PieEntry> entries = new ArrayList<>();

// Tasks to fetch data from Firestore
        Task<QuerySnapshot> singleTask = fStore.collection("Users").whereEqualTo("userCivilStatus", "Single").get();
        Task<QuerySnapshot> marriedTask = fStore.collection("Users").whereEqualTo("userCivilStatus", "Married").get();
        Task<QuerySnapshot> separatedTask = fStore.collection("Users").whereEqualTo("userCivilStatus", "Separated").get();
        Task<QuerySnapshot> otherTask = fStore.collection("Users").whereEqualTo("userCivilStatus", "Other:").get();

// Combining all tasks
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                singleTask,
                marriedTask,
                separatedTask,
                otherTask
        );

// Adding onCompleteListener to handle the result
        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {
                    int countSingle = task.getResult().get(0).size();
                    int countMarried = task.getResult().get(1).size();
                    int countSeparated = task.getResult().get(2).size();
                    int countOther = task.getResult().get(3).size();

                    int totalCount = countSingle + countMarried + countSeparated + countOther;

                    float percentageSingle = ((float) countSingle / totalCount) * 100;
                    float percentageMarried = ((float) countMarried / totalCount) * 100;
                    float percentageSeparated = ((float) countSeparated / totalCount) * 100;
                    float percentageOther = ((float) countOther / totalCount) * 100;

                    // Assuming countSingleTextView, countMarriedTextView, countSeparatedTextView, countOtherTextView are TextViews
                    countSingleTextView.setText(String.valueOf(countSingle));
                    countMarriedTextView.setText(String.valueOf(countMarried));
                    countSeparatedTextView.setText(String.valueOf(countSeparated));
                    countOtherTextView.setText(String.valueOf(countOther));

                    // Add entries for pie chart
                    entries.add(new PieEntry(percentageSingle, "Single"));
                    entries.add(new PieEntry(percentageMarried, "Married"));
                    entries.add(new PieEntry(percentageSeparated, "Separated"));
                    entries.add(new PieEntry(percentageOther, "Other"));

                    // Populate the PieChart with the data
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
        civilStatusPieChart.setData(pieData);

        civilStatusPieChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = civilStatusPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(false);

        civilStatusPieChart.getDescription().setEnabled(false);
        civilStatusPieChart.animateY(1000);

//        questionPieChart.setExtraOffsets(30f, 0f, 0f, 0f);

        civilStatusPieChart.invalidate();
    }
}