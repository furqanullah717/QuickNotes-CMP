package com.codewithfk.quickernotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.db.NoteDatabase
import com.codewithfk.quickernotes.model.Note
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch

class HomeViewModel(noteDatabase: NoteDatabase, dataStoreManager: DataStoreManager) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    val userEmail = MutableStateFlow<String>("")


    init {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail()
            userEmail.value = email ?: ""
        }
    }

    fun addNotes(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }
}