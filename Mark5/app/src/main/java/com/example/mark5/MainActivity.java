package com.example.mark5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int CAPTURE_IMAGE=1;
    static final int PIC_CROP = 2;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setContentView(R.layout.activity_main);
                } else {
                    setContentView(R.layout.activity_main);
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void LaunchCamera(View view){
        try {
            Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


           // capture.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            if(capture.resolveActivity(getPackageManager())!= null) {
                startActivityForResult(capture, CAPTURE_IMAGE);
            }else{
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }catch (Exception e){
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAPTURE_IMAGE){
            if(resultCode == RESULT_OK) {
                final String dir =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/";
                File newdir = new File(dir);
                newdir.mkdirs();
                String file = dir+ DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+".jpg";


                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(newfile);
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    image.compress(Bitmap.CompressFormat.PNG, 85, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    picUri = Uri.fromFile(newfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                performCrop();
            }

        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                ImageView picView = (ImageView)findViewById(R.id.image_pane);
                picView.setImageURI(result.getUri());
            }
        }
    }

    public void performCrop(){
        try{

            CropImage.activity(android.net.Uri.parse(picUri.toString()))
                    .setAspectRatio(1,1)
                    .setFixAspectRatio(false)
                    .start(this);
        }catch (Exception e){
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
