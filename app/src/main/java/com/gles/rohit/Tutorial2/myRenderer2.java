package com.gles.rohit.Tutorial2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.ogldevandroid.R;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 03-09-2016.
 */
public class myRenderer2 implements GLSurfaceView.Renderer {

    private Context mContext;
    private final float dotVertex[] = {0.0f,0.0f,0.0f};
    private FloatBuffer mVertexBuffer;
    private final String TAG = "GFX:";
    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int[] mVbo;
    public myRenderer2(Context applicationContext) {
        mContext = applicationContext;
        mVertexBuffer = ByteBuffer.allocateDirect(dotVertex.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(dotVertex).position(0);
        mVbo = new int[1];
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial2vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial2fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");

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

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mVbo[0]);
        GLES20.glEnableVertexAttribArray(mVertexDataHandle);
        GLES20.glVertexAttribPointer(mVertexDataHandle,3,GLES20.GL_FLOAT,false,0,0);
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,1);
    }

   public void onDestroy(){
       GLES20.glDeleteShader(mVShaderId);
       GLES20.glDeleteShader(mFShaderId);
       GLES20.glDeleteProgram(mProgramId);
       GLES20.glDeleteBuffers(1,mVbo,0);
   }


}
