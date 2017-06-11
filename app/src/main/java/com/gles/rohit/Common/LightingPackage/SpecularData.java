package com.gles.rohit.Common.LightingPackage;

/**
 * Created by rohit on 11/6/17.
 */

public class SpecularData {

    private float mIntensity;
    private float mPower;
    private float[] eyePosition;

    public SpecularData(float intensity,float power,float[] eye){
        mIntensity = intensity;
        mPower = power;
        eyePosition = eye;
    }

    public float getIntensity(){return mIntensity;}
    public float getPower(){ return mPower;}
    public float[] getEyePosition(){ return eyePosition;}
    public void setEyePosition(float[]pos){ eyePosition = pos;}
}
