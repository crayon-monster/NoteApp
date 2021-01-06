package com.ubimubi.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ubimubi.noteapp.local.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    fun submitList(noteList: List<Note>) {
        this.notes = noteList
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val description: TextView = itemView.findViewById(R.id.textView_description)
        private val date: TextView = itemView.findViewById(R.id.textView_date)

        fun bind(note: Note) {
            title.text = note.title
            description.text = note.description
            date.text = note.date
        }

    }
}