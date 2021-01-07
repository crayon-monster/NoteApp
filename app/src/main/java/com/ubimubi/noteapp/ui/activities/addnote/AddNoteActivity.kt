package com.ubimubi.noteapp.ui.activities.addnote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ubimubi.noteapp.R
import com.ubimubi.noteapp.models.entity.ParcelableNote
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var textViewDate: TextView
    private var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)

        val backButton = findViewById<ImageButton>(R.id.button_back)

        editTextTitle = findViewById(R.id.editText_title)
        editTextDescription = findViewById(R.id.editText_description)
        textViewDate = findViewById(R.id.textView_date)

        textViewDate.text = getCurrentDate()

        // Set click listeners
        backButton.setOnClickListener {
            saveData()
        }

        handleExtraData()
    }

    private fun handleExtraData() {
        intent.getParcelableExtra<ParcelableNote>("NOTE_EXTRA")?.let {
            editTextTitle.setText(it.title)
            editTextDescription.setText(it.description)
            textViewDate.text = it.date
            this.id = it.id
        }
    }

    private fun saveData() {
        val noteTitle = editTextTitle.text.trim()
        val noteDescription = editTextDescription.text.trim()

        val replyIntent = Intent()
        if (TextUtils.isEmpty(noteTitle) and TextUtils.isEmpty(noteDescription)
        ) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            replyIntent.putExtra("EXTRA_TITLE", noteTitle.toString())
            replyIntent.putExtra("EXTRA_DESCRIPTION", noteDescription.toString())
            replyIntent.putExtra("EXTRA_DATE", getCurrentDate())
            replyIntent.putExtra("EXTRA_ID", id)
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