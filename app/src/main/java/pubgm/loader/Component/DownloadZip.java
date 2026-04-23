package pubgm.loader.Component;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import pubgm.loader.R;
import com.airbnb.lottie.LottieAnimationView;

import net.lingala.zip4j.ZipFile;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadZip {

    public interface OnDownloadCompleteListener {
        void onPreExecute();
        void onDownloadComplete(boolean success);
    }

    public static class CheckJson extends AsyncTask<String, Void, JSONObject> {
        private final Context context;
        private final SharedPreferences prefs;
        private OnDownloadCompleteListener listener;

        public CheckJson(Context context) {
            this(context, null);
        }

        public CheckJson(Context context, OnDownloadCompleteListener listener) {
            this.context = context;
            this.prefs = context.getSharedPreferences("bypass_data", Context.MODE_PRIVATE);
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener != null) {
                listener.onPreExecute();
            }
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            String jsonUrl = urls[0];
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                return new JSONObject(sb.toString());
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    int newVersion = jsonObject.getInt("bypassVersion");
                    String bypassLink = jsonObject.getString("bypassLink");

                    int oldVersion = prefs.getInt("bypassVersion", 0);
                    if (newVersion != oldVersion) {
                        new DownC(context, new OnDownloadCompleteListener() {
                            @Override
                            public void onPreExecute() {}

                            @Override
                            public void onDownloadComplete(boolean success) {
                                if (!success) {
                                    showUpdateDialog(context);
                                }
                                if (listener != null) {
                                    listener.onDownloadComplete(success);
                                }
                            }
                        }).execute(bypassLink);
                        prefs.edit().putInt("bypassVersion", newVersion).apply();
                    } else {
                        String message = "✅ تم تفعيل الحماية (VIP) — أنت على أحدث إصدار";
                        showStyledToast(context, message);
                        if (listener != null) {
                            listener.onDownloadComplete(true);
                        }
                    }
                } catch (Exception e) {
                    showUpdateDialog(context);
                    if (listener != null) {
                        listener.onDownloadComplete(false);
                    }
                }
            } else {
                showUpdateDialog(context);
                if (listener != null) {
                    listener.onDownloadComplete(false);
                }
            }
        }
    }

    public static class DownC extends AsyncTask<String, Integer, String> {
        private final Context context;
        private Dialog dialog;
        private LottieAnimationView animationView;
        private TextView percentageText;
        private final SharedPreferences prefs;
        private OnDownloadCompleteListener listener;

        public DownC(Context context) {
            this(context, null);
        }

        public DownC(Context context, OnDownloadCompleteListener listener) {
            this.context = context;
            this.prefs = context.getSharedPreferences("bypass_data", Context.MODE_PRIVATE);
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_progress);
            dialog.setCancelable(false);

            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

            animationView = dialog.findViewById(R.id.animation_view);
            percentageText = dialog.findViewById(R.id.percentage_text);
            percentageText.setText("0%");
            dialog.show();

            if (listener != null) {
                listener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            String zipUrl = urls[0];
            try {
                File cacheDir = context.getCacheDir();
                File tempFile = new File(cacheDir, "bypass_temp.zip");
                File finalFile = new File(cacheDir, "bypass_final.zip");

                if (finalFile.exists()) {
                    finalFile.delete();
                }

                URL downloadUrl = new URL(zipUrl);
                HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                connection.connect();

                int fileLength = connection.getContentLength();
                if (fileLength <= 0) fileLength = 1;

                InputStream input = connection.getInputStream();
                OutputStream output = new FileOutputStream(tempFile);

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                boolean downloadSuccess = true;

                try {
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress((int) ((total * 100) / fileLength));
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    downloadSuccess = false;
                } finally {
                    try { output.close(); } catch (Exception ignored) {}
                    try { input.close(); } catch (Exception ignored) {}
                }

                if (downloadSuccess && tempFile.exists() && tempFile.length() > 0) {
                    if (tempFile.renameTo(finalFile)) {
                        return "SUCCESS";
                    } else {
                        return "RENAME_FAILED";
                    }
                } else {
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                    return "DOWNLOAD_FAILED";
                }

            } catch (Exception e) {
                return "ERROR";
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            if (percentageText != null && progress != null && progress.length > 0) {
                percentageText.setText(progress[0] + "%");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog != null && dialog.isShowing()) {
                try { dialog.dismiss(); } catch (Exception ignored) {}
            }

            boolean success = false;

            switch (result) {
                case "SUCCESS":
                    try {
                        File finalFile = new File(context.getCacheDir(), "bypass_final.zip");
                        new ZipFile(finalFile).extractAll(context.getFilesDir().getPath());

                        String message = "✅ تم تحديث الحماية (VIP) بنجاح\n!";
                        showStyledToast(context, message);
                        success = true;
                    } catch (Exception e) {
                        showUpdateDialog(context);
                    }
                    break;
                case "RENAME_FAILED":
                case "DOWNLOAD_FAILED":
                case "ERROR":
                default:
                    showUpdateDialog(context);
                    break;
            }

            if (listener != null) {
                listener.onDownloadComplete(success);
            }
        }
    }

    private static void showUpdateDialog(Context context) {
        Dialog updateDialog = new Dialog(context);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.dialog_update_required);
        updateDialog.setCancelable(true);
        updateDialog.setCanceledOnTouchOutside(true);

        Window window = updateDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(android.view.Gravity.CENTER);
        }

        try {
            updateDialog.show();
        } catch (Exception ignored) {}

        new Handler().postDelayed(() -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Mon5la"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception ignored) {}
        }, 3000);

        new Handler().postDelayed(() -> {
            try {
                if (updateDialog.isShowing()) {
                    updateDialog.dismiss();
                }
            } catch (Exception ignored) {}
            try {
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (Exception ignored) {}
        }, 4000);
    }

    private static void showStyledToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } catch (Exception ignored) {}
    }
}