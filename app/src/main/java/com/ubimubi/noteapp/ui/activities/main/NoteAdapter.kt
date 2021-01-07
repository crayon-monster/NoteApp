package com.ubimubi.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ubimubi.noteapp.models.entity.Note


class NoteAdapter(
    private val deleteListener: (Int) -> Unit,
    private val clickListener: (Note) -> Unit
) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(
            inflater.inflate(R.layout.item_note, parent, false),
            deleteListener,
            clickListener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

class NoteViewHolder(
    itemView: View,
    private val deleteListener: (Int) -> Unit,
    private val clickListener: (Note) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.textView_title)
    private val description: TextView = itemView.findViewById(R.id.textView_description)
    private val date: TextView = itemView.findViewById(R.id.textView_date)

    fun bind(note: Note) {
        title.text = note.title
        description.text = note.description
        date.text = note.date

        if (note.title.toString().isEmpty()) {
            title.visibility = View.GONE
        }

        itemView.findViewById<ImageButton>(R.id.button_delete)
            .setOnClickListener { deleteListener(adapterPosition) }
        itemView.setOnClickListener { clickListener(note) }
    }
}