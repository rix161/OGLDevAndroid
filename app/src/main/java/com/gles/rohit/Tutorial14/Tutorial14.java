package com.gles.rohit.Tutorial14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gles.rohit.Tutorial1.myRenderer;
import com.gles.rohit.ogldevandroid.R;

public class Tutorial14 extends AppCompatActivity {
    myGLSurfaceView mSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial14);
        mSurfaceView = (com.gles.rohit.Tutorial14.myGLSurfaceView)findViewById(R.id.mSurfaceView);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(new myRenderer14(getApplicationContext()));

    }
}
