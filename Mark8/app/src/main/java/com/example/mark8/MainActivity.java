package com.example.mark8;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark8.Adapter.CaptureActivityPotrait;
import com.example.mark8.Adapter.CardCustomAdapter;
import com.example.mark8.Adapter.CartViewListener;
import com.example.mark8.Adapter.SavedViewListener;
import com.example.mark8.Adapter.SearchViewListener;
import com.example.mark8.Adapter.CartCardAdapter;
import com.example.mark8.Adapter.FetchProducts;
import com.example.mark8.Adapter.SavedListAdapter;
import com.example.mark8.DTO.MainContext;
import com.example.mark8.DTO.SearchResultDTO;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainContext mainContext;
    RecyclerView searchCardList;
    private RecyclerView cartCardList;
    private RecyclerView savedList;
    View searchView;
    private View cartView;
    private View savedView;
    static final int CAPTURE_IMAGE=1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private Uri picUri,qrUri;
    private FetchProducts fetchProducts;
    private IntentIntegrator qrScan;
    private ProgressDialog progressDialog;
    private View instructionView;


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
        }
    }

    public void setupApplication(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //qr scan
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan qr code");
        qrScan.setOrientationLocked(true);
        qrScan.setBeepEnabled(true);
        qrScan.setCaptureActivity(CaptureActivityPotrait.class);

        //initiating view
        setSavedView(findViewById(R.id.saved_view));
        searchView = findViewById(R.id.search_view);
        setCartView(findViewById(R.id.cart_view));
        instructionView = findViewById(R.id.instructions);
        fetchProducts = new FetchProducts(this);
        mainContext = new MainContext();
        setProgressDialog(new ProgressDialog(this));

        // Initiating Search recycler view
        CardCustomAdapter cardCustomAdapter = new CardCustomAdapter(mainContext);
        SearchViewListener searchViewListener = new SearchViewListener(this,mainContext);

        searchCardList = findViewById(R.id.productlist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchCardList.setLayoutManager(layoutManager);

        searchCardList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        searchCardList.setAdapter(cardCustomAdapter);
        searchCardList.setOnTouchListener(searchViewListener);
        searchView.setVisibility(View.INVISIBLE);
        instructionView.setVisibility(View.VISIBLE);

        // Initiating Cart recycler view
        CartCardAdapter cartCardAdapter = new CartCardAdapter(mainContext);
        CartViewListener cartViewListener = new CartViewListener(this,mainContext);
        cartCardList = findViewById(R.id.cartList);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        cartCardList.setLayoutManager(layoutManager2);

        cartCardList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        cartCardList.setAdapter(cartCardAdapter);
        cartCardList.setOnTouchListener(cartViewListener);
        getCartView().setVisibility(View.INVISIBLE);

        // Initiating Saved list view
        SavedListAdapter savedListAdapter = new SavedListAdapter(mainContext);
        SavedViewListener savedViewListener = new SavedViewListener(this, mainContext);
        savedList = findViewById(R.id.saveList);
        getSavedView().setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this);
        savedList.setLayoutManager(layoutManager3);

        savedList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        savedList.setAdapter(savedListAdapter);
        savedList.setOnTouchListener(savedViewListener);
        getSavedView().findViewById(R.id.qrimage).setVisibility(View.GONE);
        getSavedView().findViewById(R.id.shareqr).setVisibility(View.INVISIBLE);
        getSavedView().findViewById(R.id.genqr).setVisibility(View.INVISIBLE);

        // navigation pane
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.open_cart:{
                if(mainContext.getCartList().size()==0){
                    searchView.setVisibility(View.INVISIBLE);
                    cartView.setVisibility(View.INVISIBLE);
                    savedView.setVisibility(View.INVISIBLE);
                    instructionView.setVisibility(View.VISIBLE);
                }else {
                    instructionView.setVisibility(View.INVISIBLE);
                    cartCardList.getAdapter().notifyDataSetChanged();
                    getCartView().setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                    getSavedView().setVisibility(View.INVISIBLE);
                    cartCardList.scheduleLayoutAnimation();
                }
                break;
            }
            case R.id.open_list:{

                if(mainContext.getSavedList().size()==0){
                    searchView.setVisibility(View.INVISIBLE);
                    cartView.setVisibility(View.INVISIBLE);
                    savedView.setVisibility(View.INVISIBLE);
                    instructionView.setVisibility(View.VISIBLE);
                }else {
                    instructionView.setVisibility(View.INVISIBLE);
                    savedList.getAdapter().notifyDataSetChanged();
                    getCartView().setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                    getSavedView().setVisibility(View.VISIBLE);
                    savedList.scheduleLayoutAnimation();
                }
                break;

            }
            case R.id.open_main:{
                if(mainContext.getSearchList().size()==0){
                    searchView.setVisibility(View.INVISIBLE);
                    cartView.setVisibility(View.INVISIBLE);
                    savedView.setVisibility(View.INVISIBLE);
                    instructionView.setVisibility(View.VISIBLE);
                }else {
                    instructionView.setVisibility(View.INVISIBLE);
                    getCartView().setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    getSavedView().setVisibility(View.INVISIBLE);
                    searchCardList.scheduleLayoutAnimation();
                }
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
            if(capture.resolveActivity(getPackageManager())!= null) {
                File file = getImageFile();
                picUri = FileProvider.getUriForFile(
                        this,
                        this.getApplicationContext()
                                .getPackageName() + ".provider", file);
                capture.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
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

    public File getImageFile(){
        try {
            final String dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/";
            File newdir = new File(dir);
            newdir.mkdirs();
            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".png";
            File newfile = new File(file);
            newfile.createNewFile();
            return newfile;
        }catch(Exception e){
            Toast.makeText(this,"Unable to create image file, please try again",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAPTURE_IMAGE){
            if(resultCode == RESULT_OK) {
//                final String dir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/";
//                File newdir = new File(dir);
//                newdir.mkdirs();
//                String file = dir+ DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+".png";
//                File newfile = new File(file);
//                int height,width;
//                try {
//                    newfile.createNewFile();
//                    FileOutputStream outputStream = new FileOutputStream(newfile);
//                    Bitmap image = (Bitmap) data.getExtras().get("data");
//                    ImageView imageView = findViewById(R.id.clickedimage);
//                    imageView.setImageBitmap(image);
//                    height = image.getHeight();
//                    width = image.getWidth();
//                    image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                    //picUri = Uri.fromFile(newfile);
//                    picUri = FileProvider.getUriForFile(
//                            this,
//                            this.getApplicationContext()
//                                    .getPackageName() + ".provider", newfile);
//                    //performCrop(height,width);
//                    fetchProducts.fetchProducts(image);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                performCrop();
            }

        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                try {
                    getProgressDialog().setMessage("Processing image, fetching products");
                    getProgressDialog().show();
                    mainContext.setSearchList(new ArrayList());
                    searchCardList.getAdapter().notifyDataSetChanged();

                    Bitmap bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri()),512,512,false);

                    ImageView imageView = findViewById(R.id.clickedimage);
                    imageView.setImageBitmap(bitmap);
                   fetchProducts.fetchProducts(bitmap);
                } catch (IOException e) {
                    getProgressDialog().dismiss();
                    Toast.makeText(this,"Unable to process image, please try again",Toast.LENGTH_LONG).show();
                }
            }
        }else{
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result==null){
                Toast.makeText(this,"QR Result Not Found",Toast.LENGTH_LONG).show();
            }else{
                String content = result.getContents();
                if(content!=null) {
                    try {
                        getProgressDialog().setMessage("Processing QR Code, please wait");
                        getProgressDialog().show();
                        fetchProducts.fetchSavedList(content);
                    }catch(Exception e){
                        getProgressDialog().dismiss();
                        Toast.makeText(this,"Error processing QR code.",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"No Id found, please scan correct code",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void savedListSetup(SearchResultDTO resultDTO){
        getProgressDialog().dismiss();
        if(resultDTO.getProducts() !=null && resultDTO.getProducts().size()>0) {
            mainContext.setSavedList(resultDTO.getProducts());
            savedList.getAdapter().notifyDataSetChanged();
            getSavedView().findViewById(R.id.qrimage).setVisibility(View.GONE);
            getSavedView().findViewById(R.id.shareqr).setVisibility(View.INVISIBLE);
            savedView.findViewById(R.id.genqr).setVisibility(View.VISIBLE);
            getSavedView().setVisibility(View.VISIBLE);
            searchView.setVisibility(View.INVISIBLE);
            getCartView().setVisibility(View.INVISIBLE);
            instructionView.setVisibility(View.INVISIBLE);
            savedList.scheduleLayoutAnimation();
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

    public void setUpContext(SearchResultDTO resultDTO){
        getProgressDialog().dismiss();
        if(resultDTO.getProducts() != null) {
            mainContext.setSearchList(resultDTO.getProducts());
            searchCardList.getAdapter().notifyDataSetChanged();
            TextView textView = findViewById(R.id.itemname);
            textView.setText(resultDTO.getItemName());
            searchView.setVisibility(View.VISIBLE);
            getCartView().setVisibility(View.INVISIBLE);
            getSavedView().setVisibility(View.INVISIBLE);
            instructionView.setVisibility(View.INVISIBLE);
            searchCardList.scheduleLayoutAnimation();
        }

    }

    public void qrScanner(View view){
        qrScan.initiateScan();
    }

    public void fetchId(View view){
        getProgressDialog().setMessage("Generating QR code, please wait");
        getProgressDialog().show();
        try {
            fetchProducts.generateQRCode(mainContext.getSavedList());
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Unable to generateQR Code", Toast.LENGTH_SHORT);
            getProgressDialog().dismiss();
        }
    }

    public void generateQRCode(String id){
        QRGEncoder qrgEncoder = new QRGEncoder(id, null, QRGContents.Type.TEXT, 1000);
        ImageView imageView = getSavedView().findViewById(R.id.qrimage);
        try {
            imageView.setImageBitmap(qrgEncoder.encodeAsBitmap());

            imageView.setVisibility(View.VISIBLE);
            getSavedView().findViewById(R.id.shareqr).setVisibility(View.VISIBLE);
            getProgressDialog().dismiss();
            final String dir =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/";
            File newdir = new File(dir);
            newdir.mkdirs();
            String file = dir+ "generated-qr.png";
            File newfile = new File(file);
            newfile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(newfile);
            Bitmap image = qrgEncoder.encodeAsBitmap();
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            qrUri = FileProvider.getUriForFile(
                    this,
                    this.getApplicationContext()
                            .getPackageName() + ".provider", newfile);

        } catch (Exception e) {
            getProgressDialog().dismiss();
            Toast toast = Toast.makeText(this, "Unable to generateQR Code", Toast.LENGTH_SHORT);
        }

    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public View getSavedView() {
        return savedView;
    }

    public void setSavedView(View savedView) {
        this.savedView = savedView;
    }

    public View getCartView() {
        return cartView;
    }

    public void setCartView(View cartView) {
        this.cartView = cartView;
    }

    public RecyclerView getCartCardList() {
        return cartCardList;
    }

    public void setCartCardList(RecyclerView cartCardList) {
        this.cartCardList = cartCardList;
    }

    public RecyclerView getSavedList() {
        return savedList;
    }

    public void setSavedList(RecyclerView savedList) {
        this.savedList = savedList;
    }

    public void shareQr(View view){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_STREAM, qrUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(shareIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public View getInstructionView() {
        return instructionView;
    }

    public void setInstructionView(View instructionView) {
        this.instructionView = instructionView;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
