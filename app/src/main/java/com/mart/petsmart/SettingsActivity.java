package com.mart.petsmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private TextView logout,tips,userName,userEmail;
    private CircleImageView circleImageView;
    private FirebaseFirestore firebaseFirestore;
    private String profileImageUrl,profileName,profileEmail,profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        firebaseFirestore = FirebaseFirestore.getInstance();

        logout=findViewById(R.id.text_button_logout);
        tips=findViewById(R.id.text_tips);

        userName=findViewById(R.id.usename);
        userEmail=findViewById(R.id.user_email);
        circleImageView=findViewById(R.id.image_view_pet_category);

        EventChangeListenerProfileDetails();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LogingActivity.class);
                startActivity(intent);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SettingsActivity.this, TipsViewActivity.class);
                startActivity(i);

            }
        });
    }


    private void EventChangeListenerProfileDetails(){

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        firebaseFirestore.collection("users").document(signInAccount.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {

                    profileId=documentSnapshot.get("profileId").toString();
                    profileImageUrl=documentSnapshot.get("profileImageUrl").toString();
                    profileName=documentSnapshot.get("profileName").toString();
                    profileEmail=documentSnapshot.get("userEmail").toString();

                    userName.setText(profileName);
                    userEmail.setText(profileEmail);
                    Picasso.get()
                            .load(profileImageUrl)
                            .fit().centerCrop()
                            .into(circleImageView);
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
}