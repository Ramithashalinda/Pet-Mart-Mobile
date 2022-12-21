package com.mart.petsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileDataActivity extends AppCompatActivity {

    private ImageView close;
   private CircleImageView userProfileImage;
   private TextView userName;
   private String profileId,profileImageUrl,profileName,profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile_data);

        close=findViewById(R.id.close);
        userName=findViewById(R.id.text_user_name);
        userProfileImage=findViewById(R.id.user_image_profile);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            Toast.makeText(UserProfileDataActivity.this, "Update data", Toast.LENGTH_SHORT).show();

            profileId = bundle.getString("PROFILE_ID");
            profileImageUrl = bundle.getString("PROFILE_IMAGE_URL");
            profileName = bundle.getString("PROFILE_NAME");
            profileEmail = bundle.getString("PROFILE_EMAIL");


            System.out.println("profile Id  " + profileId);
            System.out.println("profile Image url  " + profileImageUrl);
            System.out.println("profile Name  " + profileName);
            System.out.println("profile Email  " + profileEmail);

            userName.setText(profileName);

            Picasso.get()
                    .load(profileImageUrl)
                    .fit().centerCrop()
                    .into(userProfileImage);

        }
        else {
            System.out.println("sssss");
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



    }
}