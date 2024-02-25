package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_model.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    Context context;
    List<AddressModel> list;

    public AddressAdapter(Context context, List<AddressModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_layout, parent, false);

        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.addressTv.setText(list.get(position).getUserAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AddressHolder extends RecyclerView.ViewHolder{

        TextView addressTv;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);

            addressTv = itemView.findViewById(R.id.userEmail);
        }
    }
}
