attribute vec3 aPosition;
attribute vec2 aTexture;

uniform mat4 uMVPMatrix;
varying vec2 vTexture;

void main(){
    gl_Position = uMVPMatrix * vec4(aPosition,1.0);
    vTexture = aTexture;
}