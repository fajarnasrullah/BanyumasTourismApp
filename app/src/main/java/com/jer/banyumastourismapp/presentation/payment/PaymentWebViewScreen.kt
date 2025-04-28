package com.jer.banyumastourismapp.presentation.payment

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun PaymentWebViewScreen(modifier: Modifier = Modifier, url: String?) {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { itsContext ->
            WebView(itsContext).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true) }
        },
        update = { webView ->
            if (url != null) {

                webView.loadUrl(url)
            }
        }

    )

}