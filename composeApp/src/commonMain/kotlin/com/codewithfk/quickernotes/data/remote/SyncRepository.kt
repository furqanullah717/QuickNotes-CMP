package com.codewithfk.quickernotes.data.remote

import androidx.compose.runtime.remember
import com.codewithfk.quickernotes.data.db.NoteDao
import com.codewithfk.quickernotes.data.db.SyncDataDao
import com.codewithfk.quickernotes.model.NoteChange
import com.codewithfk.quickernotes.model.SyncResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class SyncRepository(
    private val userID: String,
    private val noteDao: NoteDao,
    private val syncDataDao: SyncDataDao,
    private val apiService: ApiService
) {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState = _syncState.asStateFlow()


    suspend fun performSync() = withContext(Dispatchers.IO) {
        //gather Sync data
        try {
            val metadata = syncDataDao.getSyncMetadata()
            if (metadata?.isSyncing == true) {
                return@withContext
            }

            _syncState.value = SyncState.Syncing
            syncDataDao.updateSyncingStatus(true)

            // fetch dirty notes from the local database
            val dirtyNotes = noteDao.getDirtyNotes()

            // create SyncRequest object
            val syncRequest = com.codewithfk.quickernotes.model.SyncRequest(
                since = metadata?.lastSyncTimestamp,
                changes = dirtyNotes.map { note ->
                    NoteChange(
                        id = note.id,
                        title = note.title,
                        body = note.description,
                        isDeleted = note.isDeleted,
                        updatedAt = note.updatedAt
                    )
                }
            )
            val response = apiService.sync(syncRequest)
            //process response
            response.getOrNull()?.let {
                pocessSyncResponse(it)
            }

            syncDataDao.updateLastSyncTimestamp(response.getOrNull()?.nextSince ?: "")
            syncDataDao.updateSyncingStatus(false)

            _syncState.value = SyncState.Success(response.getOrNull()!!)
        } catch (ex: Exception) {
            _syncState.value = SyncState.Error(ex.message ?: "An error occurred")
        }
    }

    suspend fun pocessSyncResponse(response: SyncResponse) = withContext(Dispatchers.IO) {
        //applied
        if (response.applied.isNotEmpty()) {
            noteDao.markAsSynced(response.applied)
        }
        // conflicts

        if (response.conflicts.isNotEmpty()) {
            val conflictNotes = response.conflicts.map { noteChange ->
                com.codewithfk.quickernotes.model.Note(
                    id = noteChange.id,
                    title = noteChange.title,
                    description = noteChange.body,
                    isDeleted = noteChange.isDeleted,
                    updatedAt = noteChange.updatedAt,
                    isDirty = false,
                    userId = userID
                )
            }
            noteDao.insertNotes(conflictNotes)
        }

        // changes
        if(response.changes.isNotEmpty()){
            val serverNotes = response.changes.map { noteChange ->
                com.codewithfk.quickernotes.model.Note(
                    id = noteChange.id,
                    title = noteChange.title,
                    description = noteChange.body,
                    isDeleted = noteChange.isDeleted,
                    updatedAt = noteChange.updatedAt,
                    isDirty = false,
                    userId = userID
                )
            }
            noteDao.insertNotes(serverNotes)
        }
    }
}


sealed class SyncState {
    object Idle : SyncState()
    object Syncing : SyncState()
    data class Success(val data: SyncResponse) : SyncState()
    data class Error(val error: String) : SyncState()
}