package com.codewithfk.quickernotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codewithfk.quickernotes.data.db.getNoteDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                database = getNoteDatabase(getDatabaseBuilder(this@MainActivity))
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val database = getNoteDatabase(getDatabaseBuilder(LocalContext.current))
    App(database)
}