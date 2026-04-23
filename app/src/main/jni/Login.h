#include <sys/types.h>
#include <pthread.h>
#include <jni.h>
#include <string>
#include <ctime>
#include <curl/curl.h>
#include "obfuscate.h"
#include "Oxorany/oxorany.h"
#include "StrEnc.h"
#include "Tools.h"
#include "HackShooter.h"
#include "json.hpp"
#include "Includes.h"
#include "Crackno.h"

using json = nlohmann::json;

int expiredDate;
static bool DaddyXerr0r = false;

std::string credit;
std::string modname;
std::string token;
std::string g_Licence;
bool xConnected = false, xServerConnection = false, memek = false;

struct MemoryStruct {
    char *memory;
    size_t size;
};

static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp) {
    size_t realsize = size * nmemb;
    struct MemoryStruct *mem = (struct MemoryStruct *) userp;
    char *ptr = (char *) realloc(mem->memory, mem->size + realsize + 1);
    if (ptr == NULL) return 0;
    mem->memory = ptr;
    memcpy(&(mem->memory[mem->size]), contents, realsize);
    mem->size += realsize;
    mem->memory[mem->size] = 0;
    return realsize;
}

bool IsVpnActive(JNIEnv *env, jobject context) {
    jclass contextClass = env->FindClass("android/content/Context");
    if (!contextClass) return false;

    jmethodID getSystemServiceMethod = env->GetMethodID(contextClass, "getSystemService", "(Ljava/lang/String;)Ljava/lang/Object;");
    if (!getSystemServiceMethod) {
        env->DeleteLocalRef(contextClass);
        return false;
    }

    jstring serviceName = env->NewStringUTF("connectivity");
    jobject connectivityManager = env->CallObjectMethod(context, getSystemServiceMethod, serviceName);
    env->DeleteLocalRef(serviceName);
    env->DeleteLocalRef(contextClass);
    
    if (!connectivityManager) return false;

    jclass connManagerClass = env->FindClass("android/net/ConnectivityManager");
    if (!connManagerClass) {
        env->DeleteLocalRef(connectivityManager);
        return false;
    }

    jmethodID getNetworkInfoMethod = env->GetMethodID(connManagerClass, "getNetworkInfo", "(I)Landroid/net/NetworkInfo;");
    if (!getNetworkInfoMethod) {
        env->DeleteLocalRef(connManagerClass);
        env->DeleteLocalRef(connectivityManager);
        return false;
    }

    const int TYPE_VPN = 17;
    jobject networkInfo = env->CallObjectMethod(connectivityManager, getNetworkInfoMethod, TYPE_VPN);
    env->DeleteLocalRef(connectivityManager);
    env->DeleteLocalRef(connManagerClass);

    if (!networkInfo) return false;

    jclass networkInfoClass = env->FindClass("android/net/NetworkInfo");
    if (!networkInfoClass) {
        env->DeleteLocalRef(networkInfo);
        return false;
    }

    jmethodID isConnectedMethod = env->GetMethodID(networkInfoClass, "isConnected", "()Z");
    if (!isConnectedMethod) {
        env->DeleteLocalRef(networkInfoClass);
        env->DeleteLocalRef(networkInfo);
        return false;
    }

    jboolean isConnected = env->CallBooleanMethod(networkInfo, isConnectedMethod);
    env->DeleteLocalRef(networkInfoClass);
    env->DeleteLocalRef(networkInfo);
    
    return (bool)isConnected;
}

bool isDebuggerConnected(JNIEnv *env) {
    jclass debugClass = env->FindClass("android/os/Debug");
    if (!debugClass) return false;
    jmethodID mid = env->GetStaticMethodID(debugClass, "isDebuggerConnected", "()Z");
    if (!mid) return false;
    jboolean result = env->CallStaticBooleanMethod(debugClass, mid);
    return result == JNI_TRUE;
}

