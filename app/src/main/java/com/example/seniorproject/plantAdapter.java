package com.example.seniorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seniorproject.R;

import java.util.List;

public class plantAdapter extends RecyclerView.Adapter<plantAdapter.PlantViewHolder> {

    private Context context;
    private List<plant> plantList;

    public plantAdapter(Context context, List<plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        plant plant = plantList.get(position);
        holder.textViewPlantName.setText(plant.getName());
        holder.textViewWateringFrequency.setText(plant.getWateringFrequency());
        holder.textViewSoilType.setText(plant.getSoilType());
        Glide.with(context).load(plant.getPicture()).into(holder.imageViewPlant);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlantName, textViewWateringFrequency, textViewSoilType;
        ImageView imageViewPlant;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlantName = itemView.findViewById(R.id.textViewPlantName);
            textViewWateringFrequency = itemView.findViewById(R.id.textViewWateringFrequency);
            textViewSoilType = itemView.findViewById(R.id.textViewSoilType);
            imageViewPlant = itemView.findViewById(R.id.imageViewPlant);
        }
    }
}

