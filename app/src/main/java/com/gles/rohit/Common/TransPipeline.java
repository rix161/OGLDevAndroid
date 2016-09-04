package com.gles.rohit.Common;

import android.opengl.Matrix;

/**
 * Created by Rohith on 04-09-2016.
 */
public class TransPipeline {
    private float []mMatrix;

    private float []mScaleMatrix;
    private float []mTranslateMatrix;
    private float []mRotateMatrix;

    private float []mAxis;
    private float mAngle;

    private float []mScale;

    public TransPipeline(){
        mMatrix = new float[16];
        mScaleMatrix = new float[16];
        mTranslateMatrix = new float[16];
        mRotateMatrix = new float[16];

        mAxis = new float[3];
        mScale = new float[3];
        Matrix.setIdentityM(mMatrix,0);
        Matrix.setIdentityM(mScaleMatrix,0);
        Matrix.setIdentityM(mTranslateMatrix,0);
        Matrix.setIdentityM(mRotateMatrix,0);
    }

    public void setScale(float []scale){
        if(scale.length == 3)
            Matrix.scaleM(mScaleMatrix,0,scale[0],scale[1],scale[2]);
    }

    public void setTranslate(float []scale){
        mScale = scale;
        Matrix.translateM(mTranslateMatrix,0,mScale[0],mScale[1],mScale[2]);
    }

    private void translate(){
        Matrix.translateM(mTranslateMatrix,0,mScale[0],mScale[1],mScale[2]);
    }

    public void setRotate(float deg,float[] axis){
        mAngle = deg;
        mAxis = axis;
        if(axis.length == 3)
            Matrix.setRotateM(mRotateMatrix,0,mAngle,mAxis[0],mAxis[1],mAxis[2]);
    }

    private void rotate(){
        Matrix.rotateM(mRotateMatrix,0,mAngle,mAxis[0],mAxis[1],mAxis[2]);
    }

    public float[] getMatrix(boolean scale,boolean translate,boolean rotate){
        if(translate)
            translate();
        if(rotate)
            rotate();
        Matrix.multiplyMM(mMatrix,0,mRotateMatrix,0,mScaleMatrix,0);
        Matrix.multiplyMM(mMatrix,0,mTranslateMatrix,0,mMatrix,0);
        return mMatrix;
    }

    public float[] getMatrix(){
        return mMatrix;
    }
}
