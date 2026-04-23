#ifndef HEXOR_PRO_IMPORTANT_IMPORT_H
#define HEXOR_PRO_IMPORTANT_IMPORT_H

#include <jni.h>
#include <string>
#include <cstdlib>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <cerrno>
#include <sys/un.h>
#include <cstring>
#include <string>
#include <cmath>
#include "struct.h"

//====== Booleand =====\\

bool isHideItem = true;
bool is360Alertv2 = true;
bool isSkeleton = true;
bool isPlayerHead = true;
bool isPlayerBox = true;
bool isKnockedPlayerHealth = true;
bool isLootBox = true;
bool isPlayerLine = true;
bool isPlayerHealth = true;
bool isPlayerName = true;
bool isPlayerDistance = true;
bool isPlayerWeapon = true;
bool isPlayerWeaponIcon = true;
bool isGrenadeWarning = true;
bool enemycountv1 = true;
bool enemycountv2 = true;
bool isPlayerUID = true;
bool isNation = true;
int size(char *ptr) {
    //variable used to access the subsequent array elements.
    int offset = 0;
    //variable that counts the number of elements in your array
    int count = 0;

    //While loop that tests whether the end of the array has been reached
    while (*(ptr + offset) != '\0') {
        //increment the count variable
        ++count;
        //advance to the next element of the array
        ++offset;
    }
    //return the size of the array
    return count;
}
#endif //HEXOR_PRO_IMPORTANT_IMPORT_H
