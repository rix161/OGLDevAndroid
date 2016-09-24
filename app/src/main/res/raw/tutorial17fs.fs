precision mediump float;
struct AmbientData {
    float intensity;
    vec3 color;
};

uniform sampler2D uTextureSampler;
uniform AmbientData uAmbientData;
varying vec2 vTexture;
void main(){
vec3 mAmbiFactor = uAmbientData.color * uAmbientData.intensity;
gl_FragColor = texture2D(uTextureSampler,vTexture)*(vec4(mAmbiFactor,1.0));
}
