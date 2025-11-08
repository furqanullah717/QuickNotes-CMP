package com.codewithfk.quickernotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.db.getNoteDatabase
import com.codewithfk.quickernotes.utils.AndroidUiUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                database = getNoteDatabase(getDatabaseBuilder(this@MainActivity)),
                dataStoreManager = DataStoreManager(createDataStore(this@MainActivity)),
                urlUtils = AndroidUiUtils(this@MainActivity)
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val database = getNoteDatabase(getDatabaseBuilder(LocalContext.current))
    val dataStoreManager = DataStoreManager(createDataStore(LocalContext.current))
    App(database, dataStoreManager, AndroidUiUtils(LocalContext.current))
}