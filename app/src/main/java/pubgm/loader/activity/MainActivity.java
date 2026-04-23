package pubgm.loader.activity;
import android.content.res.Configuration;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import pubgm.loader.BoxApplication;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import top.niunaijun.blackbox.utils.FileUtils;
import android.graphics.Color;
import static pubgm.loader.livai.R3pmodking.activity;
import static pubgm.loader.activity.LoginActivity.PASSKEY;
import static pubgm.loader.activity.LoginActivity.USERKEY;
import top.niunaijun.blackbox.BlackBoxCore;
import pubgm.loader.utils.UiKit;
import java.util.List;
import java.util.Locale;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.ColorDrawable;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import androidx.appcompat.app.AlertDialog;
import pubgm.loader.utils.PermissionUtils;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pubgm.loader.Component.DownloadZip;
import pubgm.loader.R;
import pubgm.loader.floating.FloatService;
import pubgm.loader.floating.Overlay;;
import pubgm.loader.floating.ToggleBullet;

import pubgm.loader.utils.FLog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.topjohnwu.superuser.Shell;
import android.view.LayoutInflater;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import pubgm.loader.utils.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import static top.niunaijun.blackbox.core.env.BEnvironment.getDataFilesDir;
import top.niunaijun.blackbox.entity.pm.InstallResult;
import android.os.AsyncTask;
import java.util.TimeZone;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.Environment;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.view.Gravity;
import org.lsposed.lsparanoid.Obfuscate;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import java.security.MessageDigest;


@Obfuscate
public class MainActivity extends ActivityCompat {

    public static String socket;
    public static String daemonPath;
    public static boolean check = false;
    static MainActivity instance;
    private TextView bmode, safe_txt;
    public String verPath;

    static {
        System.loadLibrary("livai");
    }

    Context ctx;
    InstallResult installResult;
    BlackBoxCore blackboxCore;
    public static native String EXP();
    public static String TimeExpired;

    private static final int REQUEST_PERMISSIONS = 1;
    private static final String PREF_NAME = "espValue";
    private SharedPreferences sharedPreferences;
    String[] appPackage = {"com.tencent.ig", "com.pubg.krmobile", "com.pubg.imobile", "com.rekoo.pubgm", "com.facebook.katana", "com.vng.pubgmobile", "com.twitter.android", "mark.via.gp"};
    public String nameGame = "PROTECTION GLOBAL";
    public String CURRENT_PACKAGE = "";
    public LinearProgressIndicator progres;
    public CardView enable, disable;
    public static int gameint = 1;
    public static String bypassmode = "Automatic";
    public static int bitversi = 64;
    public static boolean noroot = false;
    public static int device = 1;
    public static String game = "com.tencent.ig";
    TextView root;
    public static boolean kernel = false;
    public static boolean Ischeck = false;
    public static boolean modestatus = false;
    public LinearLayout container;
    static boolean isLibLoaded = false;
    public static String modeselect;
    public static String typelogin;
    private boolean isLogin;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private TextView Hari, Jam, Menit, Detik;

    
    private CardView selectedAppCard;
    private LinearLayout selectedAppContainer;
    private LinearLayout appIconsContainer;
    private ImageView selectedAppIcon;
    private TextView selectedAppTitle;
    private TextView selectedAppPackage;

    public static MainActivity get() {
        return instance;
    }

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    blackboxCore = BlackBoxCore.get();
    blackboxCore.doCreate();
    setContentView(R.layout.activity_navigation); 
    Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

    init();
    if (savedInstanceState != null) {
        return;
    }
    
    ctx = this;

    
    initAppIcons(); 

    initMenu1();
    initMenu2();
    devicecheck();

    instance = this;
    isLogin = true;
    makeDir();
    updateButtonVisibilityBasedOnInstallation();
    countTimerAccount();
    setupLogoClickListener();

