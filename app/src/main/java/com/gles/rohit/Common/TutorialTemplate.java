package com.gles.rohit.Common;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rohith on 01-10-2016.
 */

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.gles.rohit.UIControl.lightSettingOnClick;
import com.gles.rohit.ogldevandroid.R;

public class TutorialTemplate extends AppCompatActivity {

    protected myGLSurfaceView mSurfaceView;
    protected myRenderer mRenderer;
    FloatingActionButton mCameraButton;
    protected FloatingActionButton mLightButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tutorial17);
        init();

    }

    void setRenderer(){
        mRenderer = new myRenderer(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    void setLightButton(){
        mLightButton.setOnClickListener(new lightSettingOnClick(this,mRenderer));
    }


    void init(){

        ViewGroup mSurfaceViewContainer = (ViewGroup) findViewById(R.id.SVContainer);
        mSurfaceView = new myGLSurfaceView(getApplicationContext());
        mSurfaceView.setEGLContextClientVersion(2);
        setRenderer();
        mSurfaceViewContainer.addView(mSurfaceView);

        FloatingActionButton settingApp = (FloatingActionButton) findViewById(R.id.oglSettingsMain);
        settingApp.setOnClickListener( new settingOnClick());
        mCameraButton = (FloatingActionButton) findViewById(R.id.oglSettingsCamera);
        mCameraButton.setOnClickListener( new cameraSettingOnClick(findViewById(R.id.cameraKeyboard)));
        mLightButton = (FloatingActionButton) findViewById(R.id.oglSettingsLight);
        setLightButton();

        findViewById(R.id.kb_btn_right).setOnClickListener(new buttonClick(R.id.kb_btn_right));
        findViewById(R.id.kb_btn_left).setOnClickListener(new buttonClick(R.id.kb_btn_left));
        findViewById(R.id.kb_btn_up).setOnClickListener(new buttonClick(R.id.kb_btn_up));
        findViewById(R.id.kb_btn_down).setOnClickListener(new buttonClick(R.id.kb_btn_down));
        findViewById(R.id.kb_btn_forward).setOnClickListener(new buttonClick(R.id.kb_btn_forward));
        findViewById(R.id.kb_btn_back).setOnClickListener(new buttonClick(R.id.kb_btn_back));
    }

    private class settingOnClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {

            if(view.getTag().equals("unset")) {
                view.setTag("set");
                int mViewPos[] = new int[2];
                view.getLocationOnScreen(mViewPos);
                TranslateAnimation mTranslate = new TranslateAnimation(0, 48, 0, 846);
                final RelativeLayout.LayoutParams mLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                mLayout.addRule(RelativeLayout.CENTER_VERTICAL);
                mTranslate.setDuration(1000);
                view.startAnimation(mTranslate);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCameraButton.setVisibility(View.VISIBLE);
                        mLightButton.setVisibility(View.VISIBLE);
                        view.setAlpha(1.0f);
                        view.setLayoutParams(mLayout);
                    }
                },1000);
            }
            else{
                mCameraButton.setVisibility(View.INVISIBLE);
                mLightButton.setVisibility(View.INVISIBLE);

                view.setTag("unset");
                int mViewPos[] = new int[2];
                view.getLocationOnScreen(mViewPos);
                final RelativeLayout.LayoutParams mLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                mLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                TranslateAnimation mTranslate = new TranslateAnimation(0,0 , 0, -1000);
                mTranslate.setDuration(1000);
                view.startAnimation(mTranslate);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setLayoutParams(mLayout);
                        view.setAlpha(0.5f);
                    }
                },1000);
            }
        }
    }

    private class cameraSettingOnClick implements View.OnClickListener {
        View mKeyboardLayout;
        public cameraSettingOnClick(View viewById) {
            mKeyboardLayout = viewById;
        }

        @Override
        public void onClick(View view) {
            if(mKeyboardLayout.getVisibility()==View.VISIBLE){
                mKeyboardLayout.setVisibility(View.INVISIBLE);
            }
            else{
                mKeyboardLayout.setVisibility(View.VISIBLE);
            }
        }
    }

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

}

