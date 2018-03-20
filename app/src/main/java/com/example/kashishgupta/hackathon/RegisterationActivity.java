package com.example.kashishgupta.hackathon;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterationActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.editTextName)
    EditText usName;

    @BindView(R.id.editTextEmail)
    EditText usEmail;

    @BindView(R.id.editTextPhone)
    EditText usPhone;

    @BindView(R.id.editTextGst)
    EditText usGst;

    @BindView(R.id.userType)
    MaterialSpinner spinner;

    @BindView(R.id.button2)
    Button upload;

    @BindView(R.id.imageView2)
    ImageView imgView;

    @BindView(R.id.button3)
    Button register;

    Uri FilePathUri;
    StorageReference storageReference;


    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;
    String Storage_Path = "All_Image_Uploads/";

    User uRef;


    FirebaseAuth auth;
    DatabaseReference dbRef;
    FirebaseUser user;
    String image, type;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);



        ButterKnife.bind(this);
        

        uRef = new User();

        upload.setOnClickListener(this);
        register.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();


        dbRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();




        spinner.setItems("Government Employee", "Industrialist", "Student");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {


            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                switch (position) {
                    case 0:

                        imgView.setVisibility(View.VISIBLE);
                        upload.setVisibility(View.VISIBLE);
                        usGst.setVisibility(View.INVISIBLE);
                        type="Government Employee";
                        image="";


                        break;
                    case 1:

                        usGst.setVisibility(View.VISIBLE);
                        imgView.setVisibility(View.INVISIBLE);
                        upload.setVisibility(View.INVISIBLE);

                        type="Industrialist";


                        break;
                    case 2:

                        usGst.setVisibility(View.INVISIBLE);
                        imgView.setVisibility(View.INVISIBLE);
                        upload.setVisibility(View.INVISIBLE);
                        type="Student";
                        image="Student";

                        break;
                }
            }});


    }
    void registerUser(){

        auth.createUserWithEmailAndPassword(uRef.email,uRef.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterationActivity.this,"Registration Finished",Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
                            String uid = user.getUid();

                            dbRef.child("users").child(uid).setValue(uRef);

                        }else{
                            Toast.makeText(RegisterationActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    void init(){

        Intent intent = new Intent();

        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                imgView.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                upload.setText("Image Selected");
                UploadImageFileToFirebaseStorage();



            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.

            // Showing progressDialog.

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.

                            // Hiding the progressDialog after done uploading.

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                         image= taskSnapshot.getDownloadUrl().toString();

                            // Getting image upload ID.

                            // Adding image upload id s child element into databaseReference.
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully "+exception.getMessage(), Toast.LENGTH_LONG).show();


                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        }
                    });
        }
        else {


        }
    }



    @Override
    public void onClick(View v) {



            uRef.id=image;




        uRef.type=type;
        uRef.name = usName.getText().toString().trim();
        uRef.email = usEmail.getText().toString().trim();
        uRef.password = usPhone.getText().toString().trim();






        if(v.getId() == R.id.button3){
            registerUser();
        }
       else if (v.getId()==R.id.button2){

            init();





        }

    }
}
