package com.ubimubi.noteapp.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ubimubi.noteapp.NoteAdapter
import com.ubimubi.noteapp.R
import com.ubimubi.noteapp.models.entity.Note
import com.ubimubi.noteapp.toParcelableNote
import com.ubimubi.noteapp.ui.activities.addnote.AddNoteActivity
import com.ubimubi.noteapp.viewmodels.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setClickListeners()
        setClickListeners()

        noteViewModel.getNotes()
        noteViewModel.allNotes.observe(this, {
            noteAdapter.submitList(it)
            noteAdapter.notifyDataSetChanged()
        })

        initClickListeners()
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        initActivity()
    }

    fun initActivity() {

    }

    private fun setClickListeners() {
        val buttonAdd = findViewById<ImageButton>(R.id.button_add)
        buttonAdd.setOnClickListener { toAddTodoActivity() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val title: String? = data?.getStringExtra("EXTRA_TITLE")
        val description: String? = data?.getStringExtra("EXTRA_DESCRIPTION")
        val date: String? = data?.getStringExtra("EXTRA_DATE")
        val id: Int = data?.getIntExtra("EXTRA_ID", -1) ?: -1

        val note = Note(title, description, date)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            noteViewModel.insert(note)
            noteViewModel.getNotes()
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            note.id = id
            noteViewModel.update(note)
            noteViewModel.getNotes()
        }
        noteAdapter.notifyDataSetChanged()
    }

    private fun initClickListeners() {
        noteAdapter = NoteAdapter({ pos ->
            val tempList = arrayListOf<Note>()
            tempList.addAll(noteAdapter.currentList)
            val deleteNote = tempList[pos]
            tempList.removeAt(pos)

            noteAdapter.submitList(tempList)
            noteAdapter.notifyDataSetChanged()

            noteViewModel.delete(deleteNote)
        }, { note ->
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("NOTE_EXTRA", note.toParcelableNote())
            startActivityForResult(intent, 2)
        })
    }


    private fun initRecyclerView() {
        recyclerViewNotes = findViewById(R.id.recyclerView_notes)
        val mLayoutManager = LinearLayoutManager(this@MainActivity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        recyclerViewNotes.apply {
            layoutManager = mLayoutManager
            adapter = noteAdapter
        }

    }

    private fun initSwipe() {
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder,
                    target: ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    val note: Note = noteAdapter.currentList[pos]

                    val tempList = arrayListOf<Note>()
                    tempList.addAll(noteAdapter.currentList)
                    tempList.removeAt(pos)

                    noteAdapter.submitList(tempList)
                    noteAdapter.notifyItemRemoved(pos)

                    noteViewModel.delete(note)
                }
            })
        mIth.attachToRecyclerView(recyclerViewNotes)

    }

    private fun toAddTodoActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivityForResult(intent, 1)
    }
}