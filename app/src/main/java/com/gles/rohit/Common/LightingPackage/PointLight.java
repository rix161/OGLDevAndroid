package com.gles.rohit.Common.LightingPackage;

/**
 * Created by rohit on 11/6/17.
 */

public class PointLight{

    private float mColor[];
    private float mIntensity[];
    private float mAttenuation[];
    private float mPosition[];

    public PointLight(float color[],float intensity[],float[] position,float atten[]){
        mColor = color;
        mIntensity = intensity;
        mPosition = position;
        mAttenuation = atten;
    }

    public float[] getColor(){
        return  mColor;
    }

    public float[] getAttenuation(){
        return mAttenuation;

    }

    public float getAmbientIntensity(){
        if(mIntensity!=null)
            return mIntensity[0];
        return 0;
    }

    public float getDiffuseIntensity(){
        if(mIntensity!=null)
            return mIntensity[1];
        return 0;
    }

    public float[] getPosition(){
        return  mPosition;
    }

    public void updatePosition(float[] pos){
        mPosition = pos;
    }
};

