package com.gles.rohit.Common.Shadow;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.gles.rohit.Common.FileReader;
import com.gles.rohit.Common.ShaderHelper;
import com.gles.rohit.ogldevandroid.R;

/**
 * Created by rohit on 11/6/17.
 */

public class ShadowMappingTechnique {

    Context mContext;
    int mProgramId;
    int mVertexId;
    int mFragmentId;

    int mMVPMatrixHandle;
    int mVertexHandle;
    int mTextureCoordHandle;
    int mTextureSamplerHandle;

    public ShadowMappingTechnique(Context context){
        mContext = context;
    }

    public void init(){
        mVertexId = ShaderHelper.generateShader(GLES20.GL_VERTEX_SHADER, FileReader.readTextFileFromRawResource(mContext, R.raw.tutorial23_shadow));
        mFragmentId = ShaderHelper.generateShader(GLES20.GL_FRAGMENT_SHADER,FileReader.readTextFileFromRawResource(mContext,R.raw.tutorial23_shadowfs));
        if(mVertexId==-1 || mFragmentId==-1)
            Log.e("GFX","ShadowMappingTech:Something wrong with shader");

        mProgramId = ShaderHelper.generateProgram(mVertexId,mFragmentId);
        if(mProgramId == -1)
            Log.e("GFX","ShadowMappingTech:Something wrong with Program");

        GLES20.glUseProgram(mProgramId);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramId,"uMVPMatrix");
        mVertexHandle = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramId,"aTexture");
        mTextureSamplerHandle = GLES20.glGetUniformLocation(mProgramId,"uShadowMap");
    }

    public int getMVPHandle(){return mMVPMatrixHandle;}

    public int getPositionHandle(){ return mVertexHandle;}
    public int getTextureCoordHandle() { return  mTextureCoordHandle;}
    public int getTextureSamplerHandle() { return mTextureSamplerHandle;}

    public void destroy(){
        GLES20.glDeleteShader(mVertexId);
        GLES20.glDeleteShader(mFragmentId);
        GLES20.glDeleteProgram(mProgramId);
    }
}
