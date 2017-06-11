package com.gles.rohit.Common.LightingPackage;

/**
 * Created by rohit on 11/6/17.
 */

public class DirectionalLights {

    private float mDirIntensityAmbi;
    private float mDirIntensityDiffuse;
    private float[] mDirColor;
    private float[] mDirDirection;

    DirectionalLights(float AmbiIntensity,float DiffuseIntensity,float[] direction,float[] color){
        mDirIntensityAmbi = AmbiIntensity;
        mDirIntensityDiffuse = DiffuseIntensity;
        mDirDirection = direction;
        mDirColor = color;
    }

    DirectionalLights(float AmbiIntensity,float[] direction,float[] color){
        mDirIntensityAmbi = AmbiIntensity;
        mDirIntensityDiffuse = 1;
        mDirDirection = direction;
        mDirColor = color;
    }

    public float getAmbiIntensity(){return mDirIntensityAmbi;}
    public float getDiffuseIntensity(){return mDirIntensityDiffuse;}

    public float[] getPosition(){return mDirDirection;}
    public float[] getColor(){return mDirColor;}

}
