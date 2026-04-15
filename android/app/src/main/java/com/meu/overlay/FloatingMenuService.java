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
        settings.setDomStorageEnabled(true);
        
        // O SEGREDO: Vamos esperar o site carregar e injetar um comando para deixar tudo transparente
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Esse código entra no site da Lovable e desliga o fundo branco
                view.loadUrl("javascript:(function() { " +
                        "document.body.style.setProperty('background', 'transparent', 'important');" +
                        "document.body.style.setProperty('background-color', 'transparent', 'important');" +
                        "document.documentElement.style.setProperty('background', 'transparent', 'important');" +
                        "var all = document.getElementsByTagName('*');" +
                        "for (var i=0, max=all.length; i < max; i++) {" +
                        "  if (all[i].style.backgroundColor == 'white' || all[i].style.backgroundColor == 'rgb(255, 255, 255)') {" +
                        "    all[i].style.backgroundColor = 'transparent';" +
                        "  }" +
                        "}" +
                        "})()");
            }
        });

        webView.loadUrl("https://snap-flow-click.lovable.app/");

        // Configurações do Android para não ter fundo
        webView.setBackgroundColor(Color.TRANSPARENT); 
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP;
        windowManager.addView(webView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) windowManager.removeView(webView);
    }
}
