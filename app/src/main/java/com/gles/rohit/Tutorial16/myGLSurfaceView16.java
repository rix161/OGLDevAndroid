package com.gles.rohit.Tutorial16;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.gles.rohit.Tutorial14.myRenderer14;

/**
 * Created by Rohith on 13-09-2016.
 */
public class myGLSurfaceView16 extends GLSurfaceView {

    private float mPrevX;
    private float mPrevY;
    myRenderer16 myRenderer;
    private float mScale = 0.01f;

    public myGLSurfaceView16(Context context) {
        super(context);
    }
    public myGLSurfaceView16(Context context, AttributeSet attributeSet) {
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

    public void setRenderer(myRenderer16 renderer){
        myRenderer = renderer;
        super.setRenderer(renderer);
    }
}
