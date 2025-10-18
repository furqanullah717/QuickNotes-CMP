package com.codewithfk.quickernotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String, val description: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
