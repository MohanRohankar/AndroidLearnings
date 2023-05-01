package com.example.notekeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.model.repository.IRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel() : ViewModel() {
    private val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()

    fun getNotesLiveData(): LiveData<List<Note>> {
        return notesLiveData
    }

//    val noteRepository = NoteRepository()
//    val repositories = mapOf<String, IRepository>(
//        "Note" to noteRepository
//        // Add other repository interfaces and implementations here
//    )

    // Create a ViewModelFactory with the repositories map
//    val viewModelFactory = ViewModelFactory(repositories)

    // Create a NoteViewModel instance with the ViewModelFactory
//    val noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

//    fun loadNotes() {
//        GlobalScope.launch {
//            val notesList = noteRepository.getAllNotes()
//            notesLiveData.postValue(notesList)
//        }
//    }


}