    new Handler(Looper.getMainLooper()).postDelayed(() -> {
        Loadssets();
    }, 5000);
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    
}

    
    private void initAppIcons() {
        selectedAppCard = findViewById(R.id.selectedAppCard);
        selectedAppContainer = findViewById(R.id.selectedAppContainer);
        appIconsContainer = findViewById(R.id.appIconsContainer);
        selectedAppIcon = findViewById(R.id.selectedAppIcon);
        selectedAppTitle = findViewById(R.id.selectedAppTitle);
        selectedAppPackage = findViewById(R.id.selectedAppPackage);

        setupAppIconClickListeners();
    }

    private void setupAppIconClickListeners() {
        
        LinearLayout bgmiIcon = findViewById(R.id.bgmiIconContainer);
        bgmiIcon.setOnClickListener(v -> showAppDetails("BGMI", "com.pubg.imobile", R.drawable.circlebgmi, R.id.buttonContainerBgmi));

        
        LinearLayout globalIcon = findViewById(R.id.globalIconContainer);
        globalIcon.setOnClickListener(v -> showAppDetails("GLOBAL", "com.tencent.ig", R.drawable.circlegl, R.id.buttonContainerGlobal));

        
        LinearLayout koreaIcon = findViewById(R.id.koreaIconContainer);
        koreaIcon.setOnClickListener(v -> showAppDetails("KOREA", "com.pubg.krmobile", R.drawable.krcircle, R.id.buttonContainerKR));

        
        LinearLayout vietnamIcon = findViewById(R.id.vietnamIconContainer);
        vietnamIcon.setOnClickListener(v -> showAppDetails("VNG", "com.vng.pubgmobile", R.drawable.circlevn, R.id.buttonContainerVNG));

        
        LinearLayout taiwanIcon = findViewById(R.id.taiwanIconContainer);
        taiwanIcon.setOnClickListener(v -> showAppDetails("TAIWAN", "com.rekoo.pubgm", R.drawable.circletw, R.id.buttonContainerTW));

        
        LinearLayout twitterIcon = findViewById(R.id.twitterIconContainer);
        twitterIcon.setOnClickListener(v -> showAppDetails("Twitter", "com.twitter.android", R.drawable.tw, R.id.buttonContainerTR));
    }

    private void showAppDetails(String appName, String packageName, int iconRes, int buttonContainerId) {
        
        selectedAppCard.setVisibility(View.VISIBLE);
        
        
        selectedAppIcon.setImageResource(iconRes);
        selectedAppTitle.setText(appName);
        selectedAppPackage.setText(packageName);
        
        
        hideAllButtonContainers();
        
        
        LinearLayout buttonContainer = findViewById(buttonContainerId);
        if (buttonContainer != null) {
            buttonContainer.setVisibility(View.VISIBLE);
        }
        
        
        updateButtonVisibilityForSelectedApp(packageName, buttonContainerId);
    }

    private void hideAllButtonContainers() {
        int[] containerIds = {
            R.id.buttonContainerBgmi,
            R.id.buttonContainerGlobal,
            R.id.buttonContainerKR,
            R.id.buttonContainerVNG,
            R.id.buttonContainerTW,
            R.id.buttonContainerTR
        };
        
        for (int id : containerIds) {
            LinearLayout container = findViewById(id);
            if (container != null) {
                container.setVisibility(View.GONE);
            }
        }
    }

    private void updateButtonVisibilityForSelectedApp(String packageName, int buttonContainerId) {
        boolean isInstalled = blackboxCore.isInstalled(packageName, 0);
        
        MaterialButton installButton = null;
        MaterialButton uninstallButton = null;
        
        
        switch (buttonContainerId) {
            case R.id.buttonContainerBgmi:
                installButton = findViewById(R.id.InstallBgmi);
                uninstallButton = findViewById(R.id.UninstallBgmi);
                break;
            case R.id.buttonContainerGlobal:
                installButton = findViewById(R.id.InstallGlobal);
                uninstallButton = findViewById(R.id.UninstallGlobal);
                break;
            case R.id.buttonContainerKR:
                installButton = findViewById(R.id.InstallKR);
                uninstallButton = findViewById(R.id.UninstallKR);
                break;
            case R.id.buttonContainerVNG:
                installButton = findViewById(R.id.InstallVNG);
                uninstallButton = findViewById(R.id.UninstallVNG);
                break;
            case R.id.buttonContainerTW:
                installButton = findViewById(R.id.InstallTW);
                uninstallButton = findViewById(R.id.UninstallTW);
                break;
            case R.id.buttonContainerTR:
                installButton = findViewById(R.id.InstallTR);
                uninstallButton = findViewById(R.id.UninstallTR);
                break;
        }
        
        if (installButton != null && uninstallButton != null) {
            updateButtonVisibility(packageName, installButton, uninstallButton, findViewById(buttonContainerId));
        }
    }

    private void setupLogoClickListener() {
        ImageView logoImage = findViewById(R.id.logoImage);
        final boolean[] isMenuVisible = {false};
        
logoImage.setOnClickListener(v -> {
    if (!isMenuVisible[0]) {
        startFloater();
        Toast.makeText(this, getString(R.string.menu_opened), Toast.LENGTH_SHORT).show();
        isMenuVisible[0] = true;
    } else {
        stopPatcher();
        Toast.makeText(this, getString(R.string.menu_closed), Toast.LENGTH_SHORT).show();
        isMenuVisible[0] = false;
    }
});
    }

    public void devicecheck() {
        if (Shell.rootAccess()) {
            FLog.info("Root granted");
            modeselect = "روت -" + " الاندرويد" + Build.VERSION.RELEASE;
            Ischeck = true;
            noroot = true;
            device = 1;
        } else {
            FLog.info("Root not granted");
            modeselect = "بدون روت -" + " الاندرويد " + Build.VERSION.RELEASE;
            Ischeck = false;
            device = 2;
        }
    }

    @SuppressLint("SetTextI18n")
    public void initMenu1() {
        MaterialButton InstallBgmiBtn = findViewById(R.id.InstallBgmi);
        MaterialButton InstallGlobalBtn = findViewById(R.id.InstallGlobal);
        MaterialButton InstallKRBtn = findViewById(R.id.InstallKR);
        MaterialButton InstallVNGBtn = findViewById(R.id.InstallVNG);
        MaterialButton InstallTWBtn = findViewById(R.id.InstallTW);
        MaterialButton InstallTRBtn = findViewById(R.id.InstallTR);
        
        MaterialButton UninstallBgmiBtn = findViewById(R.id.UninstallBgmi);
        MaterialButton UninstallGlobalBtn = findViewById(R.id.UninstallGlobal);
        MaterialButton UninstallKRBtn = findViewById(R.id.UninstallKR);
        MaterialButton UninstallVNGBtn = findViewById(R.id.UninstallVNG);
        MaterialButton UninstallTWBtn = findViewById(R.id.UninstallTW);
        MaterialButton UninstallTRBtn = findViewById(R.id.UninstallTR);

        setupButtonAnimations();
    }

    private void setupButtonAnimations() {
        setupGameButtonAnimation(R.id.InstallBgmi, R.id.UninstallBgmi, R.id.buttonContainerBgmi, "com.pubg.imobile");
        setupGameButtonAnimation(R.id.InstallGlobal, R.id.UninstallGlobal, R.id.buttonContainerGlobal, "com.tencent.ig");
        setupGameButtonAnimation(R.id.InstallKR, R.id.UninstallKR, R.id.buttonContainerKR, "com.pubg.krmobile");
        setupGameButtonAnimation(R.id.InstallVNG, R.id.UninstallVNG, R.id.buttonContainerVNG, "com.vng.pubgmobile");
        setupGameButtonAnimation(R.id.InstallTW, R.id.UninstallTW, R.id.buttonContainerTW, "com.rekoo.pubgm");
        setupGameButtonAnimation(R.id.InstallTR, R.id.UninstallTR, R.id.buttonContainerTR, "com.twitter.android");
    }

    private void setupGameButtonAnimation(int installBtnId, int uninstallBtnId, int containerId, String packageName) {
        MaterialButton installButton = findViewById(installBtnId);
        MaterialButton uninstallButton = findViewById(uninstallBtnId);
        LinearLayout buttonContainer = findViewById(containerId);
        
        updateButtonVisibility(packageName, installButton, uninstallButton, buttonContainer);
        
        installButton.setOnClickListener(v -> {
            if (blackboxCore.isInstalled(packageName, 0)) {
                blackboxCore.launchApk(packageName, 0);
                if (!packageName.equals("com.twitter.android")) {
                    startFloater();
                    bypass();
                }
            } else {
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                    return;
                }
                
                if (needsObbFile(packageName)) {
                    if (!isObbFileAvailable(packageName)) {
                        showObbNotAvailableDialog(packageName, getGameName(packageName));
                        return;
                    }
                }

                blackboxCore.installPackageAsUser(packageName, 0);
                if (needsObbFile(packageName)) {
                    copyObbForPackage(packageName);
                }
                
                animateButtonTransition(installButton, uninstallButton, buttonContainer, true);
            }
        });
