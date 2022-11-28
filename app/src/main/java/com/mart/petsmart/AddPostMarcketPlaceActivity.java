package com.mart.petsmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mart.petsmart.model.UploadItems;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddPostMarcketPlaceActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
        {

    private static final int PICK_IMAGE_REQUEST = 1;


    BottomNavigationView bottomNavigationView;

    private Button mButtonChooseImage;
    private Button mButtonUpload;

    private EditText mEditTextTitleName , mEditTextPrice ,mEditTextPhoneNumber,mEditTestDescription ;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private StorageTask mUploadTask;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private CollectionReference collectionReference;

    private Spinner spinnerCategory,spinnerAnimalType,spinnerDistrict;

    private String  id,titleName,price,phoneNumber,description,imageUrl,uploadDate,category,animalType,district;

    private String  pPetTitle,pImageUrl,pUploadAt,pDescription,pPrice,pPhoneNumber,pCategory,pAnimalType,pDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_marcket_place);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null){
            Toast.makeText(AddPostMarcketPlaceActivity.this, "Update data", Toast.LENGTH_SHORT).show();

            pPetTitle=bundle.getString("PET_TITLE_DATA");
            pImageUrl=bundle.getString("POST_IMAGE_URL_DATA");
            pUploadAt=bundle.getString("UPLOAD_AT_DATA");
            pDescription=bundle.getString("DESCRIPTION_DATA");

            pPrice=bundle.getString("PRICE_DATA");
            pPhoneNumber=bundle.getString("PHONE_NUMBER_DATA");

            pCategory=bundle.getString("CATEGORY_DATA");
            pAnimalType=bundle.getString("ANIMAL_TYPE_DATA");
            pDistrict=bundle.getString("DISTRICT_DATA");



            System.out.println("pet title  "+ pPetTitle);
            System.out.println("Image url  "+ pImageUrl);
            System.out.println("upload time  "+ pUploadAt);
            System.out.println("description  "+ pDescription);
            System.out.println("price  "+ pPrice);
            System.out.println("phone number  "+ pPhoneNumber);
            System.out.println("category  "+ pCategory);
            System.out.println("animal type  "+ pAnimalType);
            System.out.println("district  "+ pDistrict);

try {

    mEditTextTitleName.setText(pPetTitle);
    Picasso.get()
            .load(pImageUrl)
            .fit().centerCrop()
            .into(mImageView);

    mEditTestDescription.setText(pDescription);
    mEditTextPrice.setText(pPrice);
    mEditTextPhoneNumber.setText(pPhoneNumber);
}
catch (Exception e){
    System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH  "+ e);
}



//

//                    spinnerAnimalType
//                    spinnerDistrict




        }else {
            Toast.makeText(AddPostMarcketPlaceActivity.this, "Save data", Toast.LENGTH_SHORT).show();

        }



        bottomNavigationView =(BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mButtonChooseImage = findViewById(R.id.button_chose_image);
        mButtonUpload = findViewById(R.id.button_upload);

        mEditTextTitleName = findViewById(R.id.text_title);
        mEditTextPrice = findViewById(R.id.text_price);
        mEditTextPhoneNumber = findViewById(R.id.text_phone_number);
        mEditTestDescription = findViewById(R.id.text_view_text_description);


        mProgressBar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);

        //spinner
        spinnerCategory = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.catogeroy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerDistrict = findViewById(R.id.spinner_address);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.district_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter2);


        spinnerAnimalType = findViewById(R.id.spinner_animal_type);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.animal, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(adapter3);

        if (spinnerCategory.getSelectedItem().toString().equals("Veterinary Services"))
        {
            spinnerAnimalType.setEnabled(false);

        }



        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);


        //firebase storage folder
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        collectionReference = FirebaseFirestore.getInstance().collection("users/"+signInAccount.getId()+"/uploads");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddPostMarcketPlaceActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                uploadFile();
            }
        });


        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(AddPostMarcketPlaceActivity.this)
                        .setIcon(R.drawable.ic_delete_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mImageView.setImageBitmap(null);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


                return true;
            }
        });


    }

            private String getDateToday(){
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                String today=dateFormat.format(date);

                return today;
            }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
