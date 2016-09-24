package com.gles.rohit.Common;

import android.opengl.GLES20;

/**
 * Created by Rohith on 24-09-2016.
 */


public class Lighting {
    private float mAmbiIntensity;
    private float[] mAmbiColor;
    private final int mTotalAmbiData = 4;
    private int mTotalDataSize;
    public Lighting(){
        mAmbiIntensity = 1.0f;
        mAmbiColor = new float[]{1.0f,1.0f,1.0f};
        mTotalDataSize = mTotalAmbiData;
    }

    public void setAmbientLightData(float intensity,float color[]){
        mAmbiIntensity = intensity;
        if(color.length==3)
            mAmbiColor = color;
    }


    public void doLighting(int handles[]){
        setAmbientLight(handles[0],handles[1]);
    }

    private void setAmbientLight(int intensityHandle, int colorHandle) {
        GLES20.glUniform1f(intensityHandle,mAmbiIntensity);
        GLES20.glUniform3fv(colorHandle,1,mAmbiColor,0);
    }

    public float[] getLightingData() {
        return new float[]{mAmbiIntensity,mAmbiColor[0],mAmbiColor[1],mAmbiColor[2]};
    }
}
