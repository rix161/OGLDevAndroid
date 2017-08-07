package com.gles.rohit.Common.Shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES30;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import com.gles.rohit.Common.Utils;
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

        int drawFBO[] = new int[1];
        int readFBO[] = new int[1];
        GLES30.glGetIntegerv(GLES30.GL_DRAW_FRAMEBUFFER_BINDING,drawFBO,0);
        GLES30.glGetIntegerv(GLES30.GL_READ_FRAMEBUFFER_BINDING,readFBO,0);
        Log.e("GFX","Before FBO:id:"+mFBO[0]+"draw:"+drawFBO[0]+"read:"+readFBO[0]);

        GLES30.glGenFramebuffers(1,mFBO,0);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,mFBO[0]);

        GLES30.glGenTextures(1,mTextureId,0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,mTextureId[0]);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_DEPTH_COMPONENT, mWidth, mHeight, 0, GLES30.GL_DEPTH_COMPONENT, GLES30.GL_UNSIGNED_INT, null);

        // Attach the depth texture to FBO depth attachment point
        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_TEXTURE_2D, mTextureId[0], 0);

        GLES30.glReadBuffer(GLES30.GL_NONE);
        GLES30.glDrawBuffers(1,new int[]{GLES30.GL_NONE},0);

        GLES30.glGetIntegerv(GLES30.GL_DRAW_FRAMEBUFFER_BINDING,drawFBO,0);
        GLES30.glGetIntegerv(GLES30.GL_READ_FRAMEBUFFER_BINDING,readFBO,0);
        Log.e("GFX","After FBO:id:"+mFBO[0]+"draw:"+drawFBO[0]+"read:"+readFBO[0]);


        int status = GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER);
        if(status != GLES30.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("GFX", "error:status:" + Integer.toHexString(status)+" str:"+ Utils.getError(status));
        }
    }

    public void BindForWriting(){
        GLES30.glBindFramebuffer(GLES30.GL_DRAW_FRAMEBUFFER,mFBO[0]);
    }

    public void BindForReading(int textureUnit){
        GLES30.glActiveTexture(textureUnit);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,mTextureId[0]);

    }

    public void destroy(){
        GLES30.glDeleteBuffers(1,mFBO,0);
        GLES30.glDeleteTextures(1,mTextureId,0);
    }

}
