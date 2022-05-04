package com.microsoft.playground.backgroundscript

import android.content.Context
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {

    private lateinit var wv: WebView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wv = WebView(applicationContext);
        val webSettings: WebSettings = wv.settings
        webSettings.javaScriptEnabled = true

        wv.addJavascriptInterface(ScriptBridge(), "scriptBridge")
        wv.loadDataWithBaseURL(
            "https://teams.microsoft.com",
            loadHtml(applicationContext),
            "text/html",
            null,
            null
        )
    }

    private fun loadHtml(context: Context): String {
        val builder = StringBuilder()

        BufferedReader(
            InputStreamReader(
                context.resources.openRawResource(R.raw.web_view_layout),
                StandardCharsets.UTF_8
            )
        ).use { br ->
            var line = br.readLine()
            while (line != null) {
                builder.append(line).append('\n')
                line = br.readLine()
            }
        }

        return builder.toString()

    }
}
