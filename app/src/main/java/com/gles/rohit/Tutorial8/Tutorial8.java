package com.gles.rohit.Tutorial8;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class Tutorial8 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer8 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer8(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}
