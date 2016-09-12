package com.gles.rohit.Common;

import android.util.Log;

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
}
