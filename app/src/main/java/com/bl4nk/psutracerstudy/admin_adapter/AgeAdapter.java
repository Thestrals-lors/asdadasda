package com.bl4nk.psutracerstudy.admin_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bl4nk.psutracerstudy.admin_model.AgeModel;

import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.AgeHolder> {

    Context context;
    List<AgeModel> list;

    public AgeAdapter(Context context, List<AgeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AgeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AgeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class AgeHolder extends RecyclerView.ViewHolder {

        public AgeHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
