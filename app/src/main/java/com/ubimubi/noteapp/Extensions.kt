package com.ubimubi.noteapp

import com.ubimubi.noteapp.local.Note
import com.ubimubi.noteapp.local.ParcelableNote

fun Note.toParcelableNote(): ParcelableNote {
    return ParcelableNote(this.title ?: "", this.description ?: "", this.date ?: "", this.id)
}