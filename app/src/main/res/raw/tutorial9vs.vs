uniform mat4 uMMatrix;
attribute vec3 aPosition;
varying vec4 vColor;

void main(){
    vColor = vec4(clamp(aPosition,0.0,1.0),1.0);
    gl_Position = uMMatrix*vec4(aPosition,1.0);
}