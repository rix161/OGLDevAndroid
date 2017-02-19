package com.gles.rohit.Tutorial20;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.gles.rohit.Common.ASTCTexture;
import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.Lighting;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.Common.Shape;
import com.gles.rohit.Common.ShapePlane;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.Common.myRenderer;
import com.gles.rohit.ogldevandroid.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class myRenderer20 extends myRenderer {

    public myRenderer20(Context context) {
        super(context);
        mContext = context;
        mShape = new ShapePlane(mContext);
        mPipeline = new TransPipeline();
        mLighting = new Lighting();
        mLightingHandles = new int[TOTAL_LIGHTING_HANDLE];
    }

    private Context mContext;
    private final String TAG = "GFX:";

    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int mNormalDataHandle;
    private int mMVPMatrixHandle;
    private int mMMatrixHandle;

    private int []mLightingHandles;

    private int mTextureSamplerHandle;
    private int mTextureCoordHandle;

    private TransPipeline mPipeline;
    private Shape mShape;
    private Lighting mLighting;

    private final int pointLightCount = 2;

    private final int TOTAL_LIGHTING_HANDLE = 2+4+3+1+7*pointLightCount;

    static float val = 0.0f;




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
    public void setDiffuseData(float intensity,float[] color,float[] position){ mLighting.setDirectionLightData(intensity,color,position); }

    @Override
    public float[] getLightData(){ return mLighting.getLightingData();}

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial19vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial20fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramId,"aTexture");
        mNormalDataHandle = GLES20.glGetAttribLocation(mProgramId,"aNormal");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMVPMatrix");
        mMMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMMatrix");
        mTextureSamplerHandle = GLES20.glGetUniformLocation(mProgramId,"uTextureSampler");

        mLightingHandles[0] = GLES20.glGetUniformLocation(mProgramId,"uAmbientLight.ambientIntensity");
        mLightingHandles[1] = GLES20.glGetUniformLocation(mProgramId,"uAmbientLight.ambientColor");

        mLightingHandles[2] = GLES20.glGetUniformLocation(mProgramId,"uDirectionalLight.base.color");
        mLightingHandles[3] = GLES20.glGetUniformLocation(mProgramId,"uDirectionalLight.base.ambientIntensity");
        mLightingHandles[4] = GLES20.glGetUniformLocation(mProgramId,"uDirectionalLight.base.diffuseIntensity");
        mLightingHandles[5] = GLES20.glGetUniformLocation(mProgramId,"uDirectionalLight.base.direction");

        mLightingHandles[6] = GLES20.glGetUniformLocation(mProgramId,"uSpecularIntensity");
        mLightingHandles[7] = GLES20.glGetUniformLocation(mProgramId,"uSpecularPower");
        mLightingHandles[8] = GLES20.glGetUniformLocation(mProgramId,"uEyePosition");

        int count = 1;
        mLightingHandles[9] = GLES20.glGetUniformLocation(mProgramId, "uPointLightNum");
        StringBuilder builder;
        for(int i=0;i<pointLightCount;i++) {

            builder = new StringBuilder();
            builder.append("uPointLights").append("[").append(i).append("]").append(".");

            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.color");
            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.ambientIntensity");
            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.diffuseIntensity");

            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"position");

            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.constant");
            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.linear");
            mLightingHandles[9+count++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.exponetial");

        }

        mShape.loadBuffers();
        mShape.setTexture(new ASTCTexture());
        mShape.loadTexture(R.raw.test4x4astc);

        mPipeline.setScale(new float[]{1.0f,1.0f,1.0f});
        mPipeline.mCamera.setCamera(new float[]{5.0f,1.0f,-3.0f},new float[]{1.0f,1.0f,45.0f},new float[]{0.0f,1.0f,0.0f});

        mLighting.setDirectionLightData(0.00f,new float[]{1.0f,1.0f,1.0f},new float[]{0.0f,0.0f,1.0f});
        mLighting.setSpecularLightData(0f,0.0f,mPipeline.mCamera.getEyeMatrix());
        mLighting.setNumberOfPointLights(pointLightCount);
        mLighting.addPointLight(new float[]{1.0f,0.5f,0.0f},new float[]{0.0f,0.1f},new float[]{-3.0f,1.0f,-10.0f},new float[]{0.0f,0.1f,0.0f});
        mLighting.addPointLight(new float[]{0.0f,0.5f,1.0f},new float[]{0.0f,0.5f},new float[]{0.0f,1.0f,-10.0f},new float[]{0.0f,0.1f,0.0f});

        GLES20.glUniformMatrix4fv(mMMatrixHandle,1,false,mPipeline.getModelMatrix(false,false,false),0);
    }



    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        mPipeline.setPerspective(width,height,60.0f,100.0f,1.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {



        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgramId);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        val +=0.01f;
        float newZ = new Double(20.0f*Math.sin(val)).floatValue();
        mLighting.updatePointLight(1,new float[]{3.0f,1.0f,newZ});
        mLighting.updateSpecularLightPosition(mPipeline.mCamera.getEyeMatrix());

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mPipeline.getMatrix(false,false,false),0);
        mLighting.doLighting(mLightingHandles);
        mShape.draw(mVertexDataHandle,-1,mNormalDataHandle,mTextureSamplerHandle,mTextureCoordHandle);
    }

    public void onDestroy(){
        GLES20.glDeleteShader(mVShaderId);
        GLES20.glDeleteShader(mFShaderId);
        GLES20.glDeleteProgram(mProgramId);
        mShape.destroy();
    }
}

