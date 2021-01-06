package com.ubimubi.noteapp.local

import android.app.Application

class NotesApplication : Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}