package com.example.starwar.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.starwar.data.room.ListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAnimeDb(@ApplicationContext context: Context,
    ):ListDatabase=
        Room.databaseBuilder(
            context,
            ListDatabase::class.java,
            "LIST_DATABASE"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideListDao(db:ListDatabase)=db.listDao()
}