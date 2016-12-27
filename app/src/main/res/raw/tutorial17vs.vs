uniform mat4 uMMatrix;
 attribute vec3 aPosition;
 attribute vec2 aTexture;
 varying vec2 vTexture;
 void main(){
     vTexture = aTexture;
     gl_Position = uMMatrix*vec4(aPosition,1.0);
 }