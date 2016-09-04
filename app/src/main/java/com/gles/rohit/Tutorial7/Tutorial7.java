package com.gles.rohit.Tutorial7;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class Tutorial7 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer7 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer7(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}
