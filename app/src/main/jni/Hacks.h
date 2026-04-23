#ifndef BETA_ESP_IMPORTANT_HACKS_H
#define BETA_ESP_IMPORTANT_HACKS_H

#include "socket.h"
#include "Color.h"
#include "items.h"
#include "Vector3.hpp"
#include "timer.h"
#include "Login.h"

timer FPSControl;

Color clrEnemy, clrEdge, clrBox, clrAlert, clr, clrTeam, clrDist, clrHealth, clrText, grenadeColor, clrtanda;
float h, w, x, y, z, magic_number, mx, my, top, bottom, textsize, mScale, skelSize;
Options options{1, -1, -1, 3, false, false, 1, false, 200, 200, 700, 19, 19, -1, false};
OtherFeature otherFeature{false, false, false, false, false};

int botCount, playerCount;
Response response;
Request request;
char extra[30];
char text[100];
int hCounter = 50;

Color colorByDistance(int distance, int alpha) {
    Color clrDistance;
    if (distance < 600)
        clrDistance = Color::Yellow(255);
    if (distance < 300)
        clrDistance = Color::Orange(255);
    if (distance < 150)
        clrDistance = Color::Red(255);
    return clrDistance;
}

bool isOutsideSafeZone(Vec2 pos, Vec2 screen) {
    if (pos.y < 0) return true;
    if (pos.x > screen.x) return true;
    if (pos.y > screen.y) return true;
    return pos.x < 0;
}

std::string playerstatus(int GetEnemyState) {
    switch (GetEnemyState) {
        case 520:
        case 544:
        case 656:
        case 521:
        case 528:
        case 3145736:
            return "Aiming";
        default:
            return "";
    }
}

Vec2 calculatePosition(const Vec2 &center, float radius, float angleDegrees) {
    float angleRadians = angleDegrees * (M_PI / 180.0f);
    float x = center.x + radius * cos(angleRadians);
    float y = center.y + radius * sin(angleRadians);
    return Vec2(x, y);
}

bool colorPosCenter(float sWidth, float smMx, float sHeight, float posT, float eWidth, float emMx, float eHeight, float posB) {
    return sWidth >= smMx && sHeight >= posT && eWidth <= emMx && eHeight <= posB;
}

Vec2 pushToScreenBorder(const Vec2 &location, const Vec2 &screen, float offset, float scale = 2.0f) {
    Vec2 center(screen.x / 2, screen.y / 2);
    float angle = atan2(location.y - center.y, location.x - center.x) * (180.0f / M_PI);
    return calculatePosition(center, offset * scale, angle);
}

void DrawRadar(ESP canvas, Vec2 Location, Vec2 Pos, float Size, Color clr, int TeamID) {
    canvas.DrawFilledRoundRect(Color::White(255), {Pos.x - Size / 25, Pos.y - Size / 25}, {Pos.x + Size / 25, Pos.y + Size / 25});
    canvas.DrawFillCircle(Color(clr.r, clr.g, clr.b, 255), Location, Size / 10, 0.5);
    if (isPlayerName) {
        canvas.DrawText(Color::White(255), std::to_string(TeamID).c_str(), Location, Size / 10);
    }
}

