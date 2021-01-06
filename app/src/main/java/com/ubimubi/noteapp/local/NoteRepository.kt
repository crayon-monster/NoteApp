package com.ubimubi.noteapp.local

import androidx.annotation.WorkerThread

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