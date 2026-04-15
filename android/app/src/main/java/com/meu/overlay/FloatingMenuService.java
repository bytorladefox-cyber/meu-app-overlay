package com.meu.overlay;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FloatingMenuService extends Service {
    private WindowManager windowManager;
    private WebView webView;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        
        webView.setWebViewClient(new WebViewClient());

        // --- MUDANÇA PARA TESTE ---
        // Se o Google carregar flutuando, seu app está funcionando!
        webView.loadUrl("https://www.google.com");
        // --------------------------

        webView.setBackgroundColor(Color.TRANSPARENT); 
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // Definindo um tamanho fixo (800x800) para você ver a transparência em volta
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                800, 
                800,
                (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.CENTER; // Centraliza na tela

        windowManager.addView(webView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) windowManager.removeView(webView);
    }
}
