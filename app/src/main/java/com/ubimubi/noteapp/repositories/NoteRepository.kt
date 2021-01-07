package com.ubimubi.noteapp.repositories

import androidx.annotation.WorkerThread
import com.ubimubi.noteapp.local.NoteDao
import com.ubimubi.noteapp.models.entity.Note

class NoteRepository(private val noteDao: NoteDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) = noteDao.insertNote(note)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(note: Note) = noteDao.deleteNote(note)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(note: Note) = noteDao.updateNote(note)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNotes() = noteDao.getAllNotes()

}