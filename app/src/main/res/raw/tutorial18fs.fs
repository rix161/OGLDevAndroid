precision mediump float;
struct AmbientData {
    float intensity;
    vec3 color;
};

struct DirectionalData {
    float intensity;
    vec3 color;
    vec3 position;
};

uniform sampler2D uTextureSampler;
uniform AmbientData uAmbientData;
uniform DirectionalData uDirectionData;
varying vec2 vTexture;
varying vec3 vNormal;

void main(){
    vec3 mAmbiFactor = uAmbientData.color * uAmbientData.intensity;

    float diffuseFactor = max(dot(normalize(vNormal), -uDirectionData.position),0.0);
    vec3 mDirFactor = uDirectionData.color*uDirectionData.intensity*diffuseFactor;;

    gl_FragColor = texture2D(uTextureSampler,vTexture)*(vec4(mAmbiFactor+mDirFactor,1.0));
}