uninstallButton.setOnClickListener(v -> {
    if (blackboxCore.isInstalled(packageName, 0)) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.uninstall_title, getGameName(packageName)))
                .setMessage(R.string.uninstall_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    blackboxCore.uninstallPackageAsUser(packageName, 0);
                    Toast.makeText(this, getString(R.string.uninstalling, getGameName(packageName)), Toast.LENGTH_SHORT).show();
                    animateButtonTransition(installButton, uninstallButton, buttonContainer, false);
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
});
    }

    private boolean needsObbFile(String packageName) {
        String[] nonObbApps = {
            "com.twitter.android",
            "com.facebook.katana",
            "mark.via.gp"
        };
        
        for (String app : nonObbApps) {
            if (app.equals(packageName)) {
                return false;
            }
        }
        return true;
    }

    private void animateButtonTransition(MaterialButton installButton, MaterialButton uninstallButton, 
                                        LinearLayout container, boolean isInstalling) {
        
        if (isInstalling) {
            Animation shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.shrink_to_left);
            installButton.startAnimation(shrinkAnimation);
            
            shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                
                @Override
                public void onAnimationEnd(Animation animation) {
                    uninstallButton.setVisibility(View.VISIBLE);
                    Animation slideInAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);
                    uninstallButton.startAnimation(slideInAnimation);
                    LinearLayout.LayoutParams installParams = (LinearLayout.LayoutParams) installButton.getLayoutParams();
                    installParams.weight = 1;
                    installParams.width = 0;
                    installButton.setLayoutParams(installParams);
                    
                    installButton.setText(getString(R.string.open));
                }
                
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        } else {
            Animation slideOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
            uninstallButton.startAnimation(slideOutAnimation);
            
            slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                
                @Override
                public void onAnimationEnd(Animation animation) {
                    uninstallButton.setVisibility(View.GONE);
                    
                    Animation expandAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.expand_to_full);
                    installButton.startAnimation(expandAnimation);
                    LinearLayout.LayoutParams installParams = (LinearLayout.LayoutParams) installButton.getLayoutParams();
                    installParams.weight = 0;
                    installParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    installButton.setLayoutParams(installParams);
                    
                    installButton.setText(getString(R.string.install) + getGameNameBasedOnButton(installButton));
                }
                
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }
    }
    private String getGameNameBasedOnButton(MaterialButton button) {
        switch (button.getId()) {
            case R.id.InstallBgmi: return "BGMI";
            case R.id.InstallGlobal: return "GLOBAL";
            case R.id.InstallKR: return "KOREA";
            case R.id.InstallVNG: return "VNG";
            case R.id.InstallTW: return "TAIWAN";
            case R.id.InstallTR: return "Twitter";
            default: return "";
        }
    }
    
