package com.ubimubi.noteapp.local

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<Note>

}