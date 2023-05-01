package com.example.notekeeper.model.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Model class for Note
@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey
    val noteCreationTimestamp: Long,
    val noteLastModificationTimestamp: Long,
    val noteTitle: String,
    val noteDescription: String? = null
)
