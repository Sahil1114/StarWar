package com.example.starwar.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PeopleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val birth_year: String,
    val created: Long,
    val edited: Long,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
    val films: List<String>,
    val url: String,
    val nextKey:Int
): Parcelable
