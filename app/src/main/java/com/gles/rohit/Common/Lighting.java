package com.gles.rohit.Common;

import android.opengl.GLES20;

/**
 * Created by Rohith on 24-09-2016.
 */


public class Lighting {
    private float mAmbiIntensity;
    private float[] mAmbiColor;
    private final int mTotalAmbiData = 4;

    private float mDiffuseIntensity;
    private float[] mDiffuseColor;
    private float[] mPosition;
    private final int mTotalDiffuseData = 7;

    public int mTotalDataSize;

    public Lighting(){
        mAmbiIntensity = 1.0f;
        mAmbiColor = new float[]{1.0f,1.0f,1.0f};

        mDiffuseIntensity = 1.0f;
        mDiffuseColor = new float[]{1.0f,1.0f,1.0f};
        mPosition = new float[]{1.0f,1.0f,1.0f};
        mTotalDataSize = mTotalAmbiData+mTotalDiffuseData;
    }

    public void setAmbientLightData(float intensity,float color[]){
        mAmbiIntensity = intensity;
        if(color.length==3)
            mAmbiColor = color;
    }


    public void setDiffuseLightData(float intensity,float color[],float pos[]){
        mDiffuseIntensity = intensity;
        if(color.length==3)
            mDiffuseColor = color;
        mPosition = pos;
    }


    public void doLighting(int handles[]){
        setAmbientLight(handles[0],handles[1]);
        if(handles.length > 6)
            setDiffuseLight(handles[2],handles[3],handles[4]);

    }

    private void setDiffuseLight(int handle, int handle1, int handle2) {

    }

    private void setAmbientLight(int intensityHandle, int colorHandle) {
        GLES20.glUniform1f(intensityHandle,mAmbiIntensity);
        GLES20.glUniform3fv(colorHandle,1,mAmbiColor,0);
    }

    public float[] getLightingData() {
        return new float[]{mAmbiIntensity,mAmbiColor[0],mAmbiColor[1],mAmbiColor[2],mDiffuseIntensity,mDiffuseColor[0],mPosition[0]};
    }
}
