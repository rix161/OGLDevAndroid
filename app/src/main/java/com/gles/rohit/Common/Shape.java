package com.gles.rohit.Common;

/**
 * Created by Rohith on 13-09-2016.
 */
public interface Shape {
    public void loadBuffers();
    public void draw(int vertexDataHandle,int colorHandle,int textureUniformHandle,int textureCoordHandle);
    public void draw(int vertexDataHandle,int normalHandle,int colorHandle,int textureUniformHandle,int textureCoordHandle);
    public void destroy();
    public boolean loadTexture(int resId);
    public void setTexture(Texture texture);
}
