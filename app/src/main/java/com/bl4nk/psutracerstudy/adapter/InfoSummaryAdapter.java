package com.bl4nk.psutracerstudy.adapter;

import android.net.wifi.hotspot2.pps.HomeSp;

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
import com.bl4nk.psutracerstudy.question.Question8;
import com.bl4nk.psutracerstudy.question.Question9;
import com.bl4nk.psutracerstudy.user_background.Address;
import com.bl4nk.psutracerstudy.user_background.Age;
import com.bl4nk.psutracerstudy.user_background.BatchYear;
import com.bl4nk.psutracerstudy.user_background.Birthday;
import com.bl4nk.psutracerstudy.user_background.CivilStatus;
import com.bl4nk.psutracerstudy.user_background.Email;
import com.bl4nk.psutracerstudy.user_background.ExamPassed;
import com.bl4nk.psutracerstudy.user_background.Honors;
import com.bl4nk.psutracerstudy.user_background.Name;
import com.bl4nk.psutracerstudy.user_background.PhoneNumber;
import com.bl4nk.psutracerstudy.user_background.Sex;

public class InfoSummaryAdapter extends FragmentStateAdapter {
    private static final int TAB_ITEM_SIZE = 11;

    public InfoSummaryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Email();
            case 1:
                return new Name();
            case 2:
                return new Age();
            case 3:
                return new Sex();
            case 4:
                return new Address();
            case 5:
                return new Birthday();
            case 6:
                return new PhoneNumber();
            case 7:
                return new CivilStatus();
            case 8:
                return new BatchYear();
            case 9:
                return new Honors();
            case 10:
                return new ExamPassed();
            default:
                return new Email(); // or any default fragment you want
        }
    }

    @Override
    public int getItemCount() {
        return TAB_ITEM_SIZE;
    }
}
