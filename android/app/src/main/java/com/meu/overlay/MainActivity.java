package com.meu.overlay;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se o Android for 6.0 ou superior, precisamos da permissão especial
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // Abre a tela de configuração do Android
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            Toast.makeText(this, "Autorize o app a 'Aparecer sobre outros'", Toast.LENGTH_LONG).show();
        } else {
            // Se já tem permissão, liga o menu flutuante
            startFloatingMenuService();
        }
    }

    private void startFloatingMenuService() {
        startService(new Intent(MainActivity.this, FloatingMenuService.class));
        finish(); // FECHA A TELA BRANCA IMEDIATAMENTE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startFloatingMenuService();
            } else {
                Toast.makeText(this, "Permissão negada. O menu não vai aparecer.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
