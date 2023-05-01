package com.example.notekeeper.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.notekeeper.R
import com.example.notekeeper.model.dao.database.NoteDatabase
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.utils.SharedPreferencesConstants
import com.example.notekeeper.utils.SharedPrefsUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var addNote: Button
    private lateinit var viewNote: Button
    private lateinit var deleteDB: Button

    private lateinit var mNoteRepository: NoteRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        addNote = findViewById<Button>(R.id.addNote)
        viewNote = findViewById<Button>(R.id.viewNote)
        deleteDB = findViewById<Button>(R.id.deleteDB)

        val noteDao = NoteDatabase.getDatabase(applicationContext).noteDao()
        mNoteRepository = NoteRepository(noteDao)

        GlobalScope.launch {
            mNoteRepository.deleteNoteByID(1682967522000)
        }

        addNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("sourceActivity", "MainActivity")
            startActivity(intent)
        }

        viewNote.setOnClickListener {
            startActivity(Intent(this, ViewNoteActivity::class.java))
        }

        deleteDB.setOnClickListener {
            applicationContext.deleteDatabase("note_database")
        }

//        val sharedPrefsUtil = SharedPrefsUtil(applicationContext, SharedPreferencesConstants.PreferenceName.Permissions)
//        sharedPrefsUtil.set(SharedPreferencesConstants.Permissions.LOCATION_PERMISSION_GRANTED, false)
//        var locationPermission = sharedPrefsUtil.get(SharedPreferencesConstants.Permissions.LOCATION_PERMISSION_GRANTED, false)
//        println("locationPermission : $locationPermission")
    }

    override fun onBackPressed() {
        // close the app when the back button is pressed
        finishAffinity()
    }
}