void DrawESP(ESP esp, int screenWidth, int screenHeight) {
   // اللون الأزرق الفاتح للاسم
esp.DrawTextName(Color(255, 215, 0), "𝒀𝑨𝑯𝒀𝑨 𝑽𝑰𝑷 : ", Vec2(screenWidth / 8, screenHeight / 10), screenHeight / 20);

// اللون الأبيض للترخيص أو النصوص الأخرى
//esp.DrawText(Color(255, 255, 255), (std::string("") + g_Licence).c_str(), Vec2(screenWidth / 12, screenHeight / 8.9), screenHeight / 35);
    //esp.DrawTextMode(Color::White(255), "ANDROID", Vec2(screenWidth / 5, screenHeight / 1.05), screenHeight / 30);

const char *statusText = "";
if (options.aimBullet == 0) {
   // statusText = "الـبـولت تــرك";
} else if (otherFeature.Aimbot) {
  //  statusText = "ايـمـبـوت";
} else if (otherFeature.LessRecoil || otherFeature.SmallCrosshair || otherFeature.WideView) {
  //  statusText = "التفاعلات";
} else {
   // statusText = "الـكـشـف";
}

    esp.DrawTexture(Color::Green(255), statusText, Vec2(screenWidth / 5, screenHeight / 1.09), screenHeight / 30);

    request.ScreenHeight = screenHeight;
    request.ScreenWidth = screenWidth;
    request.options = options;
    request.otherFeature = otherFeature;
    request.Mode = InitMode;

    botCount = 0, playerCount = 0;
    send((void *)&request, sizeof(request));
    receive((void *)&response);
    float mScaleY = screenHeight / 1080.0f;
    mScale = screenHeight / 1080.0f;
    skelSize = (mScale * 1.5f);
    float BoxSize = (mScaleY * 2.0f);
    textsize = screenHeight / 50;
    Vec2 screen(screenWidth, screenHeight);

    if (response.Success) {
        for (int i = 0; i < response.PlayerCount; i++) {
            PlayerData Player = response.Players[i];
            x = Player.HeadLocation.x;
            y = Player.HeadLocation.y;

            sprintf(extra, "%0.0fM", Player.Distance);
            float magic_number = (response.Players[i].Distance * response.fov);
            float nameWidth = (screenWidth / 6) / magic_number;
            float pp2 = nameWidth / 2;
            float mx = (screenWidth / 4) / magic_number;
            float my = (screenWidth / 1.38) / magic_number;
            float top = y - my + (screenWidth / 1.7) / magic_number;
            float bottom = response.Players[i].Bone.lAn.y + my - (screenWidth / 1.7) / magic_number;
            clrDist = colorByDistance((int)Player.Distance, 255);
            clrAlert = _clrID((int)Player.TeamID, 80);
            clrTeam = _clrID((int)Player.TeamID, 150);
            clr = _clrID((int)Player.TeamID, 200);
            Vec2 location(x, y);
            bool playerInCenter = colorPosCenter(screenWidth / 2, x - mx, screenHeight / 2, top, screenWidth / 2, x + mx, screenHeight / 2, bottom);

            if (Player.isBot) {
                botCount++;
                clrEnemy = Color::White(255);
                clrEdge = Color::White(80);
                clrBox = Color::White(255);
                clrText = Color::Black(255);
            } else {
                playerCount++;
                clrEnemy = clrDist;
                clrEdge = clrAlert;
                clrBox = Color::Red(255);
                clrText = Color::White(255);
            }

            if (true) {
                clrEnemy = playerInCenter ? Color::Green(255) : clrEnemy;
                clrBox = playerInCenter ? Color::Green(255) : clrBox;
                clrText = playerInCenter ? Color::Green(255) : clrText;
                clrtanda = playerInCenter ? Color::White(255) : Color::Green(255);
            }

            if (response.Players[i].HeadLocation.z != 1) {
                if (x > -50 && x < screenWidth + 50) {
if (isSkeleton && Player.Bone.isBone) {
    float skelSize = mScaleY * 2.0f;
    bool isVisible = response.Players[i].isVisible;
    Color skelColor = isVisible ? Color(0, 255, 0, 255) : Color(255, 0, 0, 200);
    float radius = screenHeight / 20.0f / magic_number;

    Vec2 neck(response.Players[i].Bone.neck.x, response.Players[i].Bone.neck.y);
    Vec2 cheast(response.Players[i].Bone.cheast.x, response.Players[i].Bone.cheast.y);
    Vec2 pelvis(response.Players[i].Bone.pelvis.x, response.Players[i].Bone.pelvis.y);
    Vec2 lSh(response.Players[i].Bone.lSh.x, response.Players[i].Bone.lSh.y);
    Vec2 rSh(response.Players[i].Bone.rSh.x, response.Players[i].Bone.rSh.y);
    Vec2 lElb(response.Players[i].Bone.lElb.x, response.Players[i].Bone.lElb.y);
    Vec2 rElb(response.Players[i].Bone.rElb.x, response.Players[i].Bone.rElb.y);
    Vec2 lWr(response.Players[i].Bone.lWr.x, response.Players[i].Bone.lWr.y);
    Vec2 rWr(response.Players[i].Bone.rWr.x, response.Players[i].Bone.rWr.y);
    Vec2 lTh(response.Players[i].Bone.lTh.x, response.Players[i].Bone.lTh.y);
    Vec2 rTh(response.Players[i].Bone.rTh.x, response.Players[i].Bone.rTh.y);
    Vec2 lKn(response.Players[i].Bone.lKn.x, response.Players[i].Bone.lKn.y);
    Vec2 rKn(response.Players[i].Bone.rKn.x, response.Players[i].Bone.rKn.y);
    Vec2 lAn(response.Players[i].Bone.lAn.x, response.Players[i].Bone.lAn.y);
    Vec2 rAn(response.Players[i].Bone.rAn.x, response.Players[i].Bone.rAn.y);

    esp.DrawLine(skelColor, skelSize, neck, cheast);
    esp.DrawLine(skelColor, skelSize, cheast, pelvis);
    esp.DrawLine(skelColor, skelSize, neck, lSh);
    esp.DrawLine(skelColor, skelSize, neck, rSh);
    esp.DrawLine(skelColor, skelSize, lSh, lElb);
    esp.DrawLine(skelColor, skelSize, lElb, lWr);
    esp.DrawFilledCircle(skelColor, lWr, radius);
    esp.DrawLine(skelColor, skelSize, rSh, rElb);
    esp.DrawLine(skelColor, skelSize, rElb, rWr);
    esp.DrawFilledCircle(skelColor, rWr, radius);
    esp.DrawLine(skelColor, skelSize, pelvis, lTh);
    esp.DrawLine(skelColor, skelSize, pelvis, rTh);
    esp.DrawLine(skelColor, skelSize, lTh, lKn);
    esp.DrawLine(skelColor, skelSize, lKn, lAn);
    esp.DrawFilledCircle(skelColor, lAn, radius);
    esp.DrawLine(skelColor, skelSize, rTh, rKn);
    esp.DrawLine(skelColor, skelSize, rKn, rAn);
    esp.DrawFilledCircle(skelColor, rAn, radius);
}

                    if (isPlayerBox) {
                        esp.DrawLine(clrBox, BoxSize, Vec2(x + pp2, top), Vec2(x + nameWidth, top));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x - pp2, top), Vec2(x - nameWidth, top));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x + nameWidth, top), Vec2(x + nameWidth, top + pp2));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x - nameWidth, top), Vec2(x - nameWidth, top + pp2));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x + pp2, bottom), Vec2(x + nameWidth, bottom));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x - pp2, bottom), Vec2(x - nameWidth, bottom));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x - nameWidth, bottom), Vec2(x - nameWidth, bottom - pp2));
                        esp.DrawLine(clrBox, BoxSize, Vec2(x + nameWidth, bottom), Vec2(x + nameWidth, bottom - pp2));
                    }

                    if (isPlayerLine) {
                        esp.DrawLine(clrBox, skelSize, Vec2(screenWidth / 2, screenHeight / 9), Vec2(x, top - screenHeight / 31));
                    }

                    if (isPlayerHealth) {
                        float healthLength = screenWidth / 24;
                        int health = response.Players[i].Health;

                        if (response.Players[i].isBot) {
                            clrHealth = Color(128, 0, 128, 200);
                        } else {
                            if (health > 75)
                                clrHealth = Color(255, 0, 0, 185);
                            else if (health > 50)
                                clrHealth = Color(255, 255, 0, 185);
                            else if (health > 25)
                                clrHealth = Color(0, 0, 255, 185);
                            else
                                clrHealth = Color(255, 140, 0, 185);
                        }

                        if (health == 0) {
                            esp.DrawText(Color(255, 0, 0), "نـــوك", Vec2(x, top), screenHeight / 27);
                        } else {
                            esp.DrawFilledRect(clrHealth, Vec2(x - healthLength, top - screenHeight / 30), Vec2(x - healthLength + (2 * healthLength) * health / 100, top - screenHeight / 225));
                            esp.DrawRect(Color(0, 0, 0), screenHeight / 640, Vec2(x - healthLength, top - screenHeight / 30), Vec2(x + healthLength, top - screenHeight / 255));
                        }

                        Color teamColor = Color(255, 255, 255, 255);
                        if (response.Players[i].TeamID == 1) teamColor = Color(34, 214, 97, 255);
                        else if (response.Players[i].TeamID == 2) teamColor = Color(63, 132, 255, 255);
                        esp.DrawFilledRect(Color(0, 0, 0, 180), Vec2(x - healthLength, top - screenHeight / 20), Vec2(x - healthLength + 30, top - screenHeight / 30));
                        char teamStr[8];
                        sprintf(teamStr, "%d", response.Players[i].TeamID);
                        esp.DrawText(teamColor, teamStr, Vec2(x - healthLength + 15, top - screenHeight / 27), screenHeight / 35);
                    }

                    if (isPlayerHead) {
                        esp.DrawFilledCircle(clrEdge, Vec2(response.Players[i].HeadLocation.x, response.Players[i].HeadLocation.y), screenHeight / 12 / magic_number);
                    }
