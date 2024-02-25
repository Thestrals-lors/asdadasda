package com.bl4nk.psutracerstudy.user_background;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.EmailModel;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bl4nk.psutracerstudy.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Age extends Fragment {

    FirebaseFirestore fStore;
    CollectionReference reference;
    RecyclerView recyclerView;
    BarChart barChart;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Age() {
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
        return inflater.inflate(R.layout.fragment_age, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();
        reference = fStore.collection("Users");

        barChart = view.findViewById(R.id.barChart);

        loadAllAge();
    }

    private void loadAllAge() {
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value == null) {
                    return;
                }

                Map<String, Integer> ageCounts = new HashMap<>();

                // Count the occurrences of each age
                for (QueryDocumentSnapshot snapshot : value) {

                    if(snapshot.getString("userType").equals("Admin")){

                    }
                    else{
                        String userAge = snapshot.getString("userAge");
                        if (userAge != null) {
                            if (ageCounts.containsKey(userAge)) {
                                ageCounts.put(userAge, ageCounts.get(userAge) + 1);
                            } else {
                                ageCounts.put(userAge, 1);
                            }
                        }

                    }
                }

                // Create entries for the BarChart
                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>(); // List to hold age labels
                int index = 0;
                for (Map.Entry<String, Integer> entry : ageCounts.entrySet()) {
                    labels.add(entry.getKey()); // Add age as label
                    entries.add(new BarEntry(index++, entry.getValue()));
                }

                // Create a dataset and set its color
                BarDataSet dataSet = new BarDataSet(entries, "Age Distribution");
                dataSet.setColor(getResources().getColor(R.color.colorPrimary));

                // Create a BarData object
                BarData barData = new BarData(dataSet);

                // Customize X-axis appearance
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Set custom labels
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setCenterAxisLabels(true);

                // Set the data to the chart
                barChart.setData(barData);

                // Check if labels exist
                if (!labels.isEmpty()) {
                    // Refresh the chart
                    barChart.invalidate();
                } else {
                    // Handle the case when there are no labels
                    Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
