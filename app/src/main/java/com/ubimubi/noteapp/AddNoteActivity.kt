package com.ubimubi.noteapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)

        val backButton = findViewById<ImageButton>(R.id.button_back)

        // Set click listeners
        backButton.setOnClickListener { finish() }
    }
}