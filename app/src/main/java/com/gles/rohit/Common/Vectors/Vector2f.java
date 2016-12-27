package com.gles.rohit.Common.Vectors;

/**
 * Created by rohit.r on 12/22/2016.
 */
public class Vector2f extends  Vectorf{

    public Vector2f(float[] pos) {

        if(pos!=null && pos.length!=2)
            return;

        mVec = pos.clone();
    }


}
