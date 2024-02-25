package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.EmailModel;
import com.bl4nk.psutracerstudy.user_background.Email;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailHolder> {

    Context context;
    List<EmailModel> list;

    public EmailAdapter(Context context, List<EmailModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);

        return new EmailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, int position) {
        holder.emailTv.setText(list.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class EmailHolder extends RecyclerView.ViewHolder{
        TextView emailTv;

        public EmailHolder(@NonNull View itemView) {
            super(itemView);

            emailTv = itemView.findViewById(R.id.userEmail);
        }
    }



}
