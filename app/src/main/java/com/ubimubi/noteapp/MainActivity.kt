package com.ubimubi.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton = findViewById<ImageButton>(R.id.button_add)

        //setClickListeners()
        addButton.setOnClickListener { toAddTodoActivity() }
    }

    private fun toAddTodoActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }
}