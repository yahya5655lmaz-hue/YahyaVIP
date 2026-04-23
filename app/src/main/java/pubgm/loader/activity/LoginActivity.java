package pubgm.loader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import pubgm.loader.R;

public class LoginActivity extends Activity {

    private EditText keyInput;
    private MaterialButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Giriş ekranı tasarımı (Kendi tasarımın)
        setContentView(R.layout.activity_main); 

        keyInput = findViewById(R.id.textUsername);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userKey = keyInput.getText().toString().trim();
                if (userKey.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Lütfen bir anahtar girin!", Toast.LENGTH_SHORT).show();
                } else {
                    checkLicenseOnPanel(userKey);
                }
            }
        });
    }

    private void checkLicenseOnPanel(String key) {
        // Senin gerçek panel adresin (DOĞRU HALİ)
        String url = "http://gamer.gd" + key;

        StringRequest request = new StringRequest(Request.Method.GET, url,
            response -> {
                if (response.trim().equals("SUCCESS")) {
                    Toast.makeText(LoginActivity.this, "𝒀𝑨𝑯𝒀𝑨 𝑽𝑰𝑷 Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Hatalı veya Süresi Dolmuş Anahtar!", Toast.LENGTH_LONG).show();
                }
            },
            error -> Toast.makeText(LoginActivity.this, "Sistem Hatası: İnternetinizi Kontrol Edin!", Toast.LENGTH_LONG).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
