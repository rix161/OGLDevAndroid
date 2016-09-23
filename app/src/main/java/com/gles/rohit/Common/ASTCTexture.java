package com.gles.rohit.Common;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES31Ext;
import android.util.Log;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Rohith on 22-09-2016.
 */
public class ASTCTexture extends Texture {
    private int mWidth;
    private int mHeight;
    private int HEADER_SIZE =16;

    protected int loadTexture(Context context, int resID){
        InputStream mInputStream = context.getResources().openRawResource(resID);
        byte []buffer = Utils.inputStreamToByteBuffer(mInputStream);

        if(buffer!=null && buffer.length!=0){
            mWidth = (((int)buffer[8]<<8)+((int)buffer[7]& 0XFF));
            mHeight = (((int)buffer[11]<<8)+((int)buffer[10]& 0XFF));
            int mInternalFormat = getInternalFormat((int)buffer[4],(int)buffer[5]);
            mTextureId = new int[1];
            GLES20.glGenTextures(1,mTextureId,0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            GLES20.glCompressedTexImage2D(GLES20.GL_TEXTURE_2D,0, mInternalFormat,
                    mWidth,mHeight,0,buffer.length-HEADER_SIZE, ByteBuffer.wrap(buffer,0,buffer.length));
            return mTextureId[0];
        }
        return -1;
    }

    private int getInternalFormat(int xDim, int yDim) {
        switch (xDim){
            case 4:
                    switch (yDim){
                        case 4: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_4x4_KHR;
                    }
                    break;
            case 5:
                switch (yDim){
                    case 4: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_5x4_KHR;
                    case 5: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_5x5_KHR;
                }
                    break;
            case 6:
                switch (yDim){
                    case 5: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_6x5_KHR;
                    case 6: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_6x6_KHR;
                }
                    break;
            case 8:
                switch (yDim){
                    case 5: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_8x5_KHR;
                    case 6: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_8x6_KHR;
                    case 8: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_8x8_KHR;
                }
                    break;
            case 10:
                switch (yDim){
                    case 5: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_10x5_KHR;
                    case 6: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_10x6_KHR;
                    case 8: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_10x8_KHR;
                    case 10: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_10x10_KHR;
                }
                break;
            case 12:
                switch (yDim){
                    case 10: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_12x10_KHR;
                    case 12: return GLES31Ext.GL_COMPRESSED_RGBA_ASTC_12x12_KHR;
                }
                    break;
            default: return -1;
        }
        return  -1;
    }

}
