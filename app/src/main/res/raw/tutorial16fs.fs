precision mediump float;
uniform sampler2D uTextureSampler;
varying vec4 vColor;
varying vec2 vTexture;
void main(){
gl_FragColor = vColor * texture2D(uTextureSampler,vTexture);
}
