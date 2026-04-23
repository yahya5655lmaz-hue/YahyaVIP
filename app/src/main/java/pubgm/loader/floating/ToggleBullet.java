package pubgm.loader.floating;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import pubgm.loader.R;

public class ToggleBullet extends Service {

    private boolean checkStatus = false;
    private View mainView;
    private RelativeLayout miniFloatView;
    private WindowManager windowManager;
    private LayoutParams paramsView;

    // JNI - C++ tarafındaki Bullet Track fonksiyonu
    public native void ToggleBullet(boolean value);

    static {
        try {
            // Hile motorunu (liblivai.so) yüklüyoruz
            System.loadLibrary("livai");
        } catch (UnsatisfiedLinkError e) {
            // Kütüphane bulunamazsa hata verme, devam et
        }
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        ShowMainView();
    }

    private void ShowMainView() {
        mainView = LayoutInflater.from(this).inflate(R.layout.toggle_aimbullet, null);
        paramsView = createParams();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        try {
            windowManager.addView(mainView, paramsView);
        } catch (Exception ignored) {}
        
        InitShowMainView();
    }

    private LayoutParams createParams() {
        int LAYOUT_FLAG = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
            ? LayoutParams.TYPE_APPLICATION_OVERLAY 
            : LayoutParams.TYPE_PHONE;

        final LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 100;
        params.y = 100;
        return params;
    }

    private void InitShowMainView() {
        miniFloatView = mainView.findViewById(R.id.miniFloatMenu);
        RelativeLayout layoutView = mainView.findViewById(R.id.layout_icon_control_aim);
        final ImageView myImageView = mainView.findViewById(R.id.imageview_aim);

        layoutView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = paramsView.x;
                        initialY = paramsView.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        
                        // Eğer sadece tıklandıysa (sürükleme yoksa)
                        if (Math.abs(Xdiff) < 10 && Math.abs(Ydiff) < 10) {
                            checkStatus = !checkStatus;
                            ToggleBullet(checkStatus);
                            
                            if (checkStatus) {
                                myImageView.setImageResource(R.drawable.b2); // Aktif ikonu
                                Toast.makeText(getApplicationContext(), "Bullet Track: ON", Toast.LENGTH_SHORT).show();
                            } else {
                                myImageView.setImageResource(R.drawable.b1); // Pasif ikonu
                                Toast.makeText(getApplicationContext(), "Bullet Track: OFF", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        paramsView.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsView.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(mainView, paramsView);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToggleBullet(false);
        if (mainView != null) windowManager.removeView(mainView);
    }
}
