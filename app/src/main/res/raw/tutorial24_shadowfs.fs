precision mediump float;
uniform sampler2D uShadowMap;
varying vec2 vTexture;

void main(){
    float depth = texture2D(uShadowMap,vTexture).x;
    depth = 1.0 - (1.0 - depth)*25.0;

    depth = 1.0
    gl_FragColor = vec4(1,0,0,0);
}