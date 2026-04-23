#include <jni.h>
#include <string>
#include <openssl/sha.h>
#include <android/log.h>
#include <array>

#define LOG_TAG "SignatureCheck"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jboolean JNICALL
Java_pubgm_loader_floating_Togglelook_verifySignatureNative(
        JNIEnv* env,
        jobject thiz,
        jbyteArray signatureBytes) {

    const std::string expectedSha256 = "09A189240868F65B8825A3EBB833C81166BC94E8A602BD98E156CF3747E38A8C";

    jsize length = env->GetArrayLength(signatureBytes);
    jbyte* bytes = env->GetByteArrayElements(signatureBytes, nullptr);

    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, bytes, length);
    SHA256_Final(hash, &sha256);

    char sha256String[65];
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        sprintf(sha256String + (i * 2), "%02X", hash[i]);
    }
    sha256String[64] = '\0';

    env->ReleaseByteArrayElements(signatureBytes, bytes, 0);

    bool isValid = (expectedSha256 == sha256String);

    if (!isValid) {
        LOGE("Invalid signature! Expected: %s, Got: %s", expectedSha256.c_str(), sha256String);
    } else {
        LOGI("Signature is valid!");
    }

    return static_cast<jboolean>(isValid);
}