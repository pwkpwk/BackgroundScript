package com.microsoft.playground.backgroundscript

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.microsoft.playground.backgroundscript.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {

    private lateinit var wv: WebView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wv = WebView(applicationContext);
        val webSettings: WebSettings = wv.settings
        webSettings.javaScriptEnabled = true

        val model = object: MainViewModel.Model {
            override fun call() {
                wv.evaluateJavascript("callBack()", null)
            }
        }
        val viewModel = MainViewModel(model)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        wv.addJavascriptInterface(Bridge(binding.root, viewModel), "scriptBridge")
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

    private class Bridge(val root: View, val viewModel: MainViewModel) {
        @JavascriptInterface
        fun started() {
            root.post {
                viewModel.setReady(true)
            }
        }

        @JavascriptInterface
        fun called() {
            root.post {
                viewModel.markCalled()
            }
        }
    }
}
