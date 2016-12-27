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
    LinkedList<DirectionalLights> mDirLights;

    private final int mTotalAmbiData = 4;
    private int mTotalDataSize;

    public Lighting(){
        mAmbiIntensity = 0.01f;
        mAmbiColor = new float[]{1.0f,1.0f,1.0f};
        mTotalDataSize = mTotalAmbiData;
        mDirLights = new LinkedList<>();
    }

    public void setAmbientLightData(float intensity,float color[]){
        mAmbiIntensity = intensity;
        if(color.length==3)
            mAmbiColor = color;
    }

    public void setDirectionLightData(float intensity,float[] color,float[] pos){
        if(mDirLights!=null) {
            mDirLights.clear();
            mDirLights.add(new DirectionalLights(intensity, pos, color));
        }
    }


    public void doLighting(int handles[]){
        setAmbientLight(handles[0],handles[1]);
        setDirectionalLight(handles);
    }

    private void setDirectionalLight(int[] handles) {
        if(handles.length<=2) return;

        for(DirectionalLights dirLight:mDirLights) {
            for (int i = 2; i < handles.length; i += 3) {
                GLES20.glUniform1f(handles[i], dirLight.getIntensity());
                GLES20.glUniform3fv(handles[i + 1], 1, dirLight.getPosition(), 0);
                GLES20.glUniform3fv(handles[i + 2], 1, dirLight.getColor(), 0);
            }
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
        if(mDirLights!=null && mDirLights.size()>0) {
            DirectionalLights light = mDirLights.get(0);
            lightData[4] = light.getIntensity();

            float dirPos[] = light.getPosition();
            lightData[5] = dirPos[0];
            lightData[6] = dirPos[1];
            lightData[7] = dirPos[2];

            float dirColor[] = light.getColor();
            lightData[8] = dirColor[0];
            lightData[9] = dirColor[1];
            lightData[10] = dirColor[2];
        }
        return lightData;
    }

    private class DirectionalLights {

        private float mDirIntensity;
        private float[] mDirColor;
        private float[] mDirPos;

        DirectionalLights(float intensity,float[] pos,float[] color){
            mDirIntensity = intensity;
            mDirPos = pos;
            mDirColor = color;
        }

        public float getIntensity(){return mDirIntensity;}
        public float[] getPosition(){return mDirPos;}
        public float[] getColor(){return mDirColor;}

    }
}
