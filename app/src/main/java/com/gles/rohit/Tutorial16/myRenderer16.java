package com.gles.rohit.Tutorial16;

import android.content.Context;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.Common.Shape;
import com.gles.rohit.Common.ShapePyramid;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.ogldevandroid.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 03-09-2016.
 */
public class myRenderer16 implements GLSurfaceView.Renderer {
    private Context mContext;
    private final String TAG = "GFX:";

    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int mColorDataHandle;
    private int mMMatrixHandle;

    private int mTextureSamplerHandle;
    private int mTextureCoordHandle;

    private TransPipeline mPipeline;
    private Shape mShape;
    public myRenderer16(Context applicationContext) {
        mContext = applicationContext;
        mShape = new ShapePyramid(mContext);
        mPipeline = new TransPipeline();
    }

    public void updateCamera(int buttonId){
        mPipeline.mCamera.updateEyeCamera(buttonId);
    }
    public void rotateCamera(float rotX,float rotY){
        mPipeline.mCamera.rotateCamera(rotX,rotY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial16vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial2fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramId,"aTexture");
        mTextureSamplerHandle = GLES20.glGetUniformLocation(mProgramId,"uTextureSampler");
        mMMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMMatrix");

        mShape.loadBuffers();
        mShape.loadTexture(R.drawable.test);

        mPipeline.setScale(new float[]{1.0f,1.0f,1.0f});
        mPipeline.setRotate(1.0f,new float[]{0.0f,1.0f,0.0f});
        mPipeline.setTranslate(new float[]{0.0f, 1.0f, -5.0f});
        mPipeline.mCamera.setCamera(new float[]{0.0f,0.0f,5.0f},new float[]{0.0f,0.0f,-10.0f},new float[]{0.0f,1.0f,0.0f});
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        mPipeline.setPerspective(width,height,30.0f,100.0f,1.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgramId);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        GLES20.glUniformMatrix4fv(mMMatrixHandle,1,false,mPipeline.getMatrix(false,false,true),0);

        mShape.draw(mVertexDataHandle,mColorDataHandle,-1,-1);
    }

    public void onDestroy(){
        GLES20.glDeleteShader(mVShaderId);
        GLES20.glDeleteShader(mFShaderId);
        GLES20.glDeleteProgram(mProgramId);
        mShape.destroy();
    }
}
