package com.meu.overlay;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove a cor de fundo da janela principal do Android
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onStart() {
        super.onStart();
        WebView webView = this.bridge.getWebView();
        
        // Força a transparência total no componente que carrega o site
        webView.setBackgroundColor(Color.TRANSPARENT);
        // Isso remove o "fundo branco" padrão do motor Chrome do Android
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
    }
}

}
