package com.mart.petsmart;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mart.petsmart.adapter.PostViewAdapter;
import com.mart.petsmart.adapter.ProfilePostAdapter;
import com.mart.petsmart.model.PostViewModel;
import com.mart.petsmart.model.User;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity implements ProfilePostAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ProfilePostAdapter profileViewAdapter;
    private FirebaseFirestore firebaseFirestore;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseReference;
    private List<PostViewModel> postViewModelsList;
    private ProgressDialog progressDialog;

   private String profileId,profileImageUrl,profileName,profileEmail;
   private TextView userName;
   private Button editProfile;
   //,userEmail;
  private   CircleImageView userprofileImage;

    private void openDetailActivity(String[] data){
        Intent intent=new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("PROFILE_NAME_KEY",data[0]);
        intent.putExtra("PET_TITLE_KEY",data[1]);
        intent.putExtra("DISTRICT_KEY",data[2]);
        intent.putExtra("POST_IMAGE_URL_KEY",data[3]);
        intent.putExtra("UPLOAD_AT_KEY",data[4]);
        intent.putExtra("DESCRIPTION_KEY",data[5]);
        intent.putExtra("PRICE_KEY",data[6]);
        intent.putExtra("PHONE_NUMBER_KEY",data[7]);
        intent.putExtra("CATEGORY_KEY",data[8]);
        intent.putExtra("ANIMAL_TYPE_KEY",data[9]);


        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_activity);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        editProfile=findViewById(R.id.button_edit_profile);


        userName=findViewById(R.id.text_view_user_profile_user_name);
     //   userEmail=findViewById(R.id.text_view_user_profile_user_email_address);
        userprofileImage=findViewById(R.id.image_view_user_profile_image);

