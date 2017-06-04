package com.gles.rohit.Tutorial22;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.Tutorial21.myRenderer21;
import com.gles.rohit.UIControl.lightSettingOnClick;

public class Tutorial22 extends TutorialTemplate {
    @Override
    public void setRenderer(){
        mRenderer = new myRenderer22(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        mLightButton.setOnClickListener(temp);
    }
}
