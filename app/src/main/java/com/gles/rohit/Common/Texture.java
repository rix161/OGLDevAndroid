package com.gles.rohit.Common;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rohith on 13-09-2016.
 */
public class Texture {

    private Bitmap mBitmap;
    protected int mTextureId[];

    public int getTextureId(){

        if(mTextureId ==null)
            return -1;

        return mTextureId[0];
    }
    public int loadTexture(Context context, String mFileName){
        AssetManager manager = context.getAssets();
        try {
            InputStream bitmapStream = manager.open(mFileName);
            mBitmap = BitmapFactory.decodeStream(bitmapStream);
            bitmapStream.close();
            return uploadTexture();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int loadTexture(Context context,int resID){
        mBitmap = BitmapFactory.decodeResource(context.getResources(),resID);

        if(mBitmap==null)
            return -1;

        return uploadTexture();
    }

    private int uploadTexture(){

        if(mBitmap == null ) return  -1;

        mTextureId = new int[1];
        GLES20.glGenTextures(1,mTextureId,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,mBitmap,0);
        return mTextureId[0];
    }

    public void destroy(){
        GLES20.glDeleteTextures(1,mTextureId,0);
    }
}
