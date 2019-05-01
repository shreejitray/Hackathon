package com.example.mark8.Adapter;

import android.graphics.Bitmap;

public class Processor {
    public static int[][][] getArrayFromImage(Bitmap image){
        int row = image.getHeight();
        int col = image.getWidth();
        int[][][] array = new int[row][col][3];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int pixel = image.getPixel(i, j);
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
}
