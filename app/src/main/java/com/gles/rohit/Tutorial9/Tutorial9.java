package com.gles.rohit.Tutorial9;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class Tutorial9 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer9 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer9(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}