@SuppressLint("ResourceAsColor")
private void updateButtonVisibility(String packageName, MaterialButton installButton, 
                                  MaterialButton uninstallButton, LinearLayout container) {
    boolean isInstalled = blackboxCore.isInstalled(packageName, 0);
    
    if (isInstalled) {
        installButton.setStrokeWidth(2);
        installButton.setStrokeColor(ColorStateList.valueOf(Color.WHITE));
        installButton.setTextColor(Color.WHITE);

        uninstallButton.setVisibility(View.VISIBLE);
        uninstallButton.setStrokeWidth(2);
        uninstallButton.setStrokeColor(ColorStateList.valueOf(Color.WHITE));
        uninstallButton.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams installParams = (LinearLayout.LayoutParams) installButton.getLayoutParams();
        installParams.weight = 1;
        installParams.width = 0;
        installParams.setMarginEnd(4);
        installButton.setLayoutParams(installParams);

        LinearLayout.LayoutParams uninstallParams = (LinearLayout.LayoutParams) uninstallButton.getLayoutParams();
        uninstallParams.setMarginStart(4);
        uninstallButton.setLayoutParams(uninstallParams);

        installButton.setText(R.string.open);
    } else {
        installButton.setStrokeWidth(0);
        installButton.setTextColor(Color.WHITE);
        
        uninstallButton.setVisibility(View.GONE);

        LinearLayout.LayoutParams installParams = (LinearLayout.LayoutParams) installButton.getLayoutParams();
        installParams.weight = 0;
        installParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        installParams.setMarginEnd(0);
        installButton.setLayoutParams(installParams);

        String gameName = getGameName(packageName);
        installButton.setText(getString(R.string.install) + gameName);
    }
}

    @SuppressLint("ResourceAsColor")
    void initMenu2() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        
        SharedPreferences sharedPreferences = getSharedPreferences("espValue", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_PERMISSIONS);
        }
    }

