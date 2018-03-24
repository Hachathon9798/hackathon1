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


        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();




        spinner.setItems("Government Employee", "Industrialist", "Student");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {


            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                switch (position) {
                    case 0:


                        usGst.setVisibility(View.VISIBLE);
                        type="Government Employee";


                        break;
                    case 1:

                        usGst.setVisibility(View.VISIBLE);

                        type="Industrialist";


                        break;
                    case 2:

                        usGst.setVisibility(View.VISIBLE);
                        type="Student";
                        usGst.setText("Student");

                        break;
                }
            }});


    }
    void registerUser1(){

        auth.createUserWithEmailAndPassword(uRef.email,uRef.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterationActivity.this,"Registration Finished",Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
                            String uid = user.getUid();

                            dbRef.child("UserGov").child(uid).setValue(uRef);

                        }else{
                            Toast.makeText(RegisterationActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    void registerUser2(){

        auth.createUserWithEmailAndPassword(uRef.email,uRef.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterationActivity.this,"Registration Finished",Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
                            String uid = user.getUid();

                            dbRef.child("UserInd").child(uid).setValue(uRef);

                        }else{
                            Toast.makeText(RegisterationActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    void registerUser3(){

        auth.createUserWithEmailAndPassword(uRef.email,uRef.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterationActivity.this,"Registration Finished",Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
                            String uid = user.getUid();

                            dbRef.child("UserStu").child(uid).setValue(uRef);

                        }else{
                            Toast.makeText(RegisterationActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



    @Override
    public void onClick(View v) {



        uRef.id=image;
        uRef.type=type;
        uRef.name = usName.getText().toString().trim();
        uRef.email = usEmail.getText().toString().trim();
        uRef.password = usPhone.getText().toString().trim();






        if(v.getId() == R.id.button3){

            if(type=="Government Employee"){
                registerUser1();
            }
            else if(type=="Industrialist"){
                registerUser2();
            }
            else if(type=="Student"){
                registerUser3();
            }
        }
       else if (v.getId()==R.id.button2){



        }

    }
}
