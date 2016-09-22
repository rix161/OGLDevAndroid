package com.gles.rohit.Common;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Rohith on 13-09-2016.
 */
public class ShapePyramid implements Shape {

    private final float pyramidData[] = {
            -1.0f, -1.0f, 0.5773f,
            1.0f,0.0f,0.0f,1.0f,
            0.0f,0.0f,
            0.0f, -1.0f, -1.15475f,
            0.0f,1.0f,0.0f,1.0f,
            0.5f,0.0f,
            1.0f, -1.0f, 0.5773f,
            0.0f,0.0f,1.0f,1.0f,
            1.0f,0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f,1.0f,1.0f,1.0f,
            0.5f,1.0f,
    };

    private final int pyramidIndex[]={
            0, 3, 1,
            1, 3, 2,
            2, 3, 0,
            0, 1, 2
    };

    private FloatBuffer mVertexBuffer;
    private IntBuffer mIndexBuffer;

    private int[] mBuffers;
    private int numberOfVBOS = 2;

    private int mStride = 9*4;
    private int mVextexDataSize = 3;
    private int mColorDataSize = 4;
    private int mTextureDataSize = 2;

    private int mTextureID = -1;
    private Context mContext;

    Texture mTexture;
    public ShapePyramid(Context context){
        mContext = context;

        mVertexBuffer = ByteBuffer.allocateDirect(pyramidData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(pyramidData).position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(pyramidIndex.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        mIndexBuffer.put(pyramidIndex).position(0);

        mBuffers = new int[numberOfVBOS];
    }

    public void setTexture(Texture texture){
        mTexture = texture;
    }

    public boolean loadTexture(int resId){
        if( mTexture==null )
            mTexture = new Texture();

        mTextureID = mTexture.loadTexture(mContext,resId);
        if(mTextureID!=-1)
            return true;
        else
            return false;
    }

    @Override
    public void loadBuffers() {
        GLES20.glGenBuffers(numberOfVBOS,mBuffers,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mVertexBuffer.capacity()*4,mVertexBuffer,GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffers[1]);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,mIndexBuffer.capacity()*4,mIndexBuffer,GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);
    }

    @Override
    public void draw(int vertexDataHandle,int colorHandle,int textureUniformHandle,int textureCoordHandle) {

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffers[0]);

        GLES20.glEnableVertexAttribArray(vertexDataHandle);
        GLES20.glVertexAttribPointer(vertexDataHandle,mVextexDataSize,GLES20.GL_FLOAT,false,mStride,0);

        if(colorHandle!=-1) {
            GLES20.glEnableVertexAttribArray(colorHandle);
            GLES20.glVertexAttribPointer(colorHandle, mColorDataSize, GLES20.GL_FLOAT, false, mStride, mVextexDataSize * 4);
        }
        if(textureCoordHandle!=-1 || mTextureID!=-1){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureID);
            GLES20.glUniform1i(textureUniformHandle,0);

            GLES20.glEnableVertexAttribArray(textureCoordHandle);
            GLES20.glVertexAttribPointer(textureCoordHandle, mTextureDataSize, GLES20.GL_FLOAT, false, mStride, (mVextexDataSize+mColorDataSize)*4);
        }
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffers[1]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,12,GLES20.GL_UNSIGNED_INT,0);

        GLES20.glDisableVertexAttribArray(vertexDataHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
        GLES20.glDisableVertexAttribArray(textureCoordHandle);
    }

    @Override
    public void destroy() {
        GLES20.glDeleteBuffers(numberOfVBOS,mBuffers,0);
    }

}
