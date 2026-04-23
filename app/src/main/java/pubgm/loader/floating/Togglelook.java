package pubgm.loader.floating;

import pubgm.loader.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Togglelook {
    static {
        System.loadLibrary("livai");
    }

    private final Activity activity;
    private native boolean verifySignatureNative(byte[] signatureBytes);

    public Togglelook(Activity activity) {
        this.activity = activity;
    }

    public void verify() {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(),
                    PackageManager.GET_SIGNATURES
            );

            for (Signature signature : packageInfo.signatures) {
                if (!verifySignatureNative(signature.toByteArray())) {
                    showFancyErrorDialog();
                    return;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            showFancyErrorDialog();
        }
    }

    private void showFancyErrorDialog() {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Activity.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(android.os.VibrationEffect.createOneShot(150, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(150);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 60, 60, 60);
        layout.setGravity(Gravity.CENTER);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#5D4037"));
        bg.setCornerRadius(40);
        bg.setStroke(5, Color.WHITE);

        TextView message = new TextView(activity);
        message.setText(activity.getString(R.string.invalid_key_format));
        message.setTextSize(22);
        message.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        message.setTextColor(Color.parseColor("#FFD700"));
        message.setGravity(Gravity.CENTER);
        message.setPadding(40, 40, 40, 40);
        message.setBackground(bg);
        message.setShadowLayer(6f, 0f, 3f, Color.BLACK);

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(700);
        fadeIn.setFillAfter(true);
        message.startAnimation(fadeIn);

        layout.addView(message, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        builder.setView(layout)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    activity.finishAffinity();
                    System.exit(0);
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(bg);
        }

        dialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            throw new RuntimeException(activity.getString(R.string.invalid_key_format));
        }, 5000);
    }
}