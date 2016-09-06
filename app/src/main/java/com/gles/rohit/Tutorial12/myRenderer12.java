package com.gles.rohit.Tutorial12;

import android.content.Context;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.Common.TransPipeline;
import com.gles.rohit.ogldevandroid.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rohith on 03-09-2016.
 */
public class myRenderer12 implements GLSurfaceView.Renderer {
    private Context mContext;
    private final float pyramidData[] = {
            -1.0f, -1.0f, 0.5773f,
            0.0f, -1.0f, -1.15475f,
            1.0f, -1.0f, 0.5773f,
            0.0f, 1.0f, 0.0f,
    };

    private final int pyramidIndex[]={
            0, 3, 1,
            1, 3, 2,
            2, 3, 0,
            0, 1, 2
    };

    private final String TAG = "GFX:";

    private FloatBuffer mVertexBuffer;
    private IntBuffer mIndexBuffer;

    private int mVShaderId;
    private int mFShaderId;
    private int mProgramId;

    private int mVertexDataHandle;
    private int mMMatrixHandle;

    private int[] mBuffers;
    private int numberOfVBOS = 2;

    private int mVextexDataSize = 3;
    TransPipeline mPipeline;

    public myRenderer12(Context applicationContext) {
        mContext = applicationContext;

        mVertexBuffer = ByteBuffer.allocateDirect(pyramidData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(pyramidData).position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(pyramidIndex.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        mIndexBuffer.put(pyramidIndex).position(0);

        mBuffers = new int[numberOfVBOS];
        mPipeline = new TransPipeline();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mVShaderId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial9vs));
        mFShaderId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial2fs));
        if(mVShaderId == -1 || mFShaderId == -1){
            Log.e(TAG,"Something is wrong with shaders");
        }
        mProgramId = ShaderHelper.generateProgram(mVShaderId,mFShaderId);
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES20.glUseProgram(mProgramId);

        mVertexDataHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mMMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMMatrix");

        GLES20.glGenBuffers(numberOfVBOS,mBuffers,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mVertexBuffer.capacity()*4,mVertexBuffer,GLES20.GL_STATIC_DRAW) ;
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffers[1]);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,mIndexBuffer.capacity()*4,mIndexBuffer,GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);

        mPipeline.setRotate(1.0f,new float[]{0.0f,1.0f,0.0f});
        mPipeline.setTranslate(new float[]{0.0f, 0.0f, -20.0f});

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
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,mBuffers[0]);

        GLES20.glEnableVertexAttribArray(mVertexDataHandle);
        GLES20.glVertexAttribPointer(mVertexDataHandle,mVextexDataSize,GLES20.GL_FLOAT,false,0,0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,mBuffers[1]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,12,GLES20.GL_UNSIGNED_INT,0);
        GLES20.glDisableVertexAttribArray(mVertexDataHandle);
    }

    public void onDestroy(){
        GLES20.glDeleteShader(mVShaderId);
        GLES20.glDeleteShader(mFShaderId);
        GLES20.glDeleteProgram(mProgramId);
        GLES20.glDeleteBuffers(numberOfVBOS,mBuffers,0);
    }
}
