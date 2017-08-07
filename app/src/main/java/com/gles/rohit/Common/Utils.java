package com.gles.rohit.Common;

import android.opengl.GLES30;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rohith on 12-09-2016.
 */
public class Utils {
    public enum lightType{
        AMBIENT,
        DIFFUSE,
        SPECULAR,
        POINT,
        SPOT
    }

    public static void printMatrix(float[]matrix,String title){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                sb.append(" ").append(matrix[j*4+i]);
            }
            Log.e("GFX",title+":"+sb.toString());
            sb.delete(0,sb.length());
        }
    }

    public static byte[] inputStreamToByteBuffer(InputStream inputStream){
        byte[] buffer = new byte[2048];;
        ByteArrayOutputStream os  = new ByteArrayOutputStream();
        int size;
        try {
            while((size = inputStream.read(buffer, 0, 2048))>=0)
                os.write(buffer, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    public static String getError(int status) {
        String retString;

        switch (status) {
            case GLES30.GL_FRAMEBUFFER_COMPLETE:
                retString = "";
                break;

            case GLES30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
                retString = "An attachment could not be bound to frame buffer object!";
                break;

            case GLES30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
                retString = "Attachments are missing! At least one image (texture) must be bound to the frame buffer object!";
                break;

        /*case GLES30.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
            retString = "The dimensions of the buffers attached to the currently used frame buffer object do not match!";
            break;

        case GLES30.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
            retString = "The formats of the currently used frame buffer object are not supported or do not fit together!";
            break;

        case GLES30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
            retString = "A Draw buffer is incomplete or undefinied. All draw buffers must specify attachment points that have images attached.";
            break;

        case GLES30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
            retString = "A Read buffer is incomplete or undefinied. All read buffers must specify attachment points that have images attached.";
            break;


             case GLES30.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS :
            retString = "If a layered image is attached to one attachment, then all attachments must be layered attachments. The attached layers do not have to have the same number of layers, nor do the layers have to come from the same kind of texture.";
            break;*/

            case GLES30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
                retString = "All images must have the same number of multisample samples.";
                break;


            case GLES30.GL_FRAMEBUFFER_UNSUPPORTED:
                retString = "Attempt to use an unsupported format combinaton!";
                break;

            default:
                retString = "Unknown error while attempting to create frame buffer object!";
                break;
        }
        return retString;
    }
}
