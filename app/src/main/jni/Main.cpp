
#include <pthread.h>
#include <jni.h>
#include <string>
#include "ESP.h"
#include "Hacks.h"


 
ESP espOverlay;
int type = 1, utype = 2;
/* ================ ESP FUNCTION =========================*/
extern "C" JNIEXPORT jstring JNICALL
Java_pubgm_loader_activity_LoginActivity_getSecurityCode(JNIEnv* env, jobject thiz) {
    return env->NewStringUTF(oxorany("UjNQTU9EVklQM01L"));
}


extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_Overlay_DrawOn(JNIEnv *env, jclass , jobject espView, jobject canvas) {
    espOverlay = ESP(env, espView, canvas);
    if (espOverlay.isValid()){
        DrawESP(espOverlay, espOverlay.getWidth(), espOverlay.getHeight());
    }
}


extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_Overlay_Close(JNIEnv *, jobject) {
    Close();
    options.openState = -1;
    options.aimBullet = -1;
    options.aimT = -1;
}


extern "C" JNIEXPORT jboolean JNICALL
Java_pubgm_loader_floating_Overlay_getReady(JNIEnv *, jobject thiz) {
    int sockCheck = 1;

    if (!Create()) {
        perror("Creation failed");
        return false;
    }
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &sockCheck, sizeof(int));
    if (!Bind()) {
        perror("Bind failed");
        return false;
    }

    if (!Listen()) {
        perror("Listen failed");
        return false;
    }
    if (Accept()) {
        return true;
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_ToggleAim_ToggleAim(JNIEnv *, jobject thiz, jboolean value) {
    if (value)
        options.openState = 0;
    else
        options.openState = -1;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_ToggleBullet_ToggleBullet(JNIEnv *, jobject thiz, jboolean value) {
    if (value)
        options.aimBullet = 0;
    else
        options.aimBullet = -1;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_ToggleSimulation_ToggleSimulation(JNIEnv *, jobject thiz, jboolean value) {
    if (value)
        options.aimT = 0;
    else
        options.aimT = -1;
}

extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_SettingValue(JNIEnv *, jobject, jint code, jboolean jboolean1) {

    switch ((int) code) {
        case 2:
            isPlayerLine = jboolean1;
            break;
        case 3:
            isPlayerBox = jboolean1;
            break;
        case 4:
            isSkeleton = jboolean1;
            break;
        case 5:
            isPlayerDistance = jboolean1;
            break;
        case 6:
            isPlayerHealth = jboolean1;
            break;
        case 7:
            isPlayerName = jboolean1;
            break;
        case 8:
            isPlayerHead = jboolean1;
            break;
        case 9:
            is360Alertv2 = jboolean1;
            break;
        case 10:
            isPlayerUID = jboolean1;
            break;
        case 11:
            isGrenadeWarning = jboolean1;
            break;
        case 12:
          //  isVehicles = jboolean1;
            break;
        case 13:
          //  isItems = jboolean1;
            break;
        case 14:
           // isLootBox = jboolean1;
            break;
        case 15:
            options.ignoreAi = jboolean1;
            break;
        case 16:
            isPlayerWeaponIcon = jboolean1;
            break;
        
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_SettingAim(JNIEnv *env, jobject thiz, jint setting_code, jboolean value) {
    switch ((int) setting_code) {
        case 1:
            options.openState = -1;
            break;
        case 2:
            options.aimBullet = -1;
            break;
        case 3:
            options.pour = value;
            break;
        case 4:
            options.ignoreBot = value;
            break;
        case 5:
            options.InputInversion = value;
            break;
        case 6:
            options.tracingStatus = value;
            break;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_SettingMemory(JNIEnv *env, jobject thiz, jint setting_code, jboolean value) {
    switch ((int) setting_code) {
        case 1:
            otherFeature.LessRecoil = value;
            break;
        case 2:
            otherFeature.SmallCrosshair = value;
            break;
        case 3:
            otherFeature.Aimbot = value;
            break;
        case 4:
            otherFeature.WideView = value;
            break;
          
    }
}

extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_Range(JNIEnv *, jobject, jint range) {
    options.aimingRange = 1 + range;
}

extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_distances(JNIEnv *, jobject, jint distances) {
    options.aimingDist = distances;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_recoil(JNIEnv *env, jobject thiz, jint recoil) {
    options.recCompe = recoil;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_recoil2(JNIEnv *env, jobject thiz, jint recoil) {
    options.recCompe1 = recoil;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_recoil3(JNIEnv *env, jobject thiz, jint recoil) {
    options.recCompe2 = recoil;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_Bulletspeed(JNIEnv *env, jobject thiz, jint bulletspeed) {
    options.aimingSpeed = bulletspeed;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_AimingSpeed(JNIEnv *env, jobject thiz, jint aimingspeed) {
    options.touchSpeed = aimingspeed;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_Smoothness(JNIEnv *env, jobject thiz, jint smoothness) {
    options.Smoothing = smoothness;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_TouchSize(JNIEnv *env, jobject thiz, jint touchsize) {
    options.touchSize = touchsize;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_TouchPosX(JNIEnv *env, jobject thiz, jint touchposx) {
    options.touchX = touchposx;
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_TouchPosY(JNIEnv *env, jobject thiz, jint touchposy) {
    options.touchY = touchposy;
}


extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_WideView(JNIEnv *env, jobject thiz, jint wideview) {
    otherFeature.WideView = wideview;
}

extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_Target(JNIEnv *, jobject, jint target) {
    options.aimbotmode = target;
}
extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_AimWhen(JNIEnv *, jobject, jint state) {
    options.aimingState = state;
}
extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_AimBy(JNIEnv *, jobject, jint aimby) {
    options.priority = aimby;
}
extern "C" JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_RadarSize(JNIEnv *, jobject, jint size) {
    request.radarSize = size;
}

// ====================== Main Activity ====================== //

extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_getOwner(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(oxorany("https://t.me/Mon5la"));
}
extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_getuser(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(oxorany("https://t.me/Mon5la"));
}
extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_getchrome(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(oxorany("https://Mon5la"));
}




//▬▬ι══════════════ι▬▬
extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_LINKVIP(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(
            oxorany("https://github.com/mohamedn5la1998-svg/n5lapro/raw/refs/heads/main/777.json"));
}


//▬▬ι══════════════ι▬▬
extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_mainURL(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(
            oxorany(""));
}


extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_Component_DownloadZip_pw(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(
            oxorany(""));
}



extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_ApiKeyBox(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(
            oxorany("KOSOMK_FUCKBITCH"));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_livai_R3pmodking_activity(JNIEnv *env, jclass clazz) {
    
        return env->NewStringUTF(
                oxorany("pubgm.loader.activity.LoginActivity"));
    }

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FloatService_SkinHack(JNIEnv *env, jobject thiz, jint setting_code) {
    switch ((int) setting_code) {
        case 1:
            otherFeature.clothes = 1;
            break;
        case 2:
            otherFeature.clothes = 2;
            break;
        case 3:
            otherFeature.clothes = 3;
            break;
        case 4:
            otherFeature.clothes = 4;
            break;
        case 5:
            otherFeature.clothes = 5;
            break;
        case 6:
            otherFeature.clothes = 6;
            break;
        case 7:
            otherFeature.clothes = 7;
            break;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_pubgm_loader_floating_FightMod_SettingValue(JNIEnv *,  jobject ,jint code,jboolean jboolean1) {
    switch((int)code){
        case 1:
            isHideItem = jboolean1;
            break;
    }
}
