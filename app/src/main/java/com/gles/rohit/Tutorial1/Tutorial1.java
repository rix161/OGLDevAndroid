package com.gles.rohit.Tutorial1;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Tutorial1 extends Activity {
    GLSurfaceView mSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(new myRenderer(getApplicationContext()));
        setContentView(mSurfaceView);
    }
}
