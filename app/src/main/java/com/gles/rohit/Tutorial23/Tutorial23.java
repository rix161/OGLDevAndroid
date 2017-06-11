package com.gles.rohit.Tutorial23;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.UIControl.lightSettingOnClick;

/**
 * Created by rohit on 9/6/17.
 */

public class Tutorial23 extends TutorialTemplate {
    @Override
    public void setRenderer(){
        mRenderer = new myRenderer23(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        mLightButton.setOnClickListener(temp);
    }
}
