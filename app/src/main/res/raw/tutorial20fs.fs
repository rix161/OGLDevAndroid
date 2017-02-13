precision mediump float;

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
uniform float uPointLightNum;
uniform pointLight uPointLights[MAX_POINT_LIGHTS];
uniform directionalLight uDirectionalLight;
uniform vec3 uEyePosition;
uniform float uSpecularPower;
uniform float uSpecularIntensity;

varying vec2 vTexture;
varying vec3 vNormal;
varying vec3 vWorldPos;

vec4 CalLightingInternal(baseLight base,vec3 direction){

    vec4 ambientColor = vec4(base.color,1.0f) * base.ambientIntensity;
    vec4 diffuseColor = vec4(0.0f,0.0f,0.0f,0.0f);
    vec4 specularColor = vec4(0.0f,0.0f,0.0f,0.0f);

    float diffuseFactor = dot(vNormal,-direction);
    if(diffuseFactor > 0){
        diffuseColor = vec4(base.color,1.0f) * base.diffuseIntensity * diffuseFactor;
        vec3 vecToEye = normalize(uEyePosition - vWorldPos);
        vec3 reflectVec = normalize(reflect(vNormal,direction));
        float specularFactor = dot(vecToEye,reflectVec);
        specularFactor = pow(specularFactor,uSpecularPower);
        if(specularFactor > 0){
            specularColor = vec4(base.color,1.0)*specularFactor*uSpecularIntensity;
        }
    }

    return (ambientColor+diffuseColor+specularColor);
}

vec4 CalDirectionalLight(){
    return CalLightingInternal(uDirectionalLight.base,uDirectionalLight.direction);
}

vec4 CalcPointLight(int i){
    vec4 lightDirection = vWorldPosition - uPointLights[i].position;
    float distance = length(lightDirection);
    lightDirection = normalize(lightDirection);
    vec4 light = CalLightingInternal(uPointLights[i].base,lightDirection);
    light = light/(uPointLights[i].attenuation.constant+uPointLights[i].attenuation.linear*distance+
    uPointLights[i].attenuation.exponetial*distance*distance);
    return light;
}



void main(){

    vec4 TotalColor = CalcDirectionalLight();
    for(int i=0 ; i<uPointLightNum ; i++){
        TotalColor += CalcPointLight(i);
    }
    gl_FragColor = texture2D(uTextureSampler,vTexture) * vec4(totalColor);
}