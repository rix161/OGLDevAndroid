precision mediump float;
uniform sampler2D uShadowMap;
varying vec2 vTexture;

float unpack(vec4 colour){
const vec4 bitShifts = vec4(1.0 / (256.0 * 256.0 * 256.0),
                                1.0 / (256.0 * 256.0),
                                1.0 / 256.0,
                                1);
    return dot(colour , bitShifts);
}

void main(){
    float shadow = unpack(texture2D(uShadowMap,vTexture));
    shadow = (shadow * 0.8) + 0.2;
    gl_FragColor = vec4(shadow);
}