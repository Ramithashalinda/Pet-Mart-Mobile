package com.mart.petsmart;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import com.mart.petsmart.model.CommunityModel;
import com.mart.petsmart.model.PostViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity implements PostViewAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private PostViewAdapter postViewAdapter;
    private FirebaseFirestore firebaseFirestore;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseReference;
    private List<PostViewModel> postViewModelsList;
    private ProgressDialog progressDialog;

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

        mRecyclerView =findViewById(R.id.recycler_view_user_profile);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModelsList =new ArrayList<>();
        postViewAdapter=new PostViewAdapter(UserProfileActivity.this, postViewModelsList);
        mRecyclerView.setAdapter(postViewAdapter);
        postViewAdapter.setOnItemClickListener(UserProfileActivity.this);

        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();;
        EventChangeListener();


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
                                    postViewModel.setUploadedAt(new Date());
                                    postViewModel.setDescription(dc.get( "description").toString());
                                    postViewModel.setPrice(Double.valueOf(dc.get( "price").toString()));
                                    postViewModel.setPhoneNumber(Integer.parseInt(dc.get( "phoneNumber").toString()));
                                    postViewModel.setCategory(dc.get( "category").toString());
                                    postViewModel.setAnimalType(dc.get( "animalType").toString());

                                    postViewModelsList.add(postViewModel);

                                }
                            });

                            postViewAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });


    }
    //String profileName, String petName, String location, String postImageUrl, Date uploadedAt, String description, Double price)

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
//        PostViewModel clickedPostViewModel=postViewModelsList.get(position);
//        final String selectedKey=clickedPostViewModel.getPostImageUrl();
//
//
//        firebaseFirestore.collection("uploads").document(clickedPostViewModel.getPostImageUrl())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//
//                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure( Exception e) {
//                        Log.w(TAG, "Error deleting document", e);
//                    }
//                });

    }
}