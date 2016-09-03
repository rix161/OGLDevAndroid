attribute vec3 aPosition;
varying vec4 vColor;
void main(){
gl_PointSize = 10.0f;
vColor = vec4(1.0,1.0,1.0,1.0);
gl_Position = vec4(aPosition,1.0);
}