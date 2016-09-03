package com.gles.rohit.Tutorial5;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.ogldevandroid.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 03-09-2016.
 */
public class myRenderer5 implements GLSurfaceView.Renderer {
    private Context mContext;
    private final float triangleData[] = {
            -1.0f,-1.0f,0.0f,
            1.0f,0.0f,0.0f,1.0f,
            1.0f,-1.0f,0.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,
            0.0f,0.0f,1.0f,1.0f,
    };

    private final String TAG = "GFX:";

    private FloatBuffer mVertexBuffer;

    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int mColorDataHandle;
    private int mScaleHandle;

    private int[] mVbo;
    private int numberOfVBOS = 1;

    private int mVextexDataSize = 3;
    private int mVextexColorSize = 4;
    private int mStride = 7 *4;

    static float mScale = 0;
    public myRenderer5(Context applicationContext) {
        mContext = applicationContext;
        mVertexBuffer = ByteBuffer.allocateDirect(triangleData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(triangleData).position(0);
        mVbo = new int[numberOfVBOS];
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial5vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial2fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mColorDataHandle = GLES20.glGetAttribLocation(mProgramId,"aColor");
        mScaleHandle = GLES20.glGetUniformLocation(mProgramId,"uScale");

        GLES20.glGenBuffers(1,mVbo,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mVbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mVertexBuffer.capacity()*4,mVertexBuffer,GLES20.GL_STATIC_DRAW) ;
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgramId);

        mScale =mScale+0.01f;
        GLES20.glUniform1f(mScaleHandle,Math.abs((float)Math.sin(mScale)));

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mVbo[0]);
        GLES20.glEnableVertexAttribArray(mVertexDataHandle);
        GLES20.glVertexAttribPointer(mVertexDataHandle,mVextexDataSize,GLES20.GL_FLOAT,false,mStride,0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mVbo[0]);
        GLES20.glEnableVertexAttribArray(mColorDataHandle);
        GLES20.glVertexAttribPointer(mColorDataHandle,mVextexColorSize,GLES20.GL_FLOAT,false,mStride,mVextexDataSize*4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);

    }

    public void onDestroy(){
        GLES20.glDeleteShader(mVShaderId);
        GLES20.glDeleteShader(mFShaderId);
        GLES20.glDeleteProgram(mProgramId);
        GLES20.glDeleteBuffers(1,mVbo,0);
    }
}
