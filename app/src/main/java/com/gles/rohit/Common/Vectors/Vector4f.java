package com.gles.rohit.Common.Vectors;

import java.util.Arrays;

/**
 * Created by rohit.r on 12/22/2016.
 */
public class Vector4f extends Vectorf {
    public Vector4f(float[] vec) {
        if(vec!=null && vec.length!=4)
            return;
        mVec = Arrays.copyOf(vec,vec.length);
    }
}
