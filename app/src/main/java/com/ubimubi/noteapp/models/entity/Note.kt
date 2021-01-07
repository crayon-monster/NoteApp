package com.ubimubi.noteapp.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_table")
data class Note(

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    var date: String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}

@Parcelize
data class ParcelableNote(
    var title: String,
    var description: String,
    var date: String,
    var id: Int
) : Parcelable