package com.example.mark8.Adapter;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.mark8.DTO.SearchRequestDTO;
import com.example.mark8.DTO.SearchResultDTO;
import com.example.mark8.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public class FetchProducts {
    private static Retrofit retrofit;
    private static String url = "http://192.168.1.12:5000/user/api/";
    private MainActivity mainActivity;

    public FetchProducts(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void fetchProducts(Bitmap image){
        int [][][]array = getArrayFromImage(image);
        SearchRequestDTO requestDTO = new SearchRequestDTO();
        requestDTO.setPayload(array);
        Retrofit client = getRetrofitInstance();
        GetProducts getProducts = retrofit.create(GetProducts.class);
        getProducts.getProducts(requestDTO).enqueue(new Callback<SearchResultDTO>() {
            @Override
            public void onResponse(Call<SearchResultDTO> call, Response<SearchResultDTO> response) {
                SearchResultDTO resultDTO = response.body();
                mainActivity.setUpContext(resultDTO);

            }

            @Override
            public void onFailure(Call<SearchResultDTO> call, Throwable t) {
                Toast.makeText(mainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static int[][][] getArrayFromImage(Bitmap image){
        int row = image.getHeight();
        int col = image.getWidth();
        int[][][] array = new int[row][col][3];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int pixel = image.getPixel(j, i);
                int red = ((pixel & 0x00FF0000) >> 16);
                int green = ((pixel & 0x0000FF00) >> 8);
                int blue = pixel & 0x000000FF;
                array[i][j][0] = red;
                array[i][j][1] = green;
                array[i][j][2] = blue;
            }
        }
        return array;
    }

    public interface GetProducts {
        @POST("fetchpoduct")
        Call<SearchResultDTO> getProducts(@Body SearchRequestDTO payload);
    }
}
