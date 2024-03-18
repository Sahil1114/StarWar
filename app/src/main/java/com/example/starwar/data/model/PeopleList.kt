package com.example.starwar.data.model

import androidx.room.Entity

data class PeopleList(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<People>
)