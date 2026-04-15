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
        settings.setDomStorageEnabled(true); // Ajuda a carregar sites modernos como Lovable
        
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://snap-flow-click.lovable.app/");

        // --- BLOCO DA TRANSPARÊNCIA ABSOLUTA ---
        // 1. Define a cor como transparente (Alpha 0)
        webView.setBackgroundColor(Color.TRANSPARENT); 
        
        // 2. Remove qualquer background resource
        webView.setBackgroundResource(0); 

        // 3. ESSENCIAL: Algumas GPUs forçam branco no hardware. 
        // Desativamos para garantir que o Alpha do PixelFormat funcione.
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // ---------------------------------------

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, // Ocupa a largura total
                WindowManager.LayoutParams.MATCH_PARENT, // Ocupa a altura total
                (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                // FLAG_NOT_TOUCH_MODAL permite que toques fora do menu passem para o fundo
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, 
                PixelFormat.TRANSLUCENT // Abre o canal de transparência da janela
        );

        params.gravity = Gravity.TOP | Gravity.LEFT;

        windowManager.addView(webView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            windowManager.removeView(webView);
            webView.destroy();
        }
    }
}
