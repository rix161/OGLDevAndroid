package com.gles.rohit.Common.Shape;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.gles.rohit.Common.Texture;
import com.gles.rohit.Common.jassimp2.AiMaterial;
import com.gles.rohit.Common.jassimp2.AiMesh;
import com.gles.rohit.Common.jassimp2.AiPostProcessSteps;
import com.gles.rohit.Common.jassimp2.AiScene;
import com.gles.rohit.Common.jassimp2.AiTextureType;
import com.gles.rohit.Common.jassimp2.Jassimp;
import com.gles.rohit.ogldevandroid.BuildConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by rohit on 3/6/17.
 */

public class ShapeModel implements Shape {

    private Context mContext;
    private AiScene mScene;

    private Vector<MeshEntry> mMeshs;
    private Vector<Texture> mMeshTextures;

    private int numberOfVBOS =2;
    private int[][] mBuffer;


    private int mVextexDataSize = 3;
    private int mNormalDataSize = 3;
    private int mColorDataSize = 0;
    private int mTextureDataSize = 2;
    private int mStride = (mVextexDataSize+mNormalDataSize+mColorDataSize+mTextureDataSize)*4;


    public ShapeModel(Context context, String fileName) throws IOException {

        mContext = context;

        Set<AiPostProcessSteps> flags = new HashSet<>();
        flags.add(AiPostProcessSteps.TRIANGULATE);
        flags.add(AiPostProcessSteps.GEN_SMOOTH_NORMALS);
        flags.add(AiPostProcessSteps.FLIP_UVS);

        mScene = Jassimp.importAssetFile(fileName,flags,mContext.getAssets());

    }

    @Override
    public void loadBuffers() {

        if(mScene == null) return;

        mMeshs = new Vector<MeshEntry>();
        for(AiMesh mesh:mScene.getMeshes()){
            mMeshs.add(new MeshEntry(mesh));
        }
        mBuffer = new int[mMeshs.size()][numberOfVBOS];

        mMeshTextures = new Vector<Texture>();
        for(AiMaterial material:mScene.getMaterials()){
            for(int i=0;i<material.getNumTextures(AiTextureType.DIFFUSE);i++) {
                String textureFile = material.getTextureFile(AiTextureType.DIFFUSE,i);
                Texture tFile = new Texture();
                tFile.loadTexture(mContext,textureFile);
                mMeshTextures.add(tFile);
            }
        }

        for(int i=0;i<mMeshs.size();i++){
            GLES20.glGenBuffers(numberOfVBOS,mBuffer[i],0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffer[i][0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mBuffer[i][0],mMeshs.get(i).getVertexBuffer(),GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffer[i][1]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffer[i][1],mMeshs.get(i).getIndexBuffer(),GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);
        }

    }

    @Override
    public void draw(int vertexDataHandle, int colorHandle, int textureUniformHandle, int textureCoordHandle) {
        draw(vertexDataHandle,-1,colorHandle,textureUniformHandle,textureCoordHandle);
    }

    @Override
    public void draw(int vertexDataHandle,int colorHandle,int normalHandle,int textureUniformHandle,int textureCoordHandle){
        for(int i=0;i<mMeshs.size();i++) {
            if(mBuffer[i]==null) return;

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffer[i][0]);

            GLES20.glEnableVertexAttribArray(vertexDataHandle);
            GLES20.glVertexAttribPointer(vertexDataHandle, mVextexDataSize, GLES20.GL_FLOAT, false, mStride, 0);

            if (colorHandle != -1) {
                GLES20.glEnableVertexAttribArray(colorHandle);
                GLES20.glVertexAttribPointer(colorHandle, mColorDataSize, GLES20.GL_FLOAT, false, mStride, mVextexDataSize * 4);
            }

            if (normalHandle != -1) {
                GLES20.glEnableVertexAttribArray(normalHandle);
                GLES20.glVertexAttribPointer(normalHandle, mNormalDataSize, GLES20.GL_FLOAT, false, mStride, (mVextexDataSize + mColorDataSize) * 4);
            }

            int textureID = -1;
            if(mMeshs.get(i).getMaterialIndex()<mMeshTextures.size())
                textureID = mMeshTextures.get(mMeshs.get(i).getMaterialIndex()).getTextureId();

            if (textureCoordHandle != -1 || textureID != -1) {
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
                GLES20.glUniform1i(textureUniformHandle, 0);

                GLES20.glEnableVertexAttribArray(textureCoordHandle);
                GLES20.glVertexAttribPointer(textureCoordHandle, mTextureDataSize, GLES20.GL_FLOAT, false, mStride, (mVextexDataSize + mColorDataSize + mNormalDataSize) * 4);
            }
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mBuffer[i][1]);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, mMeshs.get(i).getIndexBuffer().capacity(), GLES20.GL_UNSIGNED_INT, 0);
            GLES20.glDisableVertexAttribArray(vertexDataHandle);
            GLES20.glDisableVertexAttribArray(colorHandle);
            GLES20.glDisableVertexAttribArray(textureCoordHandle);
        }
    }

    @Override
    public void destroy() {

        for(Texture texture: mMeshTextures)
            texture.destroy();
    }

    @Override
    public boolean loadTexture(int resId) {
        return false;
    }

    @Override
    public void setTexture(Texture texture) {
        return;
    }

    private class MeshEntry{
        private int mMaterialIndex;
        private FloatBuffer mVertexBuffer;
        private IntBuffer mIndexBuffer;
        int posCount, normalCount,textureCount,colorCount;

        MeshEntry(AiMesh mesh){

            mMaterialIndex= mesh.getMaterialIndex();

            initVertexBuffer(mesh);
            initIndexBuffer(mesh);

        }

        int getPosCount(){return posCount;}
        int getNormalCount(){return normalCount;}
        int getTextureCount(){return textureCount;}
        int getColorCount(){return colorCount;}
        int getMaterialIndex(){return mMaterialIndex;}

        public FloatBuffer getVertexBuffer() {
            return mVertexBuffer;
        }

        public IntBuffer getIndexBuffer(){
            return mIndexBuffer;
        }

        private void initVertexBuffer(AiMesh mesh){

            int capPosition = mesh.hasPositions()?mesh.getPositionBuffer().capacity():0;
            int capNormal = mesh.hasNormals()?mesh.getNormalBuffer().capacity():0;
            int capTexture = mesh.hasTexCoords(0)?mesh.getTexCoordBuffer(0).capacity():0;
            int capColor = mesh.hasVertexColors()?mesh.getColorBuffer(4).capacity():0;


            mVertexBuffer = ByteBuffer.allocateDirect((capPosition+capNormal+capTexture+capColor)*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            if(mesh.hasPositions())
                mVertexBuffer.put(mesh.getPositionBuffer());

            if(mesh.hasNormals())
                mVertexBuffer.put(mesh.getNormalBuffer());

            if(mesh.hasColors(4))
                mVertexBuffer.put(mesh.getColorBuffer(4));

            if(mesh.hasTexCoords(0))
                mVertexBuffer.put(mesh.getTexCoordBuffer(0));
            mVertexBuffer.position(0);
        }

        private void initIndexBuffer(AiMesh mesh){

            if(mesh.isPureTriangle())
                mIndexBuffer = mesh.getIndexBuffer();

        }
    }
}
