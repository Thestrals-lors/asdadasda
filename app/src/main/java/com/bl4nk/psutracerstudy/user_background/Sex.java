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

public class Sex extends Fragment {
    FirebaseFirestore fStore;
    private PieChart sexPirChart;

    int[] colors;

    private TextView countMale, countFemale;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Sex() {
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
        return inflater.inflate(R.layout.fragment_sex, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        sexPirChart = view.findViewById(R.id.sexPieChart);

        countMale = view.findViewById(R.id.countMale);
        countFemale = view.findViewById(R.id.countFemale);
        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> maleTask = fStore.collection("Users").whereEqualTo("userSex", "Male").get();
        Task<QuerySnapshot> femaleTask = fStore.collection("Users").whereEqualTo("userSex", "Female").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(maleTask, femaleTask);

        allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if (task.isSuccessful()) {

                    QuerySnapshot maleResult = task.getResult().get(0);
                    QuerySnapshot femaleResult = task.getResult().get(1);

                    int totalCount = maleResult.size() + femaleResult.size();

                    countMale.setText(maleResult.size()+"");
                    countFemale.setText(femaleResult.size()+"");

                    // Calculating percentages
                    float percentageMale = ((float) maleResult.size() / totalCount) * 100;
                    float percentageFemale = ((float) femaleResult.size() / totalCount) * 100;

                    entries.add(new PieEntry(percentageMale, "Male"));
                    entries.add(new PieEntry(percentageFemale, "Female"));

                    setupPieChart(entries);
                } else {
                    Log.e("GenderSummary", "Error getting documents: ", task.getException());
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
        sexPirChart.setData(pieData);

        sexPirChart.setHoleRadius(0f);
        pieDataSet.setSliceSpace(0f);

        Legend legend = sexPirChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(false);

        sexPirChart.getDescription().setEnabled(false);
        sexPirChart.animateY(1000);

//        questionPieChart.setExtraOffsets(30f, 0f, 0f, 0f);

        sexPirChart.invalidate();
    }
}