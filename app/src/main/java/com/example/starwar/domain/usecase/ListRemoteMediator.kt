package com.example.starwar.domain.usecase

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwar.data.mapper.toPeopleEntity
import com.example.starwar.data.network.ApiService
import com.example.starwar.data.room.ListDatabase
import com.example.starwar.domain.model.PeopleEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ListRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val db:ListDatabase
):RemoteMediator<Int,PeopleEntity>(){

    override suspend fun initialize(): InitializeAction {

        return  InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PeopleEntity>
    ): MediatorResult {


        return try {
            val pageKey=when(loadType){
                LoadType.REFRESH -> {
                    Log.d("debug","refresh")
                    null
                }
                LoadType.PREPEND -> {
                    Log.d("debug","prepend")
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    } else {
                        Log.d("nextKey","${lastItem.nextKey+1} last")
                        lastItem.nextKey
                    }
                }
            }
            val key=pageKey?:1
            val response=apiService.getPeopleList(key)
            Log.d("remotemediator",response.toString())
                val peopleList = response?.results ?: emptyList()

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.listDao()?.deletePeopleList()
                    }


                    val nextKey = response .next?.let { getPageNumberFromUrl(it) }?:0
                    Log.d("nextKey","$nextKey next")
                    Log.d("nextKey","${key+1}")
                    val entities = peopleList.map { it.toPeopleEntity(nextKey) }
                    db.listDao().insertList(entities)

                }

                MediatorResult.Success(endOfPaginationReached = peopleList.isEmpty())

        } catch (e: Exception) {
            Log.d("remotemediator",e.localizedMessage.toString())
            MediatorResult.Error(e)
        }
    }

    private fun getPageNumberFromUrl(url: String): Int? {
        val regex = Regex("page=(\\d+)")
        val matchResult = regex.find(url)

        return matchResult?.groupValues?.getOrNull(1)?.toInt()
    }

}
