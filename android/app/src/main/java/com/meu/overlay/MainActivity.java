package com.meu.overlay;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Torna a janela do Android transparente (a "moldura")
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onStart() {
        super.onStart();
        
        // 2. Torna a WebView (o "vidro") transparente
        // Fazemos no onStart para garantir que a bridge já existe
        WebView webView = this.bridge.getWebView();
        
        // Força cor zero (transparente)
        webView.setBackgroundColor(0x00000000); 
        
        // Remove qualquer recurso de fundo herdado
        webView.setBackgroundResource(0);
        
        // Importante: Em alguns dispositivos, o HARDWARE acceleration 
        // força um fundo preto ou branco. Se continuar branco, 
        // mude LAYER_TYPE_HARDWARE para LAYER_TYPE_SOFTWARE abaixo:
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }
}
