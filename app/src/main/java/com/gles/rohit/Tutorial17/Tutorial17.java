package com.gles.rohit.Tutorial17;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.gles.rohit.ogldevandroid.R;

public class Tutorial17 extends AppCompatActivity {

    myGLSurfaceView17 mSurfaceView;
    myRenderer17 mRenderer;
    private int mViewX;
    private int mViewY;
    FloatingActionButton mCameraButton;
    FloatingActionButton mLightButton;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_tutorial17);
        ViewGroup mSurfaceViewContainer = (ViewGroup) findViewById(R.id.SVContainer);
        mSurfaceView = new myGLSurfaceView17(getApplicationContext());
        mSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new myRenderer17(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
        mSurfaceViewContainer.addView(mSurfaceView);

        FloatingActionButton settingApp = (FloatingActionButton) findViewById(R.id.oglSettingsMain);
        settingApp.setOnClickListener( new settingOnClick());
        mCameraButton = (FloatingActionButton) findViewById(R.id.oglSettingsCamera);
        mCameraButton.setOnClickListener( new cameraSettingOnClick(findViewById(R.id.cameraKeyboard)));
        mLightButton = (FloatingActionButton) findViewById(R.id.oglSettingsLight);
        mLightButton.setOnClickListener(new lightSettingOnClick(this));

       findViewById(R.id.btn_right).setOnClickListener(new buttonClick(R.id.btn_right));
       findViewById(R.id.btn_left).setOnClickListener(new buttonClick(R.id.btn_left));
       findViewById(R.id.btn_up).setOnClickListener(new buttonClick(R.id.btn_up));
       findViewById(R.id.btn_down).setOnClickListener(new buttonClick(R.id.btn_down));
       findViewById(R.id.btn_forward).setOnClickListener(new buttonClick(R.id.btn_forward));
       findViewById(R.id.btn_back).setOnClickListener(new buttonClick(R.id.btn_back));

    }

    private class settingOnClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {

            if(view.getTag().equals("unset")) {
                view.setTag("set");
                int mViewPos[] = new int[2];
                view.getLocationOnScreen(mViewPos);
                mViewX = mViewPos[0];
                mViewY = mViewPos[1];
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

    private class lightSettingOnClick implements View.OnClickListener {
        Tutorial17 mContext;
        LayoutInflater mLayoutInflator;
        PopupWindow mPopupWindow;
        View mPopUpView;
        public lightSettingOnClick(Tutorial17 tutorial17) {
            mContext = tutorial17;
            mLayoutInflator = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            mPopUpView = mLayoutInflator.inflate(R.layout.lightsettingoptions,null);
            mPopupWindow = new PopupWindow(mPopUpView,750,750,true);

        }

        @Override
        public void onClick(View view) {
            if(view.getTag().equals("unset")){
                float lightData[] = mRenderer.getLightData();
                Button mLightSetButton = (Button) mPopupWindow.getContentView().findViewById(R.id.lightSetBtn);
                final EditText mAmbiIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.ambi_light_Intensity);
                mAmbiIntensity.setText("");
                mAmbiIntensity.setText(lightData[0]+"");
                final EditText mAmbiRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.ambi_light_red);
                mAmbiRed.setText(lightData[1]+"");
                final EditText mAmbiBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.ambi_light_blue);
                mAmbiBlue.setText(lightData[2]+"");
                final EditText mAmbiGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.ambi_light_green);
                mAmbiGreen.setText(lightData[3]+"");
                mPopupWindow.showAtLocation(view,Gravity.CENTER,0,0);
                mLightSetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        float newAmbiIntensity = Float.parseFloat(mAmbiIntensity.getText().toString());
                        float newAmbiRed = Float.parseFloat(mAmbiRed.getText().toString());
                        float newAmbiGreen = Float.parseFloat(mAmbiBlue.getText().toString());
                        float newAmbiBlue = Float.parseFloat(mAmbiGreen.getText().toString());
                        mRenderer.setAmbientData(newAmbiIntensity,new float[]{newAmbiRed,newAmbiGreen,newAmbiBlue});
                        mPopupWindow.dismiss();
                    }
                });
            }
        }
    }
}
