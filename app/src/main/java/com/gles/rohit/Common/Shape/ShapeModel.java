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
    private int mColorDataSize = 4;
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
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mMeshs.get(i).getVertexBuffer().capacity()*4,mMeshs.get(i).getVertexBuffer(),GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffer[i][1]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,mMeshs.get(i).getIndexBuffer().capacity()*4,mMeshs.get(i).getIndexBuffer(),GLES20.GL_STATIC_DRAW);
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
            GLES20.glDrawElements(GLES20.GL_TRIANGLES,mMeshs.get(i).getIndicesCount(), GLES20.GL_UNSIGNED_INT, 0);

            if(vertexDataHandle!=-1)
                GLES20.glDisableVertexAttribArray(vertexDataHandle);
            if(normalHandle!=-1)
                GLES20.glDisableVertexAttribArray(normalHandle);
            if(textureCoordHandle!=-1)
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
        private int mNumIndices;

        MeshEntry(AiMesh mesh){

            mMaterialIndex= mesh.getMaterialIndex();

            initVertexBuffer(mesh);
            initIndexBuffer(mesh);

        }

        public int getMaterialIndex(){return mMaterialIndex;}
        public int getIndicesCount(){return mNumIndices;}

        public FloatBuffer getVertexBuffer() {
            return mVertexBuffer;
        }

        public IntBuffer getIndexBuffer(){
            return mIndexBuffer;
        }

        private void initVertexBuffer(AiMesh mesh){
            Vector<Float> interBuffer = new Vector<>();

            interBuffer.addAll(getMeshData(mesh));

            float interBufferRaw[] = new float[interBuffer.size()];
            for(int i=0;i<interBuffer.size();i++)
                interBufferRaw[i] = interBuffer.elementAt(i).floatValue();

            mVertexBuffer = ByteBuffer.allocateDirect(interBufferRaw.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mVertexBuffer.put(interBufferRaw).position(0);

        }

        private Vector<Float> getMeshData(AiMesh mesh) {
            Vector<Float> interBuffer = new Vector<>();

            Float[] zero2D = new Float[]{0f,0f};
            Float[] zero3D = new Float[]{0f,0f,0f};
            Float[] zero4D = new Float[]{1f,1f,1f,0f};

            for(int i=0;i<mesh.getNumVertices();i++){
                Float[]vertexPos = mesh.hasPositions()?new Float[]{mesh.getPositionX(i),mesh.getPositionY(i),mesh.getPositionZ(i)}:zero3D;
                for(Float pos:vertexPos)
                    interBuffer.add(pos);

                Float[]vertexColor = mesh.hasColors(4)?new Float[]{mesh.getColorR(i,4),mesh.getColorG(i,4),mesh.getColorB(i,4),mesh.getColorA(i,4)}:zero4D;
                for(Float color:vertexColor)
                    interBuffer.add(color);

                Float[]vertexNormal = mesh.hasNormals()?new Float[]{mesh.getNormalX(i),mesh.getNormalY(i),mesh.getNormalZ(i)}:zero3D;
                for(Float normal:vertexNormal)
                    interBuffer.add(normal);

                Float[]vertexTexCoord = mesh.hasTexCoords(0)?new Float[]{mesh.getTexCoordU(i,0),mesh.getTexCoordV(i,0)}:zero2D;
                for(Float texCoord:vertexTexCoord)
                    interBuffer.add(texCoord);
            }
            return  interBuffer;
        }

        private void initIndexBuffer(AiMesh mesh){

            mNumIndices = mesh.getNumFaces()*3;

            if(mesh.isPureTriangle())
                mIndexBuffer = mesh.getIndexBuffer();

            /*Log.e("GFX"," NumIndices:"+mNumIndices+" mIndexCap:"+mIndexBuffer.capacity()+" mIndexCap2:"+mIndexBuffer.capacity()*4);*/
        }
    }
}
