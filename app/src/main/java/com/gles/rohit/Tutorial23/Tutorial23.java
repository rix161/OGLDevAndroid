package com.gles.rohit.Tutorial23;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.util.Log;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.UIControl.lightSettingOnClick;

/**
 * Created by rohit on 9/6/17.
 */

public class Tutorial23 extends TutorialTemplate {
    @Override
    public void setRenderer(){
        final ActivityManager activityManager = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        mSurfaceView.setEGLContextClientVersion(3);
        mRenderer = new myRenderer23(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        mLightButton.setOnClickListener(temp);
    }
}
