package com.gles.rohit.Tutorial4;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles.rohit.ogldevandroid.R;

public class Tutorial4 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer4 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer4(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}
