package com.ubimubi.noteapp

import com.ubimubi.noteapp.models.entity.Note
import com.ubimubi.noteapp.models.entity.ParcelableNote

fun Note.toParcelableNote(): ParcelableNote {
    return ParcelableNote(this.title ?: "", this.description ?: "", this.date ?: "", this.id)
}