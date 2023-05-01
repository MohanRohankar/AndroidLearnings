package com.example.notekeeper.model.dao

import androidx.room.*
import com.example.notekeeper.model.dao.model.Note

// DAO (Data Access Object) interface
@Dao
interface NoteDao {

    // Insert Notes Methods
    @Insert
    fun insertNote(note: Note)

    // Update Notes Methods
    @Query("UPDATE notes_table SET noteTitle = :title WHERE noteCreationTimestamp = :id")
    fun updateNoteTitleByID(id: Long, title: String)

    @Query("UPDATE notes_table SET noteDescription = :description WHERE noteCreationTimestamp = :id")
    fun updateNoteDescriptionByID(id: Long, description: String)

    @Query("UPDATE notes_table SET noteLastModificationTimestamp = :timestamp WHERE noteCreationTimestamp = :id")
    fun updateNoteLastModificationTimestampByID(id: Long, timestamp: Long)

    // Delete Notes Methods
    @Query("DELETE FROM notes_table WHERE noteCreationTimestamp = :id")
    fun deleteNoteByID(id: Long)

    // Get Notes Methods
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes_table WHERE noteCreationTimestamp = :id")
    fun getNoteByID(id: Long): Note?

    @Query("SELECT * FROM notes_table WHERE noteCreationTimestamp BETWEEN :startTime AND :endTime")
    fun getNotesBetween(startTime: Long, endTime: Long): List<Note>

    // Search Notes Methods
    @Query("SELECT * FROM notes_table WHERE noteTitle LIKE '%' || :search || '%' OR noteDescription LIKE '%' || :search || '%'")
    fun searchNotes(search: String): List<Note>
}
