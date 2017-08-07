package com.gles.rohit.Tutorial23;

import android.content.Context;
import android.opengl.GLES20;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.gles.rohit.Common.LightingPackage.Lighting;
import com.gles.rohit.Common.LightingPackage.SpotLight;
import com.gles.rohit.Common.Shadow.ShadowMappingFBO;
import com.gles.rohit.Common.Shadow.ShadowMappingTechnique;
import com.gles.rohit.Common.Shape.Shape;
import com.gles.rohit.Common.Shape.ShapeModel;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.Common.myRenderer;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by rohit on 9/6/17.
 */

public class myRenderer23 extends myRenderer {

    private Context mContext;
    private final String TAG = "GFX:";

    private TransPipeline mPipeline;
    private Shape mShapeModel,mShapePlane;
    private Lighting mLighting;

    private final int pointLightCount =0;
    private final int spotLightCount = 1;

    private ShadowMappingTechnique mShadowMappingTech;
    private ShadowMappingFBO mShadowMappingFBO;

    private int mWidth,mHeight;

    public myRenderer23(Context context) {
        super(context);
        mContext = context;
        try {
            mShapeModel = new ShapeModel(mContext,"Model.md2");
            mShapePlane = new ShapeModel(mContext,"quad.obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPipeline = new TransPipeline();
        mLighting = new Lighting();

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;

        mShadowMappingFBO = new ShadowMappingFBO(mWidth,mHeight,mContext);
        mShadowMappingTech = new ShadowMappingTechnique(mContext);
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
    public void setDiffuseData(float intensity,float[] color,float[] position){ mLighting.setDirectionLightData(intensity,color,position); }

    @Override
    public float[] getLightData(){ return mLighting.getLightingData();}

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mShapeModel.loadBuffers();
        mShapePlane.loadBuffers();


        mLighting.setAmbientIntensity(1.0f);
        mLighting.setNumberOfSpotLights(spotLightCount);
        mLighting.addSpotLight(new float[]{1.0f,1.0f,1.0f},new float[]{0.0f,0.9f}
                ,new float[]{-20.0f,10.0f,5.0f},new float[]{0.0f,0.01f,0.0f}
                ,new float[]{20.0f,0.0f,0.0f},20.0f);


       mShadowMappingFBO.init();
        mShadowMappingTech.init();
    }



    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        mPipeline.setPerspective(width,height,60.0f,50.0f,1.0f);
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        shadowMapPass();
        renderMapPass();

    }

    private void shadowMapPass() {
        mShadowMappingFBO.BindForWriting();

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        TransPipeline pipeLine = new TransPipeline();

        pipeLine.setScale(new float[]{0.1f,0.1f,0.1f});
        pipeLine.setTranslate(new float[]{0.0f,0.0f,3.0f});

        SpotLight spotLight = mLighting.getSpotLight(0);
        if(spotLight!=null)
            pipeLine.mCamera.setCamera(spotLight.getPosition(),spotLight.getDirection(),new float[]{0.0f,1.0f,0.0f});


        pipeLine.setPerspective(mWidth,mHeight,60.0f,50.0f,1.0f);
        GLES20.glUniformMatrix4fv(mShadowMappingTech.getMVPHandle(),1,false,pipeLine.getMatrix(false,false,false),0);
        mShapeModel.draw(mShadowMappingTech.getPositionHandle(),-1,-1,mShadowMappingTech.getTextureSamplerHandle(),mShadowMappingTech.getTextureCoordHandle());
    }

    private void renderMapPass() {

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

        TransPipeline pipeLine = new TransPipeline();

        pipeLine.setScale(new float[]{3.0f,3.0f,3.0f});
        pipeLine.setTranslate(new float[]{0.0f,0.0f,10.0f});
        pipeLine.mCamera.setCamera(new float[]{0.0f,0.0f,0.0f},new float[]{0.0f,0.0f,1.0f},new float[]{0.0f,1.0f,0.0f});
        pipeLine.setPerspective(mWidth,mHeight,60.0f,50.0f,1.0f);
        GLES20.glUniformMatrix4fv(mShadowMappingTech.getMVPHandle(),1,false,pipeLine.getMatrix(false,false,false),0);

        GLES20.glUniform1i(mShadowMappingTech.getTextureSamplerHandle(),0);
        mShadowMappingFBO.BindForReading(GLES20.GL_TEXTURE0);
        mShapePlane.draw(mShadowMappingTech.getPositionHandle(),-1,-1,-1,mShadowMappingTech.getTextureCoordHandle());
    }

    public void onDestroy(){

        mShapeModel.destroy();
        mShapePlane.destroy();
        mShadowMappingFBO.destroy();
        mShadowMappingTech.destroy();
    }
}


