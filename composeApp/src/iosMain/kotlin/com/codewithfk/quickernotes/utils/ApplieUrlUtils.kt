package com.codewithfk.quickernotes.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class AppleUrlUtils : UrlUtils {
    override fun openUrlInBrowser(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}