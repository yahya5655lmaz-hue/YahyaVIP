![YahyaVIP Mod Menu](https://github.com/user-attachments/assets/1102b880-769e-4439-9221-2ac579f65235)

# 🎮 YahyaVIP - IL2CPP Android Mod Menu Template

A **modern, feature-rich Android mod menu framework** for game development and modding. Built on the robust foundation of [LGL Mod Menu](https://github.com/LGLTeam/Android-Mod-Menu/) with significant enhancements for security, functionality, and user experience.

> **⚖️ Disclaimer**: This project is for **educational purposes and game development only**. Use responsibly and in compliance with game ToS.

---

## ✨ Key Features

### 🔧 Core Functionality
- **Floating Mod Menu** - Always-accessible overlay menu system
- **IL2CPP Integration** - Seamless game engine hooks and memory manipulation
- **Modular Architecture** - Easy to extend with custom modules
- **Persistent Settings** - Save and load user preferences

### 🛡️ Security & Obfuscation
- **BlackObfuscator Integration** - Advanced code obfuscation via ASPlugin
- **LSParanoid String Encryption** - Protects sensitive strings at runtime
- **Remap Hide Support** - Optional library remapping for stealth
- **Secure Memory Access** - Safe IL2CPP resolver implementation

### 🎨 UI/UX
- **Dual Layout Modes** - Switch between modern and LGL classic designs
- **Custom Fonts** - Support for TTF font integration via C arrays
- **Responsive Design** - Optimized for various Android screen sizes
- **Smooth Animations** - Professional menu transitions

### 🚀 Developer Experience
- **UnityResolve API** - More extensive than legacy IL2cppResolver
- **Gradle Build System** - Android 8.2.2 with Java 17
- **GitHub Actions CI/CD** - Automated APK builds on push/PR
- **Comprehensive Documentation** - Clear integration guides

---

## 🏗️ Project Structure

```
YahyaVIP/
├── app/                          # Main Android application module
│   ├── src/main/
│   │   ├── java/                # Java/Kotlin source code
│   │   ├── cpp/                 # Native C++ code for IL2CPP hooks
│   │   ├── res/                 # Resources (layouts, drawables, strings)
│   │   └── AndroidManifest.xml  # App configuration
│   └── build.gradle             # App-level Gradle config
├── .github/
│   ├── workflows/main.yml       # CI/CD pipeline
│   └── ISSUE_TEMPLATE/          # Bug report & feature request templates
├── gradle/                      # Gradle wrapper
├── build.gradle                 # Root Gradle configuration
├── settings.gradle              # Multi-module settings
├── gradle.properties            # Gradle properties
├── gradlew                       # Gradle wrapper (Unix)
├── gradlew.bat                  # Gradle wrapper (Windows)
├── .gitignore                   # Git ignore rules
├── LICENSE                      # GNU General Public License v3
└── README.md                    # This file

```

---

## 🚀 Quick Start

### Prerequisites
- Android Studio 2023.1+
- JDK 17
- Android SDK (API 21+)
- Git

### Setup Instructions

#### 1. Clone the Repository
```bash
git clone https://github.com/yahya5655lmaz-hue/YahyaVIP.git
cd YahyaVIP
```

#### 2. Open in Android Studio
```bash
# Android Studio will automatically detect the Gradle project
# Sync Gradle files when prompted
```

#### 3. Configure Build Flavor
Choose your preferred mod menu layout:
- **seedhollow** - Modern default layout (recommended)
- **lgl** - Classic LGL mod menu layout

```gradle
// In app/build.gradle
flavorDimensions "layout"
productFlavors {
    seedhollow { dimension "layout" }
    lgl { dimension "layout" }
}
```

#### 4. Build the APK
```bash
# Debug build
./gradlew assembleDebug

# Release build (with ProGuard)
./gradlew assembleRelease
```

The compiled APK will be located in: `app/build/outputs/apk/debug/`

---

## 📝 Integration Guide

### 1. Add Floating Menu Permission
Add this to your target app's `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

This allows the floating menu to display as an overlay across other apps.

### 2. Register the Launcher Service
In your target app's manifest:

```xml
<service 
    android:name="com.android.support.Launcher" 
    android:enabled="true" 
    android:exported="true" 
    android:stopWithTask="true" />
```

### 3. Initialize in UnityPlayerActivity
Add this to `UnityPlayerActivity.smali` in the `onCreate` method:

```smali
invoke-static {p0}, Lcom/android/support/Loader;->Start(Landroid/content/Context;)V
```

**Alternative** (without Remap):
```smali
invoke-static {p0}, Lcom/android/support/Main;->Start(Landroid/content/Context;)V
```

---

## 🎨 Custom Font Integration

### Export Font to C Array

1. **Load TTF file** in [ImHex](https://github.com/WerWolv/ImHex)
2. **File → Export → Text Formatted Bytes → C Array**
3. **Save as** `.h` file

### Import into Project

1. Copy the `.h` file to `Includes/Fonts/`
2. Edit `Includes/Utils.h` - `LoadFontData()` method:

```cpp
jbyteArray LoadFontData(JNIEnv *env, jclass thiz, jobject ctx) {
    // Create byte array from your custom font
    jbyteArray fontData = env->NewByteArray(std::size(your_custom_font));
    
    // Populate with font data
    env->SetByteArrayRegion(fontData, 0, std::size(your_custom_font), 
                           (jbyte*)your_custom_font);
    
    return fontData;
}
```

---

## 🔧 Build Configuration

### Gradle Properties
```properties
# Android X Support
android.useAndroidX=true
android.enableJetifier=true

# Build Config Generation
android.defaults.buildfeatures.buildconfig=true

# Non-transitive R class
android.nonTransitiveRClass=false
```

### JDK Version
- **Minimum**: Java 11
- **Recommended**: Java 17 (current setup)

### Android SDK
- **Min SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34+ (recommended)

---

## 🔄 CI/CD Pipeline

GitHub Actions automatically builds debug APKs on:
- ✅ Push to `main` or `master` branch
- ✅ Pull requests to `main` or `master` branch

### Workflow Steps
1. Checkout code
2. Setup JDK 17 & Android SDK
3. Grant execute permission to `gradlew`
4. Build debug APK with stack trace logging
5. Upload APK as artifact

**Access artifacts**: GitHub Actions → Build APK → Artifacts

---

## 📦 Dependencies

### Core Libraries
- **UnityResolve.hpp** - IL2CPP API resolution
- **Dobby** - Function hooking framework
- **Remap Hide** - Optional library remapping

### Build Tools
- Android Gradle Plugin: 8.2.2
- Gradle: 8.11.1
- Java: 17

### Security Tools
- **BlackObfuscator** - Code obfuscation
- **LSParanoid** - String encryption

---

## 🎮 Example Projects

### Arknights DLC
A simple game helper built on YahyaVIP framework:
- [seedhollow/arknights-dlc](https://github.com/seedhollow/arknights-dlc)

---

## 🤝 Contributing

Contributions are welcome! Please:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Reporting Issues
- Use the [Bug Report Template](.github/ISSUE_TEMPLATE/bug_report.md)
- Include logs, screenshots, and reproduction steps

### Feature Requests
- Use the [Feature Request Template](.github/ISSUE_TEMPLATE/feature_request.md)
- Describe the problem and your proposed solution

---

## 📚 Resources

### Documentation
- [IL2CPP Documentation](https://docs.unity3d.com/Manual/IL2CPP.html)
- [Android Manifest Guide](https://developer.android.com/guide/topics/manifest/manifest-intro)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)

### Tools & References
- [ImHex - Hex Editor](https://github.com/WerWolv/ImHex)
- [AndroidIDE - Mobile Development](https://github.com/AndroidIDEOfficial/AndroidIDE)
- [Frida - Dynamic Instrumentation](https://frida.re/)

---

## 🙏 Credits

- **[nik2143](https://github.com/nik2143)** - LGL mod menu layout restoration
- **[LGL Team](https://github.com/LGLTeam/Android-Mod-Menu/)** - Original mod menu framework
- **[CodingGay](https://github.com/CodingGay/BlackObfuscator-ASPlugin)** - BlackObfuscator
- **[LSPosed](https://github.com/LSPosed/LSParanoid)** - LSParanoid string encryption
- **[issuimo](https://github.com/issuimo/UnityResolve.hpp)** - UnityResolve API
- **[jmpews](https://github.com/jmpews/Dobby/)** - Dobby framework
- **[reveny](https://github.com/reveny/Android-Library-Remap-Hide)** - Remap Hide

---

## 📄 License

This project is licensed under the **GNU General Public License v3.0** - see the [LICENSE](LICENSE) file for details.

**Summary**: You are free to use, modify, and distribute this project under the GPL v3 license. Any modifications must also be open-source and GPL licensed.

---

## ⚠️ Important Disclaimer

This project is provided **for educational purposes only**:

- ✅ **Use for**: Learning game modding, app development, security research
- ❌ **Don't use for**: Bypassing security, cheating in competitive games, unauthorized app modification
- ⚖️ **Responsibility**: Users are responsible for compliance with game Terms of Service and applicable laws
- 🛡️ **No warranty**: This project is provided as-is without any guarantee or liability

**NEVER purchase source codes on Telegram or other platforms claiming to be from this project.** This repository is always free and open-source on GitHub.

---

## 🌟 Star History

If you find this project useful, please consider giving it a ⭐ on GitHub!

---

## 📞 Support & Contact

- **Issues**: [GitHub Issues](https://github.com/yahya5655lmaz-hue/YahyaVIP/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yahya5655lmaz-hue/YahyaVIP/discussions)
- **Repository**: [yahya5655lmaz-hue/YahyaVIP](https://github.com/yahya5655lmaz-hue/YahyaVIP)

---

<div align="center">

**Made with ❤️ by the YahyaVIP Community**

*Last Updated: May 2, 2026*

</div>
