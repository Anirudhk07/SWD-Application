package com.example.swd_application.User.Activities


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.R

class EventFormWebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form_web_view)

        var url = intent.getStringExtra("url")

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setAppCacheEnabled(true)
        webView.settings.setAppCachePath(this.cacheDir.path)
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView.webViewClient = MyWebViewClient(this)
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            val url: String = request?.url.toString()

            if (url.startsWith("http:") || url.startsWith("https:")) {
                view?.loadUrl(url)
                return true
            } else if (url.startsWith("intent://")) {
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                if (intent != null) {
                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                    return if (fallbackUrl != null) {
                        view?.loadUrl(fallbackUrl)
                        true
                    } else {
                        false
                    }
                }
            }
            return false
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}
