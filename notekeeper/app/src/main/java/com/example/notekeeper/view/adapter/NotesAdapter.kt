package com.example.notekeeper.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.R
import com.example.notekeeper.model.dao.database.NoteDatabase
import com.example.notekeeper.model.dao.model.Note
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.utils.DateTimeUtils
import com.example.notekeeper.view.activities.AddNoteActivity
import com.example.notekeeper.view.activities.ViewNoteActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date

class NotesAdapter(private val context: Context, private var notesList: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private lateinit var mNoteRepository: NoteRepository

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitleText: TextView = itemView.findViewById(R.id.noteTitleText)
        val noteLastModificationTimeText: TextView = itemView.findViewById(R.id.noteLastModificationTimeText)
        val noteDeleteButton: ImageView = itemView.findViewById(R.id.noteDeleteButton)
        val noteViewButton: ImageView = itemView.findViewById(R.id.noteViewbutton)
        val noteEditButton: ImageView = itemView.findViewById(R.id.noteEditButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteTitleText.text = note.noteTitle
        holder.noteLastModificationTimeText.text = DateTimeUtils.epochTimeToString(note.noteLastModificationTimestamp)

        val noteDao = NoteDatabase.getDatabase(context).noteDao()
        mNoteRepository = NoteRepository(noteDao)

        holder.noteDeleteButton.setOnClickListener {
            val note = notesList[position]
            GlobalScope.launch {
                mNoteRepository.deleteNoteByID(note.noteCreationTimestamp)
            }
            // Convert the notesList to a mutable list and remove the note
            val mutableNotesList = notesList.toMutableList()
            mutableNotesList.removeAt(position)
            notesList = mutableNotesList.toList()
            notifyDataSetChanged()
        }

        holder.noteViewButton.setOnClickListener {
            val note = notesList[position]
            val intent = Intent(context, AddNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putString("sourceActivity", "ViewNoteActivity For View Note")
            bundle.putString("noteCreationTimestampID", DateTimeUtils.epochTimeToString(note.noteCreationTimestamp))
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context, intent, Bundle())
        }

        holder.noteEditButton.setOnClickListener {
            val note = notesList[position]
            val intent = Intent(context, AddNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putString("sourceActivity", "ViewNoteActivity")
            bundle.putString("noteCreationTimestampID", DateTimeUtils.epochTimeToString(note.noteCreationTimestamp))
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context, intent, Bundle())
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

}
