package com.gles.rohit.Common;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import com.gles.rohit.ogldevandroid.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Rohith on 17-09-2016.
 */
public class ETC2Texture extends Texture {
    private int mWidth;
    private int mHeight;
    private int HEADER_SIZE = 16;

    @Override
    protected int loadTexture(Context context, int resID){
        InputStream mInputStream = context.getResources().openRawResource(resID);
        byte []buffer = Utils.inputStreamToByteBuffer(mInputStream);
        if(buffer!=null && buffer.length!=0){
            mWidth = (((int)buffer[12]<<8)+((int)buffer[13]& 0XFF));
            mHeight =(((int)buffer[14]<<8)+((int)buffer[15]& 0XFF));
            mTextureId = new int[1];
            GLES20.glGenTextures(1,mTextureId,0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            GLES20.glCompressedTexImage2D(GLES20.GL_TEXTURE_2D,0, GLES30.GL_COMPRESSED_RGBA8_ETC2_EAC,
                    mWidth,mHeight,0,buffer.length-HEADER_SIZE, ByteBuffer.wrap(buffer,0,buffer.length));
            return mTextureId[0];
        }
        return -1;
    }
}
