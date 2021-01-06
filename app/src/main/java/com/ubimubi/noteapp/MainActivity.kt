package com.ubimubi.noteapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubimubi.noteapp.databinding.ActivityMainBinding
import com.ubimubi.noteapp.local.Note
import com.ubimubi.noteapp.local.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter

    private val noteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //setClickListeners()
        val buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener { toAddTodoActivity() }

        noteViewModel.allNotes.observe(this) { notes ->
            notes.let { noteAdapter.submitList(notes) }
        }

        initRecyclerView()
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

    private fun initRecyclerView() {
        val recyclerViewNotes = binding.recyclerViewNotes
        recyclerViewNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            noteAdapter = NoteAdapter()
            adapter = noteAdapter
        }
    }

    private fun toAddTodoActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivityForResult(intent, 1)
    }
}