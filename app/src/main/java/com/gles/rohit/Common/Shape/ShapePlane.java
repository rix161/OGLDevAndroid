package com.gles.rohit.Common.Shape;

import android.content.Context;
import android.opengl.GLES20;

import com.gles.rohit.Common.Texture;
import com.gles.rohit.Common.Vectors.Vector3f;
import com.gles.rohit.Common.Vertex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;


/**
 * Created by Rohith on 13-09-2016.
 */
public class ShapePlane implements Shape {
    float planeDepth = 20.0f;
    float planeWidth = 10.0f;
    private final Vertex planeData[] = {
            new Vertex(
                    new float[]{0.0f, 0.0f, 0.0f},
                    new float[]{1.0f,0.0f,0.0f,1.0f},
                    new float[]{0.0f,1.0f,0.0f},
                    new float[]{0.0f,0.0f}),
            new Vertex(
                    new float[]{0.0f, 0.0f, planeDepth},
                    new float[]{0.0f,1.0f,0.0f,1.0f},
                    new float[]{0.0f,1.0f,0.0f},
                    new float[]{0.0f,1.0f}),
            new Vertex(
                    new float[]{planeWidth, 0.0f, 0.0f},
                    new float[]{0.0f,0.0f,1.0f,1.0f},
                    new float[]{0.0f,1.0f,0.0f},
                    new float[]{1.0f,0.0f}),
            new Vertex(
                    new float[]{planeWidth,0.0f,planeDepth},
                    new float[]{1.0f,1.0f,1.0f,1.0f},
                    new float[]{0.0f,1.0f,0.0f},
                    new float[]{1.0f,1.0f})
    };


    private float PlaneDataRaw[];

    private final int planeIndex[]={
            0,1,2,
            2,1,3
    };

    private FloatBuffer mVertexBuffer;
    private IntBuffer mIndexBuffer;

    private int[] mBuffers;
    private int numberOfVBOS = 2;


    private int mVextexDataSize = 3;
    private int mNormalDataSize = 3;
    private int mColorDataSize = 4;
    private int mTextureDataSize = 2;
    private int mStride = (mVextexDataSize+mNormalDataSize+mColorDataSize+mTextureDataSize)*4;

    private int mTextureID = -1;
    private Context mContext;

    Texture mTexture;
    public ShapePlane(Context context){
        mContext = context;
        computeNormals();
        buildRawData();

        mVertexBuffer = ByteBuffer.allocateDirect(PlaneDataRaw.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(PlaneDataRaw).position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(planeIndex.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        mIndexBuffer.put(planeIndex).position(0);

        mBuffers = new int[numberOfVBOS];
    }

    private void buildRawData() {

        Vector<Float> interBuffer = new Vector<>();

        for(Vertex temp:planeData){

            for(float pos:temp.getPositionRaw())
                interBuffer.add(pos);

            for(float col:temp.getColorRaw())
                interBuffer.add(col);

            for(float col:temp.getNormalRaw())
                interBuffer.add(col);

            for(float tex:temp.getTextureCoordRaw())
                interBuffer.add(tex);
        }

        PlaneDataRaw = new float[interBuffer.size()];
        for(int i=0;i<interBuffer.size();i++)
            PlaneDataRaw[i] = interBuffer.elementAt(i).floatValue();
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
        draw(vertexDataHandle,colorHandle,-1,textureUniformHandle,textureCoordHandle);
    }

    @Override
    public void draw(int vertexDataHandle,int colorHandle,int normalhandle,int textureUniformHandle,int textureCoordHandle) {

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffers[0]);

        GLES20.glEnableVertexAttribArray(vertexDataHandle);
        GLES20.glVertexAttribPointer(vertexDataHandle,mVextexDataSize,GLES20.GL_FLOAT,false,mStride,0);

        if(colorHandle!=-1) {
            GLES20.glEnableVertexAttribArray(colorHandle);
            GLES20.glVertexAttribPointer(colorHandle, mColorDataSize, GLES20.GL_FLOAT, false, mStride, mVextexDataSize * 4);
        }

        if(normalhandle!=-1){
            GLES20.glEnableVertexAttribArray(normalhandle);
            GLES20.glVertexAttribPointer(normalhandle,mNormalDataSize,GLES20.GL_FLOAT,false,mStride,(mVextexDataSize+mColorDataSize)*4);
        }


        if(textureCoordHandle!=-1 || mTextureID!=-1){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureID);
            GLES20.glUniform1i(textureUniformHandle,0);

            GLES20.glEnableVertexAttribArray(textureCoordHandle);
            GLES20.glVertexAttribPointer(textureCoordHandle, mTextureDataSize, GLES20.GL_FLOAT, false, mStride, (mVextexDataSize+mColorDataSize+mNormalDataSize)*4);
        }
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffers[1]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,6,GLES20.GL_UNSIGNED_INT,0);

        GLES20.glDisableVertexAttribArray(vertexDataHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
        GLES20.glDisableVertexAttribArray(textureCoordHandle);
    }

    private void computeNormals(){

        for(int i = 0; i< planeIndex.length; i+=3){

            int i1 = planeIndex[i];
            int i2 = planeIndex[i+1];
            int i3 = planeIndex[i+2];

            Vector3f v1 = (Vector3f) planeData[i2].getPosition().subtact(planeData[i1].getPosition());
            Vector3f v2 = (Vector3f) planeData[i3].getPosition().subtact(planeData[i1].getPosition());
            Vector3f normal = v1.cross(v2.get());
            normal.normalize();
            planeData[i1].resetNormal((Vector3f) normal.add(planeData[i1].getNormal()));
            planeData[i2].resetNormal((Vector3f) normal.add(planeData[i2].getNormal()));
            planeData[i3].resetNormal((Vector3f) normal.add(planeData[i3].getNormal()));
        }

        for(int i=0;i<planeData.length;i++){
            planeData[i].getNormal().normalize();
        }
    }
    @Override
    public void destroy() {
        GLES20.glDeleteBuffers(numberOfVBOS,mBuffers,0);
    }

}
