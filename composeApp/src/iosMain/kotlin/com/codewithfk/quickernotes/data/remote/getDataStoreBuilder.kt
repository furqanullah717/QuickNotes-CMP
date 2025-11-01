package com.codewithfk.quickernotes.data.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codewithfk.quickernotes.data.cache.createDataStore
import com.codewithfk.quickernotes.data.cache.dataStoreFileName
import com.codewithfk.quickernotes.data.db.NoteDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


fun createDataStore(): DataStore<Preferences> {
    return createDataStore(
        producePath = {
            getDocumentPath() + "/${dataStoreFileName}"
        }
    )
}

@OptIn(ExperimentalForeignApi::class)
fun getDocumentPath(): String {
    val documentDirectory =  NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path)
}