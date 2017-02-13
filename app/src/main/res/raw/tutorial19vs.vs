uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexture;
varying vec2 vTexture;
varying vec3 vNormal;
varying vec3 vWorldPos;

void main(){

    vTexture = aTexture;
    gl_Position = uMVPMatrix*vec4(aPosition,1.0);
    vNormal =  normalize((uMMatrix*vec4(aNormal,0.0)).xyz);
    vWorldPos = (uMMatrix*vec4(aPosition,1.0)).xyz;
}