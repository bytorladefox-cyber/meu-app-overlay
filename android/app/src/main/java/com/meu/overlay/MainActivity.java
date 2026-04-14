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

        // Garante que a WebView do Capacitor seja transparente
        WebView webView = this.bridge.getWebView();
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setBackgroundResource(0);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }
}
