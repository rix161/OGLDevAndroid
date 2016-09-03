package com.gles.rohit.Tutorial5;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles.rohit.ogldevandroid.R;

public class Tutorial5 extends Activity {

    GLSurfaceView mSurfaceView;
    myRenderer5 myRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        myRenderer = new myRenderer5(getApplicationContext());
        mSurfaceView.setRenderer(myRenderer);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myRenderer.onDestroy();

    }
}
