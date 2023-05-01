package com.example.notekeeper.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.R
import com.example.notekeeper.model.dao.database.NoteDatabase
import com.example.notekeeper.view.adapter.NotesAdapter
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.utils.DateTimeUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewNoteActivity : AppCompatActivity() {
    private lateinit var mNoteRepository: NoteRepository
    private lateinit var emptyNoteListText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        val noteDao = NoteDatabase.getDatabase(applicationContext).noteDao()
        mNoteRepository = NoteRepository(noteDao)

        emptyNoteListText = findViewById<TextView>(R.id.emptyNoteListText)

        // Initialize RecyclerView and set the layout manager, adapter, and any other configurations
        val recyclerView = findViewById<RecyclerView>(R.id.noteListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var isNoteListEmpty: Boolean = false
        // Use coroutines to run database query on a background thread
        GlobalScope.launch {
            val notesList = mNoteRepository.getAllNotes()
            isNoteListEmpty = notesList.isEmpty()
            recyclerView.adapter = NotesAdapter(applicationContext, notesList)
        }
        if (isNoteListEmpty) {
            emptyNoteListText.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getNotesList(): List<Note> {
        return listOf()
    }
}