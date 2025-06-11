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

/**
 * Adapter class for displaying a list of plants in a RecyclerView.
 * Binds plant data to the item views and handles view creation.
 *
 * @author Roni Zuckerman
 */
public class plantAdapter extends RecyclerView.Adapter<plantAdapter.PlantViewHolder> {

    /** The context in which the adapter is running. */
    private Context context;
    /** The list of plant objects to display. */
    private List<plant> plantList;

    /**
     * Constructs a new plantAdapter.
     *
     * @param context the context in which the adapter is used
     * @param plantList the list of plants to display
     */
    public plantAdapter(Context context, List<plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    /**
     * Called when RecyclerView needs a new {@link PlantViewHolder} of the given type to represent an item.
     *
     * @param parent the parent ViewGroup into which the new view will be added
     * @param viewType the view type of the new View
     * @return a new PlantViewHolder that holds a View for each plant item
     */
    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * Binds the plant data to the item views.
     *
     * @param holder the PlantViewHolder to update
     * @param position the position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
         plant plant = plantList.get(position);
        holder.textViewPlantName.setText(plant.getName());
        holder.textViewWateringFrequency.setText(plant.getWateringFrequency());
        holder.textViewSoilType.setText(plant.getSoilType());
        // Load plant image using Glide library
        Glide.with(context).load(plant.getPicture()).into(holder.imageViewPlant);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return the number of plant items
     */
    @Override
    public int getItemCount() {
        return plantList.size();
    }

    /**
     * ViewHolder class for holding references to the views for each plant item.
     */
    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        /** TextView for displaying the plant's name. */
        TextView textViewPlantName;
        /** TextView for displaying the plant's watering frequency. */
        TextView textViewWateringFrequency;
        /** TextView for displaying the plant's soil type. */
        TextView textViewSoilType;
        /** ImageView for displaying the plant's picture. */
        ImageView imageViewPlant;

        /**
         * Constructs a new PlantViewHolder and initializes the item views.
         *
         * @param itemView the view of the plant item
         */
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlantName = itemView.findViewById(R.id.textViewPlantName);
            textViewWateringFrequency = itemView.findViewById(R.id.textViewWateringFrequency);
            textViewSoilType = itemView.findViewById(R.id.textViewSoilType);
            imageViewPlant = itemView.findViewById(R.id.imageViewPlant);
        }
    }
}