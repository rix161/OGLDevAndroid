package com.gles.rohit.Tutorial3;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles.rohit.ogldevandroid.R;

public class Tutorial3 extends Activity {

    GLSurfaceView mSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(new myRenderer3(getApplicationContext()));
        setContentView(mSurfaceView);
    }
}
