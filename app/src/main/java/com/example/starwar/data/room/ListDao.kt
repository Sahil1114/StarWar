package com.example.starwar.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwar.domain.model.PeopleEntity

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list:List<PeopleEntity>)

    @Query("SELECT * FROM PEOPLEENTITY")
     fun pagingSource() :PagingSource<Int,PeopleEntity>

    @Query("DELETE FROM PEOPLEENTITY")
    suspend fun deletePeopleList()

    @Query("SELECT * FROM PEOPLEENTITY WHERE gender = :gender")
    fun pagingSourceByGender(gender: String): PagingSource<Int, PeopleEntity>

    @Query("SELECT * FROM PEOPLEENTITY WHERE gender = 'male'")
    fun pagingSourceMale(): PagingSource<Int, PeopleEntity>

    @Query("SELECT * FROM PEOPLEENTITY WHERE gender = 'female'")
    fun pagingSourceFemale(): PagingSource<Int, PeopleEntity>

    @Query("SELECT * FROM PEOPLEENTITY WHERE gender NOT IN ('male', 'female')")
    fun pagingSourceOther(): PagingSource<Int, PeopleEntity>

    @Query("SELECT * FROM PEOPLEENTITY ORDER BY created DESC")
    fun getPagingSourceByCreated(): PagingSource<Int, PeopleEntity>

    @Query("SELECT * FROM PEOPLEENTITY ORDER BY edited ASC")
    fun getPagingSourceByEdited(): PagingSource<Int, PeopleEntity>


}