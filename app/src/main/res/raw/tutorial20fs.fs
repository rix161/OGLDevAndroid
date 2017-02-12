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

struct SpecularData{
    float intensity;
    float power;
    vec3 eyePosition;
};

uniform sampler2D uTextureSampler;
uniform AmbientData uAmbientData;
uniform DirectionalData uDirectionData;
uniform SpecularData uSpecularData;

varying vec2 vTexture;
varying vec3 vNormal;
varying vec3 vWorldPos;

void main(){
    vec3 mAmbiFactor = uAmbientData.color * uAmbientData.intensity;

    float diffuseFactor = max(dot(normalize(vNormal), -uDirectionData.position),0.0);
    vec3 mDirFactor = uDirectionData.color*uDirectionData.intensity*diffuseFactor;
    vec3 eyeVector = normalize(uSpecularData.eyePosition - vWorldPos);
    vec3 reflectVector = normalize(reflect(uDirectionData.position,normalize(vNormal)));
    float specularFactor = max(dot(eyeVector,reflectVector),0.0);
    specularFactor = pow(specularFactor,uSpecularData.power);
    vec3 specularColor = uDirectionData.color * uSpecularData.intensity*specularFactor;

    gl_FragColor = texture2D(uTextureSampler,vTexture)*(vec4(mAmbiFactor+mDirFactor+specularColor,1.0));
}