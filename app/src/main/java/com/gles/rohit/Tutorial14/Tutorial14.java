package com.gles.rohit.Tutorial14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gles.rohit.Tutorial1.myRenderer;
import com.gles.rohit.ogldevandroid.R;

public class Tutorial14 extends AppCompatActivity {
    myGLSurfaceView mSurfaceView;
    myRenderer14 mRenderer;

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
        mSurfaceView = (com.gles.rohit.Tutorial14.myGLSurfaceView)findViewById(R.id.mSurfaceView);
        mSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new myRenderer14(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);

        findViewById(R.id.btn_right).setOnClickListener(new buttonClick(R.id.btn_right));
        findViewById(R.id.btn_left).setOnClickListener(new buttonClick(R.id.btn_left));
        findViewById(R.id.btn_up).setOnClickListener(new buttonClick(R.id.btn_up));
        findViewById(R.id.btn_down).setOnClickListener(new buttonClick(R.id.btn_down));
        findViewById(R.id.btn_forward).setOnClickListener(new buttonClick(R.id.btn_forward));
        findViewById(R.id.btn_back).setOnClickListener(new buttonClick(R.id.btn_back));


    }
}
