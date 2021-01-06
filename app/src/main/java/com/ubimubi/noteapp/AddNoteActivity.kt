package com.ubimubi.noteapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)

        val backButton = findViewById<ImageButton>(R.id.button_back)

        editTextTitle = findViewById(R.id.editText_title)
        editTextDescription = findViewById(R.id.editText_description)

        // Set click listeners
        backButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(editTextTitle.text.trim()) || TextUtils.isEmpty(
                editTextDescription.text.trim()
            )
        ) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            replyIntent.putExtra("EXTRA_TITLE", editTextTitle.toString())
            replyIntent.putExtra("EXTRA_DESCRIPTION", editTextDescription.toString())
            replyIntent.putExtra("EXTRA_DATE", getCurrentDate())
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy HH:mm")
        val currentDate = Calendar.getInstance().time
        return dateFormat.format(currentDate)
    }
}