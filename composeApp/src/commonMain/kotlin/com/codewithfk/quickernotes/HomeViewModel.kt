package com.codewithfk.quickernotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.quickernotes.db.NoteDatabase
import com.codewithfk.quickernotes.model.Note

import kotlinx.coroutines.launch

class HomeViewModel(noteDatabase: NoteDatabase) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    fun addNotes(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }
}