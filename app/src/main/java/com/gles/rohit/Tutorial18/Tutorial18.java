package com.gles.rohit.Tutorial18;

import android.view.View;
import android.widget.LinearLayout;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.UIControl.lightSettingOnClick;
import com.gles.rohit.ogldevandroid.R;

public class Tutorial18 extends TutorialTemplate{

    @Override
    public void setRenderer(){
        mRenderer = new myRenderer18(getApplicationContext());
        mSurfaceView.setRenderer(mRenderer);
    }

    @Override
    public void setLightButton(){
        lightSettingOnClick temp = new lightSettingOnClick(this,mRenderer,new Utils.lightType[]{Utils.lightType.AMBIENT, Utils.lightType.DIFFUSE});
        View popUp = temp.getPopUpView();
        LinearLayout ll = (LinearLayout) popUp.findViewById(R.id.diffuse_settings);
        ll.setVisibility(View.VISIBLE);
        mLightButton.setOnClickListener(temp);
    }
}
