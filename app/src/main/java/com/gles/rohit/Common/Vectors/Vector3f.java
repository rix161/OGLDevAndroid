package com.gles.rohit.Common.Vectors;

import java.util.Arrays;

/**
 * Created by rohit.r on 12/22/2016.
 */
public class Vector3f extends Vectorf{

    public Vector3f(float[] vec) {
        if(vec!=null && vec.length!=3)
            return;
        mVec = Arrays.copyOf(vec,vec.length);
    }

    public Vector3f cross(float[] vec){

        float x = mVec[1]*vec[2] - mVec[2]*vec[1];
        float y = -(mVec[0]*vec[2] - mVec[2]*vec[0]);
        float z = mVec[0]*vec[1] - mVec[1]*vec[0];
        return new Vector3f(new float[]{x,y,z});
    }


}
