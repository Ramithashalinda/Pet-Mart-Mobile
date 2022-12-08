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

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ImageViewHolder>{

    private Context mContext;
    private List<PostViewModel> postViewModelList;
    private  OnItemClickListener mListener;

    public ProfilePostAdapter(Context context, List<PostViewModel> postViewModels){
        mContext=context;
        postViewModelList =postViewModels;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.profile_post_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        PostViewModel postViewModel= postViewModelList.get(position);

//
//        holder.textViewDateTime.setText(String.valueOf(postViewModel.getUploadedAt()));
//
//        holder.textViewTitle.setText(String.valueOf(postViewModel.getTitle()));
//


        Picasso.get()
                .load(postViewModel.getPostImageUrl())
                .fit().centerCrop()
                .into(holder.imageViewPostImage);





    }

    @Override
    public int getItemCount() {

        return postViewModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

       // private TextView textViewDateTime,textViewTitle;
        private ImageView imageViewPostImage;



        public ImageViewHolder(View itemView) {
            super(itemView);

            imageViewPostImage = itemView.findViewById(R.id.image_view_upload);

        //    textViewTitle = itemView.findViewById(R.id.text_view_profile_pet_title);
        //    textViewDateTime = itemView.findViewById(R.id.text_profile_dateAt);


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
                        case 3:
                            mListener.onUpdateItemClick(position);
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
            MenuItem updateItem=contextMenu.add(Menu.NONE,3,3,"Update");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
            updateItem.setOnMenuItemClickListener(this);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
        void onUpdateItemClick(int position);
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
