package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.BirthdayModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayHolder>{

    Context context;
    List<BirthdayModel> list;

    public BirthdayAdapter(Context context, List<BirthdayModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BirthdayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);

        return new BirthdayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayHolder holder, int position) {
        String formattedDate = formatDate(list.get(position).getBirthday());
        holder.birthdayTv.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class BirthdayHolder extends RecyclerView.ViewHolder{
        TextView birthdayTv;

        public BirthdayHolder(@NonNull View itemView) {
            super(itemView);

            birthdayTv = itemView.findViewById(R.id.userEmail);
        }
    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string in case of error
    }
}
