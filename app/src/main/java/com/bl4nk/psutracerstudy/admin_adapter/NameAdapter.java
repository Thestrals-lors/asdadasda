package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.NameModel;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameHolder>{

    Context context;
    List<NameModel> list;


    public NameAdapter(Context context, List<NameModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);

        return new NameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameHolder holder, int position) {

        String fullName = list.get(position).getFirstName() + " " +  list.get(position).getMiddleName() + " " + list.get(position).getLastName();

        holder.nameTv.setText(fullName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NameHolder extends RecyclerView.ViewHolder{

        TextView nameTv;

        public NameHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.userEmail);
        }
    }
}
