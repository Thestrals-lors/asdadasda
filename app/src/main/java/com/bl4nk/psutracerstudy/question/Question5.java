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

public class Question5 extends Fragment {

    FirebaseFirestore fStore;
    int countNot = 0;
    private PieChart questionPieChart;

    int[] colors;

    private TextView countManufacturingTextView, countAcademeTextView, countHotelTextView, countServiceTextView,
            countPublicOfficeTextView, countRestaurantTextView;

    // TextViews for Retailing, Auditing, Financial, Agriculture, Other
    private TextView countRetailingTextView, countAuditingTextView, countFinancialTextView, countAgricultureTextView, countOtherTextView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Question5() {
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
        return inflater.inflate(R.layout.fragment_question5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        colors = generateColors();
        questionPieChart = view.findViewById(R.id.questionPieChart);

        countManufacturingTextView = view.findViewById(R.id.countManufacturingTextView);
        countAcademeTextView = view.findViewById(R.id.countAcademeTextView);
        countHotelTextView = view.findViewById(R.id.countHotelTextView);
        countServiceTextView = view.findViewById(R.id.countServiceTextView);
        countPublicOfficeTextView = view.findViewById(R.id.countPublicOfficeTextView);
        countRestaurantTextView = view.findViewById(R.id.countRestaurantTextView);

        countRetailingTextView = view.findViewById(R.id.countRetailingTextView);
        countAuditingTextView = view.findViewById(R.id.countAuditingTextView);
        countFinancialTextView = view.findViewById(R.id.countFinancialTextView);
        countAgricultureTextView = view.findViewById(R.id.countAgricultureTextView);
        countOtherTextView = view.findViewById(R.id.countOtherTextView);

        ArrayList<PieEntry> entries = new ArrayList<>();

        Task<QuerySnapshot> manufacturingTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Manufacturing").get();
        Task<QuerySnapshot> academeTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Academe").get();
        Task<QuerySnapshot> hotelTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Hotel").get();
        Task<QuerySnapshot> serviceTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Service").get();
        Task<QuerySnapshot> publicOfficeTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Public Office").get();
        Task<QuerySnapshot> restaurantTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Restaurant").get();
        Task<QuerySnapshot> retailingTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Retailing").get();
        Task<QuerySnapshot> auditingTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Auditing").get();
        Task<QuerySnapshot> financialTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Financial").get();
        Task<QuerySnapshot> agricultureTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Agriculture").get();
        Task<QuerySnapshot> otherTask = fStore.collection("SurveyAnswer").whereEqualTo("question5", "Other:").get();

        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                manufacturingTask,
                academeTask,
                hotelTask,
                serviceTask,
                publicOfficeTask,
                restaurantTask,
                retailingTask,
                auditingTask,
                financialTask,
                agricultureTask,
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
                                String question1Value = document.getString("question5");
                                if (question1Value.contains("Other")) {
                                    countNot++;
                                }
                            }
                            countOtherTextView.setText(String.valueOf(countNot));
                            allTasks.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                                    if (task.isSuccessful()) {
                                        int countManufacturing = manufacturingTask.getResult().size();
                                        int countAcademe = academeTask.getResult().size();
                                        int countHotel = hotelTask.getResult().size();
                                        int countService = serviceTask.getResult().size();
                                        int countPublicOffice = publicOfficeTask.getResult().size();
                                        int countRestaurant = restaurantTask.getResult().size();
                                        int countRetailing = retailingTask.getResult().size();
                                        int countAuditing = auditingTask.getResult().size();
                                        int countFinancial = financialTask.getResult().size();
                                        int countAgriculture = agricultureTask.getResult().size();
                                        int countOther = countNot; // Assuming "Other" includes the count from "Other:" category

                                        int totalCount = countManufacturing + countAcademe + countHotel + countService +
                                                countPublicOffice + countRestaurant + countRetailing + countAuditing +
                                                countFinancial + countAgriculture + countOther;

                                        float percentageManufacturing = ((float) countManufacturing / totalCount) * 100;
                                        float percentageAcademe = ((float) countAcademe / totalCount) * 100;
                                        float percentageHotel = ((float) countHotel / totalCount) * 100;
                                        float percentageService = ((float) countService / totalCount) * 100;
                                        float percentagePublicOffice = ((float) countPublicOffice / totalCount) * 100;
                                        float percentageRestaurant = ((float) countRestaurant / totalCount) * 100;
                                        float percentageRetailing = ((float) countRetailing / totalCount) * 100;
                                        float percentageAuditing = ((float) countAuditing / totalCount) * 100;
                                        float percentageFinancial = ((float) countFinancial / totalCount) * 100;
                                        float percentageAgriculture = ((float) countAgriculture / totalCount) * 100;
                                        float percentageOther = ((float) countOther / totalCount) * 100;

                                        countManufacturingTextView.setText(String.valueOf(countManufacturing));
                                        countAcademeTextView.setText(String.valueOf(countAcademe));
                                        countHotelTextView.setText(String.valueOf(countHotel));
                                        countServiceTextView.setText(String.valueOf(countService));
                                        countPublicOfficeTextView.setText(String.valueOf(countPublicOffice));
                                        countRestaurantTextView.setText(String.valueOf(countRestaurant));
                                        countRetailingTextView.setText(String.valueOf(countRetailing));
                                        countAuditingTextView.setText(String.valueOf(countAuditing));
                                        countFinancialTextView.setText(String.valueOf(countFinancial));
                                        countAgricultureTextView.setText(String.valueOf(countAgriculture));
                                        countOtherTextView.setText(String.valueOf(countOther));

                                        // Add entries for pie chart
                                        entries.add(new PieEntry(percentageManufacturing, "Manufacturing"));
                                        entries.add(new PieEntry(percentageAcademe, "Academe"));
                                        entries.add(new PieEntry(percentageHotel, "Hotel"));
                                        entries.add(new PieEntry(percentageService, "Service"));
                                        entries.add(new PieEntry(percentagePublicOffice, "Public Office"));
                                        entries.add(new PieEntry(percentageRestaurant, "Restaurant"));
                                        entries.add(new PieEntry(percentageRetailing, "Retailing"));
                                        entries.add(new PieEntry(percentageAuditing, "Auditing"));
                                        entries.add(new PieEntry(percentageFinancial, "Financial"));
                                        entries.add(new PieEntry(percentageAgriculture, "Agriculture"));
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
