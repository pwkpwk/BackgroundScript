package com.microsoft.playground.backgroundscript

import android.webkit.JavascriptInterface

class ScriptBridge {
    @JavascriptInterface
    fun started() {
        println("The script has started")
    }
}
