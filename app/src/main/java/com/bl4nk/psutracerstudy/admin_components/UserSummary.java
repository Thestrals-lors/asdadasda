package com.bl4nk.psutracerstudy.admin_components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bl4nk.psutracerstudy.AdminMain;
import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.adapter.InfoSummaryAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserSummary extends AppCompatActivity {
    private ImageButton backBtn;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_summary);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        backBtn = findViewById(R.id.backBtn);

        InfoSummaryAdapter infoSummaryAdapter = new InfoSummaryAdapter(this);
        viewPager.setAdapter(infoSummaryAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Email");
                    break;
                case 1:
                    tab.setText("Name");
                    break;
                case 2:
                    tab.setText("Age");
                    break;
                case 3:
                    tab.setText("Sex");
                    break;
                case 4:
                    tab.setText("Address");
                    break;
                case 5:
                    tab.setText("Birthday");
                    break;
                case 6:
                    tab.setText("Phone Number");
                    break;
                case 7:
                    tab.setText("Civil Status");
                    break;
                case 8:
                    tab.setText("Batch Year");
                    break;
                case 9:
                    tab.setText("Honors");
                    break;
                case 10:
                    tab.setText("Exam Passed");
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
        Intent intent = new Intent(UserSummary.this, AdminMain.class);
        startActivity(intent);
        finish();
    }
}