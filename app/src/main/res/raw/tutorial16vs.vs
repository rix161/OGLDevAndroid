uniform mat4 uMMatrix;
attribute vec3 aPosition;
attribute vec4 aColor;
attribute vec2 aTexture;
varying vec4 vColor;
varying vec2 vTexture;
void main(){
    vColor = aColor;
    vTexture = aTexture;
    gl_Position = uMMatrix*vec4(aPosition,1.0);
}