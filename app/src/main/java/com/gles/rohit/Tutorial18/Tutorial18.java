package com.gles.rohit.Tutorial18;

import android.view.View;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.UIControl.lightSettingOnClick;

public class Tutorial18 extends TutorialTemplate{

    void setLightButton(){
        mLightButton.setOnClickListener(new lightSettingOnClick(this,mRenderer));
    }
}
