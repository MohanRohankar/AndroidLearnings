package com.example.notekeeper.model.dao.repository

import android.util.Log
import androidx.room.Query
import com.example.notekeeper.model.dao.NoteDao
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.repository.IRepository

// Repository class
class NoteRepository(private val noteDao: NoteDao) : IRepository {

    // Insert Notes Methods
    override fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    // Update Notes Methods
    override fun updateNoteTitleByID(id: Long, title: String) {
        noteDao.updateNoteTitleByID(id, title)
    }

    override fun updateNoteDescriptionByID(id: Long, description: String) {
        noteDao.updateNoteDescriptionByID(id, description)
    }

    override fun updateNoteLastModificationTimestampByID(id: Long, lastModificationTimestamp: Long) {
        noteDao.updateNoteLastModificationTimestampByID(id, lastModificationTimestamp)
    }

    // Delete Notes Methods
    override fun deleteNoteByID(id: Long) {
        noteDao.deleteNoteByID(id)
    }

    // Get Notes Methods
    override fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    override fun getNoteByID(id: Long): Note? {
        return noteDao.getNoteByID(id)
    }

    override fun getNotesBetween(startTime: Long, endTime: Long): List<Note> {
        return noteDao.getNotesBetween(startTime, endTime)
    }

    // Search Notes Methods
    override fun searchNotes(search: String): List<Note> {
        return noteDao.searchNotes(search)
    }

}
