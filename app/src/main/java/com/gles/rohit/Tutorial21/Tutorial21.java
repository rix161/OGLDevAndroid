package com.gles.rohit.Tutorial21;

import android.view.View;
import android.widget.LinearLayout;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.Tutorial20.myRenderer20;
import com.gles.rohit.Tutorial21.myRenderer21;
import com.gles.rohit.UIControl.lightSettingOnClick;
import com.gles.rohit.ogldevandroid.R;

/**
 * Created by Rohith on 27-12-2016.
 */

public class Tutorial21 extends TutorialTemplate {
    @Override
    public void setRenderer(){
        mRenderer = new myRenderer21(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        mLightButton.setOnClickListener(temp);
    }
}
