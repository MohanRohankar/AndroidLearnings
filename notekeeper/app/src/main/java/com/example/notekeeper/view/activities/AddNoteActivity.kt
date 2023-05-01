package com.example.notekeeper.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.notekeeper.R
import com.example.notekeeper.model.dao.NoteDao
import com.example.notekeeper.model.dao.NoteDao_Impl
import com.example.notekeeper.model.dao.database.NoteDatabase
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.utils.Constants
import com.example.notekeeper.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    private lateinit var mNoteRepository: NoteRepository

    private lateinit var mAddNoteTitleEditText: EditText
    private lateinit var mAddNoteDescriptionEditText: EditText
    private lateinit var mSaveNoteButton: Button
    private lateinit var mNoteLastModificationTimeText: TextView
    private lateinit var mNoteLastModificationTimeValue: TextView
    private lateinit var mNoteCreationTimeText: TextView
    private lateinit var mNoteCreationTimeValue: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val noteDao = NoteDatabase.getDatabase(applicationContext).noteDao()
        mNoteRepository = NoteRepository(noteDao)

        mAddNoteTitleEditText = findViewById<EditText>(R.id.addNoteTitleEditText)
        mAddNoteDescriptionEditText = findViewById<EditText>(R.id.addNoteDescriptionEditText)
        mSaveNoteButton = findViewById<Button>(R.id.saveNoteButton)
        mNoteLastModificationTimeText = findViewById<TextView>(R.id.noteLastModificationTimeText)
        mNoteLastModificationTimeValue = findViewById<TextView>(R.id.noteLastModificationTimeValue)
        mNoteCreationTimeText = findViewById<TextView>(R.id.noteCreationTimeText)
        mNoteCreationTimeValue = findViewById<TextView>(R.id.noteCreationTimeValue)

        if(intent.getStringExtra("sourceActivity").equals("MainActivity")) { // To Save New Note
            mNoteLastModificationTimeText.visibility = View.GONE
            mNoteLastModificationTimeValue.visibility = View.GONE
            mNoteCreationTimeText.visibility = View.GONE
            mNoteCreationTimeValue.visibility = View.GONE
        } else {
            GlobalScope.launch {
                val noteCreationTimestampID = intent.getStringExtra("noteCreationTimestampID")?.let { DateTimeUtils.stringToEpochTime(it) }
                val note = noteCreationTimestampID?.let { mNoteRepository.getNoteByID(it) }
                if (note != null) {
                    if(intent.getStringExtra("sourceActivity").equals("ViewNoteActivity For View Note")) {
                        mNoteLastModificationTimeValue.setText(DateTimeUtils.epochTimeToString(note.noteLastModificationTimestamp))
                        mNoteCreationTimeValue.setText(DateTimeUtils.epochTimeToString(note.noteCreationTimestamp))
                        mAddNoteTitleEditText.setText(note.noteTitle)
                        mAddNoteDescriptionEditText.setText(note.noteDescription)
                        // Disable focus and touch focus for the EditText view
                        mAddNoteTitleEditText.isFocusable = false
                        mAddNoteDescriptionEditText.isFocusable = false
                        mAddNoteTitleEditText.isFocusableInTouchMode = false
                        mAddNoteDescriptionEditText.isFocusableInTouchMode = false
                        mSaveNoteButton.visibility = View.GONE
                    } else {
                        mNoteLastModificationTimeValue.setText(DateTimeUtils.epochTimeToString(note.noteLastModificationTimestamp))
                        mNoteCreationTimeValue.setText(DateTimeUtils.epochTimeToString(note.noteCreationTimestamp))
                        mAddNoteTitleEditText.setText(note.noteTitle)
                        mAddNoteDescriptionEditText.setText(note.noteDescription)
                    }
                }
            }
        }

        mSaveNoteButton.setOnClickListener {
            val noteTitle = mAddNoteTitleEditText.text.toString()

            // Check note title is not empty
            if(noteTitle.equals("")) {
                    Toast.makeText(this, "Note Title can not be empty. Please try again.", Constants.NoteConstants.TOAST_SHORT_DURATION).show()
            } else {
                val noteDescription = mAddNoteDescriptionEditText.text.toString()
                var noteSaved = true

                val noteLastModificationTimestamp = DateTimeUtils.stringToEpochTime(DateTimeUtils.getCurrentDateTime())
                if(intent.getStringExtra("sourceActivity").equals("MainActivity")) { // To Save New Note
                    // First time creation and last modification time will be same
                    val noteCreationTimestamp = noteLastModificationTimestamp
                    val note = Note(noteCreationTimestamp, noteLastModificationTimestamp, noteTitle, noteDescription)
                    // Use coroutines to run database query on a background thread
                    GlobalScope.launch {
                        try {
                            mNoteRepository.insertNote(note)
                        } catch (e: java.lang.Exception) {
                            Log.e("Exception", "An error occurred: ${e.message}")
                            noteSaved = false
                        }
                    }
                } else { // To Save Existing Note
                    val noteCreationTimestampID =
                        intent.getStringExtra("noteCreationTimestampID")
                            ?.let { it1 -> DateTimeUtils.stringToEpochTime(it1) }

                    GlobalScope.launch {
                        try {
                            if (noteCreationTimestampID != null) {
                                mNoteRepository.updateNoteTitleByID(noteCreationTimestampID, noteTitle)
                            }
                            if (noteCreationTimestampID != null) {
                                mNoteRepository.updateNoteDescriptionByID(noteCreationTimestampID, noteDescription)
                            }
                            if (noteCreationTimestampID != null) {
                                mNoteRepository.updateNoteLastModificationTimestampByID(noteCreationTimestampID,
                                    DateTimeUtils.stringToEpochTime(DateTimeUtils.getCurrentDateTime()))
                            }
                        } catch (e: java.lang.Exception) {
                            Log.e("Exception", "An error occurred: ${e.message}")
                            noteSaved = false
                        }
                    }
                }

                // Check and toast if not saved or not
                if(noteSaved) {
                    Toast.makeText(this.applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
                    if(intent.getStringExtra("sourceActivity").equals("MainActivity")) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, ViewNoteActivity::class.java))
                    }
                } else {
                    Toast.makeText(this, "Error saving note. Please try again.", Constants.NoteConstants.TOAST_SHORT_DURATION).show()
                }
            }
        }
    }
}