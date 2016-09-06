package com.gles.rohit.Tutorial13;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Tutorial13 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer13 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer13(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}

