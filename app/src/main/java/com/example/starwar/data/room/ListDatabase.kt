package com.example.starwar.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwar.data.model.MetadataEntity
import com.example.starwar.data.model.People
import com.example.starwar.data.model.PeopleList
import com.example.starwar.domain.model.PeopleEntity

@Database(
    entities =[PeopleEntity::class],
    version = 3
)
@TypeConverters(TypeConvertor::class)

abstract class ListDatabase:RoomDatabase() {
    abstract fun listDao():ListDao


}