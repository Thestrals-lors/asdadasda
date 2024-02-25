package com.bl4nk.psutracerstudy.admin_components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bl4nk.psutracerstudy.AdminMain;
import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.adapter.UserPagerAdapter;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SurveySummary extends AppCompatActivity {
    private ImageButton backBtn;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_summary);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        backBtn = findViewById(R.id.backBtn);

        UserPagerAdapter userPagerAdapter = new UserPagerAdapter(this);
        viewPager.setAdapter(userPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Question1");
                    break;
                case 1:
                    tab.setText("Question2");
                    break;
                case 2:
                    tab.setText("Question3");
                    break;
                case 3:
                    tab.setText("Question4");
                    break;
                case 4:
                    tab.setText("Question5");
                    break;
                case 5:
                    tab.setText("Question6");
                    break;
                case 6:
                    tab.setText("Question7");
                    break;
                case 7:
                    tab.setText("Question8");
                    break;
                case 8:
                    tab.setText("Question9");
                    break;
                case 9:
                    tab.setText("Question10");
                    break;
                case 10:
                    tab.setText("Question11");
                    break;
                case 11:
                    tab.setText("Question12");
                    break;
                case 12:
                    tab.setText("Question13");
                    break;
                case 13:
                    tab.setText("Question14");
                    break;
                case 14:
                    tab.setText("Question15");
                    break;
                case 15:
                    tab.setText("Question16");
                    break;
                case 16:
                    tab.setText("Question17");
                    break;
            }
        }).attach();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SurveySummary.this, AdminMain.class);
        startActivity(intent);
        finish();
    }
}