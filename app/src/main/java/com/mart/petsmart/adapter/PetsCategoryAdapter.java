package com.mart.petsmart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import com.mart.petsmart.PostViewActivity;
import com.mart.petsmart.R;
import com.mart.petsmart.model.PetsCategoryModel;


import java.util.List;

public class PetsCategoryAdapter extends RecyclerView.Adapter<PetsCategoryAdapter.PetsCategoryViewHolder> {

    private Context context;
    private List<PetsCategoryModel> petsCategoryModelsList;


    public PetsCategoryAdapter(Context contexts, List<PetsCategoryModel> categoryModels) {
        context = contexts;
        petsCategoryModelsList = categoryModels;
    }

    @Override
    public PetsCategoryViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pet_category_row_item, parent, false);
        return new PetsCategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PetsCategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.petCategoryImage.setImageResource(petsCategoryModelsList.get(position).getImageUrl());
        holder.petCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetsCategoryModel petsCategoryModel=petsCategoryModelsList.get(position);
                Intent i=new Intent(context, PostViewActivity.class);
                i.putExtra("animalType","Dog");
                i.putExtra("category",petsCategoryModel.getType());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return petsCategoryModelsList.size();
    }

    public static class PetsCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView petCategoryImage;


        public PetsCategoryViewHolder( View view){

            super(view);
            petCategoryImage = itemView.findViewById(R.id.image_view_pet_category);
        }
    }
}
