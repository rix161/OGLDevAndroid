package com.gles.rohit.UIControl;

;
import android.media.audiofx.LoudnessEnhancer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.gles.rohit.Common.TutorialTemplate;
import com.gles.rohit.Common.Utils;
import com.gles.rohit.Common.myRenderer;
import com.gles.rohit.ogldevandroid.R;

/**
 * Created by Rohith on 01-10-2016.
 */

public class lightSettingOnClick implements View.OnClickListener {
    TutorialTemplate mContext;
    LayoutInflater mLayoutInflator;
    PopupWindow mPopupWindow;
    protected View mPopUpView;
    myRenderer mRenderer;
    Utils.lightType mLightTypes[];

    public lightSettingOnClick(TutorialTemplate tutorial,myRenderer renderer) {
        mContext = tutorial;
        mRenderer = renderer;
        setUpPopUp();
    }

    public lightSettingOnClick(TutorialTemplate tutorial,myRenderer renderer,Utils.lightType ligthType[]) {
        mContext = tutorial;
        mRenderer = renderer;
        setUpPopUp();
        mLightTypes = ligthType;
    }

    private void setUpPopUp() {
        mLayoutInflator = (LayoutInflater) mContext.getSystemService(TutorialTemplate.LAYOUT_INFLATER_SERVICE);
        mPopUpView = mLayoutInflator.inflate(R.layout.lightsettingoptions,null);
        mPopupWindow = new PopupWindow(mPopUpView,1024,1024,true);
    }

    public View getPopUpView(){
        return mPopUpView;
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("unset")){
            Button mLightSetButton = (Button) mPopupWindow.getContentView().findViewById(R.id.lightSetBtn);
            mPopupWindow.showAtLocation(view,Gravity.CENTER,0,0);

            for(Utils.lightType lType:mLightTypes){
                switch (lType){
                    case AMBIENT: setUpAmbientLight();
                        break;
                    case DIFFUSE: setUpDiffuseLight();
                        break;
                    case SPECULAR: setUpSpecularLight();
                        break;
                    case POINT: setUpPointLight();
                        break;
                    case SPOT: setUpSpotLight();
                        break;
                }
            }


            mLightSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mLightTypes==null || mLightTypes.length==0){
                        setAmbientLight();
                    }
                    else{
                        for(Utils.lightType lType:mLightTypes){
                            switch (lType){
                                case AMBIENT: setAmbientLight();
                                    break;
                                case DIFFUSE: setDiffuseLight();
                                    break;
                                case SPECULAR: setSpecularLight();
                                    break;
                                case POINT: setPointLight();
                                    break;
                                case SPOT: setSpotLight();
                                    break;
                            }
                        }
                    }
                    mPopupWindow.dismiss();
                }
            });
        }
    }

    private void setUpAmbientLight() {
        if(mRenderer == null)
            return;

        float lightData[] = mRenderer.getLightData();

        if(lightData == null || lightData.length<3)
            return;

        final EditText mAmbiIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_Intensity);
        mAmbiIntensity.setText("");
        mAmbiIntensity.setText(lightData[0]+"");
        final EditText mAmbiRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_red);
        mAmbiRed.setText(lightData[1]+"");
        final EditText mAmbiBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_blue);
        mAmbiBlue.setText(lightData[2]+"");
        final EditText mAmbiGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_green);
        mAmbiGreen.setText(lightData[3]+"");
    }

    private void setUpDiffuseLight() {

        if(mRenderer == null)
            return;

        float lightData[] = mRenderer.getLightData();
        if(lightData == null || lightData.length<3)
            return;

        final EditText mDiffuseIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_Intensity);
        mDiffuseIntensity.setText(lightData[4]+"");

        final EditText mDiffuseX = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_X);
        mDiffuseX.setText(lightData[5]+"");
        final EditText mDiffuseY = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_Y);
        mDiffuseY.setText(lightData[6]+"");
        final EditText mDiffuseZ = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_Z);
        mDiffuseZ.setText(lightData[7]+"");

        final EditText mDiffuseRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_red);
        mDiffuseRed.setText(lightData[8]+"");
        final EditText mDiffuseBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_blue);
        mDiffuseBlue.setText(lightData[9]+"");
        final EditText mDiffuseGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_green);
        mDiffuseGreen.setText(lightData[10]+"");
    }

    private void setUpSpecularLight() {

    }

    private void setUpPointLight() {
    }

    private void setUpSpotLight() {

    }


    private void setAmbientLight() {

        final EditText mAmbiIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_Intensity);
        final EditText mAmbiRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_red);
        final EditText mAmbiBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_blue);
        final EditText mAmbiGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_ambi_light_green);

        float newAmbiIntensity = Float.parseFloat(mAmbiIntensity.getText().toString());
        float newAmbiRed = Float.parseFloat(mAmbiRed.getText().toString());
        float newAmbiGreen = Float.parseFloat(mAmbiBlue.getText().toString());
        float newAmbiBlue = Float.parseFloat(mAmbiGreen.getText().toString());

        mRenderer.setAmbientData(newAmbiIntensity,new float[]{newAmbiRed,newAmbiGreen,newAmbiBlue});
    }


    private void setDiffuseLight() {
        final EditText mDiffuseIntensity = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_Intensity);

        final EditText mDiffuseRed = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_red);
        final EditText mDiffuseBlue = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_blue);
        final EditText mDiffuseGreen = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_light_green);

        final EditText mDiffuseX = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_X);
        final EditText mDiffuseY = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_Y);
        final EditText mDiffuseZ = (EditText) mPopupWindow.getContentView().findViewById(R.id.kb_diffuse_Z);

        float newDiffuseIntensity = Float.parseFloat(mDiffuseIntensity.getText().toString());

        float newDiffuseRed = Float.parseFloat(mDiffuseRed.getText().toString());
        float newDiffuseGreen = Float.parseFloat(mDiffuseBlue.getText().toString());
        float newDiffuseBlue = Float.parseFloat(mDiffuseGreen.getText().toString());

        float newDiffuseX = Float.parseFloat(mDiffuseX.getText().toString());
        float newDiffuseY = Float.parseFloat(mDiffuseY.getText().toString());
        float newDiffuseZ = Float.parseFloat(mDiffuseZ.getText().toString());

        mRenderer.setDiffuseData(newDiffuseIntensity,new float[]{newDiffuseRed,newDiffuseGreen,newDiffuseBlue},new float[]{newDiffuseX,newDiffuseY,newDiffuseZ});

    }

    private void setSpecularLight() {

    }

    private void setPointLight() {

    }

    private void setSpotLight() {
    }
}