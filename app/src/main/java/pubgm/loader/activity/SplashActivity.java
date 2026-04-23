package pubgm.loader.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import pubgm.loader.Component.Utils;
import pubgm.loader.R;
import pubgm.loader.utils.ActivityCompat;

import org.lsposed.lsparanoid.Obfuscate;

// @Obfuscate
public class SplashActivity extends ActivityCompat {

    public static boolean mahyong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        VideoView videoView = findViewById(R.id.splashVideo);
        Button btnSkip = findViewById(R.id.btnSkip);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(video);


        videoView.setOnCompletionListener(mp -> {
            mahyong = true;
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


        btnSkip.setOnClickListener(v -> {
            mahyong = true;
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


        videoView.start();
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}