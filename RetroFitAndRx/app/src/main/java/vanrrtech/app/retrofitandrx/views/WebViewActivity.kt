package vanrrtech.app.retrofitandrx.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import vanrrtech.app.retrofitandrx.R

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var urlString = intent.extras?.getString("url", "https://www.google.com/")

        var browser = findViewById<WebView>(R.id.webview)
        browser.loadUrl(urlString!!)
        browser.settings.javaScriptEnabled = true
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }
}