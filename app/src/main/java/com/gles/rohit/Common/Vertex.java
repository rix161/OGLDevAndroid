package com.gles.rohit.Common;

import com.gles.rohit.Common.Vectors.Vector2f;
import com.gles.rohit.Common.Vectors.Vector3f;
import com.gles.rohit.Common.Vectors.Vector4f;

/**
 * Created by rohit.r on 12/22/2016.
 */
public class Vertex {

    private Vector2f mVertexTextureCoor;
    private Vector3f mVertexPos,mVertexNormal;
    private Vector4f mVertexColor;

    public Vertex(float[] pos,float[] color,float[] normal,float[] coord){
        mVertexPos = new Vector3f(pos);
        mVertexColor = new Vector4f(color);
        mVertexNormal = new Vector3f(normal);
        mVertexTextureCoor = new Vector2f(coord);
    }

    public float[] getPositionRaw(){
        return mVertexPos.get();
    }
    public Vector3f getPosition(){
        return mVertexPos;
    }

    public float[] getColorRaw(){
        return mVertexColor.get();
    }
    public Vector4f getColor(){
        return mVertexColor;
    }

    public float[] getTextureCoordRaw(){
        return mVertexTextureCoor.get();
    }
    public Vector2f getTextureCoord(){
        return mVertexTextureCoor;
    }

    public float[] getNormalRaw(){
        return mVertexNormal.get();
    }
    public Vector3f getNormal(){
        return mVertexNormal;
    }


    public void resetNormal(Vector3f norm){ mVertexNormal = new Vector3f(norm.get());}
    
}
