precision mediump float;
uniform sampler2D uTextureSampler;
varying vec2 vTexture;
varying vec4 vColor;
void main(){
gl_FragColor = vColor; /* texture2D(uTextureSampler,vTexture)*/;
}
