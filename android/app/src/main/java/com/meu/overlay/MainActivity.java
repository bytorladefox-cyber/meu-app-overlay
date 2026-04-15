package com.meu.overlay;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. O SEGREDO: Isso avisa o Android que a janela pode ter transparência
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        
        // 2. Remove qualquer fundo padrão da janela nativa
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        
        // 3. Deixa a WebView transparente assim que ela for criada
        if (this.bridge != null) {
            WebView webView = this.bridge.getWebView();
            webView.setBackgroundColor(0x00000000);
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Reforça a transparência no início para garantir
        WebView webView = this.bridge.getWebView();
        webView.setBackgroundColor(0);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
