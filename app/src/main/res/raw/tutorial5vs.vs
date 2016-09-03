uniform float uScale;
attribute vec3 aPosition;
attribute vec4 aColor;
varying vec4 vColor;
void main(){
vColor = aColor;
gl_Position = vec4(aPosition*uScale,1.0);
}