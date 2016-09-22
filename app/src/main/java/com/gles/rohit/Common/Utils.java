package com.gles.rohit.Common;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rohith on 12-09-2016.
 */
public class Utils {

    public static void printMatrix(float[]matrix,String title){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                sb.append(" ").append(matrix[j*4+i]);
            }
            Log.e("GFX",title+":"+sb.toString());
            sb.delete(0,sb.length());
        }
    }

    public static byte[] inputStreamToByteBuffer(InputStream inputStream){
        byte[] buffer = new byte[2048];;
        ByteArrayOutputStream os  = new ByteArrayOutputStream();
        int size;
        try {
            while((size = inputStream.read(buffer, 0, 2048))>=0)
                os.write(buffer, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }
}
