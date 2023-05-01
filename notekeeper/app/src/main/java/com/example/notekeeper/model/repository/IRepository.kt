package com.example.notekeeper.model.repository

import com.example.notekeeper.model.dao.model.Note

interface IRepository {

    // Insert Notes Methods
    fun insertNote(note: Note)

    // Update Notes Methods
    fun updateNoteTitleByID(id: Long, title: String)
    fun updateNoteDescriptionByID(id: Long, description: String)
    fun updateNoteLastModificationTimestampByID(id: Long, lastModificationTimestamp: Long)

    // Delete Notes Methods
    fun deleteNoteByID(id: Long)

    // Get Notes Methods
    fun getAllNotes(): List<Note>
    fun getNoteByID(id: Long): Note?
    fun getNotesBetween(startTime: Long, endTime: Long): List<Note>

    // Search Notes Methods
    fun searchNotes(search: String): List<Note>
}