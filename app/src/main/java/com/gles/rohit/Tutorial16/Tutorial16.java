package com.gles.rohit.Tutorial16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.gles.rohit.Tutorial14.myRenderer14;
import com.gles.rohit.Tutorial15.myGLSurfaceView15;
import com.gles.rohit.ogldevandroid.R;

public class Tutorial16 extends AppCompatActivity {

    myGLSurfaceView16 mSurfaceView;
    myRenderer16 mRenderer;

    private class buttonClick implements View.OnClickListener{
        private int mButtonId;

        buttonClick(int buttonId){
            mButtonId = buttonId;
        }
        @Override
        public void onClick(View view) {
            mRenderer.updateCamera(mButtonId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial14);

        ViewGroup mSurfaceViewContainer = (ViewGroup) findViewById(R.id.SVContainer);
        mSurfaceView = new myGLSurfaceView16(getApplicationContext());
        mSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new myRenderer16(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
        mSurfaceViewContainer.addView(mSurfaceView);

        findViewById(R.id.btn_right).setOnClickListener(new buttonClick(R.id.btn_right));
        findViewById(R.id.btn_left).setOnClickListener(new buttonClick(R.id.btn_left));
        findViewById(R.id.btn_up).setOnClickListener(new buttonClick(R.id.btn_up));
        findViewById(R.id.btn_down).setOnClickListener(new buttonClick(R.id.btn_down));
        findViewById(R.id.btn_forward).setOnClickListener(new buttonClick(R.id.btn_forward));
        findViewById(R.id.btn_back).setOnClickListener(new buttonClick(R.id.btn_back));
    }
}
