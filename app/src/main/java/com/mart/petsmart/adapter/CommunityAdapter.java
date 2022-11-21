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

import com.mart.petsmart.model.CommunityModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ImageViewHolder>{

    private Context mContext;
    private List<CommunityModel> communityModelsList;
    private  OnItemClickListener mListener;

    public void setFilteredList(List<CommunityModel> filteredList){
        this.communityModelsList=filteredList;
        notifyDataSetChanged();
    }


    public CommunityAdapter(Context context, List<CommunityModel> communityModels){
        mContext=context;
        communityModelsList =communityModels;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_community, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        CommunityModel communityModel= communityModelsList.get(position);

        holder.textViewProfileName.setText(String.valueOf(communityModel.getPostByProfileName()));
        holder.textViewDateTime.setText(communityModel.getUploadedAt().toString());
        holder.textViewDescription.setText(String.valueOf(communityModel.getTitle()));

        Picasso.get()
                .load(communityModel.getPostImageUrl())
                .fit().centerCrop()
                .into(holder.imageViewPostImage);

        Picasso.get()
                .load(communityModel.getPostByProfileImageUrl())
                .fit().centerCrop()
                .into(holder.imageViewProfileImage);




    }

    @Override
    public int getItemCount() {

        return communityModelsList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView textViewProfileName,textViewDateTime,textViewDescription;
        private ImageView imageViewProfileImage,imageViewPostImage;



        public ImageViewHolder(View itemView) {
            super(itemView);




            textViewProfileName = itemView.findViewById(R.id.text_view_profile_name);
            textViewDateTime = itemView.findViewById(R.id.text_date);
            textViewDescription = itemView.findViewById(R.id.text_view_text_title);


            imageViewProfileImage = itemView.findViewById(R.id.image_view_profile);
            imageViewPostImage = itemView.findViewById(R.id.image_item_post_image);


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
                        case 2:
                            mListener.onDeleteItemClick(position);
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
            MenuItem deleteItem=contextMenu.add(Menu.NONE,2,2,"Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
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
