package com.codewithfk.quickernotes

import androidx.lifecycle.ViewModel
import com.codewithfk.quickernotes.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    fun addNotes(note: Note) {
        _notes.update { it + note }
    }
}