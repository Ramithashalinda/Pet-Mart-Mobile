package com.mart.petsmart;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mart.petsmart.model.PostViewModel;

public class UserOptionsActivity extends AppCompatActivity {

    private TextView deleteAccount;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        deleteAccount=findViewById(R.id.deleteAccount);

        firebaseFirestore = FirebaseFirestore.getInstance();

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteAccount();
            }
        });
    }

    public void onDeleteAccount() {
        new AlertDialog.Builder(UserOptionsActivity.this)
                .setIcon(R.drawable.ic_delete_24)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete this account ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(UserOptionsActivity.this);
                        firebaseFirestore.collection("users").document(signInAccount.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(getApplicationContext(), LogingActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(UserOptionsActivity.this, "Account successfully deleted!", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.w(TAG, "Error deleting document account", e);
                                    }
                                });
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }
}