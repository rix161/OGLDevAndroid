uniform mat4 uMMatrix;
attribute vec3 aPosition;
attribute vec4 aColor;
attribute vec2 aTexture;
varying vec2 vTexture;
varying vec4 vColor;
void main(){
    vTexture = aTexture;
    vColor = aColor;
    gl_Position = uMMatrix*vec4(aPosition,1.0);
}