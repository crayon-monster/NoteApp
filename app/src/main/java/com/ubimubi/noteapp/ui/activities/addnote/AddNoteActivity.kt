package com.ubimubi.noteapp.ui.activities.addnote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
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

    private var currentTitle: String = ""
    private var currentDescription: String = ""
    private var currentDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)

        val backButton = findViewById<ImageButton>(R.id.button_back)

        editTextTitle = findViewById(R.id.editText_title)
        editTextDescription = findViewById(R.id.editText_description)
        textViewDate = findViewById(R.id.textView_date)
        textViewDate.text = getDate()

        handleExtraData()

        // Handle Back Button callback
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveData()
                }

            }
        )

        // Set click listeners
        backButton.setOnClickListener {
            saveData()
        }
    }

    private fun handleExtraData() {
        Log.d("CURRENT_TITLE", "before $currentTitle")
        intent.getParcelableExtra<ParcelableNote>("NOTE_EXTRA")?.let {
            editTextTitle.setText(it.title)
            editTextDescription.setText(it.description)
            textViewDate.text = it.date
            this.id = it.id

            currentTitle = it.title
            currentDescription = it.description
            currentDate = it.date

            Log.d("CURRENT_TITLE", "before $currentTitle")
        }
    }

    private fun saveData() {
        val noteTitle = editTextTitle.text.trim().toString()
        val noteDescription = editTextDescription.text.trim().toString()
        val date = getDate()

        val replyIntent = Intent()
        if (TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription)
        ) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            Log.d(
                "CURRENT_TITLE",
                "before $currentTitle $currentDescription , $noteTitle $noteDescription"
            )
            if (currentTitle != noteTitle || currentDescription != noteDescription) {
                replyIntent.putExtra("EXTRA_TITLE", noteTitle)
                replyIntent.putExtra("EXTRA_DESCRIPTION", noteDescription)
                replyIntent.putExtra("EXTRA_DATE", date)
                replyIntent.putExtra("EXTRA_ID", id)
                setResult(Activity.RESULT_OK, replyIntent)
            } else setResult(Activity.RESULT_CANCELED, replyIntent)
        }
        finish()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy HH:mm")
        val currentDate = Calendar.getInstance().time
        return dateFormat.format(currentDate)
    }
}