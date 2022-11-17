//package com.mart.petsmart.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mart.petsmart.R;
//
//import com.mart.petsmart.model.User;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ImageViewHolder>{
//
//    private Context mContext;
//    private List<User> mUser;
//
//    public UserAdapter(Context context,List<User>users){
//        mContext=context;
//        mUser=users;
//    }
//    @Override
//    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_community, parent, false);
//        return new ImageViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ImageViewHolder holder, int position) {
//
//
//
//        User user=mUser.get(position);
//
//
//        holder.textViewProfileName.setText(user.getProfileName());
//        Picasso.get()
//                .load(user.getProfileImageUrl())
//                .fit().centerCrop()
//                .into(holder.imageViewProfileImage);
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return mUser.size();
//    }
//
//    public class ImageViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView textViewProfileName;
//        public ImageView imageViewProfileImage;
//
//
//
//        public ImageViewHolder(View itemView) {
//            super(itemView);
//
//            imageViewProfileImage = itemView.findViewById(R.id.image_view_profile);
//            textViewProfileName = itemView.findViewById(R.id.text_view_profile_name);
//
//        }
//    }
//}
