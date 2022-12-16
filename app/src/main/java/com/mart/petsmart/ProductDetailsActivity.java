package com.mart.petsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView textViewPostByName,textViewPetTitle,textViewDateTime,textViewDescription,textViewPrice,textViewLocation,textViewCategoryName,textViewAnimalType;
    private ImageView imageViewPostImage,back;
    private Button btnCall,btnMessage;

    private void InitializeWidgets(){
        textViewPetTitle=findViewById(R.id.text_view_product_pet_title);
        textViewDateTime=findViewById(R.id.text_view_product_date);
        textViewDescription=findViewById(R.id.text_view_product_description);
        textViewPrice=findViewById(R.id.text_view_product_price);
        textViewLocation=findViewById(R.id.text_view_product_location);
        textViewCategoryName=findViewById(R.id.text_view_product_category);
        textViewAnimalType=findViewById(R.id.text_view_product_type_of_animal);
        imageViewPostImage=findViewById(R.id.text_view_product_image_view);
        textViewPostByName=findViewById(R.id.text_view_post_by_name);

        btnCall=findViewById(R.id.button_call);
        btnMessage=findViewById(R.id.button_message);
        back=findViewById(R.id.image_view_product_back);



    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today=dateFormat.format(date);

        return today;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_product_details);

        InitializeWidgets();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


//receive data from
        Intent i=this.getIntent();
        String postByProfileName=i.getExtras().getString("PROFILE_NAME_KEY");
        String petTitle=i.getExtras().getString("PET_TITLE_KEY");
        String location=i.getExtras().getString("DISTRICT_KEY");
        String postImageUrl=i.getExtras().getString("POST_IMAGE_URL_KEY");
        String uploadAt=i.getExtras().getString("UPLOAD_AT_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");
        String price=i.getExtras().getString("PRICE_KEY");
        String phoneNumber=i.getExtras().getString("PHONE_NUMBER_KEY");
        String category=i.getExtras().getString("CATEGORY_KEY");
        String animalType=i.getExtras().getString("ANIMAL_TYPE_KEY");


        System.out.println("Phone Number   " +phoneNumber);

        textViewPetTitle.setText(petTitle);
        textViewDateTime.setText("Posted on "+uploadAt);
        textViewDescription.setText(description);
        textViewPrice.setText("RS. "+price);
        textViewLocation.setText("Location "+location);
        textViewCategoryName.setText("Category "+category);
        textViewAnimalType.setText("Type of animal "+animalType);
        textViewPostByName.setText("For sale by "+postByProfileName);


        Picasso.get()
                .load(postImageUrl)
                .fit().centerCrop()
                .into(imageViewPostImage);



        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("sms:"+phoneNumber);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "The SMS text");
                startActivity(it);
            }
        });



    }
}