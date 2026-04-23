package pubgm.loader.floating;

import static pubgm.loader.activity.LoginActivity.USERKEY;
import static pubgm.loader.activity.MainActivity.Ischeck;
import static pubgm.loader.activity.MainActivity.bitversi;
import static pubgm.loader.activity.MainActivity.gameint;
import static pubgm.loader.activity.MainActivity.game;
import static pubgm.loader.activity.MainActivity.modestatus;
import android.os.Handler;
import static pubgm.loader.utils.ActivityCompat.toastImage;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.ImageView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import pubgm.loader.utils.FLog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.ToggleButton;
import pubgm.loader.R;
import pubgm.loader.activity.MainActivity;
import nl.joery.animatedbottombar.AnimatedBottomBar;
import com.topjohnwu.superuser.Shell;

import java.util.Locale;




public class FloatService extends Service {

    static {
        
            System.loadLibrary("livai");
        
    }

    Context ctx;
    private boolean hideitem = false;
    private View mainView;
    private PowerManager.WakeLock mWakeLock;
    private WindowManager windowManagerMainView;
    private WindowManager.LayoutParams paramsMainView;
    private LinearLayout layout_main_view;
    private RelativeLayout layout_icon_control_view;
    public static String typelogin;

    private void setLokasi(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bahasa", lang);
        editor.apply();

    }

    private void loadbahasa() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String bahasa = sharedPreferences.getString("bahasa", "");
        setLokasi(bahasa);
    }

    private static int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }

    private void StartAimBulletFloat() {
        startService(new Intent(getApplicationContext(), ToggleBullet.class));
    }

    private void StopAimBulletFloat() {
        stopService(new Intent(getApplicationContext(), ToggleBullet.class));
    }

    public native void SettingValue(int setting_code, boolean value);

    public native void SettingMemory(int setting_code, boolean value);

    public native void SettingAim(int setting_code, boolean value);

    public native void SkinHack(int setting_code);

    public native void RadarSize(int size);

    public native void Range(int range);

    public native void recoil(int recoil);

    public native void recoil2(int recoil);

    public native void recoil3(int recoil);

    public native void Target(int target);

    public native void AimBy(int aimby);

    public native void AimWhen(int aimwhen);

    public native void distances(int distances);

    public native void Bulletspeed(int bulletspeed);

    public native void WideView(int wideview);

    public native void AimingSpeed(int aimingspeed);

    public native void Smoothness(int smoothness);

    public native void TouchSize(int touchsize);

    public native void TouchPosX(int touchposx);

    public native void TouchPosY(int touchposy);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        InitShowMainView();
        loadbahasa();
        
     }

private void InitShowMainView() {
    mainView = LayoutInflater.from(this).inflate(R.layout.float_service, null);
    paramsMainView = getparams();
    windowManagerMainView = (WindowManager) getSystemService(WINDOW_SERVICE);
    windowManagerMainView.addView(mainView, paramsMainView);
    layout_icon_control_view = mainView.findViewById(R.id.layout_icon_control_view);
    layout_main_view = mainView.findViewById(R.id.layout_main_view);

    LinearLayout layout_view = mainView.findViewById(R.id.layout_view_menu);
    layout_view.setOnTouchListener(onTouchListener());

    initDesign();
    visual(mainView);
    aimbot(mainView);
    items(mainView);
    memory(mainView);
}