//get image uri
    private String getFileExtension(Uri uri) {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));

    }


    private void uploadFile(){
        if (mImageUri !=null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "."+getFileExtension(mImageUri));

             mUploadTask = fileReference.putFile(mImageUri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             Handler handler =new Handler();
                             handler.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     mProgressBar.setProgress(0);
                                 }
                             },500);


                             taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {

                                     titleName=mEditTextTitleName.getText().toString().trim();
                                     price=mEditTextPrice.getText().toString().trim();
                                     phoneNumber = mEditTextPhoneNumber.getText().toString().trim();
                                     description =mEditTestDescription.getText().toString().trim();

                                     imageUrl=uri.toString();
                                     uploadDate = getDateToday();
                                     id= UUID.randomUUID().toString();
                                     category =spinnerCategory.getSelectedItem().toString();
                                     animalType =spinnerAnimalType.getSelectedItem().toString();
                                     district=spinnerDistrict.getSelectedItem().toString();
                                     String regexStr = "^[0-9]$";

                                     if (TextUtils.isEmpty(titleName)) {
                                         mEditTextTitleName.setError("Please enter title");
                                     } else if (TextUtils.isEmpty(price)) {
                                         mEditTextPrice.setError("Please enter price");
                                     } else if (TextUtils.isEmpty(phoneNumber)) {
                                         mEditTextPhoneNumber.setError("Please enter valid phone number");
                                     } else if(TextUtils.isEmpty(description)){
                                         mEditTestDescription.setError("Please enter description");
                                     }
                                     else {
                                         // calling method to add data to Firebase Firestore.
                                           UploadItems uploadItems =new UploadItems( id,titleName,Double.parseDouble(price),Integer.parseInt(phoneNumber),description,imageUrl,uploadDate,category,animalType,district);
                                           collectionReference.document(id).set(uploadItems);

                                         Toast.makeText(AddPostMarcketPlaceActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                         mEditTextTitleName.setText("");
                                         mEditTextPrice.setText("");
                                         mEditTextPhoneNumber.setText("");
                                         mEditTestDescription.setText("");
                                         mImageView.setImageBitmap(null);

                                     }

                                   //  UploadItems uploadItems =new UploadItems( mEditTextTitleName.getText().toString(), Double.parseDouble(mEditTextPrice.getText().toString()), Integer.parseInt(mEditTextPhoneNumber.getText().toString()), mEditTestDescription.getText().toString(), imageUrl, new Date(), spinnerCategory.getSelectedItem().toString(), spinnerAnimalType.getSelectedItem().toString(), spinnerDistrict.getSelectedItem().toString());
                                   //  collectionReference.document(UUID.randomUUID().toString()).set(uploadItems);
                                 }
                             });



                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(AddPostMarcketPlaceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                         }
                     })
                     .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onProgress(UploadTask.TaskSnapshot snapshot) {
                             double progress =(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                             mProgressBar.setProgress((int) progress);

                         }
                     });
                    } else {
                       Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();

                    }
                }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.navigation_add_post:
                return true;

            case R.id.navigation_home:
                Intent i = new Intent(AddPostMarcketPlaceActivity.this, DashBoardActivity.class);
                startActivity(i);
                return true;

            case R.id.navigation_profile:
                Intent i2 = new Intent(AddPostMarcketPlaceActivity.this, CommunityActivity.class);
                startActivity(i2);
                return true;
            case R.id.navigation_community:
                Intent i3 = new Intent(AddPostMarcketPlaceActivity.this, PostActivity.class);
                startActivity(i3);
                return true;



        }

        return false;
    }

}