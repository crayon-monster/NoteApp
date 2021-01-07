package com.ubimubi.noteapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubimubi.noteapp.local.NoteDatabase
import com.ubimubi.noteapp.models.entity.Note
import com.ubimubi.noteapp.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository =
        NoteRepository(NoteDatabase.getDatabase(application).noteDao())

    private var _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>>
        get() = _allNotes

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.insert(note) }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.update(note) }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.delete(note) }

    fun getNotes() = viewModelScope.launch(Dispatchers.IO) {
        val data = repository.getNotes()
        withContext(Dispatchers.Main) { _allNotes.value = data }
    }


}
//
//class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return NoteViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}