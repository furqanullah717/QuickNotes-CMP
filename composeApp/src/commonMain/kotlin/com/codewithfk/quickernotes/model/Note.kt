package com.codewithfk.quickernotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity
data class Note @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class) constructor(
    val title: String, val description: String,
    @PrimaryKey val id: String = Uuid.random().toString(),
    val isDeleted: Boolean = false,
    val updatedAt: String = Clock.System.now().toString(),
    val isDirty: Boolean = false, // Track if note needs syncing
    val userId: String
)

@Entity
data class SyncMetadata(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Single row table
    val lastSyncTimestamp: String? = null, // ISO 8601 timestamp
    val isSyncing: Boolean = false
)