package com.gles.rohit.UIControl;

;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.myRenderer;
import com.gles.rohit.ogldevandroid.R;

/**
 * Created by Rohith on 01-10-2016.
 */
public class lightSettingOnClick implements View.OnClickListener {
    TutorialTemplate mContext;
    LayoutInflater mLayoutInflator;
    PopupWindow mPopupWindow;
    View mPopUpView;
    myRenderer mRenderer;
    public lightSettingOnClick(TutorialTemplate tutorial,myRenderer renderer) {
        mContext = tutorial;
        mRenderer = renderer;
        mLayoutInflator = (LayoutInflater) mContext.getSystemService(TutorialTemplate.LAYOUT_INFLATER_SERVICE);
        mPopUpView = mLayoutInflator.inflate(R.layout.lightsettingoptions,null);
        mPopupWindow = new PopupWindow(mPopUpView,750,750,true);

    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("unset")){
            float lightData[] = mRenderer.getLightData();
            Button mLightSetButton = (Button) mPopupWindow.getContentView().findViewById(R.id.lightSetBtn);
            final EditText mAmbiIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_Intensity);
            mAmbiIntensity.setText("");
            mAmbiIntensity.setText(lightData[0]+"");
            final EditText mAmbiRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_red);
            mAmbiRed.setText(lightData[1]+"");
            final EditText mAmbiBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_blue);
            mAmbiBlue.setText(lightData[2]+"");
            final EditText mAmbiGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_green);
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