private boolean isObbFileAvailable(String packageName) {
    int versionCode = getVersionCodeForPackage(packageName);
    if (versionCode == 0) return false;

    File sourceObb = findObbFile(packageName, versionCode);
    if (sourceObb != null && sourceObb.exists()) {
        return true;
    }
    try {
        Thread.sleep(1000);
    } catch (InterruptedException ignored) {}
    sourceObb = findObbFile(packageName, versionCode);
    return sourceObb != null && sourceObb.exists();
}


private void showObbNotAvailableDialog(String packageName, String gameName) {
    if (isObbFileAvailable(packageName)) {
        return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    boolean isAppInstalled = false;
    try {
        getPackageManager().getPackageInfo(packageName, 0);
        isAppInstalled = true;
    } catch (PackageManager.NameNotFoundException e) {
        isAppInstalled = false;
    }

    boolean hasStoragePermission = checkStoragePermission();

    String message;
    if (!isAppInstalled) {
        message = getString(R.string.obb_app_not_installed, gameName);
    } else if (!hasStoragePermission) {
        message = getString(R.string.obb_storage_permission_required);
    } else {
        if (isObbFileAvailable(packageName)) {
            return;
        }
        message = getString(R.string.obb_file_missing);
    }

    builder.setTitle(R.string.obb_error_title)
           .setMessage(message)
           .setPositiveButton(R.string.ok_button, null);

    if (!hasStoragePermission) {
        builder.setNeutralButton(R.string.grant_permission, (dialog, which) -> requestStoragePermission());
    }

    if (!isAppInstalled) {
        builder.setNeutralButton(R.string.open_play_store, (dialog, which) -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                startActivity(intent);
            }
        });
    }

    builder.show();
}

private void copyObbForPackage(String packageName) {
    int versionCode = getVersionCodeForPackage(packageName);
    if (versionCode == 0) {
        Toast.makeText(this, getString(R.string.unknown_version, packageName), Toast.LENGTH_LONG).show();
        return;
    }

    File sourceObb = findObbFile(packageName, versionCode);
    
    if (sourceObb == null || !sourceObb.exists()) {
        Toast.makeText(this, getString(R.string.obb_not_found, packageName), Toast.LENGTH_LONG).show();
        return;
    }

    File destDir = new File("/storage/emulated/0/blackbox/Android/obb/" + packageName + "/");
    if (!destDir.exists()) {
        if (!destDir.mkdirs()) {
            Toast.makeText(this, R.string.failed_create_dir, Toast.LENGTH_LONG).show();
            return;
        }
    }

    File destObb = new File(destDir, sourceObb.getName());

    if (destObb.exists()) {
        Toast.makeText(this, getString(R.string.obb_already_installed, packageName), Toast.LENGTH_LONG).show();
        return;
    }

    new MyCopyTask(packageName).execute(sourceObb.getAbsolutePath(), packageName);
}

