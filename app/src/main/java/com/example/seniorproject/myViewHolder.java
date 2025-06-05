package com.example.seniorproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder {
     ImageView imageView;
     TextView nameView, descriptionView, treatmentView;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewPlant);
        nameView = itemView.findViewById(R.id.textViewdiseaseName);
        descriptionView = itemView.findViewById(R.id.textViewdescription);
        treatmentView = itemView.findViewById(R.id.textViewtreatment);

    }
}
