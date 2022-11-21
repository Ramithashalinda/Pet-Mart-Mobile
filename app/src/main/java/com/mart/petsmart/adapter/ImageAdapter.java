package com.mart.petsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mart.petsmart.R;

import com.mart.petsmart.model.UploadItems;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    private Context mContext;
    private List<UploadItems> mUploads;

    public ImageAdapter(Context context,List<UploadItems>uploadItems){
        mContext=context;
        mUploads=uploadItems;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_post_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {



//        UploadItems uploadItems=mUploads.get(position);
//
//
//        holder.textViewUserName.setText(uploadItems.getUserName());
//        holder.textViewDescription.setText(uploadItems.getDescription());
//        holder.textViewPetPrice.setText(String.valueOf(uploadItems.getPrice()));
//
//
//
//        //Double.parseDouble(mEditTextPrice.getText().toString())
//
//        Picasso.get()
//                .load(uploadItems.getImageUrl())
//                .fit().centerCrop()
//                .into(holder.imageViewPetImageItem);




    }

    @Override
    public int getItemCount() {

        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewUserName,textViewDescription,textViewPetPrice;
        public ImageView imageViewPetImageItem;



        public ImageViewHolder(View itemView) {
            super(itemView);
//
//            imageViewPetImageItem = itemView.findViewById(R.id.image_view_petImageItem);
//          //  textViewUserName = itemView.findViewById(R.id.text_view_userName);
//            textViewDescription = itemView.findViewById(R.id.text_view_post_location);
//            textViewPetPrice = itemView.findViewById(R.id.text_view_post_user_name);

        }
    }
}