private File findObbFile(String packageName, int versionCode) {
    String obbFileName = "main." + versionCode + "." + packageName + ".obb";

    String[] searchPaths = {
        "/storage/emulated/0/Android/obb/" + packageName,
        "/storage/emulated/0/obb/" + packageName,
        "/sdcard/Android/obb/" + packageName,
        "/sdcard/obb/" + packageName,
        Environment.getExternalStorageDirectory() + "/Android/obb/" + packageName,
        Environment.getExternalStorageDirectory() + "/obb/" + packageName
    };

    for (String path : searchPaths) {
        File obbFile = new File(path, obbFileName);
        if (obbFile.exists()) {
            return obbFile;
        }
    }

    return null;
}

    private int getVersionCodeForPackage(String packageName) {
        switch (packageName) {
            case "com.tencent.ig":
            case "com.pubg.krmobile":
            case "com.vng.pubgmobile":
            case "com.rekoo.pubgm":
                return 20725;
            case "com.pubg.imobile":
                return 20125;
            default:
                return 0;
        }
    }

    void gameversion(LinearLayout a, LinearLayout b, LinearLayout c, LinearLayout d, LinearLayout e){
        a.setBackgroundResource(R.drawable.bgfituron);
        b.setBackgroundResource(R.drawable.bgfituroff);
        c.setBackgroundResource(R.drawable.bgfituroff);
        d.setBackgroundResource(R.drawable.bgfituroff);
        e.setBackgroundResource(R.drawable.bgfituroff);
    }

    void init() {
        LinearLayout menu1 = findViewById(R.id.imenu1);
        LinearLayout menu2 = findViewById(R.id.imenu2);
    }

    private void updateButtonVisibilityBasedOnInstallation() {
        MaterialButton InstallBgmiBtn = findViewById(R.id.InstallBgmi);
        MaterialButton InstallGlobalBtn = findViewById(R.id.InstallGlobal);
        MaterialButton InstallKRBtn = findViewById(R.id.InstallKR);
        MaterialButton InstallVNGBtn = findViewById(R.id.InstallVNG);
        MaterialButton InstallTWBtn = findViewById(R.id.InstallTW);
        MaterialButton InstallTRBtn = findViewById(R.id.InstallTR);
        
        MaterialButton UninstallBgmiBtn = findViewById(R.id.UninstallBgmi);
        MaterialButton UninstallGlobalBtn = findViewById(R.id.UninstallGlobal);
        MaterialButton UninstallKRBtn = findViewById(R.id.UninstallKR);
        MaterialButton UninstallVNGBtn = findViewById(R.id.UninstallVNG);
        MaterialButton UninstallTWBtn = findViewById(R.id.UninstallTW);
        MaterialButton UninstallTRBtn = findViewById(R.id.UninstallTR);

        String[] packageNames = {
            "com.pubg.imobile",
            "com.tencent.ig",
            "com.pubg.krmobile",
            "com.vng.pubgmobile",
            "com.rekoo.pubgm",
            "com.twitter.android"
        };

        MaterialButton[] installButtons = {
            InstallBgmiBtn,
            InstallGlobalBtn,
            InstallKRBtn,
            InstallVNGBtn,
            InstallTWBtn,
            InstallTRBtn
        };
        
        MaterialButton[] uninstallButtons = {
            UninstallBgmiBtn,
            UninstallGlobalBtn,
            UninstallKRBtn,
            UninstallVNGBtn,
            UninstallTWBtn,
            UninstallTRBtn
        };

        for (int i = 0; i < packageNames.length; i++) {
            boolean isInstalled = blackboxCore.isInstalled(packageNames[i], 0);
            
            if (isInstalled) {
                installButtons[i].setVisibility(View.VISIBLE);
                uninstallButtons[i].setVisibility(View.VISIBLE);
                installButtons[i].setText("فتح");
            } else {
                installButtons[i].setVisibility(View.VISIBLE);
                uninstallButtons[i].setVisibility(View.GONE);
                
                String gameName = getGameName(packageNames[i]);
                installButtons[i].setText("تثبيت " + gameName);
            }
        }
    }

    private String getGameName(String packageName) {
        switch (packageName) {
            case "com.tencent.ig": return "GLOBAL";
            case "com.pubg.krmobile": return "KOREA";
            case "com.pubg.imobile": return "BGMI";
            case "com.rekoo.pubgm": return "TAIWAN";
            case "com.vng.pubgmobile": return "VNG";
            default: return "";
        }
    }

    void makeDir() {
        if (!Shell.rootAccess()) {
            File GlobalFolder = new File("/storage/emulated/0/blackbox/Android/obb/com.tencent.ig/");
            File KoreaFolder = new File("/storage/emulated/0/blackbox/Android/obb/com.pubg.krmobile/");
            File BgmiFolder = new File("/storage/emulated/0/blackbox/Android/obb/com.pubg.imobile/");
            if (!GlobalFolder.exists()) {
                GlobalFolder.mkdirs();
                KoreaFolder.mkdirs();
                BgmiFolder.mkdirs();
            }
        }
    }

    private class MyCopyTask extends AsyncTask<String, Integer, Boolean> {
        AlertDialog dialog;
        String message;
        ProgressBar progressBar;
        TextView progressText;
        String packageName;

        public MyCopyTask(String packageName) {
            this.packageName = packageName;
        }

@Override
protected void onPreExecute() {
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.WrapContentDialog);
    builder.setCancelable(false);
    View customLayout = getLayoutInflater().inflate(R.layout.dialog_progress, null);
    
    progressText = customLayout.findViewById(R.id.percentage_text);
    progressText.setText("0%");
    
    progressBar = customLayout.findViewById(R.id.progress_bar);
    
    dialog = builder.create();
    if (dialog.getWindow() != null) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    dialog.setView(customLayout);
    dialog.show();
}

        @Override
        protected Boolean doInBackground(String... params) {
            String sourcePath = params[0];
            File source = new File(sourcePath);
            String filename = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
            File destination = new File("/storage/emulated/0/blackbox/Android/obb/" + params[1] + "/" + filename);

            try {
                copyFileWithProgress(source, destination);
                return true;
            } catch (IOException e) {
                message = e.getMessage();
                return false;
            }
        }

        private void copyFileWithProgress(File source, File destination) throws IOException {
            try (FileInputStream in = new FileInputStream(source);
                 FileOutputStream out = new FileOutputStream(destination)) {

                long total = source.length();
                long copied = 0;
                byte[] buffer = new byte[4096];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                    copied += read;

                    int progress = (int)((copied * 100) / total);
                    publishProgress(progress);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            if (progressBar != null) {
                progressBar.setProgress(progress);
            }
            if (progressText != null) {
                progressText.setText(progress + "%");
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(getApplicationContext(), R.string.obb_copied, Toast.LENGTH_LONG).show();
                
                if (blackboxCore.isInstalled(packageName, 0)) {
                    blackboxCore.launchApk(packageName, 0);
                    startFloater();
                    bypass();
                }
            } else {
                Toast.makeText(getApplicationContext(), message != null ? message : "فشل في نسخ ملف OBB", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }

    public void Exec(String path, String toast) {
        try {
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);
            ExecuteElf("su -c " + getFilesDir() + path);
            ExecuteElf("chmod 777 " + getFilesDir() + path);
            ExecuteElf(  getFilesDir() + path);
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    private void ExecuteElf(String shell) {
        try {
            Runtime.getRuntime().exec(shell, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excpp(String path) {
        try {
            ExecuteElf("chmod 777 " + getFilesDir() + path);
            ExecuteElf(getFilesDir() + path);
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);
            ExecuteElf("su -c " + getFilesDir() + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Loadssets() {
        moveAssets(getFilesDir() + "/", "socs64");
        moveAssets(getFilesDir() + "/", "socu64");
        moveAssets(getFilesDir() + "/", "socs32");
        moveAssets(getFilesDir() + "/", "socu32");
        moveAssets(getFilesDir() + "/", "1");
        moveAssets(getFilesDir() + "/", "VNG");
        moveAssets(getFilesDir() + "/", "N5LA");
        moveAssets(getFilesDir() + "/", "3");
        moveAssets(getFilesDir() + "/", "4");
        moveAssets(getFilesDir() + "/", "5");
        moveAssets(getFilesDir() + "/", "6");
        moveAssets(getFilesDir() + "/", "7");
        moveAssets(getFilesDir() + "/", "8");
        moveAssets(getFilesDir() + "/", "TIGER");
    }

    private boolean moveAssets(String outPath, String fileName) {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("--Method--", "copyAssetsSingleFile: cannot create directory.");
                return false;
            }
        }
        try {
            InputStream inputStream = getAssets().open(fileName);
            File outFile = new File(file, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.get(), FloatService.class));
        stopService(new Intent(MainActivity.get(), Overlay.class));
        stopService(new Intent(MainActivity.get(), ToggleBullet.class));
        
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, getString(R.string.please_click_icon_logout_for_exit), Toast.LENGTH_SHORT).show();
    }

    public LinearProgressIndicator getProgresBar() {
        if (progres == null) {
            progres = findViewById(R.id.progress);
        }
        return progres;
    }

    public void doShowProgress(boolean indeterminate) {
        if (progres == null) {
            return;
        }
        progres.setVisibility(View.VISIBLE);
        progres.setIndeterminate(indeterminate);

        if (!indeterminate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                progres.setMin(0);
            }
            progres.setMax(100);
        }
    }

    public void doHideProgress() {
        if (progres == null) {
            return;
        }
        progres.setIndeterminate(true);
        progres.setVisibility(View.GONE);
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (FloatService.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void startPatcher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(MainActivity.get())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 123);
            } else {
                startFloater();
            }
        }
    }

private void startFloater() {
    try {
        startService(new Intent(MainActivity.get(), FloatService.class));
        loadAssets();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isServiceRunning()) {
                try {
                    startService(new Intent(MainActivity.get(), FloatService.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "فشل تشغيل الخدمة", Toast.LENGTH_SHORT).show();
                }
            }
        }, 500);
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "حدث خطأ أثناء بدء الفلوت", Toast.LENGTH_SHORT).show();
    }
}

    private void stopPatcher() {
        stopService(new Intent(MainActivity.get(), FloatService.class));
        stopService(new Intent(MainActivity.get(), Overlay.class));
        stopService(new Intent(MainActivity.get(), ToggleBullet.class));
    }

    public void loadAssets() {
        String filepath = Environment.getExternalStorageDirectory() + "/Android/data/.tyb";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            byte[] buffer = "DO NOT DELETE".getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String sockver = "socu64";
        if (kernel) {
            if (bitversi == 64) {
                sockver = "kernels64";
            } else if (bitversi == 32) {
                sockver = "kernels32";
            }
        } else {
            if (bitversi == 64) {
                sockver = "socu64";
            } else if (bitversi == 32) {
                sockver = "socu32";
            }
        }

        daemonPath = getFilesDir().toString() + "/" + sockver;
        socket = daemonPath;
        try {
            Runtime.getRuntime().exec("chmod 777 " + daemonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@Override
protected void onResume() {
    super.onResume();

    
    

    if (timerHandler != null && timerRunnable != null) {
        timerHandler.postDelayed(timerRunnable, 0);
    }
}

@Override
protected void onPause() {
    super.onPause();
    if (timerHandler != null && timerRunnable != null) {
        timerHandler.removeCallbacks(timerRunnable);
    }
}
    
public void bypass() {
    new Handler(Looper.getMainLooper()).postDelayed(() -> {
        try {
            if (bitversi == 64) {
                if (gameint >= 1 && gameint <= 4) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                } else if (gameint == 5) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");

                }
            } else if (bitversi == 32) {
                if (gameint >= 1 && gameint <= 4) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");

                } else if (gameint == 5) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "خطأ أثناء تشغيل الحماية", Toast.LENGTH_SHORT).show();
        }
    }, 3000);
}

    private void countTimerAccount() {
        Hari = findViewById(R.id.tv_d);
        Jam = findViewById(R.id.tv_h);
        Menit = findViewById(R.id.tv_m);
        Detik = findViewById(R.id.tv_s);

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeExpired = EXP();

                    if (TimeExpired == null || TimeExpired.isEmpty()) {
                        Hari.setText("00");
                        Jam.setText("00");
                        Menit.setText("00");
                        Detik.setText("00");

                        finishAffinity();
                        System.exit(0);
                        return;
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date expiryDate;
                    try {
                        expiryDate = dateFormat.parse(TimeExpired);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Hari.setText("00");
                        Jam.setText("00");
                        Menit.setText("00");
                        Detik.setText("00");

                        finishAffinity();
                        System.exit(0);
                        return;
                    }

                    long now = System.currentTimeMillis();
                    long distance = expiryDate.getTime() - now;

                    if (distance <= 0) {
                        Hari.setText("00");
                        Jam.setText("00");
                        Menit.setText("00");
                        Detik.setText("00");

                        finishAffinity();
                        System.exit(0);
                        return;
                    }

                    long days = distance / (24 * 60 * 60 * 1000);
                    long hours = (distance / (60 * 60 * 1000)) % 24;
                    long minutes = (distance / (60 * 1000)) % 60;
                    long seconds = (distance / 1000) % 60;

                    Hari.setText(String.format(Locale.getDefault(), "%02d", days));
                    Jam.setText(String.format(Locale.getDefault(), "%02d", hours));
                    Menit.setText(String.format(Locale.getDefault(), "%02d", minutes));
                    Detik.setText(String.format(Locale.getDefault(), "%02d", seconds));

                    timerHandler.postDelayed(this, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    Hari.setText("00");
                    Jam.setText("00");
                    Menit.setText("00");
                    Detik.setText("00");

                    finishAffinity();
                    System.exit(0);
                }
            }
        };

        timerHandler.postDelayed(timerRunnable, 0);
    }
}
// Internetten güncel offset ve durum bilgisini çeken fonksiyon
private void fetchUpdatesFromPanel() {
    String offsetUrl = "http://gamer.gd";

    RequestQueue queue = Volley.newRequestQueue(this);
    StringRequest request = new StringRequest(Request.Method.GET, offsetUrl,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String status = json.getString("status");

                    if (status.equals("online")) {
                        Toast.makeText(MainActivity.this, "YAHYA VIP: Sistem Guncel!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Hile Bakimda! Lütfen Bekleyin.", Toast.LENGTH_LONG).show();
                        finishAffinity();
                    }
                } catch (Exception e) {
                    Log.e("YAHYA_LOG", "Veri okuma hatasi");
                }
            }
        }, error -> Log.e("YAHYA_LOG", "Panel baglanti hatasi"));

    queue.add(request);
}
