package com.mart.petsmart.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mart.petsmart.R;

import com.mart.petsmart.model.PostViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ImageViewHolder>{

    private Context mContext;
    private List<PostViewModel> postViewModelList;
    private  OnItemClickListener mListener;

    public PostViewAdapter(Context context, List<PostViewModel> postViewModels){
        mContext=context;
        postViewModelList =postViewModels;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_post_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        PostViewModel postViewModel= postViewModelList.get(position);

        holder.textViewPostByProfileName.setText(String.valueOf(postViewModel.getPostByProfileName()));
        holder.textViewPrice.setText(String.valueOf(postViewModel.getPrice()));
        holder.textViewDateTime.setText(String.valueOf(postViewModel.getUploadedAt()));
        holder.textViewPhoneNumber.setText(String.valueOf(postViewModel.getPhoneNumber()));
        holder.textViewDistrict.setText(String.valueOf(postViewModel.getDistrict()));
        holder.textViewTitle.setText(String.valueOf(postViewModel.getTitle()));



        Picasso.get()
                .load(postViewModel.getPostImageUrl())
                .fit().centerCrop()
                .into(holder.imageViewPostImage);

        Picasso.get()
                .load(postViewModel.getPostByProfileImageUrl())
                .fit().centerCrop()
                .into(holder.imageViewPostByProfileImage);




    }

    @Override
    public int getItemCount() {

        return postViewModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewPostByProfileName,textViewDateTime,textViewDescription,textViewPrice,textViewPhoneNumber,textViewDistrict,textViewTitle, textViewCategory,textViewAnimalType;
        public ImageView imageViewPostByProfileImage,imageViewPostImage;



        public ImageViewHolder(View itemView) {
            super(itemView);



            textViewPostByProfileName = itemView.findViewById(R.id.text_view_post_user_name);
            textViewPrice = itemView.findViewById(R.id.text_view_post_price);
            textViewPhoneNumber = itemView.findViewById(R.id.text_view_post_phone_number);
            imageViewPostByProfileImage = itemView.findViewById(R.id.image_view_post_user_profile_image);
            imageViewPostImage = itemView.findViewById(R.id.image_view_post_petImageItem);
            textViewDistrict = itemView.findViewById(R.id.text_view_post_location);
            textViewTitle = itemView.findViewById(R.id.text_view_post_pet_name);
            textViewDateTime = itemView.findViewById(R.id.text_view_post_date_time);

//            textViewCategory = itemView.findViewById(R.id.text_view_post_pet_name);
//            textViewDateTime = itemView.findViewById(R.id.text_view_post_date_time);
//            textViewTitle = itemView.findViewById(R.id.text_view_post_pet_name);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position=getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View view) {

            if (mListener != null){
                int position= getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Select Action");
            MenuItem showItem=contextMenu.add(Menu.NONE,1,1,"Show");

            showItem.setOnMenuItemClickListener(this);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShowItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today=dateFormat.format(date);

        return today;
    }
}
