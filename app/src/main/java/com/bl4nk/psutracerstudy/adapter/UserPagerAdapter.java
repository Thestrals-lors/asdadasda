package com.bl4nk.psutracerstudy.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bl4nk.psutracerstudy.question.Question1;
import com.bl4nk.psutracerstudy.question.Question10;
import com.bl4nk.psutracerstudy.question.Question11;
import com.bl4nk.psutracerstudy.question.Question12;
import com.bl4nk.psutracerstudy.question.Question13;
import com.bl4nk.psutracerstudy.question.Question14;
import com.bl4nk.psutracerstudy.question.Question15;
import com.bl4nk.psutracerstudy.question.Question16;
import com.bl4nk.psutracerstudy.question.Question17;
import com.bl4nk.psutracerstudy.question.Question2;
import com.bl4nk.psutracerstudy.question.Question3;
import com.bl4nk.psutracerstudy.question.Question4;
import com.bl4nk.psutracerstudy.question.Question5;
import com.bl4nk.psutracerstudy.question.Question6;
import com.bl4nk.psutracerstudy.question.Question7;
import com.bl4nk.psutracerstudy.question.Question8;
import com.bl4nk.psutracerstudy.question.Question9;

public class UserPagerAdapter extends FragmentStateAdapter {
    private static final int TAB_ITEM_SIZE = 17;

    public UserPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Question1();
            case 1:
                return new Question2();
            case 2:
                return new Question3();
            case 3:
                return new Question4();
            case 4:
                return new Question5();
            case 5:
                return new Question6();
            case 6:
                return new Question7();
            case 7:
                return new Question8();
            case 8:
                return new Question9();
            case 9:
                return new Question10();
            case 10:
                return new Question11();
            case 11:
                return new Question12();
            case 12:
                return new Question13();
            case 13:
                return new Question14();
            case 14:
                return new Question15();
            case 15:
                return new Question16();
            case 16:
                return new Question17();
            default:
                return new Question1(); // or any default fragment you want
        }

    }

    @Override
    public int getItemCount() {
        return TAB_ITEM_SIZE;
    }
}
