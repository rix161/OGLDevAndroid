package com.gles.rohit.Tutorial16B;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Rohith on 13-09-2016.
 */
public class myGLSurfaceView16B extends GLSurfaceView {

    private float mPrevX;
    private float mPrevY;
    myRenderer16B myRenderer;
    private float mScale = 0.01f;

    public myGLSurfaceView16B(Context context) {
        super(context);
    }
    public myGLSurfaceView16B(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event != null){
            float x = event.getX();
            float y = event.getY();
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                float deltaX = (x - mPrevX)*mScale;
                float deltaY = (y - mPrevY)*mScale;
                myRenderer.rotateCamera(deltaX,deltaY);
            }
            mPrevX = x;
            mPrevY = y;
            return true;
        }
        else {
            return super.onTouchEvent(event);
        }
    }

    public void setRenderer(myRenderer16B renderer){
        myRenderer = renderer;
        super.setRenderer(renderer);
    }
}

