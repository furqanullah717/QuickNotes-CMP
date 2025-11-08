package com.codewithfk.quickernotes

import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.db.getNoteDatabase
import com.codewithfk.quickernotes.data.remote.createDataStore
import com.codewithfk.quickernotes.utils.AppleUrlUtils

fun MainViewController() = ComposeUIViewController {
    App(
        getNoteDatabase(
            getDatabaseBuilder()
        ),
        DataStoreManager(
            createDataStore()
        ),
        AppleUrlUtils()
    )
}