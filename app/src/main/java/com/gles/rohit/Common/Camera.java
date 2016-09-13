package com.gles.rohit.Common;

import android.opengl.Matrix;
import android.util.Log;

import com.gles.rohit.ogldevandroid.R;

/**
 * Created by Rohith on 12-09-2016.
 */
public class Camera {
    private float[] mCameraMatrix = new float[16];
    private float[] mEye = new float[3];
    private float[] mLookAt = new float[3];
    private float[] mUp = new float[3];
    private boolean DEBUG = false;
    private float mScale = 1.0f;
    Camera(){
        Matrix.setIdentityM(mCameraMatrix,0);
    }

    Camera(boolean debug){
        DEBUG = debug;
        Matrix.setIdentityM(mCameraMatrix,0);
    }

    public void setCamera(float []eye,float []lookAt,float []up){
        mEye = eye;
        mLookAt = lookAt;
        mUp = up;
        Matrix.setLookAtM(mCameraMatrix,0,mEye[0],mEye[1],mEye[2],mLookAt[0],mLookAt[1],mLookAt[2],mUp[0],mUp[1],mUp[2]);
        if(DEBUG)
            Utils.printMatrix(mCameraMatrix,"setCamera:");
    }

    public void updateEyeCamera(int buttonId){
      switch (buttonId){
           case R.id.btn_right: mEye[0] +=mScale;
                                break;
           case R.id.btn_left:  mEye[0] -=mScale;
                                break;
           case R.id.btn_up:    mEye[1] +=mScale;
                                break;
           case R.id.btn_down:  mEye[1] -=mScale;
                                break;
           case R.id.btn_forward: mEye[2] -=mScale;
                                  break;
           case R.id.btn_back:    mEye[2] +=mScale;
                                  break;
       }
        Matrix.setLookAtM(mCameraMatrix,0,mEye[0],mEye[1],mEye[2],mLookAt[0],mLookAt[1],mLookAt[2],mUp[0],mUp[1],mUp[2]);
    }
    public float[] getMatrix() {
        return mCameraMatrix;
    }

    public void rotateCamera(float rotX, float rotY) {
        mLookAt[0]+=rotX;
        mLookAt[1]+=rotY;
        Matrix.setLookAtM(mCameraMatrix,0,mEye[0],mEye[1],mEye[2],mLookAt[0],mLookAt[1],mLookAt[2],mUp[0],mUp[1],mUp[2]);
    }
}