//        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
//        if(signInAccount != null){
//           // userName.setText(signInAccount.getDisplayName());
//           // userEmail.setText(signInAccount.getEmail());
////            Picasso.get()
////                    .load(signInAccount.getPhotoUrl())
////                    .fit().centerCrop()
////                    .into(userprofileImage);
//        }


        mRecyclerView =findViewById(R.id.recycler_view_user_profile);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    //    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModelsList =new ArrayList<>();
        profileViewAdapter =new ProfilePostAdapter(UserProfileActivity.this, postViewModelsList);
        mRecyclerView.setAdapter(profileViewAdapter);
        profileViewAdapter.setOnItemClickListener(UserProfileActivity.this);


        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();;
        EventChangeListener();
        EventChangeListenerProfileDetails();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String profileId,profileImageUrl,profileName,profileEmail;

                Intent i = new Intent(UserProfileActivity.this, UserProfileDataActivity.class);
                startActivity(i);
            }
        });


    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date=new Date();
        String today=dateFormat.format(date);

        return today;
    }
    private void EventChangeListener(){

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        
        firebaseFirestore.collection("users").document(signInAccount.getId()).collection("uploads")
             //     .orderBy("uploadedAt")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if(progressDialog.isShowing())
                              //  postViewModelsList.clear();
                                progressDialog.dismiss();
                            Log.e("FireStore error",error.getMessage());
                            return;
                        }

                        for (DocumentSnapshot dc: value.getDocuments()){

                            System.out.println("Document dataaaaa   "+value.getDocuments());


                            dc.getReference().getParent().getParent().addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                    PostViewModel postViewModel=new PostViewModel();


                                    postViewModel.setPostByProfileName(value.get( "profileName").toString());
                                    postViewModel.setTitle(dc.get( "title").toString());
                                    postViewModel.setDistrict(dc.get( "district").toString());
                                    postViewModel.setPostByProfileImageUrl(value.get( "profileImageUrl").toString());
                                    postViewModel.setPostImageUrl(dc.get( "postImageUrl").toString());
                                    postViewModel.setUploadedAt(dc.get( "uploadedAt").toString());
                                    postViewModel.setDescription(dc.get( "description").toString());
                                    postViewModel.setPrice(Double.valueOf(dc.get( "price").toString()));
                                    postViewModel.setPhoneNumber(Integer.parseInt(dc.get( "phoneNumber").toString()));
                                    postViewModel.setCategory(dc.get( "category").toString());
                                    postViewModel.setAnimalType(dc.get( "animalType").toString());
                                    postViewModel.setId(dc.get("id").toString());

                                    postViewModelsList.add(postViewModel);

                                }
                            });

                            profileViewAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });


    }

    private void EventChangeListenerProfileDetails(){

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        firebaseFirestore.collection("users").document(signInAccount.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       // String profileId,profileImageUrl,profileName,profileEmail;
                        if (documentSnapshot != null) {

                            profileId=documentSnapshot.get("profileId").toString();
                            profileImageUrl=documentSnapshot.get("profileImageUrl").toString();
                            profileName=documentSnapshot.get("profileName").toString();
                            profileEmail=documentSnapshot.get("userEmail").toString();

                            userName.setText(profileName);
                            Picasso.get()
                                    .load(profileImageUrl)
                                    .fit().centerCrop()
                                    .into(userprofileImage);
                        } else {
                            Log.d("LOGGER", "No such document");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void onItemClick(int position) {
        PostViewModel clickedPostViewModel=postViewModelsList.get(position);
        String[] postViewData={clickedPostViewModel.getPostByProfileName(),clickedPostViewModel.getTitle(),
                clickedPostViewModel.getDistrict(),clickedPostViewModel.getPostImageUrl(),clickedPostViewModel.getUploadedAt().toString(),
                clickedPostViewModel.getDescription(),clickedPostViewModel.getPrice().toString(),Integer.toString(clickedPostViewModel.getPhoneNumber()),
                clickedPostViewModel.getCategory(),clickedPostViewModel.getAnimalType()};

        openDetailActivity(postViewData);


    }

    @Override
    public void onShowItemClick(int position) {

        PostViewModel clickedPostViewModel=postViewModelsList.get(position);
        String[] postViewData={clickedPostViewModel.getPostByProfileName(),clickedPostViewModel.getTitle(),
                clickedPostViewModel.getDistrict(),clickedPostViewModel.getPostImageUrl(),clickedPostViewModel.getUploadedAt().toString(),
                clickedPostViewModel.getDescription(),clickedPostViewModel.getPrice().toString(),Integer.toString(clickedPostViewModel.getPhoneNumber()),
                clickedPostViewModel.getCategory(),clickedPostViewModel.getAnimalType()};
        openDetailActivity(postViewData);


    }

    @Override
    public void onDeleteItemClick(int position) {
        new AlertDialog.Builder(UserProfileActivity.this)
                .setIcon(R.drawable.ic_delete_24)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete this item ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PostViewModel clickedPostViewModel = postViewModelsList.get(position);
                        final String selectedKey = clickedPostViewModel.getPostImageUrl();
                        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(UserProfileActivity.this);
                        firebaseFirestore.collection("users").document(signInAccount.getId()).collection("uploads").document(postViewModelsList.get(position).getId()
                                )
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(UserProfileActivity.this, "Item successfully deleted!", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }

    @Override
    public void onUpdateItemClick(int position) {
        String id=postViewModelsList.get(position).getId();
         String title=postViewModelsList.get(position).getTitle();
         String postImageUrl=postViewModelsList.get(position).getPostImageUrl();
         String uploadedAt=postViewModelsList.get(position).getUploadedAt();
         String description=postViewModelsList.get(position).getDescription();
         String price= String.valueOf(postViewModelsList.get(position).getPrice());
         String phoneNumber= String.valueOf(postViewModelsList.get(position).getPhoneNumber());
         String category=postViewModelsList.get(position).getCategory();
         String animalType=postViewModelsList.get(position).getAnimalType();
         String district=postViewModelsList.get(position).getDistrict();

         Intent intent=new Intent(this,AddPostMarcketPlaceActivity.class);
            intent.putExtra("PET_ID",id);
            intent.putExtra("PET_TITLE_DATA",title);
            intent.putExtra("POST_IMAGE_URL_DATA",postImageUrl);
            intent.putExtra("UPLOAD_AT_DATA",uploadedAt);
            intent.putExtra("DESCRIPTION_DATA",description);
            intent.putExtra("PRICE_DATA",price);
            intent.putExtra("PHONE_NUMBER_DATA",phoneNumber);
            intent.putExtra("CATEGORY_DATA",category);
            intent.putExtra("ANIMAL_TYPE_DATA",animalType);
            intent.putExtra("DISTRICT_DATA",district);

            startActivity(intent);

    }
}