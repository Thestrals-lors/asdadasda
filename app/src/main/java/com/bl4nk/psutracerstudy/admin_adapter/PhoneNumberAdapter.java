package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.PhoneNumberModel;

import java.util.List;

public class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.PhoneNumberHolder>{

    Context context;
    List<PhoneNumberModel> list;

    public PhoneNumberAdapter(Context context, List<PhoneNumberModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhoneNumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);

        return new PhoneNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneNumberHolder holder, int position) {
        holder.phoneTv.setText(list.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class PhoneNumberHolder extends RecyclerView.ViewHolder{

        TextView phoneTv;

        public PhoneNumberHolder(@NonNull View itemView) {
            super(itemView);

            phoneTv = itemView.findViewById(R.id.userEmail);
        }
    }
}