if (isPlayerName && response.Players[i].isBot) {
    sprintf(extra, "الـبـوت");
    esp.DrawText(Color(255, 255, 255), extra, Vec2(x, top - 12), textsize);
} else if (isPlayerName) {
    esp.DrawName(Color().White(255), response.Players[i].PlayerNameByte, response.Players[i].TeamID, Vec2(response.Players[i].HeadLocation.x, top - 12), textsize);
}

                    if (isPlayerDistance) {
                        sprintf(extra, "%0.0f M", response.Players[i].Distance);
                        esp.DrawText(Color(247, 175, 63, 255), extra, Vec2(x, bottom + screenHeight / 45), textsize);
                    }

                    if (isPlayerWeaponIcon && response.Players[i].Weapon.isWeapon) {
                        esp.DrawWeaponIcon(response.Players[i].Weapon.id, Vec2(x - screenWidth / 45, top - screenHeight / 15));
                    }

                    if ((is360Alertv2 && isOutsideSafeZone(location, screen)) || is360Alertv2) {
                        float renderX = x;
                        float renderY = y;

                        if (is360Alertv2 && isOutsideSafeZone(location, screen)) {
                            Vec2 hintDotRenderPos = pushToScreenBorder(location, screen, (mScaleY * 100) / 3, 5.0f);
                            esp.DrawFilledCircle(clrAlert, hintDotRenderPos, mScaleY * 15);
                        }

                        if (is360Alertv2) {
                            if (response.Players[i].HeadLocation.z == 1.0f) {
                                renderX = std::clamp(renderX, screenWidth / 12.0f, screenWidth - screenWidth / 12.0f);
                                renderY = std::clamp(renderY, screenHeight /20.0f, screenHeight - screenHeight / 20.0f);

                                Vec2 drawPos = Vec2(renderX, renderY > screenHeight / 2 ? screenHeight - screenHeight / 20 : screenHeight / 20);
                                esp.DrawFilledCircle(clrAlert, drawPos, screenHeight / 30);
                                char distanceText[32];
                                sprintf(distanceText, "%.0f m", response.Players[i].Distance);
                                esp.DrawText(Color(255, 255, 255, 255), distanceText, drawPos, textsize);
                            } else {
                                renderX = std::clamp(renderX, 0.0f, (float)screenWidth);
                                renderY = std::clamp(renderY, 0.0f, (float)screenHeight);

                                Vec2 drawPos = renderX > screenWidth / 2 ? Vec2(screenWidth - screenWidth / 40, renderY) : Vec2(screenWidth / 40, renderY);
                                esp.DrawFilledCircle(clrAlert, drawPos, screenHeight / 30);
                                char distanceText[32];
                                sprintf(distanceText, "%.0f m", response.Players[i].Distance);
                                esp.DrawText(Color(255, 255, 255, 255), distanceText, Vec2(drawPos.x, drawPos.y + screenHeight / 50), textsize);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < response.GrenadeCount; i++) {
            GrenadeData grenade = response.Grenade[i];
            if (!isGrenadeWarning || grenade.Location.z == 1.0f) continue;

            const char *grenadeTypeText;
            switch (grenade.type) {
                case 1:
                    grenadeColor = Color::Red(255);
                    grenadeTypeText = "قــنــبـلـة";
                    break;
                case 2:
                    grenadeColor = Color::Orange(255);
                    grenadeTypeText = "حــارـقه";
                    break;
                case 3:
                    grenadeColor = Color::Yellow(255);
                    grenadeTypeText = "فلاش";
                    break;
                default:
                    grenadeColor = Color::White(255);
                    grenadeTypeText = "اســـمــوك";
            }

            sprintf(extra, "%s (%.0f m)", grenadeTypeText, grenade.Distance);
            esp.DrawCircle(grenadeColor, Vec2(grenade.Location.x, grenade.Location.y), screenHeight / 60, 2.0f);
            esp.DrawText(grenadeColor, extra, Vec2(grenade.Location.x, grenade.Location.y - (screenHeight / 50)), screenHeight / 45);
            esp.DrawText(grenadeColor, "●", Vec2(grenade.Location.x, grenade.Location.y), screenHeight / 45);
        }
    }

    static int lastPlayerCount = 0;
    static std::chrono::steady_clock::time_point alertTime;
    static bool showAlert = false;

Vec2 rectTopLeft(screenWidth / 2 - 65, 50);
Vec2 rectBottomRight(screenWidth / 2 + 65, 85);

// حساب مركز المستطيل
Vec2 rectCenter((rectTopLeft.x + rectBottomRight.x) / 2,
(rectTopLeft.y + rectBottomRight.y) / 2);

if (response.InLobby) {
esp.DrawFilledRect(Color(173, 255, 47, 50), rectTopLeft, rectBottomRight);
esp.DrawRect(Color(173, 255, 47), 1.8f, rectTopLeft, rectBottomRight);

sprintf(extra, "LOBBY");  
esp.DrawText(Color::White(255), extra, rectCenter, 25);

} else {
esp.DrawFilledRect(Color(173, 255, 47, 50), rectTopLeft, rectBottomRight);
esp.DrawRect(Color(173, 255, 47), 1.8f, rectTopLeft, rectBottomRight);

int enemyCount = playerCount + botCount;  

if (enemyCount > 0) {  
    sprintf(extra, "%d", enemyCount); // رقم فقط  
    esp.DrawText(Color::White(255), extra, rectCenter, 25);  
} else {  
    sprintf(extra, "N5LA");  
    Vec2 clearTextPos = rectCenter;  
    clearTextPos.y += 2;  
    esp.DrawText(Color::White(255), extra, clearTextPos, 25);  
}

}
    using namespace std::chrono;
    if (playerCount > lastPlayerCount) {
        alertTime = steady_clock::now();
        showAlert = true;
    }
    if (showAlert && duration_cast<seconds>(steady_clock::now() - alertTime).count() < 3) {
        esp.DrawText(Color(255, 255, 0, 255), "⚠️ عدو جديد", Vec2(screenWidth / 2, screenHeight / 4.3), screenHeight / 30);
    } else if (showAlert) {
        showAlert = false;
    }
    lastPlayerCount = playerCount;

    if (options.tracingStatus) {
        float py = screenHeight / 2;
        float px = screenWidth / 2;
        esp.DrawFilledRect(Color(0, 255, 0, 50), Vec2(options.touchY - options.touchSize / 2, py * 2 - options.touchX + options.touchSize / 2), Vec2(options.touchY + options.touchSize / 2, py * 2 - options.touchX - options.touchSize / 2));
    }

    if (options.openState == 0 || options.aimBullet == 0 || options.aimT == 0) {
        Color textColor = (options.openState == 0) ? Color(255, 0, 0, 255) : (options.aimT == 0 ? Color(0, 0, 255, 255) : Color(0, 255, 0, 255));
        esp.DrawCircle(textColor, Vec2(screenWidth / 2, screenHeight / 2), options.aimingRange, 1.5);
    }

    FPSControl.SetFps(1000);
    FPSControl.AotuFPS();
}

#endif