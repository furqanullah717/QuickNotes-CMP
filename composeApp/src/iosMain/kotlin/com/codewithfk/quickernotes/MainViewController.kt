package com.codewithfk.quickernotes

import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.quickernotes.db.getNoteDatabase

fun MainViewController() = ComposeUIViewController { App(
    getNoteDatabase(getDatabaseBuilder())
) }