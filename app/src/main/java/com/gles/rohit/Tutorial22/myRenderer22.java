package com.gles.rohit.Tutorial22;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.gles.rohit.Common.ASTCTexture;
import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.Lighting;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.Common.Shape.Shape;
import com.gles.rohit.Common.Shape.ShapeModel;
import com.gles.rohit.Common.Shape.ShapePlane;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.Common.myRenderer;
import com.gles.rohit.ogldevandroid.R;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class myRenderer22 extends myRenderer {
    public myRenderer22(Context context) {
        super(context);
        mContext = context;
        //mShape = new ShapePlane(mContext);
        try {
            mShape = new ShapeModel(mContext,"Model.md2");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private final int spotLightCount = 1;

    private final int TOTAL_LIGHTING_HANDLE = 2+4+3+1+7*pointLightCount+1+9*spotLightCount;

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

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial19vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial21fs));
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

        int pointCount = 9;
        mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, "uPointLightNum");
        StringBuilder builder;
        for(int i=0;i<pointLightCount;i++) {

            builder = new StringBuilder();
            builder.append("uPointLights").append("[").append(i).append("]").append(".");

            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.color");
            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.ambientIntensity");
            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"base.diffuseIntensity");

            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"position");

            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.constant");
            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.linear");
            mLightingHandles[pointCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"attenuation.exponetial");

        }

        int spotCount = pointCount;
        mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, "uSpotLightNum");
        for(int i=0;i<spotLightCount;i++) {

            builder = new StringBuilder();
            builder.append("uSpotLights").append("[").append(i).append("]").append(".");

            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.base.color");
            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.base.ambientIntensity");
            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.base.diffuseIntensity");

            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.position");

            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.attenuation.constant");
            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.attenuation.linear");
            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"pointbase.attenuation.exponetial");

            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"direction");
            mLightingHandles[spotCount++] = GLES20.glGetUniformLocation(mProgramId, builder.toString()+"cutoff");

        }

        mShape.loadBuffers();
        mShape.setTexture(new ASTCTexture());
        mShape.loadTexture(R.raw.test4x4astc);

        mPipeline.setScale(new float[]{0.1f,0.1f,0.1f});
        mPipeline.mCamera.setCamera(new float[]{0.0f,7.0f,-15.0f},new float[]{0.0f,-0.2f,1.0f},new float[]{0.0f,1.0f,0.0f});

        mLighting.setAmbientIntensity(1.0f);
        mLighting.setDirectionLightData(0.00f,new float[]{1.0f,1.0f,1.0f},new float[]{0.0f,0.0f,1.0f});
        mLighting.setSpecularLightData(0f,0.0f,mPipeline.mCamera.getEyeMatrix());

        mLighting.setNumberOfPointLights(pointLightCount);
        mLighting.addPointLight(new float[]{1.0f,0.5f,0.0f},new float[]{0.0f,0.1f},new float[]{-3.0f,1.0f,-10.0f},new float[]{0.0f,0.1f,0.0f});
        mLighting.addPointLight(new float[]{0.0f,0.5f,1.0f},new float[]{0.0f,0.1f},new float[]{3.0f,1.0f,-10.0f},new float[]{0.0f,0.1f,0.0f});

        mLighting.setNumberOfSpotLights(spotLightCount);
        mLighting.addSpotLight(new float[]{1.0f,1.0f,1.0f},new float[]{0.0f,0.9f}
                ,new float[]{-1.0f,1.0f,-10.0f},new float[]{0.0f,0.1f,0.0f}
                ,new float[]{0.0f,-1.0f,0.0f},20.0f);

        GLES20.glUniformMatrix4fv(mMMatrixHandle,1,false,mPipeline.getModelMatrix(false,false,false),0);
    }



    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        mPipeline.setPerspective(width,height,60.0f,100.0f,1.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {



        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgramId);

        val +=0.01f;
        float newZ = new Double(20.0f*Math.sin(val)).floatValue();
        float newZ2 = new Double(20.0f*Math.cos(val+0.001)).floatValue();
        mLighting.updatePointLight(1,new float[]{3.0f,1.0f,newZ});
        mLighting.updateSpotLight(0,new float[]{-1.0f,1.0f,newZ2});
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

