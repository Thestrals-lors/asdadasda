package com.bl4nk.psutracerstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bl4nk.psutracerstudy.user_fragment.Profile;
import com.bl4nk.psutracerstudy.user_fragment.SurveyQuestion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private int activeItemId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        clickListener();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == activeItemId) {
                    return true;
                }
                activeItemId = itemId;

                if(item.getItemId() == R.id.surveyTab){
                    replaceFragment(new SurveyQuestion());
                }
                else if(item.getItemId() == R.id.profileTab){
                    replaceFragment(new Profile());
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.surveyTab);
    }

    private void init(){
        frameLayout = findViewById(R.id.user_frameLayout);
        bottomNavigationView = findViewById(R.id.user_bottomNav);
    }

    private void clickListener() {
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void updateSelectedNavItem(int itemId) {
        bottomNavigationView.setSelectedItemId(itemId);
    }
}