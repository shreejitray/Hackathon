package com.example.mark8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mark8.Adapter.CardCustomAdapter;
import com.example.mark8.Adapter.CardSwipeListener;
import com.example.mark8.Adapter.CartCardAdapter;
import com.example.mark8.Adapter.SavedListAdapter;
import com.example.mark8.DTO.MainContext;
import com.example.mark8.DTO.Product;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  MainContext mainContext;
    RecyclerView searchCardList;
    RecyclerView cartCardList;
    RecyclerView savedList;
    static final int CAPTURE_IMAGE=1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private Uri picUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add toolbar
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
            setupApplication();
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
                    setupApplication();
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

    public void setupApplication(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // main context
        mainContext = new MainContext();
        CardCustomAdapter cardCustomAdapter = new CardCustomAdapter(mainContext);
        CardSwipeListener cardSwipeListener = new CardSwipeListener();
        cardSwipeListener.setMainContext(mainContext);

        searchCardList = findViewById(R.id.productlist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchCardList.setLayoutManager(layoutManager);

        searchCardList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        searchCardList.setAdapter(cardCustomAdapter);
        searchCardList.setOnTouchListener(cardSwipeListener);


        CartCardAdapter cartCardAdapter = new CartCardAdapter(mainContext);
        cartCardList = findViewById(R.id.cartList);
        cartCardList.setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        cartCardList.setLayoutManager(layoutManager2);

        cartCardList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        cartCardList.setAdapter(cartCardAdapter);

        SavedListAdapter savedListAdapter = new SavedListAdapter(mainContext);
        savedList = findViewById(R.id.saveList);
        savedList.setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this);
        savedList.setLayoutManager(layoutManager3);

        savedList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        savedList.setAdapter(savedListAdapter);



        // navigation pane
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void populateData() {

        List<Product> products = new ArrayList<>();

        for(int i=0;i<10;i++){
            Product product = new Product();
            product.setId(i);
            product.setImageUrl("https://img.icons8.com/office/24/000000/shopping-cart.png");
            product.setPrice("25");
            product.setName("phone");
            product.setCount(0);
            products.add(product);
        }
        mainContext.setSearchList(products);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.open_cart:{
                cartCardList.getAdapter().notifyDataSetChanged();
                savedList.setVisibility(View.INVISIBLE);
                searchCardList.setVisibility(View.INVISIBLE);
                cartCardList.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.open_list:{

                cartCardList.setVisibility(View.INVISIBLE);
                savedList.getAdapter().notifyDataSetChanged();
                searchCardList.setVisibility(View.INVISIBLE);
                savedList.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.open_main:{
                savedList.setVisibility(View.INVISIBLE);
                searchCardList.setVisibility(View.VISIBLE);
                cartCardList.setVisibility(View.INVISIBLE);
                break;
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    populateData();
                    searchCardList.getAdapter().notifyDataSetChanged();
                } catch (IOException e) {
                    System.out.println("File not found");
                }
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
