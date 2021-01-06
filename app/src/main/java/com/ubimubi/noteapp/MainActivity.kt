package com.ubimubi.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ubimubi.noteapp.local.Note
import com.ubimubi.noteapp.local.NoteViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setClickListeners()
        setClickListeners()

        noteViewModel.allNotes.observe(this, {
            noteAdapter.submitList(it)
            noteAdapter.notifyDataSetChanged()
        })
        initActivity()


        initDeleteListener()
        initRecyclerView()
        initSwipe()
    }

    private fun initActivity() {

    }

    override fun onStart() {
        super.onStart()
        noteViewModel.getNotes()
    }

    private fun setClickListeners() {
        val buttonAdd = findViewById<ImageButton>(R.id.button_add)
        buttonAdd.setOnClickListener { toAddTodoActivity() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val title: String? = data?.getStringExtra("EXTRA_TITLE")
            val description: String? = data?.getStringExtra("EXTRA_DESCRIPTION")
            val date: String? = data?.getStringExtra("EXTRA_DATE")

            val note = Note(title, description, date)
            noteViewModel.insert(note)
        }
    }

    private fun initDeleteListener() {
        noteAdapter = NoteAdapter { note ->
            val tempList = arrayListOf<Note>()
            tempList.addAll(noteAdapter.notes)
            val pos = tempList.indexOf(note)
            tempList.remove(note)

            noteAdapter.submitList(tempList)
            noteAdapter.notifyItemRemoved(pos)

            noteViewModel.delete(note)
        }
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
                    val note: Note = noteAdapter.notes[pos]

                    val tempList = arrayListOf<Note>()
                    tempList.addAll(noteAdapter.notes)
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