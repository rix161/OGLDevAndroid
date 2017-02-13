package com.gles.rohit.Common;

import android.opengl.GLES20;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Rohith on 24-09-2016.
 */


public class Lighting {
    private float mAmbiIntensity;
    private float[] mAmbiColor;
    private DirectionalLights mDirLights;
    private SpecularData mSpecularData;

    private final int mTotalAmbiData = 4;

    public Lighting(){
        mAmbiIntensity = 1.0f;
        mAmbiColor = new float[]{1.0f,1.0f,1.0f};

    }

    public void setAmbientLightData(float intensity,float color[]){
        mAmbiIntensity = intensity;
        if(color.length==3)
            mAmbiColor = color;
    }

    public void setDirectionLightData(float intensity,float[] color,float[] direction){

        mDirLights = new DirectionalLights(intensity, direction, color);

    }


    public void doLighting(int handles[]){
        setAmbientLight(handles[0],handles[1]);
        setDirectionalLight(handles);
        setSpecularData(handles);
    }

    private void setSpecularData(int[] handles) {
        if(handles.length>5) {
            GLES20.glUniform1f(handles[5], mSpecularData.getIntensity());
            GLES20.glUniform1f(handles[6], mSpecularData.getPower());
            GLES20.glUniform3fv(handles[7], 1, mSpecularData.getEyePosition(), 0);
        }
    }

    private void setDirectionalLight(int[] handles) {
        if(handles.length<=2) return;

        if(mDirLights!=null) {
            GLES20.glUniform1f(handles[2], mDirLights.getIntensity());
            GLES20.glUniform3fv(handles[3], 1, mDirLights.getPosition(), 0);
            GLES20.glUniform3fv(handles[4], 1, mDirLights.getColor(), 0);
        }

    }

    private void setAmbientLight(int intensityHandle, int colorHandle) {
        GLES20.glUniform1f(intensityHandle,mAmbiIntensity);
        GLES20.glUniform3fv(colorHandle,1,mAmbiColor,0);
    }

    public float[] getLightingData() {
        float [] lightData = new float[11];

        lightData[0] = mAmbiIntensity;
        lightData[1] = mAmbiColor[0];
        lightData[2] = mAmbiColor[1];
        lightData[3] = mAmbiColor[2];

        if(mDirLights!=null) {

            lightData[4] = mDirLights.getIntensity();

            float dirPos[] = mDirLights.getPosition();
            lightData[5] = dirPos[0];
            lightData[6] = dirPos[1];
            lightData[7] = dirPos[2];

            float dirColor[] = mDirLights.getColor();
            lightData[8] = dirColor[0];
            lightData[9] = dirColor[1];
            lightData[10] = dirColor[2];

        }

        return lightData;
    }

    public void setSpecularLightData(float intensity, float power, float[] eyePosition) {
        mSpecularData = new SpecularData(intensity,power,eyePosition);
    }

    private class DirectionalLights {

        private float mDirIntensity;
        private float[] mDirColor;
        private float[] mDirDirection;

        DirectionalLights(float intensity,float[] direction,float[] color){
            mDirIntensity = intensity;
            mDirDirection = direction;
            mDirColor = color;
        }

        public float getIntensity(){return mDirIntensity;}
        public float[] getPosition(){return mDirDirection;}
        public float[] getColor(){return mDirColor;}

    }

    private class SpecularData {

        private float mIntensity;
        private float mPower;
        private float[] eyePosition;

        SpecularData(float intensity,float power,float[] eye){
            mIntensity = intensity;
            mPower = power;
            eyePosition = eye;
        }

        public float getIntensity(){return mIntensity;}
        public float getPower(){ return mPower;}
        public float[] getEyePosition(){ return eyePosition;}
        public void setEyePosition(float[]pos){ eyePosition = pos;}
    }

}
