package com.ubimubi.noteapp.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ubimubi.noteapp.R
import com.ubimubi.noteapp.models.entity.Note
import com.ubimubi.noteapp.toParcelableNote
import com.ubimubi.noteapp.ui.activities.addnote.AddNoteActivity
import com.ubimubi.noteapp.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = arrayListOf<Note>()

    private var searchNoteList = arrayListOf<Note>()
    private val currentAllNotes = arrayListOf<Note>()

    private val noteViewModel: NoteViewModel by viewModels()

    private lateinit var searchItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setClickListeners()
        setClickListeners()

        noteViewModel.allNotes.observe(this, {
            if (!noteList.containsAll(it)) {
                noteAdapter.submitList(it)
                noteAdapter.notifyDataSetChanged()
                noteList.clear()
                noteList.addAll(it)
                currentAllNotes.clear()
                currentAllNotes.addAll(it)
            }
        })

        initRecyclerView()
        initTopBar()
        initSearching()
    }

    override fun onStart() {
        super.onStart()
        updateNotes()
    }

    override fun onResume() {
        super.onResume()
        updateNotes()
    }

    private fun hideSearchBar() {
        if (search_box.visibility == View.VISIBLE) {
            search_box.visibility = View.GONE
            search_box.setText("")
            searchItem.setIcon(R.drawable.ic_baseline_search_24)
        }
    }

    private fun updateNotes() {
        noteViewModel.getNotes()
    }

    private fun initSearching() {
        search_box.doAfterTextChanged {
            val searchText = it.toString().toLowerCase(Locale.ROOT)

            if (searchText != "") {
                for (note in currentAllNotes) {
                    val noteTitle = note.title!!.toLowerCase(Locale.ROOT)
                    val noteDescription = note.description!!.toLowerCase(Locale.ROOT)

                    if (noteTitle.contains(searchText) || noteDescription.contains(searchText)) {
                        searchNoteList.add(note)
                        Log.d(
                            "note_search",
                            "Search text: $it  Note title: $noteTitle Note description: $noteDescription \n List size: ${searchNoteList.size} "
                        )
                    }
                }
                noteAdapter.submitList(searchNoteList.toList())
            } else noteAdapter.submitList(currentAllNotes)

            noteAdapter.notifyDataSetChanged()
            searchNoteList.clear()
        }
    }

    private fun initTopBar() {
        toolbar.inflateMenu(R.menu.main_menu_toolbar)

        toolbar.setOnMenuItemClickListener {
            this.searchItem = it
            when (it.itemId) {
                R.id.searching_button -> {
                    if (search_box.visibility == View.GONE) {
                        search_box.visibility = View.VISIBLE
                        it.setIcon(R.drawable.ic_baseline_close_24)
                    } else {
                        hideSearchBar()
                    }
                    true
                }
                R.id.settings_button -> {
                    Toast.makeText(this, "This will work in the future :)", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    private fun setClickListeners() {
        button_add.setOnClickListener {
            toAddTodoActivity()
            hideSearchBar()
        }
        initDeleteAndUpdateListeners()
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

    private fun initDeleteAndUpdateListeners() {
        noteAdapter = NoteAdapter({ pos ->
            val tempList = arrayListOf<Note>()
            tempList.addAll(noteAdapter.currentList)
            val deleteNote = tempList[pos]

            tempList.removeAt(pos)

            currentAllNotes.remove(deleteNote)
            noteViewModel.delete(deleteNote)

            noteAdapter.submitList(tempList)
            noteAdapter.notifyDataSetChanged()

        }, { note ->
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("NOTE_EXTRA", note.toParcelableNote())
            startActivityForResult(intent, 2)

            // Hide search bar
            if (search_box.visibility == View.VISIBLE) {
                search_box.visibility = View.GONE
                search_box.setText("")
                searchItem.setIcon(R.drawable.ic_baseline_search_24)
            }
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

    private fun toAddTodoActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivityForResult(intent, 1)
    }

}