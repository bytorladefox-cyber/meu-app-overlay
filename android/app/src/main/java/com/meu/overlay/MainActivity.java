package com.meu.overlay;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verifica se tem permissão para sobreposição
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 123);
        } else {
            iniciarServico();
        }
    }

    private void iniciarServico() {
        Intent intent = new Intent(this, FloatingMenuService.class);
        startService(intent);
        finish(); // Fecha a tela branca e deixa só a bolha
    }
}
// Essencial: Desativa aceleração de hardware apenas na WebView 
            // para evitar que o motor gráfico preencha o fundo com preto/branco
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reforço de segurança: Garante transparência ao voltar para o app
        if (this.bridge != null) {
            this.bridge.getWebView().setBackgroundColor(0);
        }
    }
}
