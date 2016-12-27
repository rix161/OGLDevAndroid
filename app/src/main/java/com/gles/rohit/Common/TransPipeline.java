package com.gles.rohit.Common;

import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by Rohith on 04-09-2016.
 */
public class TransPipeline {
    private float []mMatrix;

    private float []mScaleMatrix;
    private float []mTranslateMatrix;
    private float []mRotateMatrix;

    private float []mPerspectiveMatrix;


    private float []mAxis;
    private static float mAngle;

    private float []mScale;

    private float []mTranslate;
    private boolean DEBUG = false;

    public Camera mCamera;
    public TransPipeline(){

        mMatrix = new float[16];
        mScaleMatrix = new float[16];
        mTranslateMatrix = new float[16];
        mRotateMatrix = new float[16];
        mPerspectiveMatrix = new float[16];
        mCamera = new Camera();

        mAxis = new float[3];
        mScale = new float[3];
        mTranslate = new float[3];
        Matrix.setIdentityM(mMatrix,0);
        Matrix.setIdentityM(mScaleMatrix,0);
        Matrix.setIdentityM(mTranslateMatrix,0);
        Matrix.setIdentityM(mRotateMatrix,0);
        Matrix.setIdentityM(mPerspectiveMatrix,0);
    }

    public void setScale(float []scale){
        if(scale.length == 3)
            Matrix.scaleM(mScaleMatrix,0,scale[0],scale[1],scale[2]);
        if(DEBUG)
            Utils.printMatrix(mScaleMatrix,"SetScaleMatrix");
    }

    public void setTranslate(float []translate){
        mTranslate = translate;
        Matrix.translateM(mTranslateMatrix,0,mTranslate[0],mTranslate[1],mTranslate[2]);
        if(DEBUG)
            Utils.printMatrix(mTranslateMatrix,"SetTranslate");
    }

    private void translate(){
        Matrix.translateM(mTranslateMatrix,0,mTranslate[0],mTranslate[1],mTranslate[2]);
    }

    public void setRotate(float deg,float[] axis){
        mAngle = deg;
        mAxis = axis;
        if(axis.length == 3)
            Matrix.setRotateM(mRotateMatrix,0,mAngle,mAxis[0],mAxis[1],mAxis[2]);

        if(DEBUG)
            Utils.printMatrix(mRotateMatrix,"SetRotate");
    }

    private void rotate(){
        setRotate(mAngle+1f,mAxis);
    }

    public float[] getModelMatrix(boolean scale,boolean translate,boolean rotate){

        Matrix.setIdentityM(mMatrix,0);
        if(translate)
            translate();
        if(rotate)
            rotate();

        Matrix.multiplyMM(mMatrix,0,mScaleMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mRotateMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mTranslateMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mCamera.getMatrix(),0,mMatrix,0);

        return mMatrix;
    }

    public float[] getMatrix(boolean scale,boolean translate,boolean rotate){
        Matrix.setIdentityM(mMatrix,0);
        if(translate)
            translate();
        if(rotate)
            rotate();

        Matrix.multiplyMM(mMatrix,0,mScaleMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mRotateMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mTranslateMatrix,0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mCamera.getMatrix(),0,mMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mPerspectiveMatrix,0,mMatrix,0);

        return mMatrix;
    }

    public void setPerspective(float width,float height,float fov,float far,float near){
        Matrix.perspectiveM(mPerspectiveMatrix,0,fov,(width/height),near,far);
        if(DEBUG)
            Utils.printMatrix(mPerspectiveMatrix,"setPerspective");
    }

    public float[] getMatrix(){
        return mMatrix;
    }

}
