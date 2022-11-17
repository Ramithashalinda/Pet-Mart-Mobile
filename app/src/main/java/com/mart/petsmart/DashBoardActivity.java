package com.mart.petsmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mart.petsmart.adapter.PetsAdsAdapter;
import com.mart.petsmart.adapter.PetsCategoryAdapter;
import com.mart.petsmart.model.PetsAdsModel;
import com.mart.petsmart.model.PetsCategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    TextView name;
    CircleImageView profileImage;
    ImageButton dashBoardMeanu,buttonFish,buttonCat,buttonBird,buttonDog;





    private RecyclerView petAdsRecyclerView;
    private List<PetsAdsModel> petAdsList;
    private PetsAdsAdapter petsAdsAdapter;



    private RecyclerView petCategoryRecyclerView;
    private List<PetsCategoryModel> petsCategoryList;
    private PetsCategoryAdapter petsCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dash_board);

        bottomNavigationView =(BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        name=findViewById(R.id.text_view_dashboard_profile_name);
        profileImage=findViewById(R.id.image_view_profile_image);
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            Picasso.get()
                    .load(signInAccount.getPhotoUrl())
                    .fit().centerCrop()
                    .into(profileImage);
        }
        petAdsRecyclerView=findViewById(R.id.recycler_view_dashboard_pets_ads);
        petAdsList=new ArrayList<>();
        petAdsList.add(new PetsAdsModel(1,R.drawable.petsbanner));
        petAdsList.add(new PetsAdsModel(2,R.drawable.petsaccbanner));
        petAdsList.add(new PetsAdsModel(3,R.drawable.petsaccbanner));
        petAdsList.add(new PetsAdsModel(4,R.drawable.petsaccbanner));
        petAdsList.add(new PetsAdsModel(5,R.drawable.petsaccbanner));

        setPetAdsRecycler(petAdsList);


        petCategoryRecyclerView=findViewById(R.id.recycler_view_dashboard_pets_category);
        petsCategoryList=new ArrayList<>();
        petsCategoryList.add(new PetsCategoryModel(1,R.drawable.pupicat,"Veterinary Services"));
        petsCategoryList.add(new PetsCategoryModel(2,R.drawable.catcat,"Veterinary Services"));
        petsCategoryList.add(new PetsCategoryModel(3,R.drawable.birdcat,"Veterinary Services"));
        petsCategoryList.add(new PetsCategoryModel(4,R.drawable.fishcat,"Veterinary Services"));


        setPetCategoryRecycler(petsCategoryList);




        dashBoardMeanu=findViewById(R.id.image_view_dashboard_menuu);

        dashBoardMeanu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });



        buttonDog=findViewById(R.id.button_dog);
        buttonCat=findViewById(R.id.button_cat);
        buttonBird=findViewById(R.id.button_bird);
        buttonFish=findViewById(R.id.button_fish);



        buttonDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardActivity.this, PostViewActivity.class);
                i.putExtra("animalType","Dog");
                i.putExtra("category","Pet");
                startActivity(i);

            }
        });

        buttonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardActivity.this, PostViewActivity.class);
                i.putExtra("animalType","Cat");
                i.putExtra("category","Pet");
                startActivity(i);

            }
        });
        buttonBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardActivity.this, PostViewActivity.class);
                i.putExtra("animalType","Bird");
                i.putExtra("category","Pet");
                startActivity(i);

            }
        });
        buttonFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardActivity.this, PostViewActivity.class);
                i.putExtra("animalType","Fish");
                i.putExtra("category","Pet");
                startActivity(i);

            }
        });





    }

    private void setPetAdsRecycler(List<PetsAdsModel> datalist){
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        petAdsRecyclerView.setLayoutManager(layoutManager);
        petsAdsAdapter=new PetsAdsAdapter(this,datalist);
        petAdsRecyclerView.setAdapter(petsAdsAdapter);
    }

    private void setPetCategoryRecycler(List<PetsCategoryModel> datalist){
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        petCategoryRecyclerView.setLayoutManager(layoutManager);
        petsCategoryAdapter=new PetsCategoryAdapter(this,datalist);
        petCategoryRecyclerView.setAdapter(petsCategoryAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.navigation_home:
                return true;

            case R.id.navigation_add_post:
                Intent i = new Intent(DashBoardActivity.this, AddPostMarcketPlaceActivity.class);
                startActivity(i);
                return true;

            case R.id.navigation_profile:
                Intent i2 = new Intent(DashBoardActivity.this, CommunityActivity.class);
                startActivity(i2);
                return true;
            case R.id.navigation_community:
                Intent i3 = new Intent(DashBoardActivity.this, UserProfileActivity.class);
                startActivity(i3);
                return true;



        }

        return false;
    }



}


