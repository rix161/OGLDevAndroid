package com.gles.rohit.Tutorial17;

import android.content.Context;

import android.opengl.GLES20;
import android.util.Log;

import com.gles.rohit.Common.ETC2Texture;
import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.LightingPackage.Lighting;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.Common.Shape.Shape;
import com.gles.rohit.Common.Shape.ShapePyramid;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.Common.myRenderer;
import com.gles.rohit.ogldevandroid.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 03-09-2016.
 */
public class myRenderer17 extends myRenderer {
    private Context mContext;
    private final String TAG = "GFX:";

    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int mMMatrixHandle;

    private int []mLightingHandles;

    private int mTextureSamplerHandle;
    private int mTextureCoordHandle;

    private TransPipeline mPipeline;
    private Shape mShape;
    private Lighting mLighting;

    private final int TOTAL_LIGHTING_HANDLE = 2;


    public myRenderer17(Context applicationContext) {
        super(applicationContext);
        mContext = applicationContext;
        mShape = new ShapePyramid(mContext);
        mPipeline = new TransPipeline();
        mLighting = new Lighting();
        mLightingHandles = new int[TOTAL_LIGHTING_HANDLE];
    }

    @Override
    public void updateCamera(int buttonId){
        mPipeline.mCamera.updateEyeCamera(buttonId);
    }
    @Override
    public void rotateCamera(float rotX,float rotY){
        mPipeline.mCamera.rotateCamera(rotX,rotY);
    }

    @Override
    public void setAmbientData(float intensity,float[] color){ mLighting.setAmbientLightData(intensity,color);}

    @Override
    public float[] getLightData(){ return mLighting.getLightingData();}

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial17vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial17fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramId,"aTexture");
        mMMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMMatrix");
        mTextureSamplerHandle = GLES20.glGetUniformLocation(mProgramId,"uTextureSampler");
        mLightingHandles[0] = GLES20.glGetUniformLocation(mProgramId,"uAmbientData.intensity");
        mLightingHandles[1] = GLES20.glGetUniformLocation(mProgramId,"uAmbientData.color");

        mShape.loadBuffers();
        mShape.setTexture(new ETC2Texture());
        mShape.loadTexture(R.raw.testetc);

        mPipeline.setScale(new float[]{1.0f,1.0f,1.0f});
        mPipeline.setRotate(1.0f,new float[]{0.0f,1.0f,0.0f});
        mPipeline.setTranslate(new float[]{0.0f, 1.0f, -5.0f});
        mPipeline.mCamera.setCamera(new float[]{0.0f,0.0f,5.0f},new float[]{0.0f,0.0f,-10.0f},new float[]{0.0f,1.0f,0.0f});
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        mPipeline.setPerspective(width,height,30.0f,100.0f,1.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgramId);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        GLES20.glUniformMatrix4fv(mMMatrixHandle,1,false,mPipeline.getMatrix(false,false,true),0);
        mLighting.doLighting(mLightingHandles);
        mShape.draw(mVertexDataHandle,-1,mTextureSamplerHandle,mTextureCoordHandle);
    }

    public void onDestroy(){
        GLES20.glDeleteShader(mVShaderId);
        GLES20.glDeleteShader(mFShaderId);
        GLES20.glDeleteProgram(mProgramId);
        mShape.destroy();
    }
}
