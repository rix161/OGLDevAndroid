package com.gles.rohit.Tutorial10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class Tutorial10 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer10 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer10(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}