public void initDesign() {
    AnimatedBottomBar espTabBar = this.mainView.findViewById(R.id.esp_tabbar);
    final LinearLayout menu1 = mainView.findViewById(R.id.menuf1);
    final LinearLayout menu2 = mainView.findViewById(R.id.menuf2);
    final LinearLayout menu3 = mainView.findViewById(R.id.menuf3);
    final LinearLayout menu4 = mainView.findViewById(R.id.menuf4);

    ImageView closeBtn = this.mainView.findViewById(R.id.close_btn);
    closeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FloatService.this.layout_main_view.setVisibility(View.GONE);
            FloatService.this.layout_icon_control_view.setVisibility(View.VISIBLE);
        }
    });

    espTabBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
        @Override
        public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab,
                                  int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
            switch (newTab.getId()) {
                case R.id.player:
                    menu1.setVisibility(View.VISIBLE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    break;
                case R.id.navItem:
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.VISIBLE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    break;
                case R.id.navAim:
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.VISIBLE);
                    menu4.setVisibility(View.GONE);
                    break;
                case R.id.navSdk:
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onTabReselected(int index, @NonNull AnimatedBottomBar.Tab tab) {

        }
    });
}


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = layout_icon_control_view;
            final View expandedView = layout_main_view;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = paramsMainView.x;
                        initialY = paramsMainView.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        paramsMainView.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsMainView.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManagerMainView.updateViewLayout(mainView, paramsMainView);
                        return true;

                }
                return false;
            }
        };
    }

    private boolean isViewCollapsed() {
        return mainView == null || layout_icon_control_view.getVisibility() == View.VISIBLE;
    }

    private WindowManager.LayoutParams getparams() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getLayoutType(),
                getFlagsType(),
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        return params;
    }

    private int getFlagsType() {
        int LAYOUT_FLAG = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        return LAYOUT_FLAG;
    }

       @Override
    public void onDestroy() {
        super.onDestroy();
        new Thread(new Runnable(){
                @Override
                public void run() {

                }
            }).start();
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
        
        if (mainView != null){
            windowManagerMainView.removeView(mainView);
		}
		
        
    }
    

    boolean getConfig(String key) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    private int getFps() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("fps", 100);
    }

    private void setFps(int fps) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("fps", fps);
        ed.apply();
    }

    private void setValue(String key, boolean b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(key, b);
        ed.apply();

    }

    private void setradarSize(int radarSize) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("radarSize", radarSize);
        ed.apply();
    }

    private int getradarSize() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("radarSize", 0);
    }

    private int getrangeAim() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getrangeAim", 0);
    }

    private void getrangeAim(int getrangeAim) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getrangeAim", getrangeAim);
        ed.apply();
    }

    private int getDistances() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("Distances", 0);
    }

    private void setDistances(int Distances) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("Distances", Distances);
        ed.apply();
    }

    private int getrecoilAim() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getrecoilAim", 0);
    }

    private void getrecoilAim(int getrecoilAim) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getrecoilAim", getrecoilAim);
        ed.apply();
    }

    private int getrecoilAim2() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getrecoilAim2", 0);
    }

    private void getrecoilAim2(int getrecoilAim) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getrecoilAim2", getrecoilAim);
        ed.apply();
    }

    private int getrecoilAim3() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getrecoilAim2", 0);
    }

    private void getrecoilAim3(int getrecoilAim) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getrecoilAim2", getrecoilAim);
        ed.apply();
    }

    private int getbulletspeedAim() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getbulletspeedAim", 0);
    }

    private void getbulletspeedAim(int getbulletspeedAim) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getbulletspeedAim", getbulletspeedAim);
        ed.apply();
    }

    private int getwideview() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("getwideview", 0);
    }

    private void getwideview(int getwideview) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("getwideview", getwideview);
        ed.apply();
    }

    void setTouchSize(int touchsize) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("touchsize", touchsize);
        ed.apply();
    }

    int getTouchSize() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("touchsize", 600);
    }

    void setTouchPosX(int posX) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("posX", posX);
        ed.apply();
    }

    int getTouchPosX() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("posX", 650);
    }

    void setTouchPosY(int posY) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("posY", posY);
        ed.apply();
    }

    int getTouchPosY() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("posY", 1400);
    }

    private boolean getConfigitem(String key, boolean a) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getBoolean(key, a);
    }

    private void setConfigitem(String a, boolean b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(a, b);
        ed.apply();
    }

    private int getEspValue(String a, int b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt(a, b);
    }

    private void setEspValue(String a, int b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(a, b);
        ed.apply();
    }

    private int getAimSpeed() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("AimingSpeed", 18);
    }

    private void setAimSpeed(int AimingSpeed) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("AimingSpeed", AimingSpeed);
        ed.apply();
    }

    private int getSmoothness() {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("smoothness", 20);
    }

    private void setSmoothness(int smoothness) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("smoothness", smoothness);
        ed.apply();
    }

    public void espvisual(final ToggleButton a, final int b) {
        a.setChecked(getConfig((String) a.getText()));
        SettingValue(b, getConfig((String) a.getText()));
        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton p1, boolean p2) {
                setValue(String.valueOf(a.getText()), a.isChecked());
                SettingValue(b, a.isChecked());
            }
        });
    }

    public void setaim(final Switch a, final int b) {
        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton p1, boolean isChecked) {
                setValue(String.valueOf(a.getText()), a.isChecked());
                SettingAim(b, a.isChecked());
            }
        });
    }

    public void vehicless(final ToggleButton ToggleButton) {
        ToggleButton.setChecked(getConfig((String) ToggleButton.getText()));
        ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue(String.valueOf(ToggleButton.getText()), ToggleButton.isChecked());
            }
        });
    }

    public void itemss(final ToggleButton ToggleButton) {
        ToggleButton.setChecked(getConfig((String) ToggleButton.getText()));
        ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValue(String.valueOf(ToggleButton.getText()), ToggleButton.isChecked());
            }
        });
    }
    public void memory(final Switch a, final int b) {
        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton p1, boolean isChecked) {
                setValue(String.valueOf(a.getText()), a.isChecked());
                SettingMemory(b, a.isChecked());
            }
        });
    }

    void setupSeekBar(final SeekBar seekBar, final TextView textView, final int initialValue, final Runnable onChangeFunction) {
        seekBar.setProgress(initialValue);
        textView.setText(String.valueOf(initialValue));
        onChangeFunction.run();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
                onChangeFunction.run();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void DrawESP() {
        if (Shell.rootAccess()) {
            FLog.info("Root granted");
            MainActivity.socket = "su -c " + MainActivity.daemonPath;
            startService(new Intent(this, Overlay.class));
        } else {
            FLog.info("Root not granted");
            MainActivity.socket = MainActivity.daemonPath;
            startService(new Intent(MainActivity.get(), Overlay.class));
        }
    }

    public void Exec(String path, String toast) {
        try {
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);
            ExecuteElf("su -c " + getFilesDir() + path);
            ExecuteElf("chmod 777 " + getFilesDir() + path);
            ExecuteElf(getFilesDir() + path);
            toastImage(R.drawable.ic_check, toast);
        } catch (Exception e) {
        }
    }
    public void excpp(String path) {
        try {
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);
            ExecuteElf("su -c " + getFilesDir() + path);
            ExecuteElf("chmod 777 " + getFilesDir() + path);
            ExecuteElf(getFilesDir() + path);
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

    

    private void StopESP() {
        stopService(new Intent(this, Overlay.class));
    }
    
    
    
    private void visual(View visual) {
    final Switch drawesp = visual.findViewById(R.id.isenableesp);
    final Switch iscrash = visual.findViewById(R.id.iscrash);
    final Switch menuisland = visual.findViewById(R.id.menuisland);
    final Switch menuloho = visual.findViewById(R.id.menuloho);
    final LinearLayout menucrash = visual.findViewById(R.id.menucrash);
    
        if (Shell.rootAccess()) {
            menuloho.setVisibility(View.VISIBLE);
        } else {
            menuloho.setVisibility(View.VISIBLE);
        }
        
        drawesp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DrawESP();
                }else{
                    StopESP();
                    StopAimBulletFloat();
                }
            }
        });

    menuloho.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bitversi == 64) {
                if (gameint == 5) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                } else {
                            Exec("/akram", "✅️ تم تفعيل الحماية");

                }
            } else if (bitversi == 32) {
                if (gameint == 5) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");

                } else {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                }
            }
        }
    });

    menuisland.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            if (bitversi == 64) {
                if (gameint == 5) {

                            Exec("/akram", "✅️ تم تفعيل الحماية");

                } else {
                            Exec("/akram", "✅️ تم تفعيل الحماية");

                }
            }
        }else{
            if (bitversi == 64) {
                if (gameint == 5) {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                } else {
                            Exec("/akram", "✅️ تم تفعيل الحماية");
                }
            }
        }
    }
});
   
        final ToggleButton fps3 = visual.findViewById(R.id.fps60);
        final ToggleButton fps4 = visual.findViewById(R.id.fps120);
        final ToggleButton fps5 = visual.findViewById(R.id.fps130);

        int CheckFps = getFps();
        if (CheckFps == 60) {
            fps3.setChecked(true);
            ESPView.sleepTime = 1000 / 60;
        } else if (CheckFps == 90) {
            fps4.setChecked(true);
            ESPView.sleepTime = 1000 / 90;
        } else if (CheckFps == 120) {
            fps5.setChecked(true);
            ESPView.sleepTime = 1000 / 120;
        } else {
            fps3.setChecked(true);
            ESPView.sleepTime = 1000 / 60;
        }


        fps3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fps4.setChecked(false);
                    fps5.setChecked(false);
                    setFps(60);
                    ESPView.ChangeFps(60);
                }
            }
        });

        fps4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fps3.setChecked(false);
                    fps5.setChecked(false);
                    setFps(90);
                    ESPView.ChangeFps(90);
                }
            }
        });

        fps5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fps3.setChecked(false);
                    fps4.setChecked(false);
                    setFps(120);
                    ESPView.ChangeFps(120);
                }
            }
        });

        SharedPreferences sharedPreferences = visual.getContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
        String bypassmode = sharedPreferences.getString("bypassmode", "manual");

    if (Shell.rootAccess()) {
        menuloho.setVisibility(View.VISIBLE);
        visual.findViewById(R.id.menucrash).setVisibility(View.GONE);
    } else {
        menuloho.setVisibility(View.VISIBLE);
        visual.findViewById(R.id.menucrash).setVisibility(View.GONE);
    }
