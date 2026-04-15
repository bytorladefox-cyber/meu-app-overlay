package com.meu.overlay;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 1. Força o uso do seu estilo transparente ANTES de criar a tela
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // 2. Configurações de Janela de baixo nível para permitir transparência
        Window window = getWindow();
        
        // Remove qualquer flag que possa forçar opacidade
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        
        // Define o formato do pixel para Transparente (fura a camada do sistema)
        window.setFormat(PixelFormat.TRANSLUCENT);
        
        // Força o fundo da janela a ser nulo/transparente
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);

        // 3. Configuração do WebView do Capacitor
        if (this.bridge != null) {
            WebView webView = this.bridge.getWebView();
            // Cor 0 é o transparente absoluto (Alpha 0)
            webView.setBackgroundColor(0); 
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
