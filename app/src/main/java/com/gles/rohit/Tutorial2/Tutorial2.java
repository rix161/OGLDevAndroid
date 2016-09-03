package com.gles.rohit.Tutorial2;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Tutorial2 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer2 mRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new myRenderer2(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRenderer.onDestroy();
    }
}