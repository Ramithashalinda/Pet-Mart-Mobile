package com.mart.petsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import com.mart.petsmart.R;
import com.mart.petsmart.model.PetNoticeModel;


import java.util.List;

public class PetNoticeAdapter extends RecyclerView.Adapter<PetNoticeAdapter.PetsNoticeViewHolder> {

    private Context context;
    private List<PetNoticeModel> petsNoticeModelsList;

    public PetNoticeAdapter(Context contexts, List<PetNoticeModel> petsNoticeModels) {
        context = contexts;
        petsNoticeModelsList = petsNoticeModels;

    }

    @Override
    public PetsNoticeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pet_notice_row_item, parent, false);
        return new PetsNoticeViewHolder(view);
    }


    @Override
    public void onBindViewHolder( PetsNoticeViewHolder holder, int position) {

        holder.petNoticeImage.setImageResource(petsNoticeModelsList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return petsNoticeModelsList.size();
    }

    public static class PetsNoticeViewHolder extends RecyclerView.ViewHolder{

        ImageView petNoticeImage;

        public PetsNoticeViewHolder( View view){

            super(view);
            petNoticeImage = itemView.findViewById(R.id.image_view_pet_notice);


        }
    }
}
