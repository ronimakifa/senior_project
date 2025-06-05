package com.example.seniorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class diseaseAdapter extends RecyclerView.Adapter<myViewHolder>{
    Context context;
    List<disease> diseaseList;

    public diseaseAdapter(Context context, List<disease> diseaseList) {
        this.context = context;
        this.diseaseList = diseaseList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.disease_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nameView.setText(diseaseList.get(position).getName());
        holder.descriptionView.setText(diseaseList.get(position).getDescription());
        holder.treatmentView.setText(diseaseList.get(position).getTreatment());
        holder.imageView.setImageResource(diseaseList.get(position).getPicture());
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }
}
