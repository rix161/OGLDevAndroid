package com.gles.rohit.Common.LightingPackage;

/**
 * Created by rohit on 11/6/17.
 */

public class SpotLight extends PointLight {

    private  float[] direction;
    private float cutoff;

    public SpotLight(float[] color, float[] intensity, float[] position, float[] atten,float[] direction,float cutoff) {
        super(color, intensity, position, atten);
        this.direction = direction;
        this.cutoff = (float)Math.cos(Math.toRadians(cutoff));
    }

    public float[] getDirection(){ return  direction;}

    public float getCutoff(){ return cutoff;}
}