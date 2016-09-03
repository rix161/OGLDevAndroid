package com.gles.rohit.Tutorial6;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class Tutorial6 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer6 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer6(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}
