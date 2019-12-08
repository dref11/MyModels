package com.zybooks.mmimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.content.*;
import android.widget.*;

import android.content.Intent;
import android.widget.Button;
import java.io.InputStream;
import java.io.FileNotFoundException;


public class HomeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    ImageButton CamButton, UploadButton;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_EXTERNAL_PERMISSION_CODE = 101;

    static ModelsDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = database.getInstance(this);

        CamButton = findViewById(R.id.CamButton);
        UploadButton = findViewById(R.id.UploadButton);

        //Manually check for permission during runtime
        checkForPermissions();

    }
    public void openCamera(View v){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public void uploadPhoto(View v){


        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_EXTERNAL_PERMISSION_CODE);
        }
        else{
            Intent intent = new Intent(HomeActivity.this,ChoosePhotoActivity.class);
            startActivity(intent);
        }
    }

    public void viewModels(View v){
        startActivity(new Intent (HomeActivity.this, MyModelsActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //Get data from result
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            //Convert to bytes to store in databse
            BitmapHelper dbBitmapHelper = new BitmapHelper();
            byte [] photoBytes = dbBitmapHelper.getBytes(photo);
            database.insert("Model", photoBytes);

            //Start new activity
            startActivity(new Intent(HomeActivity.this, MyModelsActivity.class));
        }

        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            try {
                //Get uri results
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                //Convert to bytes to store to databse
                BitmapHelper dbBitmapHelper = new BitmapHelper();
                byte [] photoBytes = dbBitmapHelper.getBytes(bitmap);
                database.insert("Model", photoBytes);

                //Start view activity
                startActivity(new Intent(HomeActivity.this, MyModelsActivity.class));

            } catch (FileNotFoundException e) {
            }
        }
    }

    //Function to request permissions during runtime
    void checkForPermissions(){

        //Check for the current activity if permission is granted for external read
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            //Permission not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                //Show rationale if permission is not granted
            } else{

                //Request the permission during runtime
                ActivityCompat.requestPermissions(this,
                        new String []{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALLERY_REQUEST);
            }

        }else{
            //Permission is already granted
        }

        //Check for the current activity if permisison is granted for external write
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY_REQUEST);

        }
    }
}