void ShowCanaryMessage(JNIEnv *env, jobject context) {
    jclass toast = env->FindClass("android/widget/Toast");
    jmethodID makeText = env->GetStaticMethodID(toast, "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");
    jmethodID show = env->GetMethodID(toast, "show", "()V");

    jclass contextClass = env->FindClass("android/content/Context");
    jmethodID getStringMethod = env->GetMethodID(contextClass, "getString", "(I)Ljava/lang/String;");
    jclass rStringClass = env->FindClass("pubgm/loader/R$string");
    jfieldID stringField = env->GetStaticFieldID(rStringClass, "invalid_key_format", "I");
    jint stringId = env->GetStaticIntField(rStringClass, stringField);
    jstring message = (jstring)env->CallObjectMethod(context, getStringMethod, stringId);

    jobject toastObj = env->CallStaticObjectMethod(toast, makeText, context, message, 1);
    env->CallVoidMethod(toastObj, show);

    jclass activity = env->FindClass("android/app/Activity");
    jmethodID finish = env->GetMethodID(activity, "finish", "()V");
    env->CallVoidMethod(context, finish);
}

extern "C" JNIEXPORT jstring JNICALL native_Check(JNIEnv *env, jclass clazz, jobject mContext, jstring mUserKey) {

    const char *userKey = env->GetStringUTFChars(mUserKey, 0);

    if (isDebuggerConnected(env)) {
        ShowCanaryMessage(env, mContext);
        env->ReleaseStringUTFChars(mUserKey, userKey);
        return env->NewStringUTF("Debugger detected");
    }

    if (IsVpnActive(env, mContext)) {
        env->ReleaseStringUTFChars(mUserKey, userKey);
        return env->NewStringUTF("اقفل الكناري يخول ");
    }

    std::string hwid = userKey;
    hwid += GetAndroidID(env, mContext);
    hwid += GetDeviceModel(env);
    hwid += GetDeviceBrand(env);

    std::string UUID = GetUUID(env, hwid.c_str());

    std::string errMsg;

    struct MemoryStruct chunk{};
    chunk.memory = (char *) malloc(1);
    chunk.size = 0;

    CURL *curl;
    CURLcode res;
    curl = curl_easy_init();
    if (curl) {
        std::string url = oxorany("https://2.x10.mx/public/connect");
        curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "POST");
        curl_easy_setopt(curl, CURLOPT_URL,url.c_str());
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
        curl_easy_setopt(curl, CURLOPT_DEFAULT_PROTOCOL, "https");
        struct curl_slist *headers = NULL;
        headers = curl_slist_append(headers, "Content-Type: application/x-www-form-urlencoded");
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);

        char data[4096];
        sprintf(data, "game=PUBG&user_key=%s&serial=%s", userKey, UUID.c_str());
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, data);

        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *) &chunk);

        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);

        res = curl_easy_perform(curl);
        if (res == CURLE_OK) {
            try {
                json result = json::parse(chunk.memory);
                if (result["status"] == true) {
                    std::string token = result["data"]["token"].get<std::string>();
                    expiredDate = result["data"]["rng"].get<time_t>();

                    if (expiredDate + 30 > time(0)) {
                        std::string auth = "PUBG";
                        auth += "-";
                        auth += userKey;
                        auth += "-";
                        auth += UUID;
                        auth += "-";
                        std::string license = oxorany("Vm8LD3eC0d3rSaADCPVPVTaNhASuF19E");
                        auth += license.c_str();
                        std::string outputAuth = Tools::CalcMD5(auth);

                        DaddyXerr0r = true;
                        xServerConnection = true;
                        memek = true;
                        if (DaddyXerr0r) {
                            pthread_t t;
                        }
                        g_Licence = result["data"]["EXP"].get<std::string>();
                    }
                } else {
                    errMsg = "INVALID_KEY_FORMAT";
                }
            } catch (json::exception &e) {
                errMsg = "SERVER_RESPONSE_ERROR";
            }
        } else {
            errMsg = "SERVER_CONNECTION_ERROR";
        }
    }
    curl_easy_cleanup(curl);

    env->ReleaseStringUTFChars(mUserKey, userKey);

    if (!DaddyXerr0r) {
        ShowCanaryMessage(env, mContext);
    }

    return DaddyXerr0r ? env->NewStringUTF("OK") : env->NewStringUTF(errMsg.c_str());
}

int Register1(JNIEnv *env) {
    JNINativeMethod methods[] = {{"suckmydick", "(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;", (void *) native_Check}};

    jclass clazz = env->FindClass("pubgm/loader/activity/LoginActivity");
    if (!clazz)
        return -1;

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return -1;

    return 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    if (Register1(env) != 0)
        return -1;
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_pubgm_loader_activity_MainActivity_EXP(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(g_Licence.c_str());
}