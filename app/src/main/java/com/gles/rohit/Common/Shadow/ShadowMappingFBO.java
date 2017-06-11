package com.gles.rohit.Common.Shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.gles.rohit.ogldevandroid.R;

/**
 * Created by rohit on 10/6/17.
 */

public class ShadowMappingFBO {

    private int mFBO[];
    private int mTextureId[];
    private int mWidth,mHeight;
    Context mContext;

    public ShadowMappingFBO(int width, int height,Context context){
        mFBO = new int[1];
        mTextureId = new int[1];
        mWidth = width;
        mHeight = height;
        mContext = context;

    }


    public void init(){

        GLES20.glGenTextures(1,mTextureId,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D,0,GLES20.GL_DEPTH_COMPONENT,mWidth,mHeight,0,GLES20.GL_DEPTH_COMPONENT,GLES20.GL_FLOAT,null);

        GLES20.glGenBuffers(1,mFBO,0);
        GLES20.glBindBuffer(GLES20.GL_FRAMEBUFFER,mFBO[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,GLES20.GL_DEPTH_ATTACHMENT,GLES20.GL_TEXTURE_2D,mTextureId[0],0);

        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        if(status != GLES20.GL_FRAMEBUFFER_COMPLETE)
            Log.e("GFX","error:status"+status);
    }

    public void BindForWriting(){
        GLES20.glBindBuffer(GLES20.GL_FRAMEBUFFER,mFBO[0]);
    }

    public void BindForReading(int textureUnit){
        GLES20.glActiveTexture(textureUnit);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);

    }

    public void destroy(){
        GLES20.glDeleteBuffers(1,mFBO,0);
        GLES20.glDeleteTextures(1,mTextureId,0);
    }

}
