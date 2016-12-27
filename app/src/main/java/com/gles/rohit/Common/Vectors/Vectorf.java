package com.gles.rohit.Common.Vectors;

import android.util.Log;

/**
 * Created by rohit.r on 12/22/2016.
 */
public class Vectorf {
    protected float[] mVec;

    public float[] get(){ return mVec;}

    public int size(){
        if(mVec!=null)
            return mVec.length;
        else
            return -1;
    }

    public Vectorf add(Vectorf vec){

        if(vec==null || this.size()!=vec.size()) return null;

        float[] newVec = new float[vec.size()];
        float[] vec2 = vec.get();

        for(int i=0;i<vec.size();i++){
            newVec[i] = mVec[i]+vec2[i];
        }

        return buildNewVec(newVec);
    }

    public Vectorf subtact(Vectorf vec){

        if(vec==null || this.size()!=vec.size()) return null;

        float[] newVec = new float[vec.size()];
        float[] vec2 = vec.get();

        for(int i=0;i<vec.size();i++){
            newVec[i] = mVec[i]-vec2[i];
        }

        return buildNewVec(newVec);
    }

    public Vectorf scalarMultiply(float scale){

        float[] newVec = new float[mVec.length];
        for(int i=0;i<mVec.length;i++){
            newVec[i] = mVec[i]*scale;
        }

        return buildNewVec(newVec);
    }

    public Vectorf cross(Vectorf vec){
        return null;
    }

    private Vectorf buildNewVec(float[] newVec) {

        switch (newVec.length){
            case 2: return new Vector2f(newVec);
            case 3: return new Vector3f(newVec);
            case 4: return new Vector4f(newVec);
            default: return null;
        }

    }

    public void normalize(){

        double length = 0;

        for(float vals:mVec){
            length +=vals*vals;
        }

        length = Math.sqrt(length);
        for(int i=0;i<mVec.length;i++){
            mVec[i] /=(float)length;
        }
    }

    public String print(){

        StringBuilder sb = new StringBuilder();

        for(float val:mVec){
            sb.append(val).append(" ");
        }

        return sb.toString();
    }

}
