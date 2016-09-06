package com.gles.rohit.Tutorial12;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Tutorial12 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer12 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer12(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}

