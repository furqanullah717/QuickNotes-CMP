package com.codewithfk.quickernotes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.codewithfk.quickernotes.data.cache.createDataStore
import com.codewithfk.quickernotes.data.cache.dataStoreFileName


fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)