package pubgm.loader.floating;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import pubgm.loader.activity.MainActivity;
import pubgm.loader.utils.FLog;
import pubgm.loader.utils.FPrefs;

import java.io.IOException;

public class Overlay extends Service {

    static {
        try {
            System.loadLibrary("livai");
        } catch (UnsatisfiedLinkError w) {
            FLog.error(w.getMessage());
        }
    }

    private native boolean getReady();
    private native void Close();
    public static native void DrawOn(ESPView espView, Canvas canvas);

    private WindowManager windowManager;
    private ESPView overlayView;
    private static Overlay Instance;

    @SuppressLint("StaticFieldLeak")
    public static Context ctx;

    public FPrefs getPref() {
        return FPrefs.with(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        Instance = this;
        windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        overlayView = new ESPView(ctx);
        DrawCanvas();
        Start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Close();
        if (overlayView != null) {
            try {
                windowManager.removeViewImmediate(overlayView);
            } catch (Exception ignored) {}
            overlayView = null;
        }
        Instance = null;
    }

    private void Start() {
        if (Instance != null) {
            Thread readyThread = new Thread(() -> {
                try {
                    getReady();
                } catch (Throwable t) {
                    FLog.error("getReady crash: " + t.getMessage());
                }
            });
            readyThread.setPriority(Thread.MAX_PRIORITY);
            readyThread.start();

            Thread shellThread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    if (MainActivity.socket != null && !MainActivity.socket.trim().isEmpty()) {
                        Shell(MainActivity.socket);
                    }
                } catch (Throwable e) {
                    FLog.error("Start crash: " + e.getMessage());
                }
            });
            shellThread.setPriority(Thread.NORM_PRIORITY);
            shellThread.start();
        }
    }

    private void DrawCanvas() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0,
                getNavigationBarHeight(),
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        try {
            if (overlayView.getParent() == null) {
                windowManager.addView(overlayView, params);
            }
        } catch (Exception e) {
            FLog.error("AddView crash: " + e.getMessage());
        }
    }

    private int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private void Shell(String str) {
        if (str == null) return;
        try {
            String cmd = str.trim();
            if (!cmd.isEmpty()) {
                Process proc = Runtime.getRuntime().exec(cmd);
                proc.waitFor();
            }
        } catch (IOException e) {
            FLog.error("Shell exec error: " + e.getMessage());
        } catch (Throwable t) {
            FLog.error("Shell crash: " + t.getMessage());
        }
    }

    public static boolean getConfig(String key) {
        SharedPreferences sp = ctx.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}