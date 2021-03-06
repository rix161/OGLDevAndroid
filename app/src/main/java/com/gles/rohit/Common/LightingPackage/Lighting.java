package com.gles.rohit.Common.LightingPackage;

import android.opengl.GLES20;

import java.util.LinkedList;

/**
 * Created by Rohith on 24-09-2016.
 */


public class Lighting {
    private float mAmbiIntensity;
    private float[] mAmbiColor;
    private DirectionalLights mDirLights;
    private SpecularData mSpecularData;
    private LinkedList<PointLight> mPointLights;
    private LinkedList<SpotLight> mSpotLights;
    private int mNumPointLights;
    private int mNumSpotLights;

    public Lighting(){
        mAmbiIntensity = 0.0f;
        mAmbiColor = new float[]{1.0f,1.0f,1.0f};
        mPointLights = new LinkedList<>();
        mSpotLights = new LinkedList<>();
        mNumPointLights = 0;
        mNumSpotLights = 0;
    }

    public void setAmbientIntensity(float intensity){mAmbiIntensity = intensity;}
    public void addPointLight(float[] color,float[] intensity,float[] pos,float[] attenuation ){

        if(mPointLights.size() >= mNumPointLights) return;

        mPointLights.add(new PointLight(color,intensity,pos,attenuation));

    }

    public void addSpotLight(float[] color,float[] intensity,float[] pos,float[] attenuation,float[] direction,float cutoff ){

        if(mSpotLights.size() >= mNumSpotLights) return;

        mSpotLights.add(new SpotLight(color,intensity,pos,attenuation,direction,cutoff));

    }

    public SpotLight getSpotLight(int index){
        if(mSpotLights.size()<index) return null;
        return mSpotLights.get(index);

    }

    public void updatePointLight(int index,float[] newPos){
        if(index>=mPointLights.size())return;
        mPointLights.get(index).updatePosition(newPos);
    }

    public void updateSpotLight(int index,float[] newPos){
        if(index>=mSpotLights.size())return;
        mSpotLights.get(index).updatePosition(newPos);
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
        setPointLightData(handles);
        setSpotLightData(handles);
    }

    private void setSpotLightData(int[] handles) {
        int spotLightIndex = 9+7*mNumPointLights+1;

        if(handles.length <= spotLightIndex) return;

        GLES20.glUniform1i(handles[spotLightIndex],mSpotLights.size());
        int count = 1;

        for(SpotLight pLight:mSpotLights) {
            GLES20.glUniform3fv(handles[spotLightIndex+count++],1,pLight.getColor(),0);
            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getAmbientIntensity());
            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getDiffuseIntensity());

            GLES20.glUniform3fv(handles[spotLightIndex+count++],1,pLight.getPosition(),0);

            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getAttenuation()[0]);
            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getAttenuation()[1]);
            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getAttenuation()[2]);

            GLES20.glUniform3fv(handles[spotLightIndex+count++],1,pLight.getDirection(),0);
            GLES20.glUniform1f(handles[spotLightIndex+count++],pLight.getCutoff());

        }

    }

    private void setPointLightData(int[] handles) {
        int pointLightIndex = 9;

        if(handles.length <= pointLightIndex) return;

        GLES20.glUniform1i(handles[pointLightIndex],mPointLights.size());
        int count = 1;

        for(PointLight pLight:mPointLights) {
            GLES20.glUniform3fv(handles[pointLightIndex+count++],1,pLight.getColor(),0);
            GLES20.glUniform1f(handles[pointLightIndex+count++],pLight.getAmbientIntensity());
            GLES20.glUniform1f(handles[pointLightIndex+count++],pLight.getDiffuseIntensity());
            GLES20.glUniform3fv(handles[pointLightIndex+count++],1,pLight.getPosition(),0);
            GLES20.glUniform1f(handles[pointLightIndex+count++],pLight.getAttenuation()[0]);
            GLES20.glUniform1f(handles[pointLightIndex+count++],pLight.getAttenuation()[1]);
            GLES20.glUniform1f(handles[pointLightIndex+count++],pLight.getAttenuation()[2]);
        }
    }


    private void setSpecularData(int[] handles) {
        if(handles.length>6 && mSpecularData!=null) {
            GLES20.glUniform1f(handles[6], mSpecularData.getIntensity());
            GLES20.glUniform1f(handles[7], mSpecularData.getPower());
            GLES20.glUniform3fv(handles[8], 1, mSpecularData.getEyePosition(), 0);
        }
    }

    private void setDirectionalLight(int[] handles) {
        if(handles.length<=2) return;

        if(mDirLights!=null) {
            GLES20.glUniform3fv(handles[2], 1, mDirLights.getColor(), 0);
            GLES20.glUniform1f(handles[3], mDirLights.getAmbiIntensity());
            GLES20.glUniform1f(handles[4], mDirLights.getDiffuseIntensity());
            GLES20.glUniform3fv(handles[5], 1, mDirLights.getPosition(), 0);
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

            lightData[4] = mDirLights.getAmbiIntensity();

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

    public void updateSpecularLightPosition(float[] eyePos){
        if(mSpecularData!=null)
            mSpecularData.setEyePosition(eyePos);
    }

    public void setNumberOfPointLights(int pointLightCount) {
        mNumPointLights = pointLightCount;
    }

    public void setNumberOfSpotLights(int spotLightCount) { mNumSpotLights = spotLightCount;}

}
