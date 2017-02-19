precision mediump float;

struct ambientLight{
    float ambientIntensity;
    vec3 ambientColor;
};


struct baseLight {
  vec3 color;
  float ambientIntensity;
  float diffuseIntensity;
};

struct directionalLight{
   baseLight base;
   vec3 direction;
};

struct Attenuation{
    float constant;
    float linear;
    float exponetial;
};

struct pointLight{
    baseLight base;
    vec3 position;
    Attenuation attenuation;
};

const int MAX_POINT_LIGHTS = 2;

uniform sampler2D uTextureSampler;

uniform ambientLight uAmbientLight;
uniform directionalLight uDirectionalLight;

uniform float uSpecularIntensity;
uniform float uSpecularPower;
uniform vec3 uEyePosition;

uniform int uPointLightNum;
uniform pointLight uPointLights[MAX_POINT_LIGHTS];

varying vec2 vTexture;
varying vec3 vNormal;
varying vec3 vWorldPos;

vec4 CalLightingInternal(baseLight base,vec3 direction){

    vec4 ambientColor = vec4(base.color,1.0) * base.ambientIntensity;
    vec4 diffuseColor = vec4(0.0,0.0,0.0,0.0);
    vec4 specularColor = vec4(0.0,0.0,0.0,0.0);

    float diffuseFactor = dot(vNormal,-direction);
    if(diffuseFactor > 0.0){
        diffuseColor = vec4(base.color,1.0) * base.diffuseIntensity * diffuseFactor;
        vec3 vecToEye = normalize(uEyePosition - vWorldPos);
        vec3 reflectVec = normalize(reflect(vNormal,direction));
        float specularFactor = dot(vecToEye,reflectVec);
        specularFactor = pow(specularFactor,uSpecularPower);
        if(specularFactor > 0.0){
            specularColor = vec4(base.color,1.0)*specularFactor*uSpecularIntensity;
        }
    }

    return (ambientColor+diffuseColor+specularColor);
}

vec4 CalcDirectionalLight(){
    return CalLightingInternal(uDirectionalLight.base,uDirectionalLight.direction);
}

vec4 CalcPointLight(int i){
    vec3 lightDirection = vWorldPos - uPointLights[i].position;
    float distance = length(lightDirection);
    lightDirection = normalize(lightDirection);
    vec4 light = CalLightingInternal(uPointLights[i].base,lightDirection);
    light = light/(uPointLights[i].attenuation.constant+uPointLights[i].attenuation.linear*distance+
    uPointLights[i].attenuation.exponetial*distance*distance);
    return light;
}



void main(){

    vec4 TotalColor = vec4(uAmbientLight.ambientColor*uAmbientLight.ambientIntensity,1.0);
    TotalColor += CalcDirectionalLight();
    for(int i=0 ; i<uPointLightNum ; i++){
        TotalColor += CalcPointLight(i);
    }
    gl_FragColor = texture2D(uTextureSampler,vTexture) * TotalColor;
}