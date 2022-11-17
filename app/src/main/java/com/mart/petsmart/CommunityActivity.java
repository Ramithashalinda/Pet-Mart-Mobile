package com.mart.petsmart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mart.petsmart.adapter.CommunityAdapter;
import com.mart.petsmart.model.CommunityModel;
import com.mart.petsmart.model.PostViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityActivity extends AppCompatActivity implements CommunityAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SearchView searchView;


    private FirebaseFirestore firebaseFirestore;

    private CommunityAdapter communityAdapter;

    private List<CommunityModel> communityModelList;

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
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_community_recycler_view);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();;

        mRecyclerView =findViewById(R.id.community_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        firebaseFirestore = FirebaseFirestore.getInstance();

        communityModelList =new ArrayList<>();
        communityAdapter =new CommunityAdapter(CommunityActivity.this,communityModelList);
        mRecyclerView.setAdapter(communityAdapter);

        communityAdapter.setOnItemClickListener(CommunityActivity.this);

        searchView=findViewById(R.id.test_view_community_search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        EventChangeListener();

    }
    private void EventChangeListener(){
        firebaseFirestore.collectionGroup("uploads")
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


                    dc.getReference().getParent().getParent().addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            CommunityModel communityModel=new CommunityModel();


                            communityModel.setPostByProfileName(value.get( "profileName").toString());
                            communityModel.setTitle(dc.get( "title").toString());
                            communityModel.setDistrict(dc.get( "district").toString());
                            communityModel.setPostByProfileImageUrl(value.get( "profileImageUrl").toString());
                            communityModel.setPostImageUrl(dc.get( "postImageUrl").toString());
                            communityModel.setUploadedAt(dc.get( "uploadedAt").toString());
                            communityModel.setDescription(dc.get( "description").toString());
                            communityModel.setPrice(Double.valueOf(dc.get( "price").toString()));
                            communityModel.setPhoneNumber(Integer.parseInt(dc.get( "phoneNumber").toString()));
                            communityModel.setCategory(dc.get( "category").toString());
                            communityModel.setAnimalType(dc.get( "animalType").toString());


                            communityModelList.add(communityModel);


                        }
                    });

                    communityAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });


    }


    @Override
    public void onItemClick(int position) {


        CommunityModel communityModel=communityModelList.get(position);
        String[] communityViewData={communityModel.getPostByProfileName(),communityModel.getTitle(),
                communityModel.getDistrict(),communityModel.getPostImageUrl(),communityModel.getUploadedAt().toString(),
                communityModel.getDescription(),communityModel.getPrice().toString(),Integer.toString(communityModel.getPhoneNumber()),
                communityModel.getCategory(),communityModel.getAnimalType()};

        openDetailActivity(communityViewData);
    }

    @Override
    public void onShowItemClick(int position) {


        CommunityModel communityModel=communityModelList.get(position);
        String[] communityViewData={communityModel.getPostByProfileName(),communityModel.getTitle(),
                communityModel.getDistrict(),communityModel.getPostImageUrl(),communityModel.getUploadedAt().toString(),
                communityModel.getDescription(),communityModel.getPrice().toString(),Integer.toString(communityModel.getPhoneNumber()),
                communityModel.getCategory(),communityModel.getAnimalType()};

        openDetailActivity(communityViewData);
    }

    @Override
    public void onDeleteItemClick(int position) {

    }

    private void filterList(String test) {
        List<CommunityModel> filteredList =new ArrayList<>();
        for (CommunityModel communityModel: communityModelList ){
            if (communityModel.getTitle().toLowerCase().contains(test.toLowerCase())){
                filteredList.add(communityModel);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            communityAdapter.setFilteredList(filteredList);
        }
    }
}