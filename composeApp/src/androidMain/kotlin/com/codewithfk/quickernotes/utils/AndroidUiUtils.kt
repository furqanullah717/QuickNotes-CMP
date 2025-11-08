package com.codewithfk.quickernotes.utils

import android.content.Context

class AndroidUiUtils(val context: Context) : UrlUtils {
    override fun openUrlInBrowser(url: String) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse(url)
        }
        context.startActivity(intent)
    }
}