package com.zybooks.mmimages;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.FileNotFoundException;
import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    Button CamButton, UploadButton;
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
            Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoIntent,GALLERY_REQUEST);
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
                e.printStackTrace();
            }
        }
    }

}