final ToggleButton isLine = visual.findViewById(R.id.isLine);
espvisual(isLine, 2);
final ToggleButton isBox = visual.findViewById(R.id.isBox);
espvisual(isBox, 3);
final ToggleButton isSkeleton = visual.findViewById(R.id.isSkeleton);
espvisual(isSkeleton, 4);
final ToggleButton isDistance = visual.findViewById(R.id.isDistance);
espvisual(isDistance, 5);
final ToggleButton isHealth = visual.findViewById(R.id.isHealth);
espvisual(isHealth, 6);
final ToggleButton isName = visual.findViewById(R.id.isName);
espvisual(isName, 7);
final ToggleButton isHead = visual.findViewById(R.id.isHead);
espvisual(isHead, 8);
final ToggleButton isWeaponIcon = visual.findViewById(R.id.isWeaponIcon);
espvisual(isWeaponIcon, 16);
final ToggleButton isNoBot = visual.findViewById(R.id.isNoBot);
espvisual(isNoBot, 15);
    }

    private void items(View items) {
        LinearLayout menui1 = items.findViewById(R.id.items1);
        LinearLayout menui2 = items.findViewById(R.id.lyvehicle);
        View bottomi1 = items.findViewById(R.id.bottomi1);
        View bottomi2 = items.findViewById(R.id.bottomi2);
        LinearLayout navi1 = mainView.findViewById(R.id.navitems);
        LinearLayout navi2 = mainView.findViewById(R.id.navvehicle);
        
        navi1.setOnClickListener(v -> {
            menui1.setVisibility(View.VISIBLE);
            menui2.setVisibility(View.GONE);
            bottomi1.setVisibility(View.VISIBLE);
            bottomi2.setVisibility(View.GONE);
        });

        navi2.setOnClickListener(v -> {
            menui1.setVisibility(View.GONE);
            menui2.setVisibility(View.VISIBLE);
            bottomi1.setVisibility(View.GONE);
            bottomi2.setVisibility(View.VISIBLE);
        });
        final ToggleButton Desert = items.findViewById(R.id.Desert);
        itemss(Desert);

        final ToggleButton M416 = items.findViewById(R.id.m416);
        itemss(M416);

        final ToggleButton QBZ = items.findViewById(R.id.QBZ);
        itemss(QBZ);

        final ToggleButton SCARL = items.findViewById(R.id.SCARL);
        itemss(SCARL);

        final ToggleButton AKM = items.findViewById(R.id.AKM);
        itemss(AKM);

        final ToggleButton M16A4 = items.findViewById(R.id.M16A4);
        itemss(M16A4);

        final ToggleButton AUG = items.findViewById(R.id.AUG);
        itemss(AUG);

        final ToggleButton M249 = items.findViewById(R.id.M249);
        itemss(M249);

        final ToggleButton Groza = items.findViewById(R.id.Groza);
        itemss(Groza);

        final ToggleButton MK47 = items.findViewById(R.id.MK47);
        itemss(MK47);

        final ToggleButton M762 = items.findViewById(R.id.M762);
        itemss(M762);

        final ToggleButton G36C = items.findViewById(R.id.G36C);
        itemss(G36C);

        final ToggleButton DP28 = items.findViewById(R.id.DP28);
        itemss(DP28);

        final ToggleButton MG3 = items.findViewById(R.id.MG3);
        itemss(MG3);

        final ToggleButton FAMAS = items.findViewById(R.id.FAMAS);
        itemss(FAMAS);


        final ToggleButton HoneyBadger = items.findViewById(R.id.HoneyBadger);
        itemss(HoneyBadger);


        final ToggleButton AC32 = items.findViewById(R.id.AC32);
        itemss(AC32);


        //SMG

        final ToggleButton UMP = items.findViewById(R.id.UMP);
        itemss(UMP);

        final ToggleButton bizon = items.findViewById(R.id.bizon);
        itemss(bizon);

        final ToggleButton MP5K = items.findViewById(R.id.MP5K);
        itemss(MP5K);

        final ToggleButton TommyGun = items.findViewById(R.id.TommyGun);
        itemss(TommyGun);

        final ToggleButton vector = items.findViewById(R.id.vector);
        itemss(vector);

        final ToggleButton P90 = items.findViewById(R.id.P90);
        itemss(P90);

        final ToggleButton UZI = items.findViewById(R.id.UZI);
        itemss(UZI);


        //Snipers

        final ToggleButton AWM = items.findViewById(R.id.AWM);
        itemss(AWM);

        final ToggleButton QBU = items.findViewById(R.id.QBU);
        itemss(QBU);

        final ToggleButton Kar98k = items.findViewById(R.id.Kar98k);
        itemss(Kar98k);

        final ToggleButton M24 = items.findViewById(R.id.M24);
        itemss(M24);

        final ToggleButton SLR = items.findViewById(R.id.SLR);
        itemss(SLR);

        final ToggleButton SKS = items.findViewById(R.id.SKS);
        itemss(SKS);

        final ToggleButton MK14 = items.findViewById(R.id.MK14);
        itemss(MK14);

        final ToggleButton Mini14 = items.findViewById(R.id.Mini14);
        itemss(Mini14);

        final ToggleButton Mosin = items.findViewById(R.id.Mosin);
        itemss(Mosin);

        final ToggleButton VSS = items.findViewById(R.id.VSS);
        itemss(VSS);

        final ToggleButton AMR = items.findViewById(R.id.AMR);
        itemss(AMR);

        final ToggleButton Win94 = items.findViewById(R.id.Win94);
        itemss(Win94);

        final ToggleButton MK12 = items.findViewById(R.id.MK12);
        itemss(MK12);

        //Scopes

        final ToggleButton x2 = items.findViewById(R.id.x2);
        itemss(x2);

        final ToggleButton x3 = items.findViewById(R.id.x3);
        itemss(x3);

        final ToggleButton x4 = items.findViewById(R.id.x4);
        itemss(x4);

        final ToggleButton x6 = items.findViewById(R.id.x6);
        itemss(x6);

        final ToggleButton x8 = items.findViewById(R.id.x8);
        itemss(x8);

        final ToggleButton canted = items.findViewById(R.id.canted);
        itemss(canted);

        final ToggleButton hollow = items.findViewById(R.id.hollow);
        itemss(hollow);

        final ToggleButton reddot = items.findViewById(R.id.reddot);
        itemss(reddot);

        //Armor

        final ToggleButton bag1 = items.findViewById(R.id.bag1);
        itemss(bag1);

        final ToggleButton bag2 = items.findViewById(R.id.bag2);
        itemss(bag2);

        final ToggleButton bag3 = items.findViewById(R.id.bag3);
        itemss(bag3);

        final ToggleButton helmet1 = items.findViewById(R.id.helmet1);
        itemss(helmet1);

        final ToggleButton helmet2 = items.findViewById(R.id.helmet2);
        itemss(helmet2);

        final ToggleButton helmet3 = items.findViewById(R.id.helmet3);
        itemss(helmet3);

        final ToggleButton vest1 = items.findViewById(R.id.vest1);
        itemss(vest1);

        final ToggleButton vest2 = items.findViewById(R.id.vest2);
        itemss(vest2);

        final ToggleButton vest3 = items.findViewById(R.id.vest3);
        itemss(vest3);

        //Ammo
        final ToggleButton a9 = items.findViewById(R.id.a9);
        itemss(a9);

        final ToggleButton a7 = items.findViewById(R.id.a7);
        itemss(a7);

        final ToggleButton a5 = items.findViewById(R.id.a5);
        itemss(a5);

        final ToggleButton a300 = items.findViewById(R.id.a300);
        itemss(a300);

        final ToggleButton a45 = items.findViewById(R.id.a45);
        itemss(a45);

        final ToggleButton Arrow = items.findViewById(R.id.arrow);
        itemss(Arrow);

        final ToggleButton BMG50 = items.findViewById(R.id.BMG50);
        itemss(BMG50);

        final ToggleButton a12 = items.findViewById(R.id.a12);
        itemss(a12);

        //Shotgun
        final ToggleButton DBS = items.findViewById(R.id.DBS);
        itemss(DBS);

        final ToggleButton NS2000 = items.findViewById(R.id.NS2000);
        itemss(NS2000);

        final ToggleButton S686 = items.findViewById(R.id.S686);
        itemss(S686);

        final ToggleButton sawed = items.findViewById(R.id.sawed);
        itemss(sawed);

        final ToggleButton M1014 = items.findViewById(R.id.M1014);
        itemss(M1014);

        final ToggleButton S1897 = items.findViewById(R.id.S1897);
        itemss(S1897);

        final ToggleButton S12K = items.findViewById(R.id.S12K);
        itemss(S12K);

        //Throwables
        final ToggleButton grenade = items.findViewById(R.id.grenade);
        itemss(grenade);

        final ToggleButton molotov = items.findViewById(R.id.molotov);
        itemss(molotov);

        final ToggleButton stun = items.findViewById(R.id.stun);
        itemss(stun);

        final ToggleButton smoke = items.findViewById(R.id.smoke);
        itemss(smoke);

        //Medics

        final ToggleButton painkiller = items.findViewById(R.id.painkiller);
        itemss(painkiller);

        final ToggleButton medkit = items.findViewById(R.id.medkit);
        itemss(medkit);

        final ToggleButton firstaid = items.findViewById(R.id.firstaid);
        itemss(firstaid);

        final ToggleButton bandage = items.findViewById(R.id.bandage);
        itemss(bandage);

        final ToggleButton injection = items.findViewById(R.id.injection);
        itemss(injection);

        final ToggleButton energydrink = items.findViewById(R.id.energydrink);
        itemss(energydrink);

        //Handy
        final ToggleButton Pan = items.findViewById(R.id.Pan);
        itemss(Pan);

        final ToggleButton Crowbar = items.findViewById(R.id.Crowbar);
        itemss(Crowbar);

        final ToggleButton Sickle = items.findViewById(R.id.Sickle);
        itemss(Sickle);

        final ToggleButton Machete = items.findViewById(R.id.Machete);
        itemss(Machete);

        final ToggleButton Crossbow = items.findViewById(R.id.Crossbow);
        itemss(Crossbow);

        final ToggleButton Explosive = items.findViewById(R.id.Explosive);
        itemss(Explosive);

        //Pistols
        final ToggleButton P92 = items.findViewById(R.id.P92);
        itemss(P92);

        final ToggleButton R45 = items.findViewById(R.id.R45);
        itemss(R45);

        final ToggleButton P18C = items.findViewById(R.id.P18C);
        itemss(P18C);

        final ToggleButton P1911 = items.findViewById(R.id.P1911);
        itemss(P1911);

        final ToggleButton R1895 = items.findViewById(R.id.R1895);
        itemss(R1895);

        final ToggleButton Scorpion = items.findViewById(R.id.Scorpion);
        itemss(Scorpion);

        //Other
        final ToggleButton CheekPad = items.findViewById(R.id.CheekPad);
        itemss(CheekPad);

        final ToggleButton Choke = items.findViewById(R.id.Choke);
        itemss(Choke);

        final ToggleButton CompensatorSMG = items.findViewById(R.id.CompensatorSMG);
        itemss(CompensatorSMG);


        final ToggleButton FlashHiderSMG = items.findViewById(R.id.FlashHiderSMG);
        itemss(FlashHiderSMG);


        final ToggleButton FlashHiderAr = items.findViewById(R.id.FlashHiderAr);
        itemss(FlashHiderAr);

        final ToggleButton ArCompensator = items.findViewById(R.id.ArCompensator);
        itemss(ArCompensator);

        final ToggleButton TacticalStock = items.findViewById(R.id.TacticalStock);
        itemss(TacticalStock);

        final ToggleButton Duckbill = items.findViewById(R.id.Duckbill);
        itemss(Duckbill);

        final ToggleButton FlashHiderSniper = items.findViewById(R.id.FlashHiderSniper);
        itemss(FlashHiderSniper);

        final ToggleButton SuppressorSMG = items.findViewById(R.id.SuppressorSMG);
        itemss(SuppressorSMG);

        final ToggleButton HalfGrip = items.findViewById(R.id.HalfGrip);
        itemss(HalfGrip);

        final ToggleButton StockMicroUZI = items.findViewById(R.id.StockMicroUZI);
        itemss(StockMicroUZI);

        final ToggleButton SuppressorSniper = items.findViewById(R.id.SuppressorSniper);
        itemss(SuppressorSniper);

        final ToggleButton SuppressorAr = items.findViewById(R.id.SuppressorAr);
        itemss(SuppressorAr);

        final ToggleButton SniperCompensator = items.findViewById(R.id.SniperCompensator);
        itemss(SniperCompensator);

        final ToggleButton ExQdSniper = items.findViewById(R.id.ExQdSniper);
        itemss(ExQdSniper);

        final ToggleButton QdSMG = items.findViewById(R.id.QdSMG);
        itemss(QdSMG);

        final ToggleButton ExSMG = items.findViewById(R.id.ExSMG);
        itemss(ExSMG);

        final ToggleButton QdSniper = items.findViewById(R.id.QdSniper);
        itemss(QdSniper);

        final ToggleButton ExSniper = items.findViewById(R.id.ExSniper);
        itemss(ExSniper);

        final ToggleButton ExAr = items.findViewById(R.id.ExAr);
        itemss(ExAr);

        final ToggleButton ExQdAr = items.findViewById(R.id.ExQdAr);
        itemss(ExQdAr);

        final ToggleButton QdAr = items.findViewById(R.id.QdAr);
        itemss(QdAr);

        final ToggleButton ExQdSMG = items.findViewById(R.id.ExQdSMG);
        itemss(ExQdSMG);

        final ToggleButton QuiverCrossBow = items.findViewById(R.id.QuiverCrossBow);
        itemss(QuiverCrossBow);

        final ToggleButton BulletLoop = items.findViewById(R.id.BulletLoop);
        itemss(BulletLoop);

        final ToggleButton ThumbGrip = items.findViewById(R.id.ThumbGrip);
        itemss(ThumbGrip);

        final ToggleButton LaserSight = items.findViewById(R.id.LaserSight);
        itemss(LaserSight);

        final ToggleButton AngledGrip = items.findViewById(R.id.AngledGrip);
        itemss(AngledGrip);

        final ToggleButton LightGrip = items.findViewById(R.id.LightGrip);
        itemss(LightGrip);

        final ToggleButton VerticalGrip = items.findViewById(R.id.VerticalGrip);
        itemss(VerticalGrip);

        final ToggleButton GasCan = items.findViewById(R.id.GasCan);
        itemss(GasCan);

        //Vehicle
        final ToggleButton UTV = items.findViewById(R.id.UTV);
        vehicless(UTV);

        final ToggleButton Buggy = items.findViewById(R.id.Buggy);
        vehicless(Buggy);

        final ToggleButton UAZ = items.findViewById(R.id.UAZ);
        vehicless(UAZ);

        final ToggleButton Trike = items.findViewById(R.id.Trike);
        vehicless(Trike);

        final ToggleButton Bike = items.findViewById(R.id.Bike);
        vehicless(Bike);

        final ToggleButton Dacia = items.findViewById(R.id.Dacia);
        vehicless(Dacia);

        final ToggleButton Jet = items.findViewById(R.id.Jet);
        vehicless(Jet);

        final ToggleButton Boat = items.findViewById(R.id.Boat);
        vehicless(Boat);

        final ToggleButton Scooter = items.findViewById(R.id.Scooter);
        vehicless(Scooter);

        final ToggleButton Bus = items.findViewById(R.id.Bus);
        vehicless(Bus);

        final ToggleButton Mirado = items.findViewById(R.id.Mirado);
        vehicless(Mirado);

        final ToggleButton Rony = items.findViewById(R.id.Rony);
        vehicless(Rony);

        final ToggleButton Snowbike = items.findViewById(R.id.Snowbike);
        vehicless(Snowbike);

        final ToggleButton Snowmobile = items.findViewById(R.id.Snowmobile);
        vehicless(Snowmobile);

        final ToggleButton Tempo = items.findViewById(R.id.Tempo);
        vehicless(Tempo);

        final ToggleButton Truck = items.findViewById(R.id.Truck);
        vehicless(Truck);

        final ToggleButton MonsterTruck = items.findViewById(R.id.MonsterTruck);
        vehicless(MonsterTruck);

        final ToggleButton BRDM = items.findViewById(R.id.BRDM);
        vehicless(BRDM);

        final ToggleButton ATV = items.findViewById(R.id.ATV);
        vehicless(ATV);

        final ToggleButton LadaNiva = items.findViewById(R.id.LadaNiva);
        vehicless(LadaNiva);

        final ToggleButton Motorglider = items.findViewById(R.id.Motorglider);
        vehicless(Motorglider);

        final ToggleButton CoupeRB = items.findViewById(R.id.CoupeRB);
        vehicless(CoupeRB);

        //Special
        final ToggleButton Crate = items.findViewById(R.id.Crate);
        itemss(Crate);

        final ToggleButton Airdrop = items.findViewById(R.id.Airdrop);
        itemss(Airdrop);

        final ToggleButton DropPlane = items.findViewById(R.id.DropPlane);
        itemss(DropPlane);

        final ToggleButton FlareGun = items.findViewById(R.id.FlareGun);
        itemss(FlareGun);
        
        

        final LinearLayout checkall = mainView.findViewById(R.id.itemscheckall);
        final LinearLayout noneall = mainView.findViewById(R.id.itemsblockall);
        final LinearLayout checkallv = mainView.findViewById(R.id.mobilscheckall);
        final LinearLayout noneallv = mainView.findViewById(R.id.mobilsblockall);

        checkallv.setOnClickListener(v -> {
            Buggy.setChecked(true);
            UAZ.setChecked(true);
            Trike.setChecked(true);
            Bike.setChecked(true);
            Dacia.setChecked(true);
            Jet.setChecked(true);
            Boat.setChecked(true);
            Scooter.setChecked(true);
            Bus.setChecked(true);
            Mirado.setChecked(true);
            Rony.setChecked(true);
            Snowbike.setChecked(true);
            Snowmobile.setChecked(true);
            Tempo.setChecked(true);
            Truck.setChecked(true);
            MonsterTruck.setChecked(true);
            BRDM.setChecked(true);
            LadaNiva.setChecked(true);
            ATV.setChecked(true);
            UTV.setChecked(true);
            CoupeRB.setChecked(true);
            Motorglider.setChecked(true);
        });

        noneallv.setOnClickListener(v -> {
            Buggy.setChecked(false);
            UAZ.setChecked(false);
            Trike.setChecked(false);
            Bike.setChecked(false);
            Dacia.setChecked(false);
            Jet.setChecked(false);
            Boat.setChecked(false);
            Scooter.setChecked(false);
            Bus.setChecked(false);
            Mirado.setChecked(false);
            Rony.setChecked(false);
            Snowbike.setChecked(false);
            Snowmobile.setChecked(false);
            Tempo.setChecked(false);
            Truck.setChecked(false);
            MonsterTruck.setChecked(false);
            BRDM.setChecked(false);
            LadaNiva.setChecked(false);
            ATV.setChecked(false);
            UTV.setChecked(false);
            CoupeRB.setChecked(false);
            Motorglider.setChecked(false);
        });

        checkall.setOnClickListener(v -> {

            /* Other */
            Crate.setChecked(true);
            Airdrop.setChecked(true);
            DropPlane.setChecked(true);
            CheekPad.setChecked(true);
            Choke.setChecked(true);


            /* Scope */
            canted.setChecked(true);
            reddot.setChecked(true);
            hollow.setChecked(true);
            x2.setChecked(true);
            x3.setChecked(true);
            x4.setChecked(true);
            x6.setChecked(true);
            x8.setChecked(true);

            /* Weapon */
            AWM.setChecked(true);
            QBU.setChecked(true);
            SLR.setChecked(true);
            SKS.setChecked(true);
            Mini14.setChecked(true);
            M24.setChecked(true);
            Kar98k.setChecked(true);
            VSS.setChecked(true);
            Win94.setChecked(true);
            AUG.setChecked(true);
            M762.setChecked(true);
            SCARL.setChecked(true);
            M416.setChecked(true);
            M16A4.setChecked(true);
            MK47.setChecked(true);
            G36C.setChecked(true);
            QBZ.setChecked(true);
            AKM.setChecked(true);
            Groza.setChecked(true);
            S12K.setChecked(true);
            DBS.setChecked(true);
            S686.setChecked(true);
            S1897.setChecked(true);
            sawed.setChecked(true);
            TommyGun.setChecked(true);
            MP5K.setChecked(true);
            vector.setChecked(true);
            UZI.setChecked(true);
            R1895.setChecked(true);
            Explosive.setChecked(true);
            P92.setChecked(true);
            P18C.setChecked(true);
            R45.setChecked(true);
            P1911.setChecked(true);
            Desert.setChecked(true);
            Sickle.setChecked(true);
            Machete.setChecked(true);
            Pan.setChecked(true);
            MK14.setChecked(true);
            Scorpion.setChecked(true);

            Mosin.setChecked(true);
            MK12.setChecked(true);
            AMR.setChecked(true);

            M1014.setChecked(true);
            NS2000.setChecked(true);
            P90.setChecked(true);
            MG3.setChecked(true);
            AC32.setChecked(true);
            HoneyBadger.setChecked(true);
            FAMAS.setChecked(true);

            /* Ammo */
            a45.setChecked(true);
            a9.setChecked(true);
            a7.setChecked(true);
            a300.setChecked(true);
            a5.setChecked(true);
            BMG50.setChecked(true);
            a12.setChecked(true);

            SniperCompensator.setChecked(true);
            DP28.setChecked(true);
            M249.setChecked(true);
            grenade.setChecked(true);
            smoke.setChecked(true);
            molotov.setChecked(true);
            painkiller.setChecked(true);
            injection.setChecked(true);
            energydrink.setChecked(true);
            firstaid.setChecked(true);
            bandage.setChecked(true);
            medkit.setChecked(true);
            FlareGun.setChecked(true);
            UMP.setChecked(true);
            bizon.setChecked(true);
            CompensatorSMG.setChecked(true);
            FlashHiderSMG.setChecked(true);
            FlashHiderAr.setChecked(true);
            ArCompensator.setChecked(true);
            TacticalStock.setChecked(true);
            Duckbill.setChecked(true);
            FlashHiderSniper.setChecked(true);
            SuppressorSMG.setChecked(true);
            HalfGrip.setChecked(true);
            StockMicroUZI.setChecked(true);
            SuppressorSniper.setChecked(true);
            SuppressorAr.setChecked(true);
            ExQdSniper.setChecked(true);
            QdSMG.setChecked(true);
            ExSMG.setChecked(true);
            QdSniper.setChecked(true);
            ExSniper.setChecked(true);
            ExAr.setChecked(true);
            ExQdAr.setChecked(true);
            QdAr.setChecked(true);
            ExQdSMG.setChecked(true);
            QuiverCrossBow.setChecked(true);
            BulletLoop.setChecked(true);
            ThumbGrip.setChecked(true);
            LaserSight.setChecked(true);
            AngledGrip.setChecked(true);
            LightGrip.setChecked(true);
            VerticalGrip.setChecked(true);
            GasCan.setChecked(true);
            Arrow.setChecked(true);
            Crossbow.setChecked(true);
            bag1.setChecked(true);
            bag2.setChecked(true);
            bag3.setChecked(true);
            helmet1.setChecked(true);
            helmet2.setChecked(true);
            helmet3.setChecked(true);
            vest1.setChecked(true);
            vest2.setChecked(true);
            vest3.setChecked(true);
            stun.setChecked(true);
            Crowbar.setChecked(true);
        });

        noneall.setOnClickListener(v -> {
            /* Other */
            Crate.setChecked(false);
            Airdrop.setChecked(false);
            DropPlane.setChecked(false);
            CheekPad.setChecked(false);
            Choke.setChecked(false);


            /* Scope */
            canted.setChecked(false);
            reddot.setChecked(false);
            hollow.setChecked(false);
            x2.setChecked(false);
            x3.setChecked(false);
            x4.setChecked(false);
            x6.setChecked(false);
            x8.setChecked(false);

            /* Weapon */
            AWM.setChecked(false);
            QBU.setChecked(false);
            SLR.setChecked(false);
            SKS.setChecked(false);
            Mini14.setChecked(false);
            M24.setChecked(false);
            Kar98k.setChecked(false);
            VSS.setChecked(false);
            Win94.setChecked(false);
            AUG.setChecked(false);
            M762.setChecked(false);
            SCARL.setChecked(false);
            M416.setChecked(false);
            M16A4.setChecked(false);
            MK47.setChecked(false);
            G36C.setChecked(false);
            QBZ.setChecked(false);
            AKM.setChecked(false);
            Groza.setChecked(false);
            S12K.setChecked(false);
            DBS.setChecked(false);
            S686.setChecked(false);
            S1897.setChecked(false);
            sawed.setChecked(false);
            TommyGun.setChecked(false);
            MP5K.setChecked(false);
            vector.setChecked(false);
            UZI.setChecked(false);
            R1895.setChecked(false);
            Explosive.setChecked(false);
            P92.setChecked(false);
            P18C.setChecked(false);
            R45.setChecked(false);
            P1911.setChecked(false);
            Desert.setChecked(false);
            Sickle.setChecked(false);
            Machete.setChecked(false);
            Pan.setChecked(false);
            MK14.setChecked(false);
            Scorpion.setChecked(false);

            Mosin.setChecked(false);
            MK12.setChecked(false);
            AMR.setChecked(false);

            M1014.setChecked(false);
            NS2000.setChecked(false);
            P90.setChecked(false);
            MG3.setChecked(false);
            AC32.setChecked(false);
            HoneyBadger.setChecked(false);
            FAMAS.setChecked(false);

            /* Ammo */
            a45.setChecked(false);
            a9.setChecked(false);
            a7.setChecked(false);
            a300.setChecked(false);
            a5.setChecked(false);
            BMG50.setChecked(false);
            a12.setChecked(false);

            SniperCompensator.setChecked(false);
            DP28.setChecked(false);
            M249.setChecked(false);
            grenade.setChecked(false);
            smoke.setChecked(false);
            molotov.setChecked(false);
            painkiller.setChecked(false);
            injection.setChecked(false);
            energydrink.setChecked(false);
            firstaid.setChecked(false);
            bandage.setChecked(false);
            medkit.setChecked(false);
            FlareGun.setChecked(false);
            UMP.setChecked(false);
            bizon.setChecked(false);
            CompensatorSMG.setChecked(false);
            FlashHiderSMG.setChecked(false);
            FlashHiderAr.setChecked(false);
            ArCompensator.setChecked(false);
            TacticalStock.setChecked(false);
            Duckbill.setChecked(false);
            FlashHiderSniper.setChecked(false);
            SuppressorSMG.setChecked(false);
            HalfGrip.setChecked(false);
            StockMicroUZI.setChecked(false);
            SuppressorSniper.setChecked(false);
            SuppressorAr.setChecked(false);
            ExQdSniper.setChecked(false);
            QdSMG.setChecked(false);
            ExSMG.setChecked(false);
            QdSniper.setChecked(false);
            ExSniper.setChecked(false);
            ExAr.setChecked(false);
            ExQdAr.setChecked(false);
            QdAr.setChecked(false);
            ExQdSMG.setChecked(false);
            QuiverCrossBow.setChecked(false);
            BulletLoop.setChecked(false);
            ThumbGrip.setChecked(false);
            LaserSight.setChecked(false);
            AngledGrip.setChecked(false);
            LightGrip.setChecked(false);
            VerticalGrip.setChecked(false);
            GasCan.setChecked(false);
            Arrow.setChecked(false);
            Crossbow.setChecked(false);
            bag1.setChecked(false);
            bag2.setChecked(false);
            bag3.setChecked(false);
            helmet1.setChecked(false);
            helmet2.setChecked(false);
            helmet3.setChecked(false);
            vest1.setChecked(false);
            vest2.setChecked(false);
            vest3.setChecked(false);
            stun.setChecked(false);
            Crowbar.setChecked(false);
        });
    }

    private void aimbot(View aimbot) {
        TextView menutextaimtouch = aimbot.findViewById(R.id.texttouch);
        LinearLayout aimsec = aimbot.findViewById(R.id.aimsec);
        LinearLayout menurotation = aimbot.findViewById(R.id.rotationmenu);
        LinearLayout aimspeedmenu = aimbot.findViewById(R.id.aimspeedmenu);
        LinearLayout recoilmenu = aimbot.findViewById(R.id.recoilmenu);
        LinearLayout recoilmenus2 = aimbot.findViewById(R.id.recoilmenus2);
        LinearLayout smoothnessmenu = aimbot.findViewById(R.id.smoothnessmenu);
        RadioButton bullettrack = aimbot.findViewById(R.id.bullettrack);
        final LinearLayout touchLocationmenu = aimbot.findViewById(R.id.touchlocationmenu);
        final LinearLayout touchsizemenu = aimbot.findViewById(R.id.touchsizemenu);
        final LinearLayout posXmenu = aimbot.findViewById(R.id.posXmenu);
        final LinearLayout posYmenu = aimbot.findViewById(R.id.posYmenu);

        if (!modestatus) {
            bullettrack.setVisibility(View.VISIBLE);
        } else {
            bullettrack.setVisibility(View.VISIBLE);
        }


        RadioGroup aimgrup = aimbot.findViewById(R.id.grupaim);
        aimgrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.disableaim:
                        StopAimBulletFloat();
                        menutextaimtouch.setVisibility(View.GONE);
                        menurotation.setVisibility(View.GONE);
                        aimspeedmenu.setVisibility(View.GONE);
                        smoothnessmenu.setVisibility(View.GONE);
                        break;

                    case R.id.bullettrack:
                        StartAimBulletFloat();
                        menutextaimtouch.setVisibility(View.GONE);
                        menurotation.setVisibility(View.GONE);
                        aimspeedmenu.setVisibility(View.GONE);
                        smoothnessmenu.setVisibility(View.GONE);
                        touchLocationmenu.setVisibility(View.GONE);
                        touchsizemenu.setVisibility(View.GONE);
                        recoilmenu.setVisibility(View.GONE);
                        recoilmenus2.setVisibility(View.GONE);
                        posXmenu.setVisibility(View.GONE);
                        posYmenu.setVisibility(View.GONE);
                        break;
                }
            }
        });


        final Switch aimKnocked = aimbot.findViewById(R.id.aimknocked);
        setaim(aimKnocked, 3);

        final Switch aimignore = aimbot.findViewById(R.id.aimignorebot);
        setaim(aimignore, 4);

        final Switch changerotation = aimbot.findViewById(R.id.rotationscren);
        setaim(changerotation, 5);

        final Switch touchlocation = aimbot.findViewById(R.id.touchlocation);
        setaim(touchlocation, 6);

        final SeekBar rangeSeekBar = aimbot.findViewById(R.id.range);
        final TextView rangeText = aimbot.findViewById(R.id.rangetext);
        setupSeekBar(rangeSeekBar, rangeText, getrangeAim(), new Runnable() {
            @Override
            public void run() {
                Range(rangeSeekBar.getProgress());
                getrangeAim(rangeSeekBar.getProgress());
            }
        });

        final SeekBar distancesSeekBar = aimbot.findViewById(R.id.distances);
        final TextView distancesText = aimbot.findViewById(R.id.distancetext);
        setupSeekBar(distancesSeekBar, distancesText, getDistances(), new Runnable() {
            @Override
            public void run() {
                distances(distancesSeekBar.getProgress());
                setDistances(distancesSeekBar.getProgress());
            }
        });


        final SeekBar recoilSeekBar2 = aimbot.findViewById(R.id.Recoil2);
        final TextView recoilText2 = aimbot.findViewById(R.id.recoiltext2);
        setupSeekBar(recoilSeekBar2, recoilText2, getrecoilAim(), new Runnable() {
            @Override
            public void run() {
                recoil(recoilSeekBar2.getProgress());
                getrecoilAim(recoilSeekBar2.getProgress());
            }
        });

        final SeekBar recoilSeekBar = aimbot.findViewById(R.id.Recoil);
        final TextView recoilText = aimbot.findViewById(R.id.recoiltext);
        setupSeekBar(recoilSeekBar, recoilText, getrecoilAim(), new Runnable() {
            @Override
            public void run() {
                recoil2(recoilSeekBar.getProgress());
                getrecoilAim2(recoilSeekBar.getProgress());
            }
        });

        final SeekBar recoilSeekBars2 = aimbot.findViewById(R.id.Recoils2);
        final TextView recoilTexts2 = aimbot.findViewById(R.id.recoiltexts2);
        setupSeekBar(recoilSeekBars2, recoilTexts2, getrecoilAim(), new Runnable() {
            @Override
            public void run() {
                recoil3(recoilSeekBars2.getProgress());
                getrecoilAim3(recoilSeekBars2.getProgress());
            }
        });

        final SeekBar bulletSpeedSeekBar = aimbot.findViewById(R.id.bulletspeed);
        final TextView bulletSpeedText = aimbot.findViewById(R.id.bulletspeedtext);
        setupSeekBar(bulletSpeedSeekBar, bulletSpeedText, getbulletspeedAim(), new Runnable() {
            @Override
            public void run() {
                Bulletspeed(bulletSpeedSeekBar.getProgress());
                getbulletspeedAim(bulletSpeedSeekBar.getProgress());
            }
        });

        final SeekBar AimSpeedSize = aimbot.findViewById(R.id.aimingspeed);
        final TextView AimSpeedText = aimbot.findViewById(R.id.aimingspeedtext);
        setupSeekBar(AimSpeedSize, AimSpeedText, getAimSpeed(), new Runnable() {
            @Override
            public void run() {
                AimingSpeed(AimSpeedSize.getProgress());
                setAimSpeed(AimSpeedSize.getProgress());
            }
        });

        final SeekBar SmoothSize = aimbot.findViewById(R.id.Smoothness);
        final TextView SmoothText = aimbot.findViewById(R.id.smoothtext);
        setupSeekBar(SmoothSize, SmoothText, getSmoothness(), new Runnable() {
            @Override
            public void run() {
                Smoothness(SmoothSize.getProgress());
                setSmoothness(SmoothSize.getProgress());
            }
        });

        final SeekBar touchsize = mainView.findViewById(R.id.touchsize);
        final TextView touchsizetext = mainView.findViewById(R.id.touchsizetext);
        setupSeekBar(touchsize, touchsizetext, getTouchSize(), new Runnable() {
            @Override
            public void run() {
                TouchSize(touchsize.getProgress());
                setTouchSize(touchsize.getProgress());
            }
        });

        final SeekBar touchPosX = mainView.findViewById(R.id.touchPosX);
        final TextView touchPosXtext = mainView.findViewById(R.id.touchPosXtext);
        setupSeekBar(touchPosX, touchPosXtext, getTouchPosX(), new Runnable() {
            @Override
            public void run() {
                TouchPosX(touchPosX.getProgress());
                setTouchPosX(touchPosX.getProgress());
            }
        });

        final SeekBar touchPosY = mainView.findViewById(R.id.touchPosY);
        final TextView touchPosYtext = mainView.findViewById(R.id.touchPosYtext);
        setupSeekBar(touchPosY, touchPosYtext, getTouchPosY(), new Runnable() {
            @Override
            public void run() {
                TouchPosY(touchPosY.getProgress());
                setTouchPosY(touchPosY.getProgress());
            }
        });


        final RadioGroup aimby = aimbot.findViewById(R.id.aimby);
        aimby.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId = aimby.getCheckedRadioButtonId();
                RadioButton btn = aimbot.findViewById(chkdId);
                AimBy(Integer.parseInt(btn.getTag().toString()));
            }
        });

        final RadioGroup aimwhen = aimbot.findViewById(R.id.aimwhen);
        aimwhen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId = aimwhen.getCheckedRadioButtonId();
                RadioButton btn = aimbot.findViewById(chkdId);
                AimWhen(Integer.parseInt(btn.getTag().toString()));
            }
        });

        final RadioGroup aimbotmode = aimbot.findViewById(R.id.aimbotmode);
        aimbotmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId = aimbotmode.getCheckedRadioButtonId();
                RadioButton btn = aimbot.findViewById(chkdId);
                Target(Integer.parseInt(btn.getTag().toString()));
            }
        });
    }

    private void memory(View memory) {
        final Switch amms = memory.findViewById(R.id.isaimlock);
        memory(amms, 3);
        final SeekBar wideviewSeekBar = memory.findViewById(R.id.rangewide);
        final TextView wideviewText = memory.findViewById(R.id.rangetextwide);


        setupSeekBar(wideviewSeekBar, wideviewText, getwideview(), new Runnable() {
            @Override
            public void run() {
                WideView(wideviewSeekBar.getProgress());
                getwideview(wideviewSeekBar.getProgress());
            }
        });
    }

}
