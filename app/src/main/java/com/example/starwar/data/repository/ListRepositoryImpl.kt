package com.example.starwar.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.starwar.common_utils.FilterStatus
import com.example.starwar.data.network.ApiService
import com.example.starwar.data.room.ListDatabase
import com.example.starwar.domain.model.PeopleEntity
import com.example.starwar.domain.repository.ListRepository
import com.example.starwar.domain.usecase.ListRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: ListDatabase
) : ListRepository {

    override fun getPagingData(status: FilterStatus): Flow<PagingData<PeopleEntity>> {
        val pagingSourceFactory: () -> PagingSource<Int, PeopleEntity> = {
            when (status) {
                FilterStatus.MALE -> database.listDao().pagingSourceMale()
                FilterStatus.FEMALE -> database.listDao().pagingSourceFemale()
                FilterStatus.NA -> database.listDao().pagingSourceOther()
                FilterStatus.CREATED -> database.listDao().getPagingSourceByCreated()
                FilterStatus.EDITED -> database.listDao().getPagingSourceByEdited()
                else -> database.listDao().pagingSource()
            }
        }
        return if (status == FilterStatus.ALL) {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 10,
                    maxSize = 300,
                ),
                remoteMediator = ListRemoteMediator(apiService, database)
            ) {
                pagingSourceFactory.invoke()
            }.flow
        } else {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 10,
                    maxSize = 300,
                )
            ) {
                pagingSourceFactory.invoke()
            }.flow
        }
    }
}
