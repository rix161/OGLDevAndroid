package com.gles.rohit.Common;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 01-10-2016.
 */
public class myRenderer implements GLSurfaceView.Renderer {
    public myRenderer(Context context) {

    }

    public void updateCamera(int buttonId){}
    public void rotateCamera(float rotX,float rotY) {}
    public void setAmbientData(float intensity,float[] color){}
    public void setDiffuseData(float intensity,float[] color,float[] position){}
    public float[] getLightData(){return null;}


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
