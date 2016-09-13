package com.gles.rohit.Tutorial14;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Rohith on 11-09-2016.
 */
public class myGLSurfaceView extends GLSurfaceView {
    private Context mContext;
    private GLSurfaceView mRenderer;
    public myGLSurfaceView(Context context) {
        super(context);
        mContext = context;
    }

    public myGLSurfaceView(Context context,AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
    }

    public void setRenderer(GLSurfaceView.Renderer renderer){
        super.setRenderer(renderer);
    }
}
