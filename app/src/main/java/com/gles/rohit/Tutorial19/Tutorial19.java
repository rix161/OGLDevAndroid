package com.gles.rohit.Tutorial19;

import android.view.View;
import android.widget.LinearLayout;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.Tutorial18.myRenderer18;
import com.gles.rohit.UIControl.lightSettingOnClick;
import com.gles.rohit.ogldevandroid.R;

/**
 * Created by Rohith on 27-12-2016.
 */

public class Tutorial19 extends TutorialTemplate {
    @Override
    public void setRenderer(){
        mRenderer = new myRenderer19(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        mLightButton.setOnClickListener(temp);
    }
}
