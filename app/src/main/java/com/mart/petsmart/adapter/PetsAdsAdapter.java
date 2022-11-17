package com.mart.petsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import com.mart.petsmart.DashBoardActivity;

import com.mart.petsmart.R;
import com.mart.petsmart.model.PetsAdsModel;
import com.mart.petsmart.model.UploadItems;


import java.util.List;

public class PetsAdsAdapter extends RecyclerView.Adapter<PetsAdsAdapter.PetsAdsViewHolder> {

    private Context context;
    private List<PetsAdsModel> petsAdsModelsList;

    public PetsAdsAdapter(Context contexts, List<PetsAdsModel> petsAdsModels) {
        context = contexts;
        petsAdsModelsList = petsAdsModels;
    }

    @Override
    public PetsAdsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pet_ads_row_item, parent, false);
        return new PetsAdsViewHolder(view);
    }


    @Override
    public void onBindViewHolder( PetsAdsViewHolder holder, int position) {

        holder.petAdsImage.setImageResource(petsAdsModelsList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return petsAdsModelsList.size();
    }

    public static class PetsAdsViewHolder extends RecyclerView.ViewHolder{

        ImageView petAdsImage;

        public PetsAdsViewHolder( View view){

            super(view);
            petAdsImage = itemView.findViewById(R.id.image_view_pet_ads);


        }
    }
}
