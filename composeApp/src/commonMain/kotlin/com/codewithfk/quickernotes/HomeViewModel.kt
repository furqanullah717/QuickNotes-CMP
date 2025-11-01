package com.codewithfk.quickernotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.db.NoteDatabase
import com.codewithfk.quickernotes.data.remote.ApiService
import com.codewithfk.quickernotes.data.remote.HttpClientFactory
import com.codewithfk.quickernotes.data.remote.SyncRepository
import com.codewithfk.quickernotes.data.remote.SyncState
import com.codewithfk.quickernotes.model.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch

class HomeViewModel(
    val noteDatabase: NoteDatabase,
    val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    val userEmail = MutableStateFlow<String>("")


    init {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail()
            userEmail.value = email ?: ""
            performSync()
        }
    }

    fun performSync() {
        viewModelScope.launch {
            val apiService = ApiService(HttpClientFactory.getHttpClient(), dataStoreManager)
            val userID = dataStoreManager.getUserId() ?: return@launch
            val syncRepository = SyncRepository(
                userID,
                noteDatabase.noteDao(),
                noteDatabase.syncMetadataDao(),
                apiService
            )

            syncRepository.performSync()

            syncRepository.syncState.collectLatest {
                when (it) {
                    is SyncState.Idle -> {
                    }

                    is SyncState.Syncing -> {
                        // Handle syncing state if needed
                    }

                    is SyncState.Success -> {
                        // Handle success state if needed
                    }

                    is SyncState.Error -> {
                        // Handle error state if needed
                    }
                }
            }
        }

    }

    fun addNotes(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note)
            performSync()
        }
    }
}