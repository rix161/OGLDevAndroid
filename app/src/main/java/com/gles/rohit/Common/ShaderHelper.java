package com.gles.rohit.Common;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Rohith on 03-09-2016.
 */
public class ShaderHelper {
    public static int generateShader(int shaderType, String shaderCode) {
        int shaderId = -1;
        final String TAG = "GFX:UTIL:generateShader";

        shaderId = GLES20.glCreateShader(shaderType);
        if(shaderId!=0){
            GLES20.glShaderSource(shaderId,shaderCode);
            GLES20.glCompileShader(shaderId);
            final int[] compileStatus = new int [1];
            GLES20.glGetShaderiv(shaderId,GLES20.GL_COMPILE_STATUS,compileStatus,0);
            if(compileStatus[0] == 0){
                Log.e(TAG,"Failed Shader Compilation"+GLES20.glGetShaderInfoLog(shaderId));
                GLES20.glDeleteShader(shaderId);
                return -1;
            }
        }
        else{
            return -1;
        }
        return shaderId;
    }

    public static int generateProgram(final int vertexShaderID,final int fragmentShaderID){
        int programId = -1;
        final String TAG = "GFX:UTIL:GenerateProgram";
        programId = GLES20.glCreateProgram();
        if(programId !=0){
            GLES20.glAttachShader(programId,vertexShaderID);
            GLES20.glAttachShader(programId,fragmentShaderID);
            GLES20.glLinkProgram(programId);
            final int linkStatus[] = new int[1];
            GLES20.glGetProgramiv(programId,GLES20.GL_LINK_STATUS,linkStatus,0);
            if(linkStatus[0] == 0){
                Log.e(TAG,"Failed to LinkProgram"+GLES20.glGetProgramInfoLog(programId));
                GLES20.glDeleteProgram(programId);
                return -1;
            }
        }else{ Log.e(TAG,"2)Failed to LinkProgram:"); return  -1;}
        return  programId;
    }
}
